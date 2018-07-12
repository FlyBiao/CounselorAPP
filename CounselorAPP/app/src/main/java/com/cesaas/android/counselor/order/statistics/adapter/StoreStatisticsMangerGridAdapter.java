package com.cesaas.android.counselor.order.statistics.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.widget.gridview.BaseViewHolder;

import java.util.List;

/**
 * 统计管理GridAdapter
 * Created by cebsaas on 2017/2/21.
 */

public class StoreStatisticsMangerGridAdapter extends BaseAdapter {
    private Context mContext;

    private TextView tv;
    private TextView title;
    private LinearLayout ll_grids;

    private List<String> menu;
    private List<String> imgs;

    public StoreStatisticsMangerGridAdapter(Context mContext, List<String> menuName, List<String> imgs) {
        super();
        this.mContext = mContext;
        this.menu=menuName;
        this.imgs=imgs;
    }

    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_item_store_statics, parent, false);
        }
        tv = BaseViewHolder.get(convertView, R.id.tv_item);
        title = BaseViewHolder.get(convertView, R.id.iv_item_title);
        ll_grids=BaseViewHolder.get(convertView, R.id.ll_grids);

        ll_grids.setVisibility(View.VISIBLE);
//        iv.setBackgroundResource(imgs.get(position));
        title.setText(imgs.get(position));
        tv.setText(menu.get(position));

        return convertView;
    }
}
