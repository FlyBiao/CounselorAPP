package com.cesaas.android.java.adapter.move;

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
import com.cesaas.android.java.bean.move.MoveDeliveryGoodsDetailBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/6/1 14:49
 * Version 1.0
 */

public class MoveDeliveryGoodsDetailScanAdapter extends SwipeMenuAdapter<MoveDeliveryGoodsDetailScanAdapter.DefaultViewHolder> {

    private List<MoveDeliveryGoodsDetailBean> mData=new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    static Context mContext;

    public MoveDeliveryGoodsDetailScanAdapter(List<MoveDeliveryGoodsDetailBean>  data, Context mContext){
        this.mData=data;
        this.mContext=mContext;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imove_delivery_goods_detail_scan, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(final DefaultViewHolder holder, final int position) {
        holder.setData(mData.get(position).getBarcodeNo(),mData.get(position).getTitle(),mData.get(position).getSkuValue1()+mData.get(position).getSkuValue2(),mData.get(position).getNum(),mData.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_barcodeNo,tv_title,tv_sku,tv_num;
        ImageView iv_img;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            tv_sku = (TextView) itemView.findViewById(R.id.tv_sku);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_barcodeNo = (TextView) itemView.findViewById(R.id.tv_barcodeNo);
            iv_img= (ImageView) itemView.findViewById(R.id.iv_img);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String barcodeNo,String title,String sku,int num,String img) {
            this.tv_barcodeNo.setText(barcodeNo);
            this.tv_title.setText(title);
            this.tv_sku.setText(sku);
            this.tv_num.setText(String.valueOf(num));

            if(img!=null && !"".equals(img) && !"NULL".equals(img)){
                // 加载网络图片
                Glide.with(mContext).load(img).crossFade().into((ImageView) this.iv_img);
            }else{
                this.iv_img.setImageResource(R.mipmap.default_image);
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
