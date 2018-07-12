package com.cesaas.android.counselor.order.label.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.flowlayout.OnTagSelectListener;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.tag.adapter.TagAdapter;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.label.bean.CategoryTagBean;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;

import com.cesaas.android.counselor.order.listener.OnItemClickListener;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2017/5/14 14:29
 * Version 1.0
 */

public class GetCategoryAdapter extends SwipeMenuAdapter<GetCategoryAdapter.DefaultViewHolder> {

    private List<CategoryTagBean> categoryListBeen;
    private List<GetTagListBean> selectedTagList=new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private Context ct;

    public static TagAdapter<GetTagListBean> mBaseTagAdapter;
    public static FlowTagLayout tag_flow_layout;

    public GetCategoryAdapter(List<CategoryTagBean> categoryListBeen,Context ct){
        this.categoryListBeen=categoryListBeen;
        this.ct=ct;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, final int position) {
        holder.setData(categoryListBeen.get(position).CategoryName);
        holder.setOnItemClickListener(mOnItemClickListener);

        mBaseTagAdapter = new TagAdapter<>(ct);
        tag_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        tag_flow_layout.setAdapter(mBaseTagAdapter);
        mBaseTagAdapter.onlyAddAll(categoryListBeen.get(position).getTagList);

        tag_flow_layout.setOnTagSelectListener(new com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
//                    selectedTagList=new ArrayList<>();
                    for (int i : selectedList) {
                        GetTagListBean tagBean=new GetTagListBean();
                        tagBean.setCategoryId(categoryListBeen.get(position).getTagList.get(i).getCategoryId());
                        tagBean.setCategoryName(categoryListBeen.get(position).getCategoryName());
                        tagBean.setTagId(categoryListBeen.get(position).getTagList.get(i).getTagId());
                        tagBean.setTagName(categoryListBeen.get(position).getTagList.get(i).getTagName());
                        selectedTagList.add(tagBean);
                    }
                    EventBus.getDefault().post(selectedTagList);
                } else {
//                    Toast.makeText(ct,"没有选择标签！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryListBeen == null ? 0 : categoryListBeen.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_tag_category_name;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_tag_category_name = (TextView) itemView.findViewById(R.id.tv_tag_category_name);
            tag_flow_layout= (FlowTagLayout) itemView.findViewById(R.id.tag_flow_layout);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title) {
            this.tv_tag_category_name.setText(title);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
