package com.cesaas.android.counselor.order.member.fragment.service.volume;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.ui.activity.QueryShopActivity;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.counselor.order.member.adapter.volume.CouponsAdapter;
import com.cesaas.android.counselor.order.member.adapter.volume.SingleChoiceCouponsRecyAdapter;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.volume.ResultPushBean;
import com.cesaas.android.counselor.order.member.bean.service.volume.ResultTicketListBean;
import com.cesaas.android.counselor.order.member.bean.service.volume.TicketListBean;
import com.cesaas.android.counselor.order.member.bean.service.volume.TicketVipsBean;
import com.cesaas.android.counselor.order.member.net.service.PushNet;
import com.cesaas.android.counselor.order.member.net.service.TicketListNet;
import com.cesaas.android.counselor.order.member.volume.PushListActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.bean.RevceiveRouteOrderEventBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 优惠券Fragment
 * Created at 2017/8/30 16:32
 * Version 1.0
 */

public class CouponsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_date,tv_volume_number,tv_send,tv_cancel;

    private View view;
    private int delayMillis = 2000;
    private boolean isSelectAll = false;
    private int index = 0;

    private PushNet pushNet;
    private TicketListNet net;
//    private CouponsAdapter adapter;
    private SingleChoiceCouponsRecyAdapter adapter;
    private List<TicketListBean> mData=new ArrayList<>();
    private List<MemberServiceListBean> memberServiceList;

    private int TicketId;
    private JSONArray vipArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        view = inflater.inflate(R.layout.fragment_send_volume, container, false);
        tv_not_date= (TextView) view.findViewById(R.id.tv_not_date);
        tv_volume_number= (TextView) view.findViewById(R.id.tv_volume_number);
        tv_cancel= (TextView) view.findViewById(R.id.tv_cancel);
        tv_send= (TextView) view.findViewById(R.id.tv_send);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mData.size(); i++) {
                    mData.get(i).setChecked(false);
                    mData.get(i).setSelect(false);
                }
                TicketId=0;
                adapter.notifyDataSetChanged();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TicketId!=0){
                    vipArray=new JSONArray();
                    for (int i=0;i<memberServiceList.size();i++){
                        TicketVipsBean t=new TicketVipsBean();
                        t.setMobile(memberServiceList.get(i).getMobile());
                        t.setVipId(memberServiceList.get(i).getVipId());
                        t.setBirthday(memberServiceList.get(i).getBirthday());
                        t.setGradeId(memberServiceList.get(i).getGradeId());
                        t.setVipName(memberServiceList.get(i).getName());
                        t.setMemberId(memberServiceList.get(i).getMemberId());
                        vipArray.put(t.getTicketVips());
                    }
                    pushNet=new PushNet(getContext());
                    pushNet.setData(TicketId,vipArray);
                }else{
                    ToastFactory.getLongToast(getContext(),"请选择推送券！");
                }
            }
        });
        return view;
    }

    public void onEventMainThread(ResultPushBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                Bundle bundle=new Bundle();
                bundle.putSerializable("PushList", (Serializable) msg.TModel);
                Skip.mNextFroData(getActivity(),PushListActivity.class,bundle);
            }else{
                ToastFactory.getLongToast(getContext(),"推送成功-推送券状态列表");
            }
        }else{
            ToastFactory.getLongToast(getContext(),"推送券失败："+msg.Message);
        }
    }

    public void onEventMainThread(ResultTicketListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_volume_number.setText(String.valueOf(msg.TModel.size()));
                tv_not_date.setVisibility(View.GONE);
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);

                adapter=new SingleChoiceCouponsRecyAdapter(mData);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new SingleChoiceCouponsRecyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        for (int i = 0; i < mData.size(); i++) {
                            mData.get(i).setChecked(false);
                            mData.get(i).setSelect(false);
                        }
                        mData.get(position).setChecked(true);
                        mData.get(position).setSelect(true);
                        TicketId=mData.get(position).getTICKET_ID();
                        adapter.notifyDataSetChanged();
                    }
                });
            }else{
                tv_not_date.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_date.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(getContext(),"获取推送券失败："+msg.Message);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        net=new TicketListNet(getContext());
        net.setData();

        Bundle bundle=getActivity().getIntent().getExtras();
        if(bundle!=null){
            memberServiceList= (List<MemberServiceListBean>) bundle.getSerializable("MemberList");
        }
    }

    @Override
    public void fetchData() {

    }


    @Override
    public void onRefresh() {
        adapter=new SingleChoiceCouponsRecyAdapter(mData);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                net=new TicketListNet(getContext());
                net.setData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, delayMillis);
    }
}
