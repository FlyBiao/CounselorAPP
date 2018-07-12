package com.cesaas.android.counselor.order.activity.user.bean;

/**
 * Author FGB
 * Description
 * Created at 2017/5/5 16:16
 * Version 1.0
 */

public class RegisterShopNameBean {

    private String ShopId;//店铺ID
    private String Name;//店铺名称
    private String ShopCity;//店铺所在城市

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String shopId) {
        ShopId = shopId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getShopCity() {
        return ShopCity;
    }

    public void setShopCity(String shopCity) {
        ShopCity = shopCity;
    }
}
