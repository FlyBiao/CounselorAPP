package com.cesaas.android.counselor.order.report.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.report.bean.MySubscriptionBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 我的订阅数据适配器
 * Created 2017/3/25 11:29
 * Version 1.zero
 */
public class MySubscriptionAdapter extends SwipeMenuAdapter<MySubscriptionAdapter.DefaultViewHolder> {

    private List<MySubscriptionBean> subscriptionBeanList;

    private OnItemClickListener mOnItemClickListener;

    public MySubscriptionAdapter(List<MySubscriptionBean> subscriptionBeanList) {
        this.subscriptionBeanList = subscriptionBeanList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return subscriptionBeanList == null ? 0 : subscriptionBeanList.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscription, parent, false);
    }

    @Override
    public MySubscriptionAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new MySubscriptionAdapter.DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(MySubscriptionAdapter.DefaultViewHolder holder, int position) {
        holder.setData(subscriptionBeanList.get(position).getTitle(),subscriptionBeanList.get(position).getId(),subscriptionBeanList.get(position).getIsUsed());
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_subscription_name,tv_subscription_count;
        ImageView bookmark_checked;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_subscription_name = (TextView) itemView.findViewById(R.id.tv_subscription_name);
            tv_subscription_count= (TextView) itemView.findViewById(R.id.tv_subscription_count);
            bookmark_checked= (ImageView) itemView.findViewById(R.id.bookmark);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,int subscriptionCount,int isSubscription) {
            this.tv_subscription_name.setText(title);
            this.tv_subscription_count.setText(""+subscriptionCount);
            if(isSubscription==0){//未订阅
                this.bookmark_checked.setImageResource(R.mipmap.bookmark);
            }else{//已订阅
                this.bookmark_checked.setImageResource(R.mipmap.bookmark_checked);
            }
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }






}
