package com.cesaas.android.counselor.order.shopmange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tag.OnInitSelectedPosition;
import com.cesaas.android.counselor.order.shopmange.bean.ClerkSearchRecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/6/15 17:50
 * Version 1.0
 */

public class SearchRecordTagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<ClerkSearchRecordBean> mDataList;

    public SearchRecordTagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_item, null);

        TextView textView = (TextView) view.findViewById(R.id.tv_tag);
        String t = mDataList.get(position).getName();
        if(t!=null){
            if (t instanceof String) {
                textView.setText((String) t);
            }
        }

        return view;
    }

    public void onlyAddAll(List<ClerkSearchRecordBean> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<ClerkSearchRecordBean> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    public int position=-1;
    public void setSelected(int p) {
        position=p;
    }

    @Override
    public boolean isSelectedPosition(int poi) {
        if (poi == position) {
            return true;
        }
        return false;
    }
}
