package com.cesaas.android.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.order.bean.ReceiveOrderDetailsBean;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/7/24 19:54
 * Version 1.0
 */

public class ReceiveOrderDetailAdapter extends BaseAdapter {

    private List<ReceiveOrderDetailsBean.OrderItemDetail> list;
    private Context ct;

    private TextView tvTitle,tvCode,tvSize,tv_sum;

    public ReceiveOrderDetailAdapter(List<ReceiveOrderDetailsBean.OrderItemDetail> list,Context ct){
        this.list=list;
        this.ct=ct;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(ct).inflate(R.layout.item_receive_order_details, parent, false);

        initView(position,convertView);

        return convertView;
    }

    private void initView(int position, View convertView) {
        tvTitle= (TextView) convertView.findViewById(R.id.tv_title);
        tvCode= (TextView) convertView.findViewById(R.id.tv_code);
        tvSize= (TextView) convertView.findViewById(R.id.tv_size);
        tv_sum= (TextView) convertView.findViewById(R.id.tv_sum);

        tvTitle.setText(list.get(position).getBarcodeId()+"");
        tvCode.setText(list.get(position).getBarcodeNo());
        tv_sum.setText("x"+list.get(position).getQuantity());
        tvSize.setText(list.get(position).getSkuValue1()+" "+list.get(position).getSkuValue2());

    }
}
