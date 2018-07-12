package com.cesaas.android.counselor.order.report.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.report.bean.ResultGetCategoryListBean;

import java.util.List;

/**
 * Author FGB
 * Description 报表分类Adapter
 * Created 2017/3/27 9:34
 * Version 1.zero
 */
public class ReportCategorAdapter extends BaseAdapter {

    private List<ResultGetCategoryListBean.GetCategoryListBean> categoryListBeanList;
    private Context mContext;
    private TextView categorName;

    public ReportCategorAdapter(Context mContext,List<ResultGetCategoryListBean.GetCategoryListBean> categoryListBeanList){
        this.mContext=mContext;
        this.categoryListBeanList=categoryListBeanList;
    }

    @Override
    public int getCount() {
        return categoryListBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryListBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_report_center_categor, null);
        categorName= (TextView) convertView.findViewById(R.id.tv_report_categor_name);
        categorName.setText(categoryListBeanList.get(position).Title);

        return convertView;
    }
}
