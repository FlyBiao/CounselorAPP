package com.cesaas.android.order.route.fragment;

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
import com.cesaas.android.counselor.order.bean.RsultReceivingOrderBean;
import com.cesaas.android.counselor.order.bean.RsultRejectOrderBean;
import com.cesaas.android.counselor.order.bean.SortBean;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.counselor.order.net.ReceivingOrderNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.SortUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.bean.ResultUnReceiveOrderBean;
import com.cesaas.android.order.bean.RevceiveRouteOrderEventBean;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.cesaas.android.order.net.UnReceiveOrderNet;
import com.cesaas.android.order.route.adapter.ReceiveRouteOrderAdapter;
import com.cesaas.android.order.ui.activity.NewOrderActivity;
import com.cesaas.android.order.ui.activity.ReceiveOrderDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 路由接单Fragment
 * Created at 2017/8/30 16:32
 * Version 1.0
 */

public class RouteReceiveOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_date;

    private View view;
    private static int pageIndex = 1;
    private int delayMillis = 2000;
    private int orderId;

    private UnReceiveOrderNet unReceiveOrderNet;
    private List<UnReceiveOrderBean> orderLis=new ArrayList<>();
    private ReceiveRouteOrderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        view = inflater.inflate(R.layout.fragment_order_route, container, false);
        tv_not_date= (TextView) view.findViewById(R.id.tv_not_date);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        return view;
    }

    public void onEventMainThread(RevceiveRouteOrderEventBean msg) {
        orderId=msg.getOrderId();
        if(orderId!=0){
            ReceivingOrderNet net=new ReceivingOrderNet(getContext(),1);
            net.setData(orderId);
        }else{
            ToastFactory.getLongToast(getContext(),"未获取当前订单号！");
        }
    }

    public void onEventMainThread(RsultReceivingOrderBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(getContext(),"接单成功！");
            //跳转到发货页面
            Bundle bundle = new Bundle();
            bundle.putInt("TradeId", orderId);
            bundle.putInt("OrderType", 10);
            Skip.mNextFroData(getActivity(), ReceiveOrderDetailActivity.class, bundle,true);

        }else{
            ToastFactory.getLongToast(getContext(),"接单失败！"+msg.Message);
        }
    }

    public void onEventMainThread(RsultRejectOrderBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(getContext(),"拒绝接单成功！");
            //刷新
            unReceiveOrderNet=new UnReceiveOrderNet(getContext(),1);
            unReceiveOrderNet.setData(pageIndex,SortUtils.setSort());
        }else{
            ToastFactory.getLongToast(getContext(),"拒绝接单失败！"+msg.Message);
        }
    }

    public void onEventMainThread(ResultUnReceiveOrderBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_date.setVisibility(View.GONE);

                orderLis=new ArrayList<>();
                orderLis.addAll(msg.TModel);

                adapter=new ReceiveRouteOrderAdapter(orderLis,getActivity(),getContext());
                mRecyclerView.setAdapter(adapter);

            }else{
                tv_not_date.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_date.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(getContext(),"获取待发货订单失败:"+msg.Message);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unReceiveOrderNet=new UnReceiveOrderNet(getContext(),1);
        unReceiveOrderNet.setData(pageIndex, SortUtils.setSort());

        adapter=new ReceiveRouteOrderAdapter(orderLis,getActivity(),getContext());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void fetchData() {

    }


    @Override
    public void onRefresh() {
        adapter=new ReceiveRouteOrderAdapter(orderLis,getActivity(),getContext());
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                unReceiveOrderNet=new UnReceiveOrderNet(getContext(),1);
                unReceiveOrderNet.setData(pageIndex, SortUtils.setSort());
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

}
