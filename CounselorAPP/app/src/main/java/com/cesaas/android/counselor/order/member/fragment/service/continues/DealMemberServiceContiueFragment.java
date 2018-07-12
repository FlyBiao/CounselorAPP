package com.cesaas.android.counselor.order.member.fragment.service.continues;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.member.adapter.service.birth.MemberDealServiceBirthAdapter;
import com.cesaas.android.counselor.order.member.adapter.service.continues.MemberDealServiceContiuneAdapter;
import com.cesaas.android.counselor.order.member.bean.service.MemberServiceSearchEventBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultColseServiceBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultServiceRemarkBean;
import com.cesaas.android.counselor.order.member.bean.service.SendMsgInfoBean;
import com.cesaas.android.counselor.order.member.bean.service.birth.BirthdayWishesServiceBean;
import com.cesaas.android.counselor.order.member.bean.service.birth.ResultBirthdayWishesServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.continues.GetSecondListBean;
import com.cesaas.android.counselor.order.member.bean.service.continues.ResultDealGetSecondListBean;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultSendMsgBean;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultSendMsgServiceBean;
import com.cesaas.android.counselor.order.member.net.service.BirthListNet;
import com.cesaas.android.counselor.order.member.net.service.GetSecondListNet;
import com.cesaas.android.counselor.order.member.service.MemberServiceResultActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 待处理会员服务-再次服务
 * Created at 2018/2/27 17:33
 * Version 1.0
 */

public class DealMemberServiceContiueFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data,tv_all_service_sum;

    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;

    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    private MemberDealServiceContiuneAdapter adapter;
    private List<GetSecondListBean> mData=new ArrayList<>();
    private GetSecondListNet net;

    private SendMsgInfoBean infoBean=new SendMsgInfoBean();

    /**
     * 单例
     */
    public static DealMemberServiceContiueFragment newInstance() {
        DealMemberServiceContiueFragment fragmentCommon = new DealMemberServiceContiueFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_deal_member_service;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        mRecyclerView=findView(R.id.rv_list);
        mSwipeRefreshLayout=findView(R.id.swipeLayout);
        tv_not_data=findView(R.id.tv_not_data);
        tv_all_service_sum=findView(R.id.tv_all_service_sum);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        net=new GetSecondListNet(getContext(),1);
        net.setData(pageIndex,10);
    }

    public void onEventMainThread(MemberServiceSearchEventBean msg) {
        if(msg.getType()==10){
            net=new GetSecondListNet(getContext(),1);
            net.setData(pageIndex,10,msg.getSearchValue());
        }
    }

    public void onEventMainThread(SendMsgInfoBean msg) {
        if(msg!=null){
            infoBean=new SendMsgInfoBean();
            infoBean=msg;
        }
    }

    public void onEventMainThread(ResultSendMsgServiceBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(getContext(),"发送短信成功!");
            bundle.putInt("Id",infoBean.getId());
            bundle.putString("Name",infoBean.getName());
            bundle.putString("Phone",infoBean.getPhone());
            bundle.putString("sex",infoBean.getSex());
            bundle.putInt("VipId",infoBean.getId());
            Skip.mNextFroData(getActivity(), MemberServiceResultActivity.class,bundle,true);
        }else{
            ToastFactory.getLongToast(getContext(),"发送短信失败："+ msg.Message);
        }
    }

    public void onEventMainThread(ResultServiceRemarkBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(getContext(),"添加备注成功!");
            net=new GetSecondListNet(getContext(),1);
            net.setData(pageIndex,10);
        }else{
            ToastFactory.getLongToast(getContext(),"添加备注失败:"+msg.Message);
        }
    }

    public void onEventMainThread(ResultColseServiceBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(getContext(),"关闭任务成功!");
            net=new GetSecondListNet(getContext(),1);
            net.setData(pageIndex,10);
        }else{
            ToastFactory.getLongToast(getContext(),"关闭任务失败:"+msg.Message);
        }
    }

    public void onEventMainThread(ResultDealGetSecondListBean msg) {
        if(msg.IsSuccess!=false){
            tv_all_service_sum.setText(msg.RecordCount+"");
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_data.setVisibility(View.GONE);
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);
                adapter=new MemberDealServiceContiuneAdapter(mData,getActivity(),getContext());
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
        adapter = new MemberDealServiceContiuneAdapter(mData,getActivity(),getContext());
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                net=new GetSecondListNet(getContext(),1);
                net.setData(pageIndex,10);
                isErr = false;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }
}
