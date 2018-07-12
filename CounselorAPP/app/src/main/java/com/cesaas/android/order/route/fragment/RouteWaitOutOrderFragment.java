package com.cesaas.android.order.route.fragment;

import android.content.Intent;
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
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.SortBean;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.SortUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.bean.ResultWaitOutOrderBean;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.cesaas.android.order.net.WaitOutOrderListNet;
import com.cesaas.android.order.route.adapter.WaitOutRouteOrderAdapter;
import com.cesaas.android.order.ui.activity.ReceiveOrderDetailActivity;
import com.cesaas.android.order.ui.fragment.WaitOutNewOrderStateView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.json.JSONArray;

import java.util.ArrayList;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 路由单-待发货Fragment
 * Created at 2017/8/30 16:32
 * Version 1.0
 */

public class RouteWaitOutOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_date;

    private View view;
    private int pageIndex=1;
    private int delayMillis = 2000;
    private int mCurrentCounter = 0;
    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    private WaitOutRouteOrderAdapter adapter;
    private ArrayList<UnReceiveOrderBean> orderLis= new ArrayList<>();
    private WaitOutOrderListNet net;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        view = inflater.inflate(R.layout.fragment_waitout_order_route, container, false);
        tv_not_date= (TextView) view.findViewById(R.id.tv_not_date);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        return view;
    }

    public void onEventMainThread(ResultWaitOutOrderBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_date.setVisibility(View.GONE);

                orderLis=new ArrayList<>();
                orderLis.addAll(msg.TModel);

                adapter=new WaitOutRouteOrderAdapter(orderLis,getActivity(),getContext());
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

        net=new WaitOutOrderListNet(getContext(),1);
        net.setData(pageIndex,2, SortUtils.setSort());

        adapter=new WaitOutRouteOrderAdapter(orderLis,getActivity(),getContext());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void fetchData() {

    }


    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                net=new WaitOutOrderListNet(getContext(),1);
                net.setData(pageIndex,2, SortUtils.setSort());
                isErr = false;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {//退单
            if (data != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        net=new WaitOutOrderListNet(getContext(),1);
                        net.setData(pageIndex,2, SortUtils.setSort());
                    }
                });
            }
        }
    }

}
