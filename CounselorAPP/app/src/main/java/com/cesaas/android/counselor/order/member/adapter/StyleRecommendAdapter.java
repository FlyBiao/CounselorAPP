package com.cesaas.android.counselor.order.member.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.StyleRecommendtBean;
import com.cesaas.android.counselor.order.member.bean.Tags;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description StyleRecommendAdapter
 * Created at 2017/11/8 17:25
 * Version 1.0
 */

public class StyleRecommendAdapter extends BaseQuickAdapter<StyleRecommendtBean, BaseViewHolder> {

    private List<StyleRecommendtBean> mData;

    public StyleRecommendAdapter(List<StyleRecommendtBean> mData) {
        super( R.layout.item_style_recommend,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, StyleRecommendtBean item) {
        helper.setText(R.id.tv_title,item.getShopStyleId()+"");
        helper.setText(R.id.tv_code,item.getStyleNo());
        helper.setText(R.id.tv_price,"￥"+item.getSellPrice());
        helper.setText(R.id.tv_attr,item.getStyleName());

        if(item.getImageUrl()!=null && !"".equals(item.getImageUrl()) && !"NULL".equals(item.getImageUrl())){
            // 加载网络图片
            Glide.with(mContext).load(item.getImageUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_image));
        }else{
            helper.setImageResource(R.id.iv_image,R.mipmap.default_image);
        }

    }
}
