package com.cesaas.android.counselor.order.shopmange.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.shopmange.bean.AllShopBean;
import com.cesaas.android.counselor.order.shopmange.bean.ShopMatchBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 商品搭配Adapter
 * Created 2017/4/27 17:57
 * Version 1.0
 */
public class ShopMatchAdapter extends SwipeMenuAdapter<ShopMatchAdapter.DefaultViewHolder>{

    private List<ShopMatchBean> mAllShopBeanList;
    private OnItemClickListener mOnItemClickListener;
    static Context mContext;

    public ShopMatchAdapter(List<ShopMatchBean> mAllShopBeanList, Context mContext){
        this.mAllShopBeanList=mAllShopBeanList;
        this.mContext=mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_match, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(final DefaultViewHolder holder, final int position) {

        holder.setData(mAllShopBeanList.get(position).getTitle(),mAllShopBeanList.get(position).getImageUrl()
                ,mAllShopBeanList.get(position).getFocus(),mAllShopBeanList.get(position).getLike(),mAllShopBeanList.get(position).getEvaluate());
        holder.setOnItemClickListener(mOnItemClickListener);

    }

    @Override
    public int getItemCount() {
        return mAllShopBeanList == null ? 0 : mAllShopBeanList.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_shop_title,tv_shop_style_code,tv_shop_style_price,tv_shop_sales_price,tv_shop_sales_volume;
        TextView tv_focus,tv_like,tv_evaluate;
        ImageView iv_shop_img;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_shop_title = (TextView) itemView.findViewById(R.id.tv_shop_title);
            tv_shop_style_code= (TextView) itemView.findViewById(R.id.tv_shop_style_code);
            iv_shop_img= (ImageView) itemView.findViewById(R.id.iv_shop_img);
            tv_shop_style_price= (TextView) itemView.findViewById(R.id.tv_shop_style_price);
            tv_shop_sales_price= (TextView) itemView.findViewById(R.id.tv_shop_sales_price);
            tv_shop_sales_volume= (TextView) itemView.findViewById(R.id.tv_shop_sales_volume);
            tv_focus= (TextView) itemView.findViewById(R.id.tv_focus);
            tv_like= (TextView) itemView.findViewById(R.id.tv_like);
            tv_evaluate= (TextView) itemView.findViewById(R.id.tv_evaluate);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,String ImageUrl ,int focus,int like,int evaluate) {

            this.tv_focus.setText(focus+"");
            this.tv_like.setText(like+"");
            this.tv_evaluate.setText(evaluate+"");
            this.tv_shop_title.setText(title);
            Glide.with(mContext).load(ImageUrl).placeholder(R.mipmap.loading).into(iv_shop_img);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
