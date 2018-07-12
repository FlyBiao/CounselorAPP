package com.cesaas.android.counselor.order.inventory.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.inventory.bean.InventoryDifferenceBean;
import com.cesaas.android.counselor.order.inventory.bean.InventoryListBean;
import com.cesaas.android.counselor.order.inventory.ui.InventoryDifferenceActivity;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.utils.Skip;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class InventoryDifferenceAdapter extends SwipeMenuAdapter<InventoryDifferenceAdapter.DefaultViewHolder> {

    private List<InventoryDifferenceBean> titles;
    private OnItemClickListener mOnItemClickListener;

    private Context ct;
    private  Activity activity;

    public InventoryDifferenceAdapter(List<InventoryDifferenceBean> titles, Context ct, Activity activity) {
        this.titles = titles;
        this.ct=ct;
        this.activity=activity;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory_difference, parent, false);
    }

    @Override
    public InventoryDifferenceAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final InventoryDifferenceAdapter.DefaultViewHolder holder, final int position) {
        holder.setData(titles.get(position).getNo(),
                titles.get(position).getType(),
                titles.get(position).getDate(),
                titles.get(position).getAddress(),
                titles.get(position).getReceiveAddress(),
                titles.get(position).getCount());
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_diff_no,tv_diff_type,tv_diff_date,tv_address,tv_diff_receive_address,diff_count;

        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tv_diff_no= (TextView) itemView.findViewById(R.id.tv_diff_no);
            tv_diff_type= (TextView) itemView.findViewById(R.id.tv_diff_type);
            tv_diff_date= (TextView) itemView.findViewById(R.id.tv_diff_date);
            tv_address= (TextView) itemView.findViewById(R.id.tv_address);
            tv_diff_receive_address= (TextView) itemView.findViewById(R.id.tv_diff_receive_address);
            diff_count= (TextView) itemView.findViewById(R.id.diff_count);
        }

        public void setData(String no,int type,String date,String address,String diffAddress,int count) {
            this.tv_diff_no.setText(no);
            this.tv_diff_type.setText(type+"");
            this.tv_diff_date.setText(date);
            this.tv_address.setText(address);
            this.tv_diff_receive_address.setText(diffAddress);
            this.diff_count.setText(count+"");
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
