package com.cesaas.android.counselor.order.custom.tag.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tag.OnInitSelectedPosition;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanHailong on 15/10/19.
 */
public class TagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

    private TextView textView;
    private View view;

    private final Context mContext;
    private final List<GetTagListBean> mDataList;
    public List<Integer> exisTags=new ArrayList<>();

    public String tagName;

    public TagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
        exisTags=new ArrayList<>();
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

        view= LayoutInflater.from(mContext).inflate(R.layout.tag_item, null);
        textView= (TextView) view.findViewById(R.id.tv_tag);
        if(exisTags.size()!=0){
            if(exisTags.indexOf(mDataList.get(position).getTagId())!=-1){
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.round_rectangle_w_bg));
            }else{
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.round_rectangle_bg));
            }
        }

        tagName= mDataList.get(position).getTagName();
        textView.setText(tagName);

        return view;
    }

    public void onlyAddAll(List<GetTagListBean> datas,List<Integer> tags) {
        exisTags.addAll(tags);
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }
    public void onlyAddAll(List<GetTagListBean> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<GetTagListBean> datas,List<Integer> tags) {
        mDataList.clear();
        onlyAddAll(datas,tags);
    }

    public void clearAndAddAll(List<GetTagListBean> datas) {
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
