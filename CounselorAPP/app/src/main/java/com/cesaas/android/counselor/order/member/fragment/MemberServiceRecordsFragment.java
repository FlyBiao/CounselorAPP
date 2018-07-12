package com.cesaas.android.counselor.order.member.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.member.adapter.MemberServiceRecordAdapter;
import com.cesaas.android.counselor.order.member.bean.service.MemberServiceResultBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultMemberServiceResultBean;
import com.cesaas.android.counselor.order.member.net.service.MemberServiceResultNet;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailActivity;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailsActivity;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员标签
 * Created at 2018/2/27 17:33
 * Version 1.record
 */

public class MemberServiceRecordsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView rv_member_service_record_list;
    private TextView tv_not_data;

    private List<MemberServiceResultBean> mData=new ArrayList<>();
    private MemberServiceRecordAdapter adapter;
    private MemberServiceResultNet net;

    /**
     * 单例
     */
//    public static MemberServiceRecordFragment newInstance() {
//        MemberServiceRecordFragment fragmentCommon = new MemberServiceRecordFragment();
//        return fragmentCommon;
//    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_member_service_record;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        tv_not_data=findView(R.id.tv_not_data);
        rv_member_service_record_list=findView(R.id.rv_member_service_record_list);
        rv_member_service_record_list.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        net=new MemberServiceResultNet(getContext());
        net.setData(MemberReturnVisitDetailActivity.getVipId());

    }


    public void onEventMainThread(ResultMemberServiceResultBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                try{
                    tv_not_data.setVisibility(View.GONE);
                    mData=new ArrayList<>();
                    mData.addAll(msg.TModel);
                    adapter=new MemberServiceRecordAdapter(mData);
                    rv_member_service_record_list.setAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
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

    }
}
