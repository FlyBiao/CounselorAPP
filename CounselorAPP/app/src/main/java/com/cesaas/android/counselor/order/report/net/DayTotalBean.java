package com.cesaas.android.counselor.order.report.net;

import java.io.Serializable;

/**
 * Author FGB
 * Description 获取店铺日目标Bean
 * Created at 2017/5/9 17:37
 * Version 1.0
 */

public class DayTotalBean implements Serializable {

    /**
     * VipNum : 0
     * CounselorGoal : 0.0
     * Card : 0
     * ShopGoal : 0.0
     */
    private int VipNum;
    private double CounselorGoal;
    private int Card;
    private double ShopGoal;
    private int TodayCard;
    private double TodayCounselorGoal;
    private double TodayShopGoal;

    public int getTodayCard() {
        return TodayCard;
    }

    public void setTodayCard(int todayCard) {
        TodayCard = todayCard;
    }

    public double getTodayCounselorGoal() {
        return TodayCounselorGoal;
    }

    public void setTodayCounselorGoal(double todayCounselorGoal) {
        TodayCounselorGoal = todayCounselorGoal;
    }

    public double getTodayShopGoal() {
        return TodayShopGoal;
    }

    public void setTodayShopGoal(double todayShopGoal) {
        TodayShopGoal = todayShopGoal;
    }

    public void setVipNum(int VipNum) {
        this.VipNum = VipNum;
    }

    public void setCounselorGoal(double CounselorGoal) {
        this.CounselorGoal = CounselorGoal;
    }

    public void setCard(int Card) {
        this.Card = Card;
    }

    public void setShopGoal(double ShopGoal) {
        this.ShopGoal = ShopGoal;
    }

    public int getVipNum() {
        return VipNum;
    }

    public double getCounselorGoal() {
        return CounselorGoal;
    }

    public int getCard() {
        return Card;
    }

    public double getShopGoal() {
        return ShopGoal;
    }
}
