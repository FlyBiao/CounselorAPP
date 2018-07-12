package com.cesaas.android.counselor.order.member.fragment.service.dormancy;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.member.adapter.service.visit.MemberDealServiceAdapter;
import com.cesaas.android.counselor.order.shoptask.bean.ResultShopTaskListBean;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 更多会员服务--休眠激活
 * Created at 2018/2/27 17:33
 * Version 1.0
 */

public class MoreMemberServiceDormancyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data;

    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;

    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    private MemberDealServiceAdapter adapter;
    private List<String> mData=new ArrayList<>();

    /**
     * 单例
     */
    public static MoreMemberServiceDormancyFragment newInstance() {
        MoreMemberServiceDormancyFragment fragmentCommon = new MoreMemberServiceDormancyFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_more_member_service;
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
        mData.add("More1");
        mData.add("More2");
        mData.add("More3");
//        adapter=new MemberDealServiceAdapter(mData,getActivity(),getContext());
//        mRecyclerView.setAdapter(adapter);
    }

    public void onEventMainThread(ResultShopTaskListBean msg) {
//        if(msg.IsSuccess!=false){
//            if(msg.TModel!=null && msg.TModel.size()!=0){
//                taskList=new ArrayList<>();
//                taskList.addAll(msg.TModel);
//                adapter=new TaskListAdapter(taskList);
//                mRecyclerView.setAdapter(adapter);
//            }else{
//                tv_not_data.setVisibility(View.VISIBLE);
//            }
//        }else{
//            tv_not_data.setVisibility(View.VISIBLE);
//            ToastFactory.getLongToast(getContext(),"Msg:"+msg.Message);
//        }
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onRefresh() {
//        adapter = new MemberDealServiceAdapter(mData,getActivity(),getContext());
//        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isErr = false;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }
}
