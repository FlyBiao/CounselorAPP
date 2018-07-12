package com.cesaas.android.counselor.order.member.fragment.service.volume;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.order.bean.RevceiveRouteOrderEventBean;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 优惠券Fragment
 * Created at 2017/8/30 16:32
 * Version 1.0
 */

public class GiftFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_date;

    private View view;
    private static int pageIndex = 1;
    private int delayMillis = 2000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        view = inflater.inflate(R.layout.fragment_sgift_volume, container, false);
        tv_not_date= (TextView) view.findViewById(R.id.tv_not_date);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        return view;
    }

    public void onEventMainThread(RevceiveRouteOrderEventBean msg) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void fetchData() {

    }


    @Override
    public void onRefresh() {
//        adapter=new ReceiveRouteOrderAdapter(orderLis,getActivity(),getContext());
//        adapter.setEnableLoadMore(false);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                unReceiveOrderNet=new UnReceiveOrderNet(getContext(),1);
//                unReceiveOrderNet.setData(pageIndex, SortUtils.setSort());
//                mSwipeRefreshLayout.setRefreshing(false);
//                adapter.setEnableLoadMore(true);
//            }
//        }, delayMillis);
    }

}
