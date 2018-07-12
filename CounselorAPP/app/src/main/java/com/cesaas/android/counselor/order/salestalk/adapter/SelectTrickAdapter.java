package com.cesaas.android.counselor.order.salestalk.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.salestalk.bean.ResultCustomListBean;
import com.cesaas.android.counselor.order.salestalk.bean.ResultSalesTalkCategoryListBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 选择话术Adapter
 * Created at 2017/5/4 10:30
 * Version 1.0
 */

public class SelectTrickAdapter extends SwipeMenuAdapter<SelectTrickAdapter.DefaultViewHolder> {

    private List<ResultSalesTalkCategoryListBean.SalesTalkCategoryListBean> customListBeanList;
    private OnItemClickListener mOnItemClickListener;

    public SelectTrickAdapter(List<ResultSalesTalkCategoryListBean.SalesTalkCategoryListBean> customListBeanList){
            this.customListBeanList=customListBeanList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_sales_talk, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        holder.setData(customListBeanList.get(position).Content,customListBeanList.get(position).Id);
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return customListBeanList==null ? 0 : customListBeanList.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_sales_talk_content, tv_talk_type;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_sales_talk_content = (TextView) itemView.findViewById(R.id.tv_sales_talk_content);
            tv_talk_type = (TextView) itemView.findViewById(R.id.tv_talk_type);
            tv_talk_type.getBackground().setAlpha(100);//0~255透明度值
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String content, int status) {

            this.tv_sales_talk_content.setText(content);
            this.tv_talk_type.setText("销售");

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
