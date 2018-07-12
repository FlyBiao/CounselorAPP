package com.cesaas.android.counselor.order.member.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.base.BaseTabBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.adapter.service.apply.MemberDealInfoAdapter;
import com.cesaas.android.counselor.order.member.bean.service.ResultCloseBean;
import com.cesaas.android.counselor.order.member.bean.service.SearchVipEventBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.MemberApplyListBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyAgreeBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyCompleteListBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyListBean;
import com.cesaas.android.counselor.order.member.net.service.VipDataListNet;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 待处理会员更改信息
 * Created at 2018/2/27 17:33
 * Version 1.0
 */

public class DealMemberInfoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data;
    private TextView tv_sum;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;

    private VipDataListNet net;
    private MemberDealInfoAdapter adapter;
    private List<MemberApplyListBean> mData=new ArrayList<>();

    /**
     * 单例
     */
    public static DealMemberInfoFragment newInstance() {
        DealMemberInfoFragment fragmentCommon = new DealMemberInfoFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_deal_member_info;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        mRecyclerView=findView(R.id.rv_list);
        mSwipeRefreshLayout=findView(R.id.swipeLayout);
        tv_sum=findView(R.id.tv_sum);
        tv_not_data=findView(R.id.tv_not_data);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        net=new VipDataListNet(getContext(),1);
        net.setData(pageIndex,10);
    }

    public void onEventMainThread(SearchVipEventBean msg) {
        if(msg.getType()==10){
            net=new VipDataListNet(getContext(),1);
            net.setData(pageIndex,10,msg.getContent());
        }
    }

    public void onEventMainThread(ResultCloseBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(getContext(),"拒绝成功！");
            initData();
        }else{
            ToastFactory.getLongToast(getContext(),"拒绝失败！"+msg.Message);
        }
    }

    public void onEventMainThread(ResultMemberApplyAgreeBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(getContext(),"已审核通过！");
            initData();
        }else{
            ToastFactory.getLongToast(getContext(),"通过失败！"+msg.Message);
        }
    }

    public void onEventMainThread(ResultMemberApplyListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                totalSize=msg.RecordCount;
                tv_sum.setText(String.valueOf(msg.RecordCount));

                tv_not_data.setVisibility(View.GONE);

                mData=new ArrayList<>();
                mData.addAll(msg.TModel);

                if(isLoadMore==true){
                    adapter.addData(mData);
                    adapter.loadMoreComplete();
                    adapter.notifyDataSetChanged();
                }else{
                    adapter=new MemberDealInfoAdapter(mData,getActivity());
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                adapter.setOnLoadMoreListener(this, mRecyclerView);
                mCurrentCounter = adapter.getData().size();
            }else{
                tv_not_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(getContext(),"Msg:"+msg.Message);
        }
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        adapter = new MemberDealInfoAdapter(mData,getActivity());
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(true);
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
                    net=new VipDataListNet(getContext(),1);
                    net.setData(pageIndex,10);
                } else {
                    //获取更多数据失败
                    isErr = true;
                    adapter.loadMoreFail();
                }
            }
            mSwipeRefreshLayout.setEnabled(true);
        }
    }
}
