package com.cesaas.android.counselor.order.member.adapter.service.tag;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guohao on 2017/9/6.
 */

public class TagSelectAdapter extends RecyclerView.Adapter<TagSelectAdapter.ViewHolder> {

    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private int secret = 0;
    private String title = "";
    private Context context;
    private Activity activity;
    private List<GetTagListBean> mMyLiveList;
    private List<Integer> exisTags;
    private OnItemClickListener mOnItemClickListener;

    public TagSelectAdapter(Context context,Activity activity) {
        this.context = context;
        this.activity=activity;
    }

    public void notifyAdapter(List<GetTagListBean> myLiveList,List<Integer> exisTags, boolean isAdd) {
        if (!isAdd) {
            this.mMyLiveList = myLiveList;
            this.exisTags=exisTags;
        } else {
            this.mMyLiveList.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<GetTagListBean> getMyLiveList() {
        if (mMyLiveList == null) {
            mMyLiveList = new ArrayList<>();
        }
        return mMyLiveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_tag_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mMyLiveList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GetTagListBean myLive = mMyLiveList.get(holder.getAdapterPosition());
            holder.mTvTitle.setText(myLive.getTagName());
            if(mMyLiveList.get(position).isSelect()==true){
                holder.mTvTitle.setBackgroundColor(context.getResources().getColor(R.color.new_base_bg));
                holder.mTvTitle.setTextColor(context.getResources().getColor(R.color.white));
            }else{
                holder.mTvTitle.setBackground(context.getResources().getDrawable(R.drawable.round_rectangle_bg));
                holder.mTvTitle.setTextColor(context.getResources().getColor(R.color.defalult_text_color));
            }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), mMyLiveList);
                if (myLive.isSelect() && mMyLiveList.get(position).isSelect()==true) {//已选中
                    holder.mTvTitle.setBackground(context.getResources().getDrawable(R.drawable.round_rectangle_bg));
                    holder.mTvTitle.setTextColor(context.getResources().getColor(R.color.defalult_text_color));

                }else if(myLive.isSelect()){//选中
                    holder.mTvTitle.setBackgroundColor(context.getResources().getColor(R.color.new_base_bg));
                    holder.mTvTitle.setTextColor(context.getResources().getColor(R.color.white));
                }
                else {//未选中
                    holder.mTvTitle.setBackground(context.getResources().getDrawable(R.drawable.round_rectangle_bg));
                    holder.mTvTitle.setTextColor(context.getResources().getColor(R.color.defalult_text_color));

                }
                notifyDataSetChanged();
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClickListener(int pos, List<GetTagListBean> myLiveList);
    }
    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_tag)
        TextView mTvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

}
