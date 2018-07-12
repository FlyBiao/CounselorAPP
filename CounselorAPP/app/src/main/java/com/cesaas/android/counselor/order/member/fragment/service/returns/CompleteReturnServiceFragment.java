package com.cesaas.android.counselor.order.member.fragment.service.returns;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.member.adapter.service.returns.ReturnServiceCompleteAdapter;
import com.cesaas.android.counselor.order.member.adapter.service.visit.CompleteMemberServiceVisitAdapter;
import com.cesaas.android.counselor.order.member.bean.service.MemberServiceSearchEventBean;
import com.cesaas.android.counselor.order.member.bean.service.returns.ResultReturnServiceCompleteBean;
import com.cesaas.android.counselor.order.member.bean.service.returns.ResultReturnServiceDealBean;
import com.cesaas.android.counselor.order.member.bean.service.returns.ReturnServiceBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceCompleteListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.SalesVisitServiceBean;
import com.cesaas.android.counselor.order.member.net.service.ReturnServiceNet;
import com.cesaas.android.counselor.order.member.net.service.VisitListNet;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 已完成退换货
 * Created at 2018/2/27 17:33
 * Version 1.0
 */

public class CompleteReturnServiceFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data,tv_all_service_sum;

    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;

    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    private ReturnServiceCompleteAdapter adapter;
    private List<ReturnServiceBean> mData=new ArrayList<>();
    private ReturnServiceNet net;

    /**
     * 单例
     */
    public static CompleteReturnServiceFragment newInstance() {
        CompleteReturnServiceFragment fragmentCommon = new CompleteReturnServiceFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_complete_member_service;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        mRecyclerView=findView(R.id.rv_list);
        mSwipeRefreshLayout=findView(R.id.swipeLayout);
        tv_all_service_sum=findView(R.id.tv_all_service_sum);
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
        net=new ReturnServiceNet(getContext(),2);
        net.setData(pageIndex,20);
    }

    public void onEventMainThread(MemberServiceSearchEventBean msg) {
        if(msg.getType()==20){
            net=new ReturnServiceNet(getContext(),2);
            net.setData(pageIndex,20,msg.getSearchValue());
        }
    }

    public void onEventMainThread(ResultReturnServiceCompleteBean msg) {
        if(msg.IsSuccess!=false){
            tv_all_service_sum.setText(msg.RecordCount+"");
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_data.setVisibility(View.GONE);
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);
                adapter=new ReturnServiceCompleteAdapter(mData,getActivity(),getContext());
                mRecyclerView.setAdapter(adapter);
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
        adapter = new ReturnServiceCompleteAdapter(mData,getActivity(),getContext());
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                net=new ReturnServiceNet(getContext(),2);
                net.setData(pageIndex,20);
                isErr = false;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }
}
