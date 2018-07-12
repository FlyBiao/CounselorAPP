package com.cesaas.android.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultExpressBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/7/25 15:18
 * Version 1.0
 */

public class OrderExpressAdapter extends BaseAdapter {

    private Context ct;
    private List<ResultExpressBean.ExpressBean> expressList;

    public OrderExpressAdapter(List<ResultExpressBean.ExpressBean> expressList,Context ct){
        this.expressList=expressList;
        this.ct=ct;
    }

    @Override
    public int getCount() {
        return expressList.size();
    }

    @Override
    public Object getItem(int position) {
        return expressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(ct).inflate(R.layout.item_express_list, null);

        TextView tv_name=(TextView) convertView.findViewById(R.id.tv_name);
        RadioButton selectBtn=(RadioButton) convertView.findViewById(R.id.radio);
        selectBtn.setVisibility(View.GONE);

        tv_name.setText(expressList.get(position).Name);

        return convertView;
    }
}
