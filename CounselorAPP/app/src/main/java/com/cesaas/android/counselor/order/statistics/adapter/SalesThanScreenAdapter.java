package com.cesaas.android.counselor.order.statistics.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.statistics.bean.SalesThanBean;
import com.cesaas.android.counselor.order.statistics.bean.SalesThanScreenBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;


/**
 * Author FGB
 * Description 销售对比筛选Adapter
 * Created 2017/3/15 16:19
 * Version 1.zero
 */
public class SalesThanScreenAdapter extends SwipeMenuAdapter<SalesThanScreenAdapter.DefaultViewHolder> {

    private List<SalesThanScreenBean> thanBeanList;

    private OnItemClickListener mOnItemClickListener;

    public SalesThanScreenAdapter(List<SalesThanScreenBean> thanBeanList) {
        this.thanBeanList = thanBeanList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return thanBeanList == null ? 0 : thanBeanList.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sales_than_screen, parent, false);
    }

    @Override
    public SalesThanScreenAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(SalesThanScreenAdapter.DefaultViewHolder holder, int position) {
        holder.setData(thanBeanList.get(position).getShopName());
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title) {
            this.tvTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
