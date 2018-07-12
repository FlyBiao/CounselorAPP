package com.cesaas.android.order.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.ShopDetailActivity;
import com.cesaas.android.counselor.order.adapter.OrderAdapter;
import com.cesaas.android.counselor.order.adapter.OrderThingsAdapter;
import com.cesaas.android.counselor.order.bean.ResultGetO2OOrderListBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.fragment.ReceiveOrderFragment;
import com.cesaas.android.counselor.order.net.OrderBackNet;
import com.cesaas.android.counselor.order.net.ReceivingFonfirmOrderNet;
import com.cesaas.android.counselor.order.net.ReceivingOrderNet;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.cesaas.android.order.net.OrderBackNetTest;
import com.cesaas.android.order.net.RejectOrderNet;
import com.cesaas.android.order.ui.fragment.ReceiveNewOrderFragment;
import com.haozhang.lib.SlantedTextView;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Author FGB
 * Description
 * Created at 2017/7/24 10:41
 * Version 1.0
 */

public class ReceiveNewOrderAdapter extends BaseAdapter {

    private Context ct;
    public double sum = 0;

    private List<UnReceiveOrderBean> data = new ArrayList<>();
    public static BitmapUtils bitmapUtils;

    private RejectOrderNet rejectOrderNet;// 标识无货
    private ReceivingOrderNet fonfirmOrderNet;// 标识有货

    private AbPrefsUtil abpUtil;
    private Activity activity;
    private TextView tv_order_create_date;
    private TextView tv_order_number;
    private TextView btn_rob_order;
    private TextView tv_not_huo;
    private ImageView tv_huihua;
    private TextView tv_order_expressType;
    private TextView tv_receive_user;
    private TextView tv_send_orders;
    private SlantedTextView slv_right_tri_express;
    private SlantedTextView slv_right_tri_self_lift;

    private LinearLayout ll_un_receive_order;

    private TextView tvCheck;

    private ListView lv;
    private List<UnReceiveOrderBean.OrderItem> list = new ArrayList<>();

    public ReceiveNewOrderAdapter(Context ct, Activity activity) {
        this.ct = ct;
        this.activity = activity;
    }

    public ReceiveNewOrderAdapter(Context ct, List<UnReceiveOrderBean> data) {
        this.ct = ct;
        this.data = data;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<UnReceiveOrderBean> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    public void remove(ResultGetO2OOrderListBean.GetO2OOrderListBean order) {
        this.data.remove(order);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(ct).inflate(R.layout.item_receive_order, parent, false);
        tv_order_create_date = (TextView) convertView.findViewById(R.id.tv_order_create_date);

        tv_order_number = (TextView) convertView.findViewById(R.id.tv_order_no);
        tv_receive_user = (TextView) convertView.findViewById(R.id.tv_receive_user);
        tv_not_huo = (TextView) convertView.findViewById(R.id.tv_not_huo);
        tv_huihua = (ImageView) convertView.findViewById(R.id.tv_huihua);
        tv_order_expressType = (TextView) convertView.findViewById(R.id.tv_express);
        lv = (ListView) convertView.findViewById(R.id.list_order_things);
        tv_send_orders=(TextView)convertView.findViewById(R.id.tv_send_orders);
        ll_un_receive_order=(LinearLayout) convertView.findViewById(R.id.ll_un_receive_order);
        slv_right_tri_express= (SlantedTextView) convertView.findViewById(R.id.slv_right_tri_express);
        slv_right_tri_self_lift= (SlantedTextView) convertView.findViewById(R.id.slv_right_tri_self_lift);

        final UnReceiveOrderBean bean = data.get(position);

        tv_order_create_date.setText("下单时间:" + bean.getCreateTime());
        tv_order_number.setText("订单号:" + bean.getSyncCode());
        tv_receive_user.setText(bean.getConsignee());

        list = new ArrayList<>();
        for (int i = 0; i < bean.OrderItem.size(); i++) {

            UnReceiveOrderBean.OrderItem itemBean = new UnReceiveOrderBean.OrderItem();
            itemBean=bean.OrderItem.get(i);
            list.add(itemBean);
        }

        ReceiveOrderThingsAdapter adapter = new ReceiveOrderThingsAdapter(ct, list);

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, lv);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight + (lv.getDividerHeight() * (adapter.getCount() - 1));
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        lv.setLayoutParams(params);

        lv.setAdapter(adapter);

        /**
         * 查看订单详情
         */
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
//
//                Bundle bundle = new Bundle();
//                bundle.putString("Title", list.get(i).getTitle());
//                bundle.putString("ImageUrl", list.get(i).getImageUrl());
//                bundle.putString("StyleCode", list.get(i).getStyleCode());
//                bundle.putString("BarcodeCode", list.get(i).getBarcodeCode());
//                bundle.putDouble("Price", list.get(i).getPrice());
//                bundle.putString("Attr", list.get(i).getAttr());
//
//                Skip.mNextFroData(activity, ShopDetailActivity.class, bundle);
//            }
//        });

        /**
         * 接单
         */
        tv_send_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fonfirmOrderNet=new ReceivingOrderNet(ct);
                fonfirmOrderNet.setData(bean.getOrderId());

//                new Handler().postDelayed(new Runnable() {
////
//                        @Override
//                        public void run() {
//                            ReceiveOrderFragment.handler.obtainMessage().sendToTarget();
//                        }
//                    }, 1000);
            }
        });

        /**
         * 标识无货
         */
        tv_not_huo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                rejectOrderNet = new RejectOrderNet(ct);
                rejectOrderNet.setData(bean.getOrderId());
//
//                    ReceiveNewOrderAdapter.this.data.clear();
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            ReceiveOrderFragment.handler.obtainMessage().sendToTarget();
//
//                        }
//                    }, 1000);
            }
        });

        return convertView;
    }

}
