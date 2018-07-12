package com.cesaas.android.counselor.order.boss.bean.member;

import java.io.Serializable;

/**
 * Author FGB
 * Description 会员服务PACD统计(按店铺查询业绩列表)
 * Created at 2018/4/27 14:22
 * Version 1.0
 */

public class ShopSalesListBean extends  TaskTypeShopDataBean implements Serializable {
    private int ShopId;
    private String ShopName;
    private double ShopTaskSums;
    private double ShopSums;

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

    public double getShopTaskSums() {
        return ShopTaskSums;
    }

    public void setShopTaskSums(double shopTaskSums) {
        ShopTaskSums = shopTaskSums;
    }

    public double getShopSums() {
        return ShopSums;
    }

    public void setShopSums(double shopSums) {
        ShopSums = shopSums;
    }
}
