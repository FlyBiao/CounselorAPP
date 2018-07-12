package com.cesaas.android.counselor.order.statistics.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.progress.CustomIndicateProgressView;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.statistics.bean.SalesThanBean;
import com.cesaas.android.counselor.order.widget.SalesThanProgressbar;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;


/**
 * Author FGB
 * Description 销售对比Adapter
 * Created 2017/3/15 16:19
 * Version 1.zero
 */
public class SalesThanAdapter extends SwipeMenuAdapter<SalesThanAdapter.DefaultViewHolder> {

    private List<SalesThanBean> thanBeanList;

    private OnItemClickListener mOnItemClickListener;

    public SalesThanAdapter(List<SalesThanBean> thanBeanList) {
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
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sales_than, parent, false);
    }

    @Override
    public SalesThanAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(SalesThanAdapter.DefaultViewHolder holder, int position) {
        holder.setData(thanBeanList.get(position).getShopName(),thanBeanList.get(position).getCountPayment());
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        SalesThanProgressbar salesThanProgress;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            salesThanProgress= (SalesThanProgressbar) itemView.findViewById(R.id.pgb_sales_than_progress);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,double v) {
            this.tvTitle.setText(title);
            this.salesThanProgress.setMaxValue(100);
            this.salesThanProgress.setCurrentValue(Float.parseFloat(v+""));
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
