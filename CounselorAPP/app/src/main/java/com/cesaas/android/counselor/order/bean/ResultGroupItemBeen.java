package com.cesaas.android.counselor.order.bean;

import com.cesaas.android.counselor.order.boss.bean.ShopListBean;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/1/11 11:31
 * Version 1.0
 */

public class ResultGroupItemBeen {
    private List<ShopListBean> shopListBeen;

    public List<ShopListBean> getShopListBeen() {
        return shopListBeen;
    }

    public void setShopListBeen(List<ShopListBean> shopListBeen) {
        this.shopListBeen = shopListBeen;
    }
}
