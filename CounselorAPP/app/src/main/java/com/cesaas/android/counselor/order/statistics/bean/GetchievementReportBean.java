package com.cesaas.android.counselor.order.statistics.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 根据报表业务设计图获取当天业务报表数据Bean
 * Created 2017/3/13 19:08
 * Version 1.zero
 */
public class GetchievementReportBean implements Serializable{

    public double TodaySalesCount;//今日销售额
    public int TodayItemsCount;//今日销售件数
    public int TodayOrderCount;//今日订单数量
    public double MonthSalesCount;//月销售量
    public int MonthItemsCount;//月出售件数
    public int MonthOrderCount;//月订单数量
    public double YearSalesCount;//年销售量
    public int YearItemsCount;//年出售件数
    public int YearOrderCount;//年订单数量
    public double TodayMemebersConsumption; //今日会员消费金额
    public double TodayUnMemberConsumption;//今日非会员消费金额
    public int CountAllOrder;//订单总数
    public int CountAllMember;//总会员数

    public ArrayList<InfactGainBean> InfactGain;
}
