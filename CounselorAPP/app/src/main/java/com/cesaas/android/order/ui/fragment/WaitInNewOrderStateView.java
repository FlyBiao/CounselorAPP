package com.cesaas.android.order.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.OrderDetailActivity;
import com.cesaas.android.counselor.order.activity.order.bean.ResultWaitInCounselorOrderBean;
import com.cesaas.android.counselor.order.activity.order.net.WaitInCounselorOrderNet;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailsActivity;
import com.cesaas.android.counselor.order.net.VipOrderListNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.view.WaitInOrderStateView;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.cesaas.android.order.adapter.NewOrderStateViewAdapter;
import com.cesaas.android.order.bean.ResultUnReceiveOrderBean;
import com.cesaas.android.order.bean.ResultWaitOutOrderBean;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.cesaas.android.order.route.RouteReceiveOrderAdapter;
import com.cesaas.android.order.route.bean.ReceiveOrderSection;
import com.cesaas.android.order.route.fragment.RouteReceiveOrderFragment;
import com.cesaas.android.order.ui.activity.ReceiveOrderDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/7/24 10:16
 * Version 1.0
 */

public class WaitInNewOrderStateView extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_date;

    private View view;
    private static int pageIndex = 1;
    private int delayMillis = 1000;

    private UnReceiveOrderNet unReceiveOrderNet;
    private List<ReceiveOrderSection> mData=new ArrayList<>();
    private RouteReceiveOrderAdapter sectionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_route_send, container, false);
        tv_not_date= (TextView) view.findViewById(R.id.tv_not_date);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unReceiveOrderNet=new UnReceiveOrderNet(getContext());
        unReceiveOrderNet.setData(pageIndex,4);
    }

    @Override
    public void fetchData() {

    }


    @Override
    public void onRefresh() {
        sectionAdapter=new RouteReceiveOrderAdapter(R.layout.item_route_send_order_details, R.layout.item_route_send_order, mData,2,getContext());
        sectionAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                unReceiveOrderNet=new UnReceiveOrderNet(getContext());
                unReceiveOrderNet.setData(pageIndex,4);
                mSwipeRefreshLayout.setRefreshing(false);
                sectionAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    /**
     * Author FGB
     * Description 待接单
     * Created at 2017/7/24 17:00
     * Version 1.0
     */

    public class UnReceiveOrderNet extends BaseNet {

        public UnReceiveOrderNet(Context context) {
            super(context, true);
            this.uri="OrderRoute/sw/order/OrderList";
//            this.uri="OrderRoute/sw/order/UnReceiveOrder";
        }

        public void setData(int page,int OrderStatus){
            try {
                data.put("OrderStatus", OrderStatus);
                data.put("PageIndex", page);
                data.put("PageSize", 50);
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mPostNet(); // 开始请求网络
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            Log.i("test","待接单："+rJson);
            ResultUnReceiveOrderBean msg= JsonUtils.fromJson(rJson,ResultUnReceiveOrderBean.class);
            if(msg.IsSuccess!=false){
                if (msg != null && msg.TModel.size()!=0) {
                    tv_not_date.setVisibility(View.GONE);
                    mData=new ArrayList<>();
                    for(int i=0;i<msg.TModel.size();i++){
                        for (int j=0;j<msg.TModel.get(i).getOrderItem().size();j++){
                            mData.add(new ReceiveOrderSection(true, "Section", false,msg.TModel.get(i).getCreateName(),msg.TModel.get(i).getCreateTime(),msg.TModel.get(i).getSyncCode(),msg.TModel.get(i).getOrderId(),msg.TModel.get(i).getConsignee()));
                            mData.add(new ReceiveOrderSection(new UnReceiveOrderBean.OrderItem(msg.TModel.get(i).getOrderItem().get(j).getSkuValue1(),msg.TModel.get(i).getOrderItem().get(j).getSkuValue2(),msg.TModel.get(i).getOrderItem().get(j).getBarcodeNo(),msg.TModel.get(i).getOrderItem().get(j).getPayMent(),msg.TModel.get(i).getOrderItem().get(j).getQuantity(),msg.TModel.get(i).getOrderItem().get(j).getOrderId())));
                        }
                    }
                    sectionAdapter= new RouteReceiveOrderAdapter(R.layout.item_route_send_order_details, R.layout.item_route_send_order, mData,2,getContext());
                    sectionAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                    sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            ReceiveOrderSection mySection = mData.get(position);
                            if (mySection.isHeader){
                                Bundle bundle = new Bundle();
                                bundle.putInt("OrderType",30);
                                bundle.putInt("TradeId", mySection.getOrderId());
                                Skip.mNextFroData(getActivity(), ReceiveOrderDetailActivity.class, bundle);
                            }else{
                                Bundle bundle = new Bundle();
                                bundle.putInt("OrderType",30);
                                bundle.putInt("TradeId", mySection.t.getOrderId());
                                Skip.mNextFroData(getActivity(), ReceiveOrderDetailActivity.class, bundle);
                            }
                        }
                    });
                    mRecyclerView.setAdapter(sectionAdapter);
                }else{
                    tv_not_date.setVisibility(View.VISIBLE);
                }
            }else{
                tv_not_date.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);

        }
    }
}

