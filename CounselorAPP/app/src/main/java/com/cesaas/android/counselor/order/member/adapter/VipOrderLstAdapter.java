package com.cesaas.android.counselor.order.member.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.VipOrderListSection;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/11/11 11:40
 * Version 1.0
 */

public class VipOrderLstAdapter extends BaseSectionQuickAdapter<VipOrderListSection,BaseViewHolder> {

    public VipOrderLstAdapter(int layoutResId, int sectionHeadResId, List<VipOrderListSection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, VipOrderListSection item) {
        if(item.getCreateTime()!=null && !"".equals(item.getCreateTime())){
            helper.setText(R.id.tv_time,AbDateUtil.formats(item.getCreateTime()));
            helper.setText(R.id.tv_day,AbDateUtil.getDateYMDs(item.getCreateTime()));
        }else{
            helper.setText(R.id.tv_day,"未获取订单时间");
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, VipOrderListSection item) {

        helper.setText(R.id.tv_title,item.t.getStyleName());
        helper.setText(R.id.tv_code,item.t.getBarcodeNo());
        helper.setText(R.id.tv_price,"￥"+item.t.getPayMent());
        helper.setText(R.id.tv_attr,item.t.getSkuValue1()+" "+item.t.getSkuValue2()+" "+item.t.getSkuValue3());

        if(item.t.getImageUrl()!=null && !"".equals(item.t.getImageUrl()) && !"NULL".equals(item.t.getImageUrl())){
            // 加载网络图片
            Glide.with(mContext).load(item.t.getImageUrl()).crossFade().error(R.mipmap.default_image).into((ImageView) helper.getView(R.id.iv_image));
        }else{
            helper.setImageResource(R.id.iv_image,R.mipmap.default_image);
        }
    }
}
