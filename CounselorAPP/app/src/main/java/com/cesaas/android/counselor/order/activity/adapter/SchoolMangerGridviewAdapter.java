package com.cesaas.android.counselor.order.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.widget.gridview.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 商学院管理 Gridview
 * Created 2017/4/11 19:55
 * Version 1.zero
 */
public class SchoolMangerGridviewAdapter extends BaseAdapter {
    private Context mContext;

    private TextView tv;
    private TextView iv;
    private LinearLayout ll_grids;

    private List<String> menu;
    private List<Integer> imgs;

    public SchoolMangerGridviewAdapter(Context mContext, List<String> menuName, List<Integer> imgs) {
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
                    R.layout.grid_item_school_manager, parent, false);
        }
        tv = BaseViewHolder.get(convertView, R.id.tv_item);
        iv = BaseViewHolder.get(convertView, R.id.iv_item);
        ll_grids=BaseViewHolder.get(convertView, R.id.ll_grids);

        ll_grids.setVisibility(View.VISIBLE);
        iv.setText(imgs.get(position));
        iv.setTypeface(App.font);
        tv.setText(menu.get(position));

        return convertView;
    }

}
