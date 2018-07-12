package com.cesaas.android.counselor.order.boss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.bean.ShopListBean;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/8/20 17:48
 * Version 1.0
 */

public class AreaListAdapter extends BaseAdapter {

    private List<ShopListBean> shopListBeen;
    private Context ct;

    public AreaListAdapter(List<ShopListBean> shopListBeen,Context ct){
        this.shopListBeen=shopListBeen;
        this.ct=ct;
    }

    @Override
    public int getCount() {
        return shopListBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return shopListBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(ct).inflate(R.layout.item_diaolg, null);
        TextView title= (TextView) convertView.findViewById(R.id.tv_dialog_title);
        title.setText(shopListBeen.get(position).getAreaName());
        return convertView;
    }
}
