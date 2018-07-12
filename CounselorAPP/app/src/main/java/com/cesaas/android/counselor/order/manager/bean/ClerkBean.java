package com.cesaas.android.counselor.order.manager.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/9/12 16:43
 * Version 1.0
 */

public class ClerkBean implements Serializable {

    private String icon;
    private String iconTest;
    private String name;
    private String shop;
    private int shopId;
    private int id;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIconTest() {
        return iconTest;
    }

    public void setIconTest(String iconTest) {
        this.iconTest = iconTest;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }
}
