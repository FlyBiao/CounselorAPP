package com.cesaas.android.counselor.order.member.service;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.user.ITSupportActivity;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.member.adapter.MemberServiceCheckAdapter;
import com.cesaas.android.counselor.order.member.bean.service.ResultServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultServiceRemarkBean;
import com.cesaas.android.counselor.order.member.bean.service.ServiceListBean;
import com.cesaas.android.counselor.order.member.net.service.ServiceListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员服务检查
 */
public class MemberServiceCheckActivity extends BaseTemplateActivity implements SwipeRefreshLayout.OnRefreshListener{

    private TextView tvTitle,tv_not_data;
    private TextView tv_close,tv_ongoing,tv_complete,tv_service_sum;
    private ImageView iv_tow,iv_three;
    private ImageView tvRightTitle;
    private LinearLayout llBack,ll_switch_graphics;
    private RecyclerView rv_member_list;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean isErr;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int index=-1;

    private ArrayList<TextView> tvs=new ArrayList<TextView>();
    private List<ServiceListBean> mData=new ArrayList<>();
    private MemberServiceCheckAdapter adapter;
    private ServiceListNet net;

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_service_check;
    }

    public void onEventMainThread(ResultServiceRemarkBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"添加备注成功！");
            if(index==0){
                net=new ServiceListNet(mContext);
                net.setData(pageIndex,10);
            }else if(index==1){
                net=new ServiceListNet(mContext);
                net.setData(pageIndex,20);
            }else if(index==2){
                net=new ServiceListNet(mContext);
                net.setData(pageIndex,30);
            }else{
                net=new ServiceListNet(mContext);
                net.setData(pageIndex,10);
            }
        }else{
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }

    public void onEventMainThread(ResultServiceListBean msg) {
        if(msg.IsSuccess!=false){
            tv_service_sum.setText(msg.RecordCount+"");
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_data.setVisibility(View.GONE);
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);
                adapter=new MemberServiceCheckAdapter(mData,mActivity);
                rv_member_list.setAdapter(adapter);
            }else{
                tv_not_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }

    @Override
    public void initViews() {
        tv_close=findView(R.id.tv_close);
        ll_switch_graphics=findView(R.id.ll_switch_graphics);
        tv_ongoing=findView(R.id.tv_ongoing);
        tv_complete=findView(R.id.tv_complete);
        tv_service_sum=findView(R.id.tv_service_sum);
        tvs.add(tv_ongoing);
        tvs.add(tv_complete);
        tvs.add(tv_close);
        tvTitle=findView(R.id.tv_base_title);
        tv_not_data=findView(R.id.tv_not_data);
        tvTitle.setText("会员服务检查");
        tvRightTitle= (ImageView) findViewById(R.id.iv_add_module);
        tvRightTitle.setVisibility(View.GONE);
        tvRightTitle.setImageResource(R.mipmap.add_module);
        llBack=findView(R.id.ll_base_title_back);
        iv_tow=findView(R.id.iv_tow);
        iv_three=findView(R.id.iv_three);
        iv_tow.setImageResource(R.mipmap.tow_bule);
        iv_three.setImageResource(R.mipmap.three_bule);

        mSwipeRefreshLayout=findView(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        rv_member_list=findView(R.id.rv_member_list);
        rv_member_list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initListener() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
        tvRightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastFactory.getLongToast(mContext,"select all");
            }
        });

        tv_close.setOnClickListener(this);
        tv_ongoing.setOnClickListener(this);
        tv_complete.setOnClickListener(this);
        ll_switch_graphics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mNext(mActivity,MemberLoyAltyActivity.class);
            }
        });
    }

    @Override
    public void initData() {
        net=new ServiceListNet(mContext);
        net.setData(pageIndex,10);
        rv_member_list.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {

            }
        });
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.tv_ongoing:
                index=0;
                net=new ServiceListNet(mContext);
                net.setData(pageIndex,10);
                break;
            case R.id.tv_complete:
                index=1;
                net=new ServiceListNet(mContext);
                net.setData(pageIndex,20);
                break;
            case R.id.tv_close:
                index=2;
                net=new ServiceListNet(mContext);
                net.setData(pageIndex,30);
                break;
        }
        setColor(index);
    }

    private void setColor(int index) {
        for(int i=0;i<tvs.size();i++){
            tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.purple_pressed));
            tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.white));
        tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_purple_bg));
    }

    @Override
    public void onRefresh() {
        adapter=new MemberServiceCheckAdapter(mData,mActivity);
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                net=new ServiceListNet(mContext);
                net.setData(pageIndex,10);
                isErr = false;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

}
