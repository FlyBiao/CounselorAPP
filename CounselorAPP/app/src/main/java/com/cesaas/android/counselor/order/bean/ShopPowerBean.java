package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/1/24 16:30
 * Version 1.0
 */

public class ShopPowerBean implements Serializable {

    /**
     * IsDel : 0
     * ShopId : 11255
     * ShopName : 王府井百货
     */

    private int IsDel;
    private int ShopId;
    private String ShopName;

    public void setIsDel(int IsDel) {
        this.IsDel = IsDel;
    }

    public void setShopId(int ShopId) {
        this.ShopId = ShopId;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public int getIsDel() {
        return IsDel;
    }

    public int getShopId() {
        return ShopId;
    }

    public String getShopName() {
        return ShopName;
    }
}
