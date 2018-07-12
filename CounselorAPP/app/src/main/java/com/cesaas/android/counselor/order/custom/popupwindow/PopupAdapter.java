package com.cesaas.android.counselor.order.custom.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.user.bean.RegisterShopNameBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 弹窗类表适配器类
 * Created at 2017/5/4 16:36
 * Version 1.0
 */

public class PopupAdapter extends BaseAdapter {
    private Context myContext;
    private LayoutInflater inflater;
    private List<RegisterShopNameBean> myItems;
    private int myType;

    public PopupAdapter(Context context, List<RegisterShopNameBean> items, int type) {
        this.myContext = context;
        this.myItems = items;
        this.myType = type;

        inflater = LayoutInflater.from(myContext);

    }

    @Override
    public int getCount() {
        return myItems.size();
    }

    @Override
    public String getItem(int position) {
        if(myItems.get(position).getShopCity()!=null && !"".equals(myItems.get(position).getShopCity())){
            return myItems.get(position).getShopCity()+"-"+myItems.get(position).getName();
        }else{
            return myItems.get(position).getName();
        }
}

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PopupHolder holder = null;
        if (convertView == null) {
            holder = new PopupHolder();
            convertView = inflater.inflate(R.layout.top_popup_item, null);
            holder.itemNameTv = (TextView) convertView
                    .findViewById(R.id.popup_tv);
            if (myType == 0) {
                holder.itemNameTv.setGravity(Gravity.CENTER);
            } else if (myType == 1) {
                holder.itemNameTv.setGravity(Gravity.LEFT);
            } else if (myType == 2) {
                holder.itemNameTv.setGravity(Gravity.RIGHT);
            }
            convertView.setTag(holder);
        } else {
            holder = (PopupHolder) convertView.getTag();
        }
        String itemName = getItem(position);
        holder.itemNameTv.setText(itemName);
        return convertView;
    }

    private class PopupHolder {
        TextView itemNameTv;
    }
}
