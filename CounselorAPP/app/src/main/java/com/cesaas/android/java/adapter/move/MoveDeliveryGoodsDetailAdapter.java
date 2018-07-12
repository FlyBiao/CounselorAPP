package com.cesaas.android.java.adapter.move;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.java.bean.move.MoveDeliveryGoodsDetailBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 调货发货商品条码列表
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class MoveDeliveryGoodsDetailAdapter extends BaseQuickAdapter<MoveDeliveryGoodsDetailBean, BaseViewHolder> {

    private List<MoveDeliveryGoodsDetailBean> mData;

    private Context mContext;
    private  Activity mActivity;

    public MoveDeliveryGoodsDetailAdapter(List<MoveDeliveryGoodsDetailBean> mData, Activity activity, Context ct) {
        super(R.layout.item_imove_delivery_goods_detail,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=ct;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MoveDeliveryGoodsDetailBean item) {
        helper.setText(R.id.tv_barcodeNo,item.getBarcodeNo());
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_sku,item.getSkuValue1()+" "+item.getSkuValue2());
        helper.setText(R.id.tv_listPrice,"￥"+item.getListPrice());
        helper.setText(R.id.tv_num,String.valueOf(item.getNum()));
        helper.setText(R.id.tv_receivingNum,String.valueOf(item.getReceivingNum()));

        if(item.getImageUrl()!=null && !"".equals(item.getImageUrl()) && !"NULL".equals(item.getImageUrl())){
            // 加载网络图片
            Glide.with(mContext).load(item.getImageUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_img));
        }else{
            helper.setImageResource(R.id.iv_img,R.mipmap.default_image);
        }
    }
}
