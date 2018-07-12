package com.cesaas.android.counselor.order.shopmange.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.adapter.SearchRecordAdapter;
import com.cesaas.android.counselor.order.shopmange.bean.ClerkSearchRecordBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 店员查询记录Adapter
 * Created at 2017/6/15 10:36
 * Version 1.0
 */

public class ClerkSearchRecordAdapter extends SwipeMenuAdapter<ClerkSearchRecordAdapter.DefaultViewHolder> {

    private List<ClerkSearchRecordBean> clerkSearchRecordBeen;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public ClerkSearchRecordAdapter (List<ClerkSearchRecordBean> clerkSearchRecordBeen){
        this.clerkSearchRecordBeen=clerkSearchRecordBeen;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clerk_search_record, parent, false);
    }

    @Override
    public ClerkSearchRecordAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new ClerkSearchRecordAdapter.DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(ClerkSearchRecordAdapter.DefaultViewHolder holder, int position) {
        holder.setData(clerkSearchRecordBeen.get(position).getName());
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return clerkSearchRecordBeen == null ? 0 : clerkSearchRecordBeen.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_search_name;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_search_name = (TextView) itemView.findViewById(R.id.tv_search_name);


        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String name) {
            tv_search_name.setText(name);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
