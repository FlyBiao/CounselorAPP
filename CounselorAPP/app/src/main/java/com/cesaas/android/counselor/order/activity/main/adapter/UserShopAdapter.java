package com.cesaas.android.counselor.order.activity.main.adapter;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ShopPowerBean;
import com.cesaas.android.counselor.order.boss.bean.SalesProportionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 店员店铺列表
 * Created at 2018/1/20 11:40
 * Version 1.0
 */

public class UserShopAdapter extends BaseQuickAdapter<ShopPowerBean, BaseViewHolder> {

    private List<ShopPowerBean> mData;

    public UserShopAdapter(List<ShopPowerBean> mData) {
        super( R.layout.item_user_shop_list,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopPowerBean item) {
        helper.setText(R.id.tv_shop_name,item.getShopName());

    }
}
