package com.cesaas.android.counselor.order.bean;

/**
 * Author FGB
 * Description 会员数据统计Bean
 * Created 2017/3/2 15:26
 * Version 1.zero
 */
public class VipDataStatisticsBean extends BaseBean{

    private String ShopName;
    private int TodayVip;
    private int MonthVip;
    private int TotalVip;

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public int getTodayVip() {
        return TodayVip;
    }

    public void setTodayVip(int todayVip) {
        TodayVip = todayVip;
    }

    public int getMonthVip() {
        return MonthVip;
    }

    public void setMonthVip(int monthVip) {
        MonthVip = monthVip;
    }

    public int getTotalVip() {
        return TotalVip;
    }

    public void setTotalVip(int totalVip) {
        TotalVip = totalVip;
    }
}
