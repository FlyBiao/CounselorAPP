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
import com.cesaas.android.counselor.order.bean.SortBean;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.counselor.order.utils.SortUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.bean.ResultWaitInOrderBean;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.cesaas.android.order.net.WaitOutOrderListNet;
import com.cesaas.android.order.route.adapter.WaitInOutRouteOrderAdapter;

import org.json.JSONArray;

import java.util.ArrayList;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 已发货订单路由
 * Created at 2017/7/24 10:16
 * Version 1.0
 */

public class RouteWaitInOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_date;

    private View view;
    private static int pageIndex = 1;
    private int delayMillis = 2000;

    private WaitInOutRouteOrderAdapter adapter;
    private ArrayList<UnReceiveOrderBean> orderLis= new ArrayList<>();
    private WaitOutOrderListNet net;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        view = inflater.inflate(R.layout.fragment_order_route_send, container, false);
        tv_not_date= (TextView) view.findViewById(R.id.tv_not_date);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        return view;
    }

    public void onEventMainThread(ResultWaitInOrderBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_date.setVisibility(View.GONE);

                orderLis=new ArrayList<>();
                orderLis.addAll(msg.TModel);

                adapter=new WaitInOutRouteOrderAdapter(orderLis,getActivity(),getContext());
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
        net=new WaitOutOrderListNet(getContext(),2);
        net.setData(pageIndex,4, SortUtils.setSort());
        adapter=new WaitInOutRouteOrderAdapter(orderLis,getActivity(),getContext());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void fetchData() {

    }


    @Override
    public void onRefresh() {
        adapter=new WaitInOutRouteOrderAdapter(orderLis,getActivity(),getContext());
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                net=new WaitOutOrderListNet(getContext(),2);
                net.setData(pageIndex,4, SortUtils.setSort());
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }
}

