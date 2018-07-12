package com.cesaas.android.counselor.order.member;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseActivity;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.bean.ResultFans;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.adapter.MemberDistributionAdapter;
import com.cesaas.android.counselor.order.member.adapter.MemberDistributionDetailAdapter;
import com.cesaas.android.counselor.order.member.adapter.TabLayoutViewPagerAdapter;
import com.cesaas.android.counselor.order.member.bean.MBaseBack;
import com.cesaas.android.counselor.order.member.bean.MemberDistributionBean;
import com.cesaas.android.counselor.order.member.bean.MemberDistributionDetailBean;
import com.cesaas.android.counselor.order.member.bean.ResultCancelBinVipBean;
import com.cesaas.android.counselor.order.member.bean.ResultMemberDistributionDetailBean;
import com.cesaas.android.counselor.order.member.net.GetBindVipNet;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailsActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * 已分配会员列表详情
 */
public class MemberDistributionDetailActivity extends BaseTemplateActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_distribution_detail;
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private LinearLayout llBaseBack,ll_search;
    private EditText et_search_content;
    private TextView tvBaseTitle;

    private int pageIndex=1;
    private int CounselorId;
    private GetBindVipNet mGetBindVipNet;

    private List<MemberDistributionDetailBean> mDistributionDetailBeanList;
    private MemberDistributionDetailAdapter mAdapter;

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
            if(msg.TModel !=null){
                mDistributionDetailBeanList=new ArrayList<>();
                mDistributionDetailBeanList.addAll(msg.TModel);

                mAdapter=new MemberDistributionDetailAdapter(mDistributionDetailBeanList,mContext);
                mAdapter.setOnItemClickListener(onItemClickListener);
                mSwipeMenuRecyclerView.setAdapter(mAdapter);
            }

        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败!"+msg.Message);
        }
    }

    @Override
    public void initViews() {

        Bundle bundle=getIntent().getExtras();
        CounselorId=bundle.getInt("CounselorID");

        mGetBindVipNet=new GetBindVipNet(mContext);
        mGetBindVipNet.setData(pageIndex,CounselorId);

        ll_search= (LinearLayout) findViewById(R.id.ll_search);
        et_search_content= (EditText) findViewById(R.id.et_search_content);
        et_search_content.setHint("根据手机号，名称搜索");
        mSwipeRefreshLayout=findView(R.id.swipe_layout);
        mSwipeMenuRecyclerView=findView(R.id.recycler_distribution_member_detail_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
//        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        llBaseBack=findView(R.id.ll_base_title_back);
        tvBaseTitle=findView(R.id.tv_base_title);
        tvBaseTitle.setText("维护会员");
    }

    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSwipeMenuRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //重新加载数据
                    mGetBindVipNet=new GetBindVipNet(mContext);
                    mGetBindVipNet.setData(pageIndex,CounselorId);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };


    @Override
    public void initListener() {
        llBaseBack.setOnClickListener(this);
        ll_search.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            //跳转会员详情
            Bundle bundle=new Bundle();
            bundle.putInt("Id",mDistributionDetailBeanList.get(position).getVipId());//暂时处理
            bundle.putInt("VipId",mDistributionDetailBeanList.get(position).getVipId());
            bundle.putString("Phone",mDistributionDetailBeanList.get(position).getMobile());
            bundle.putString("Date",mDistributionDetailBeanList.get(position).getBirthday());
            bundle.putString("Desc",mDistributionDetailBeanList.get(position).getNickName());
            bundle.putString("Remark",mDistributionDetailBeanList.get(position).getNickName());
            bundle.putInt("Status",20);
            if(mDistributionDetailBeanList.get(position).getVipName()!=null && !"".equals(mDistributionDetailBeanList.get(position).getVipName())){
                bundle.putString("Name",mDistributionDetailBeanList.get(position).getVipName());
                bundle.putString("Title",mDistributionDetailBeanList.get(position).getVipName());
            }else{
                bundle.putString("Name",mDistributionDetailBeanList.get(position).getNickName());
                bundle.putString("Title",mDistributionDetailBeanList.get(position).getNickName());
            }
            Skip.mNextFroData(mActivity,MemberReturnVisitDetailsActivity.class,bundle);
        }
    };

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MBaseBack back=new MBaseBack();
        back.setBack(true);
        EventBus.getDefault().post(back);
        return super.onKeyDown(keyCode, event);
    }
}
