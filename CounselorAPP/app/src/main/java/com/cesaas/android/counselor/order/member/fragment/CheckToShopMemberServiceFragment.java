package com.cesaas.android.counselor.order.member.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.member.adapter.service.check.MemberCheckDealServiceAdapter;
import com.cesaas.android.counselor.order.member.adapter.service.check.MemberCheckGoShopServiceAdapter;
import com.cesaas.android.counselor.order.member.bean.service.check.CheckServiceDetailsBean;
import com.cesaas.android.counselor.order.member.bean.service.check.ResultCheckGoShopServiceDetailsBean;
import com.cesaas.android.counselor.order.member.net.service.CheckServiceDetailNet;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceCheckDetailActivity;
import com.cesaas.android.counselor.order.shoptask.bean.ResultShopTaskListBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 检查到店会员服务
 * Created at 2018/2/27 17:33
 * Version 1.0
 */

public class CheckToShopMemberServiceFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data;

    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;

    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    private MemberCheckGoShopServiceAdapter adapter;
    private List<CheckServiceDetailsBean> mData=new ArrayList<>();
    private CheckServiceDetailNet net;

    /**
     * 单例
     */
    public static CheckToShopMemberServiceFragment newInstance() {
        CheckToShopMemberServiceFragment fragmentCommon = new CheckToShopMemberServiceFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_check_toshop_member_service;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        mRecyclerView=findView(R.id.rv_list);
        mSwipeRefreshLayout=findView(R.id.swipeLayout);
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
        net=new CheckServiceDetailNet(getContext(),3);
        net.setData(MemberServiceCheckDetailActivity.getId(),40);
    }

    public void onEventMainThread(ResultCheckGoShopServiceDetailsBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);

                adapter=new MemberCheckGoShopServiceAdapter(mData);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                        Bundle bundle=new Bundle();
                        bundle.putInt("Id",mData.get(position).getId());
                        bundle.putInt("VipId",mData.get(position).getVipId());
                        bundle.putString("Name",mData.get(position).getName());
                        bundle.putString("Date",mData.get(position).getDate());
                        bundle.putString("Desc",mData.get(position).getRemark());
                        bundle.putString("Remark",mData.get(position).getRemark());
                        bundle.putString("Title",MemberServiceCheckDetailActivity.getTitles());
                        bundle.putInt("Status",mData.get(position).getStatus());
                        Skip.mNextFroData(getActivity(),MemberReturnVisitDetailActivity.class,bundle);
                    }
                });
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
        adapter = new MemberCheckGoShopServiceAdapter(mData);
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                net=new CheckServiceDetailNet(getContext(),3);
                net.setData(MemberServiceCheckDetailActivity.getId(),40);
                isErr = false;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }
}
