package com.cesaas.android.counselor.order.boss.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 获取店铺目标达成情况列表
 * Created at 2018/7/4 10:58
 * Version 1.0
 */

public class GetShopMonthInfoBean implements Serializable {
    private String ShopId;
    private String ShopName;
    private String OrganizationName;
    private String Goal;//目标
    private int CardGoal;//	开卡目标
    private String SurpassGoal;//挑战目标
    private String Sale;//销售额
    private String SaleReach;//销售达成
    private int CardNum;//	开卡数量
    private String CardReach;//	开卡达成

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String shopId) {
        ShopId = shopId;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getOrganizationName() {
        return OrganizationName;
    }

    public void setOrganizationName(String organizationName) {
        OrganizationName = organizationName;
    }

    public String getGoal() {
        return Goal;
    }

    public void setGoal(String goal) {
        Goal = goal;
    }

    public int getCardGoal() {
        return CardGoal;
    }

    public void setCardGoal(int cardGoal) {
        CardGoal = cardGoal;
    }

    public String getSurpassGoal() {
        return SurpassGoal;
    }

    public void setSurpassGoal(String surpassGoal) {
        SurpassGoal = surpassGoal;
    }

    public String getSale() {
        return Sale;
    }

    public void setSale(String sale) {
        Sale = sale;
    }

    public String getSaleReach() {
        return SaleReach;
    }

    public void setSaleReach(String saleReach) {
        SaleReach = saleReach;
    }

    public int getCardNum() {
        return CardNum;
    }

    public void setCardNum(int cardNum) {
        CardNum = cardNum;
    }

    public String getCardReach() {
        return CardReach;
    }

    public void setCardReach(String cardReach) {
        CardReach = cardReach;
    }
}
