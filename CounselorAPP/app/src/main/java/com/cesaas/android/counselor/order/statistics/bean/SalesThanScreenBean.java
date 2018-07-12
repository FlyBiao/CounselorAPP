package com.cesaas.android.counselor.order.statistics.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 销量对比Bean
 * Created 2017/3/15 17:31
 * Version 1.zero
 */
public class SalesThanScreenBean implements Serializable{

    private int ShopId;
    private String ShopName;

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }
}
