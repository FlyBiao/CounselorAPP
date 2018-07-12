package com.cesaas.android.counselor.order.boss.adapter.shop;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.bean.ShopListBean;

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

public class ScreenShopAdapter extends RecyclerView.Adapter<ScreenShopAdapter.ViewHolder> {

    private int shopType;
    private int secret = 0;
    private String title = "";
    private Context context;
    private Activity activity;
    private List<ShopListBean> mMyLiveList=new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public ScreenShopAdapter(Context context,Activity activity,int shopTyps) {
        this.context = context;
        this.activity=activity;
        this.shopType=shopTyps;
    }

    public void notifyAdapter(List<ShopListBean> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mMyLiveList = myLiveList;
        } else {
            this.mMyLiveList.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<ShopListBean> getMyLiveList() {
        if (mMyLiveList == null) {
            mMyLiveList = new ArrayList<>();
        }
        return mMyLiveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_live, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mMyLiveList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ShopListBean myLive = mMyLiveList.get(holder.getAdapterPosition());
        holder.mTvTitle.setText(myLive.getShopName());
        holder.mTvSource.setText(myLive.getAreaName());
        if (myLive.isSelect()==true) {
            holder.mCheckBox.setImageResource(R.mipmap.check);
        } else {
            holder.mCheckBox.setImageResource(R.mipmap.check_not);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), mMyLiveList);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClickListener(int pos,List<ShopListBean> myLiveList);
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
