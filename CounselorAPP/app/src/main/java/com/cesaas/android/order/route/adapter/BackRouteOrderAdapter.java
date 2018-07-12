package com.cesaas.android.order.route.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.express.view.ExpressQueryRouteActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.order.bean.BackOrderBean;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.cesaas.android.order.ui.activity.ReceiveOrderDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 已退单路由订单
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class BackRouteOrderAdapter extends BaseQuickAdapter<BackOrderBean, BaseViewHolder> {
    private RecyclerView mRecyclerView;

    private BackOrderItemAdapter adapter;
    private List<BackOrderBean> mData;
    private Activity mActivity;
    private Context mContext;

    public BackRouteOrderAdapter(List<BackOrderBean> mData, Activity activity, Context context) {
        super( R.layout.item_back_route_order,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final BackOrderBean item) {

        helper.setText(R.id.tv_order_no,item.getOrderId()+"");
        helper.setText(R.id.tv_order_date,item.getCreateTime());
        helper.setText(R.id.tv_order_user,item.getConsignee());
        helper.setText(R.id.tv_order_total_amount,"￥"+item.getPayMent());
        helper.setText(R.id.tv_order_count,item.getRetailQuantity()+"");

        switch (item.getOrderStatus()){
            case 1://待接单
                helper.setText(R.id.tv_order_status,"待接单");
                helper.setTextColor(R.id.tv_order_status,mContext.getResources().getColor(R.color.new_base_bg));
                break;
            case 2://待发货
                helper.setText(R.id.tv_order_status,"待发货");
                helper.setTextColor(R.id.tv_order_status,mContext.getResources().getColor(R.color.lightgreen));
                break;
            case 3://已退单
                helper.setText(R.id.tv_order_status,"已退单");
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

        if(item.getRetailFrom()==1){
            helper.setImageResource(R.id.iv_retail_from,R.mipmap.order_offline);
        }else{
            helper.setImageResource(R.id.iv_retail_from,R.mipmap.order_online);
        }

        mRecyclerView=helper.getView(R.id.rv_order_item);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
        adapter=new BackOrderItemAdapter(item.getOrderItem(),mActivity,mContext);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("OrderType",40);
                bundle.putInt("TradeId", item.getOrderId());
                Skip.mNextFroData(mActivity, ReceiveOrderDetailActivity.class, bundle);

            }
        });helper.setOnClickListener(R.id.tv_express_send, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("TradeId", item.getWayBillNo());
                Skip.mNextFroData(mActivity, ExpressQueryRouteActivity.class,bundle);

            }
        });helper.setOnClickListener(R.id.ll_order_details, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("OrderType",40);
                bundle.putInt("TradeId", item.getOrderId());
                Skip.mNextFroData(mActivity, ReceiveOrderDetailActivity.class, bundle);
            }
        });

    }
}
