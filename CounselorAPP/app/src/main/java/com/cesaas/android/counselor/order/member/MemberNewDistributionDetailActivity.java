package com.cesaas.android.counselor.order.member;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.adapter.MemberDistributionDetailAdapter;
import com.cesaas.android.counselor.order.member.adapter.MemberNewDistributionDetailAdapter;
import com.cesaas.android.counselor.order.member.adapter.member.AllMemberAdapter;
import com.cesaas.android.counselor.order.member.bean.MBaseBack;
import com.cesaas.android.counselor.order.member.bean.MemberDistributionDetailBean;
import com.cesaas.android.counselor.order.member.bean.ResultCancelBinVipBean;
import com.cesaas.android.counselor.order.member.bean.ResultMemberDistributionDetailBean;
import com.cesaas.android.counselor.order.member.net.GetBindVipNet;
import com.cesaas.android.counselor.order.member.net.service.MemberServiceListNet;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailsActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * 已分配会员列表详情(new)
 */
public class MemberNewDistributionDetailActivity extends BaseTemplateActivity implements BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_new_distribution_detail;
    }

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data;
    private LinearLayout llBaseBack,ll_search;
    private EditText et_search_content;
    private TextView tvBaseTitle;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;

    private int CounselorId;

    private GetBindVipNet mGetBindVipNet;
    private List<MemberDistributionDetailBean> mData;
    private MemberNewDistributionDetailAdapter adapter;

    /**
     * 接受ResultCancelBinVipBean发送消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultCancelBinVipBean msg){
        if(msg.IsSuccess==true){
            ToastFactory.getLongToast(mContext,"解绑会员成功！");
            mGetBindVipNet.setData(pageIndex,CounselorId);

        }else{
            ToastFactory.getLongToast(mContext,"解绑会员失败！"+msg.Message);
        }
    }

    /**
     * 接受发送消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultMemberDistributionDetailBean msg){
        if(msg.IsSuccess==true){
            if(msg.TModel !=null && msg.TModel.size()!=0){
                tv_not_data.setVisibility(View.GONE);
                totalSize=msg.RecordCount;
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);

                if(isLoadMore==true){
                    adapter.addData(mData);
                    adapter.loadMoreComplete();
                    adapter.notifyDataSetChanged();
                }else{
                    adapter=new MemberNewDistributionDetailAdapter(mData,mActivity,mContext);
                    mRecyclerView.setAdapter(adapter);
                }
                adapter.setOnLoadMoreListener(this, mRecyclerView);
                mCurrentCounter = adapter.getData().size();

                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        //跳转会员详情
                        Bundle bundle=new Bundle();
                        bundle.putInt("Id",0);//暂时处理
                        bundle.putInt("VipId",mData.get(position).getVipId());
                        bundle.putString("Phone",mData.get(position).getMobile());
                        bundle.putString("Date",mData.get(position).getBirthday());
                        bundle.putString("Desc",mData.get(position).getNickName());
                        bundle.putString("Remark",mData.get(position).getNickName());
                        bundle.putInt("Status",20);
                        if(mData.get(position).getVipName()!=null && !"".equals(mData.get(position).getVipName())){
                            bundle.putString("Name",mData.get(position).getVipName());
                            bundle.putString("Title",mData.get(position).getVipName());
                        }else{
                            bundle.putString("Name",mData.get(position).getNickName());
                            bundle.putString("Title",mData.get(position).getNickName());
                        }
                        Skip.mNextFroData(mActivity,MemberReturnVisitDetailsActivity.class,bundle);
                    }
                });
            }else{
                tv_not_data.setVisibility(View.VISIBLE);
            }

        }else{
            tv_not_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(mContext,"获取数据失败!"+msg.Message);
        }
    }

    @Override
    public void initViews() {
        ll_search= (LinearLayout) findViewById(R.id.ll_search);
        et_search_content= (EditText) findViewById(R.id.et_search_content);
        et_search_content.setHint("根据手机号，名称搜索");
        et_search_content.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mGetBindVipNet=new GetBindVipNet(mContext);
                    mGetBindVipNet.setData(pageIndex,CounselorId,et_search_content.getText().toString());
                }
                return false;
            }
        });

        llBaseBack=findView(R.id.ll_base_title_back);
        tvBaseTitle=findView(R.id.tv_base_title);
        tvBaseTitle.setText("维护会员");

        mRecyclerView=findView(R.id.rv_list);
        mSwipeRefreshLayout=findView(R.id.swipeLayout);
        tv_not_data=findView(R.id.tv_not_data);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void initListener() {
        llBaseBack.setOnClickListener(this);
        ll_search.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Bundle bundle=getIntent().getExtras();
        CounselorId=bundle.getInt("CounselorID");

        mGetBindVipNet=new GetBindVipNet(mContext);
        mGetBindVipNet.setData(pageIndex,CounselorId);
    }

    @Override
    public void processClick(View v) {

        switch (v.getId()){
            case R.id.ll_base_title_back:
                MBaseBack back=new MBaseBack();
                back.setBack(true);
                EventBus.getDefault().post(back);
                Skip.mBack(mActivity);
            break;
            case R.id.ll_search:
                if(!TextUtils.isEmpty(et_search_content.getText().toString())){
                    mGetBindVipNet=new GetBindVipNet(mContext);
                    mGetBindVipNet.setData(pageIndex,CounselorId,et_search_content.getText().toString());
                }else{
                    mGetBindVipNet=new GetBindVipNet(mContext);
                    mGetBindVipNet.setData(pageIndex,CounselorId);
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        adapter = new MemberNewDistributionDetailAdapter(mData,mActivity,mContext);
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                mGetBindVipNet=new GetBindVipNet(mContext);
                mGetBindVipNet.setData(pageIndex,CounselorId);
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        if (adapter.getData().size() < Constant.PAGE_SIZE) {
            adapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= totalSize) {
                //数据全部加载完毕
                adapter.loadMoreEnd(mLoadMoreEndGone);
            } else {
                if (isErr) {
                    //成功获取更多数据
                    isLoadMore=true;
                    pageIndex+=1;
                    mGetBindVipNet=new GetBindVipNet(mContext);
                    mGetBindVipNet.setData(pageIndex,CounselorId);
                } else {
                    //获取更多数据失败
                    isErr = true;
                    adapter.loadMoreFail();
                }
            }
            mSwipeRefreshLayout.setEnabled(true);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MBaseBack back=new MBaseBack();
        back.setBack(true);
        EventBus.getDefault().post(back);
        return super.onKeyDown(keyCode, event);
    }

}
