package com.cesaas.android.counselor.order.boss.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 获取店铺目标总计
 * Created at 2018/7/4 10:47
 * Version 1.0
 */

public class GetShopMonthSumBean implements Serializable {
    private String GoalSum;//总目标
    private String SaleSum;//总销售
    private int ShopNum;//店铺数量
    private String Rate;//达成率
    private String ShopAverage;//店均
    private String CounselorAverage;//人均
    private String AreaAverage;//坪效

    public String getGoalSum() {
        return GoalSum;
    }

    public void setGoalSum(String goalSum) {
        GoalSum = goalSum;
    }

    public String getSaleSum() {
        return SaleSum;
    }

    public void setSaleSum(String saleSum) {
        SaleSum = saleSum;
    }

    public int getShopNum() {
        return ShopNum;
    }

    public void setShopNum(int shopNum) {
        ShopNum = shopNum;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getShopAverage() {
        return ShopAverage;
    }

    public void setShopAverage(String shopAverage) {
        ShopAverage = shopAverage;
    }

    public String getCounselorAverage() {
        return CounselorAverage;
    }

    public void setCounselorAverage(String counselorAverage) {
        CounselorAverage = counselorAverage;
    }

    public String getAreaAverage() {
        return AreaAverage;
    }

    public void setAreaAverage(String areaAverage) {
        AreaAverage = areaAverage;
    }
}
