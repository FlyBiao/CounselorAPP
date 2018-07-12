package com.cesaas.android.counselor.order.member.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.CartBean;
import com.cesaas.android.counselor.order.manager.bean.GetLstByVipTagBean;
import com.cesaas.android.counselor.order.member.bean.GoodsInfoBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 购物车
 * Created at 2017/11/8 17:25
 * Version 1.0
 */

public class GetLstByVipTagAdapter extends BaseQuickAdapter<GetLstByVipTagBean, BaseViewHolder> {

    private List<GetLstByVipTagBean> mData;

    public GetLstByVipTagAdapter(List<GetLstByVipTagBean> mData) {
        super( R.layout.item_goods_info,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, GetLstByVipTagBean item) {
        helper.setText(R.id.tv_list_price,"￥"+item.getListPrice());
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_attr,item.getNo());
        if(item.getImageUrl()!=null && !"".equals(item.getImageUrl()) && !"NULL".equals(item.getImageUrl())){
            // 加载网络图片
            Glide.with(mContext).load(item.getImageUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_image));
        }else{
            helper.setImageResource(R.id.iv_image,R.mipmap.ic_launcher);
        }
    }
}
