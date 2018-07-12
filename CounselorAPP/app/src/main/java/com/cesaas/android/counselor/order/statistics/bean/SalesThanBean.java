package com.cesaas.android.counselor.order.statistics.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author FGB
 * Description 销量对比Bean
 * Created 2017/3/15 17:31
 * Version 1.zero
 */
public class SalesThanBean implements Serializable{

    private String ShopName;
    private double CountPayment;

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public double getCountPayment() {
        return CountPayment;
    }

    public void setCountPayment(double countPayment) {
        CountPayment = countPayment;
    }
}
