package com.cesaas.android.order.route.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.CheckCargoOrderRouteActivity;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.member.FocusEventBusBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailsActivity;
import com.cesaas.android.counselor.order.member.util.BottonDialogUtils;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.cesaas.android.order.ui.activity.AddOrderRemarkActivity;
import com.cesaas.android.order.ui.activity.ReceiveOrderDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 待发货路由订单
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class WaitOutRouteOrderAdapter extends BaseQuickAdapter<UnReceiveOrderBean, BaseViewHolder> {
    private RecyclerView mRecyclerView;

    private WaitOutRouteOrderItemAdapter adapter;
    private List<UnReceiveOrderBean> mData;
    private Activity mActivity;
    private Context mContext;

    public WaitOutRouteOrderAdapter(List<UnReceiveOrderBean> mData, Activity activity, Context context) {
        super( R.layout.item_waitout_route_order,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final UnReceiveOrderBean item) {

        helper.setText(R.id.tv_order_no,item.getOrderId()+"");
        helper.setText(R.id.tv_order_date,item.getCreateTime());
        helper.setText(R.id.tv_order_user,item.getConsignee());
        helper.setText(R.id.tv_order_total_amount,"￥"+item.getPayMent());
        helper.setText(R.id.tv_order_count,item.getRetailQuantity()+"");

        if(item.getRetailFrom()==1){//只能接单发货
            helper.setVisible(R.id.tv_back_order,false);
            helper.setImageResource(R.id.iv_retail_from,R.mipmap.order_offline);
        }else{
            helper.setVisible(R.id.tv_back_order,true);
            helper.setImageResource(R.id.iv_retail_from,R.mipmap.order_online);
        }

        switch (item.getOrderStatus()){
            case 1://待接单
                helper.setText(R.id.tv_order_status,"待接单");
                helper.setTextColor(R.id.tv_order_status,mContext.getResources().getColor(R.color.new_base_bg));
                break;
            case 2://待发货
                helper.setText(R.id.tv_order_status,"待发货");
                helper.setTextColor(R.id.tv_order_status,mContext.getResources().getColor(R.color.lightgreen));
                break;
            case 4://已发货
                helper.setText(R.id.tv_order_status,"已发货");
                helper.setTextColor(R.id.tv_order_status,mContext.getResources().getColor(R.color.green_pressed));
                break;
            default:
                helper.setText(R.id.tv_order_status,"");
                break;
        }
        mRecyclerView=helper.getView(R.id.rv_order_item);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
        adapter=new WaitOutRouteOrderItemAdapter(item.getOrderItem(),mActivity,mContext);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("TradeId", item.getOrderId());
                bundle.putInt("OrderType", 10);
                Skip.mNextFroData(mActivity, ReceiveOrderDetailActivity.class, bundle);
            }
        });

        helper.setOnClickListener(R.id.tv_express_send, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("TradeId", item.getOrderId()+"");
                Skip.mNextFroData(mActivity, CheckCargoOrderRouteActivity.class, bundle);
            }
        });helper.setOnClickListener(R.id.tv_back_order, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddOrderRemarkActivity.class);
                intent.putExtra("OrderId",item.getOrderId()+"");
                intent.putExtra("OrderType","1000");
                mActivity.startActivityForResult(intent, Constant.ORDER_BACK);
            }
        });helper.setOnClickListener(R.id.ll_order_details, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("TradeId", item.getOrderId());
                bundle.putInt("OrderType", 10);
                Skip.mNextFroData(mActivity, ReceiveOrderDetailActivity.class, bundle);
            }
        });

    }
}
