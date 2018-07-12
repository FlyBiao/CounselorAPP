package com.cesaas.android.counselor.order.member.bean.service.results;

import java.io.Serializable;

/**
 * Author FGB
 * Description 查询店员每日目标分配情况
 * Created at 2018/3/30 10:41
 * Version 1.0
 */

public class GetListCounselorDayGoalBean implements Serializable {

    private String Date;
    private double SalesTarget;
    private double SalesSurpass;
    private int CardTarget;
    private int IsSet;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

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

    public int getIsSet() {
        return IsSet;
    }

    public void setIsSet(int isSet) {
        IsSet = isSet;
    }
}
