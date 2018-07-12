package com.cesaas.android.counselor.order.power.adapter;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.power.bean.MenuDataBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/1/31 10:47
 * Version 1.0
 */

public class ShopTaskMenuActionPowerAdapter extends BaseQuickAdapter<MenuDataBean, BaseViewHolder> {

    private List<MenuDataBean> mData;

    public ShopTaskMenuActionPowerAdapter(List<MenuDataBean> mData) {
        super( R.layout.item_shop_task_menu,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuDataBean item) {
        helper.setText(R.id.tv_menu_name,item.getMenuName());
        helper.setText(R.id.iv_menu,item.getMenuImage());
        helper.setTypeface(R.id.iv_menu,App.font);
    }
}
