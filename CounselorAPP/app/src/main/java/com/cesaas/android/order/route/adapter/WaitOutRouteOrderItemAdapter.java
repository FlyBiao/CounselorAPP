package com.cesaas.android.order.route.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 待发货路由订单item
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class WaitOutRouteOrderItemAdapter extends BaseQuickAdapter<UnReceiveOrderBean.OrderItem, BaseViewHolder> {
    private List<UnReceiveOrderBean.OrderItem> mData;
    private Activity mActivity;
    private Context mContext;


    public WaitOutRouteOrderItemAdapter(List<UnReceiveOrderBean.OrderItem> mData, Activity activity, Context context) {
        super( R.layout.item_waitout_route_order_item,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final UnReceiveOrderBean.OrderItem item) {

        if(item.getImageUrl()!=null && !"".equals(item.getImageUrl()) && !"NULL".equals(item.getImageUrl())){
            // 加载网络图片
            Glide.with(mContext).load(item.getImageUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_img));
        }else{
            helper.setImageResource(R.id.iv_img,R.mipmap.ic_launcher);
        }

    }
}
