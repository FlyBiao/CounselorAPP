package com.cesaas.android.counselor.order.member.bean.service.results;

import java.io.Serializable;

/**
 * Author FGB
 * Description 查询店铺月度目标
 * Created at 2018/3/29 10:01
 * Version 1.0
 */

public class GetShopGoalBean implements Serializable {
    private double SalesTarget;
    private double SalesSurpass;
    private int CardTarget;

    public double getSalesTarget() {
        return SalesTarget;
    }

    public void setSalesTarget(double salesTarget) {
        SalesTarget = salesTarget;
    }

    public double getSalesSurpass() {
        return SalesSurpass;
    }

    public void setSalesSurpass(double salesSurpass) {
        SalesSurpass = salesSurpass;
    }

    public int getCardTarget() {
        return CardTarget;
    }

    public void setCardTarget(int cardTarget) {
        CardTarget = cardTarget;
    }
}
