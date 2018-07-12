package com.cesaas.android.order.route;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.net.service.RejectNet;
import com.cesaas.android.counselor.order.net.ReceivingOrderNet;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.cesaas.android.order.net.RejectOrderNet;
import com.cesaas.android.order.route.bean.ReceiveOrderSection;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 路由接单Adapter
 * Created at 2017/8/30 15:35
 * Version 1.0
 */

public class RouteReceiveOrderAdapter extends BaseSectionQuickAdapter<ReceiveOrderSection,BaseViewHolder>{

    private int type;
    private Context ct;

    public RouteReceiveOrderAdapter(int layoutResId, int sectionHeadResId, List<ReceiveOrderSection> data,int type,Context ct) {
        super(layoutResId, sectionHeadResId, data);
        this.type=type;
        this.ct=ct;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final ReceiveOrderSection item) {
        helper.setText(R.id.tv_order_create_date,"下单时间:"+item.getCreateTime());
        helper.setText(R.id.tv_receive_user, item.getConsignee());
        helper.setText(R.id.tv_receive_user, item.getConsignee());
        helper.setText(R.id.tv_order_no, "订单号:"+item.getOrderId());
        switch (type){
            case 1:
                helper.setText(R.id.tv_order_status,"待接单");
                helper.setTextColor(R.id.tv_order_status,ct.getResources().getColor(R.color.new_base_bg));
                break;
            case 2:
                helper.setVisible(R.id.tv_order_status,true);
                helper.setText(R.id.tv_order_status,"已发货");
                helper.setTextColor(R.id.tv_order_status,ct.getResources().getColor(R.color.base_logistics));
                break;
        }
    }

    @Override
    protected void convert(BaseViewHolder helper,ReceiveOrderSection item) {
        UnReceiveOrderBean.OrderItem orderItem=item.t;

        helper.setText(R.id.tv_order_name,orderItem.getSkuValue1());
        helper.setText(R.id.tv_order_attr,orderItem.getSkuValue2());
        helper.setText(R.id.tv_type_code,orderItem.getBarcodeNo());
        helper.setText(R.id.tv_quantity,"X"+orderItem.getQuantity());
        helper.setText(R.id.tv_order_price,"￥"+orderItem.getPayMent());
        helper.setImageResource(R.id.iv_img,R.drawable.no_picture);

        //        helper.setOnClickListener(R.id.tv_send_orders, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ReceivingOrderNet net=new ReceivingOrderNet(mContext);
//                net.setData(item.getOrderId());
//            }
//        });helper.setOnClickListener(R.id.tv_not_huo, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RejectOrderNet net=new RejectOrderNet(mContext);
//                net.setData(item.getOrderId());
//
//            }
//        });

    }
}
