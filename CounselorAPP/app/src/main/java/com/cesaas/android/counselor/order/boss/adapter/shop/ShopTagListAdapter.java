package com.cesaas.android.counselor.order.boss.adapter.shop;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.GroupTagBean;
import com.cesaas.android.counselor.order.boss.bean.ShopListBean;
import com.cesaas.android.counselor.order.boss.bean.ShopTagListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author FGB
 * Description
 * Created at 2018/4/21 11:58
 * Version 1.0
 */

public class ShopTagListAdapter extends RecyclerView.Adapter<ShopTagListAdapter.ViewHolder> {

    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private int shopType;
    private int secret = 0;
    private String title = "";
    private Context context;
    private Activity activity;
    private List<GroupTagBean> mMyLiveList=new ArrayList<>();
    private OnItemClickListenerTag mOnItemClickListener;

    public ShopTagListAdapter(Context context, Activity activity, int shopTyps) {
        this.context = context;
        this.activity=activity;
        this.shopType=shopTyps;
    }

    public void notifyAdapter(List<GroupTagBean> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mMyLiveList = myLiveList;
        } else {
            this.mMyLiveList.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<GroupTagBean> getMyLiveList() {
        if (mMyLiveList == null) {
            mMyLiveList = new ArrayList<>();
        }
        return mMyLiveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_tag_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mMyLiveList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GroupTagBean myLive = mMyLiveList.get(holder.getAdapterPosition());
        if(myLive.groupInfo!=null && !"".equals(myLive.groupInfo)){
            holder.mTvTitle.setText(myLive.groupInfo);
        }else{
            holder.mTvTitle.setText("其他");
        }
        holder.mTvSource.setText(mMyLiveList.get(holder.getAdapterPosition()).getGroupItemBeen().size()+"个店铺");

        if (myLive.isSelect()) {
            holder.mCheckBox.setImageResource(R.mipmap.check);
        } else {
            holder.mCheckBox.setImageResource(R.mipmap.check_not);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.OnItemClickListenerTag(holder.getAdapterPosition(), mMyLiveList);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListenerTag onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListenerTag {
        void OnItemClickListenerTag(int pos, List<GroupTagBean> myLiveList);
    }
    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_source)
        TextView mTvSource;
        @BindView(R.id.check_box)
        ImageView mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
