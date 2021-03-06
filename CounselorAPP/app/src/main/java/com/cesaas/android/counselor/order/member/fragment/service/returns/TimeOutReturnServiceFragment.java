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
import com.cesaas.android.counselor.order.member.adapter.service.returns.ReturnServiceTimeOutAdapter;
import com.cesaas.android.counselor.order.member.adapter.service.visit.TimeOutMemberServiceVisitAdapter;
import com.cesaas.android.counselor.order.member.bean.service.ResultColseServiceBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultServiceRemarkBean;
import com.cesaas.android.counselor.order.member.bean.service.SendMsgInfoBean;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultSendMsgBean;
import com.cesaas.android.counselor.order.member.bean.service.returns.ResultReturnServiceTimeOutBean;
import com.cesaas.android.counselor.order.member.bean.service.returns.ReturnServiceBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceTimeOutListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.SalesVisitServiceBean;
import com.cesaas.android.counselor.order.member.net.service.ReturnServiceNet;
import com.cesaas.android.counselor.order.member.net.service.VisitListNet;
import com.cesaas.android.counselor.order.member.service.MemberServiceResultActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 超时逾期退换货
 * Created at 2018/2/27 17:33
 * Version 1.0
 */

public class TimeOutReturnServiceFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data;

    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;

    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    private ReturnServiceTimeOutAdapter adapter;
    private List<ReturnServiceBean> mData=new ArrayList<>();
    private ReturnServiceNet net;

    private SendMsgInfoBean infoBean=new SendMsgInfoBean();

    /**
     * 单例
     */
    public static TimeOutReturnServiceFragment newInstance() {
        TimeOutReturnServiceFragment fragmentCommon = new TimeOutReturnServiceFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_timeout_member_service;
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
        net=new ReturnServiceNet(getContext(),3);
        net.setData(pageIndex,40);
    }

    public void onEventMainThread(SendMsgInfoBean msg) {
        if(msg!=null){
            infoBean=new SendMsgInfoBean();
            infoBean=msg;
        }
    }

    public void onEventMainThread(ResultSendMsgBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(getContext(),"发送短信成功!");
            bundle.putInt("Id",infoBean.getId());
            bundle.putString("Name",infoBean.getName());
            bundle.putString("Phone",infoBean.getPhone());
            bundle.putString("sex",infoBean.getSex());
            Skip.mNextFroData(getActivity(), MemberServiceResultActivity.class,bundle,true);
        }else{
            ToastFactory.getLongToast(getContext(),"发送短信失败："+ msg.Message);
        }
    }

    public void onEventMainThread(ResultServiceRemarkBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(getContext(),"添加备注成功!");
            net=new ReturnServiceNet(getContext(),1);
            net.setData(pageIndex,10);
        }else{
            ToastFactory.getLongToast(getContext(),"添加备注失败:"+msg.Message);
        }
    }

    public void onEventMainThread(ResultColseServiceBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(getContext(),"关闭任务成功!");
            net=new ReturnServiceNet(getContext(),1);
            net.setData(pageIndex,10);
        }else{
            ToastFactory.getLongToast(getContext(),"关闭任务失败:"+msg.Message);
        }
    }

    public void onEventMainThread(ResultReturnServiceTimeOutBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_data.setVisibility(View.GONE);
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);
                adapter=new ReturnServiceTimeOutAdapter(mData,getActivity(),getContext());
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
        adapter = new ReturnServiceTimeOutAdapter(mData,getActivity(),getContext());
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                net=new ReturnServiceNet(getContext(),3);
                net.setData(pageIndex,40);
                isErr = false;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }
}
