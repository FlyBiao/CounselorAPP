package com.cesaas.android.counselor.order.salestalk.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.salestalk.bean.ResultSalesTalkCategoryListBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/5/17 12:27
 * Version 1.0
 */

public class SelectVerbalTrickAdapter  extends SwipeMenuAdapter<SelectVerbalTrickAdapter.DefaultViewHolder>{

    private List<ResultSalesTalkCategoryListBean.SalesTalkCategoryListBean> mSalesTalkCategoryListBeen;
    private OnItemClickListener mOnItemClickListener;
    public static Context mContext;
    public static Activity mActivity;

    public SelectVerbalTrickAdapter(List<ResultSalesTalkCategoryListBean.SalesTalkCategoryListBean> mSalesTalkCategoryListBeen, Context mContext){
        this.mContext=mContext;
        this.mSalesTalkCategoryListBeen=mSalesTalkCategoryListBeen;
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
        holder.setData(mSalesTalkCategoryListBeen.get(position).Content,mSalesTalkCategoryListBeen.get(position).Question);
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mSalesTalkCategoryListBeen == null ? 0 : mSalesTalkCategoryListBeen.size();
    }


    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_sales_talk_content,tv_sales_question;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.tv_sales_talk_content = (TextView) itemView.findViewById(R.id.tv_sales_talk_content);
            this.tv_sales_question= (TextView) itemView.findViewById(R.id.tv_sales_question);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String name,String question) {
            this.tv_sales_question.setText(question);
            this.tv_sales_talk_content.setText(name);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
