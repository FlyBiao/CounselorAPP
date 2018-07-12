package com.cesaas.android.counselor.order.boss.bean.member;

import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.NewDateUtils;
import com.cesaas.android.java.utils.DateTools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/4/26 9:03
 * Version 1.0
 */

public class TimeDataUtils {

    private static List<TimeDataBean> CurrentDate=new ArrayList<>();
    private static List<TimeDataBean> CurrentDates=new ArrayList<>();

    public static List<TimeDataBean> getCurrentDate(){
        CurrentDate=new ArrayList<>();

        TimeDataBean d=new TimeDataBean();
        d.setType(0);
        d.setDateTitle("实 时");
        d.setStartDate(AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"));
        d.setEndDate(AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"));
        CurrentDate.add(d);

        TimeDataBean d1=new TimeDataBean();
        d1.setType(1);
        d1.setDateTitle("昨 天");
        d1.setStartDate(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        d1.setEndDate(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        CurrentDate.add(d1);

        TimeDataBean d2=new TimeDataBean();
        d2.setType(2);
        d2.setDateTitle("最近3天");
        d2.setStartDate(AbDateUtil.getThreeDay());
        d2.setEndDate(AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"));
        CurrentDate.add(d2);

        TimeDataBean d3=new TimeDataBean();
        d3.setType(3);
        d3.setDateTitle("最近7天");
        d3.setStartDate(AbDateUtil.getTopWeek());
        d3.setEndDate(AbDateUtil.getCurrentWeek());
        CurrentDate.add(d3);

        TimeDataBean d4=new TimeDataBean();
        d4.setType(4);
        d4.setDateTitle("本 周");
        d4.setStartDate(AbDateUtil.getTopWeek());
        d4.setEndDate(AbDateUtil.getCurrentWeek());
        CurrentDate.add(d4);

        TimeDataBean d5=new TimeDataBean();
        d5.setType(5);
        d5.setDateTitle("上 周");
        d5.setStartDate(AbDateUtil.getBeginDayOfLastWeek());
        d5.setEndDate(AbDateUtil.getEndDayOfLastWeek());
        CurrentDate.add(d5);

        TimeDataBean d6=new TimeDataBean();
        d6.setType(6);
        d6.setDateTitle("本 月");
        d6.setStartDate(AbDateUtil.getFirstDayOfMonth("yyyy-MM-dd 00:00:00"));
        d6.setEndDate(AbDateUtil.getLastDayOfMonth("yyyy-MM-dd 23:59:59"));
        CurrentDate.add(d6);

        TimeDataBean d7=new TimeDataBean();
        d7.setType(7);
        d7.setDateTitle("上 月");
        d7.setStartDate(AbDateUtil.upMonthOneDay());
        d7.setEndDate(AbDateUtil.upMonthLastDay());
        CurrentDate.add(d7);

        TimeDataBean d8=new TimeDataBean();
        d8.setType(8);
        d8.setDateTitle("本 季");
        d8.setStartDate(AbDateUtil.getFirstSeasonDate());
        Date date = DateTools.parseDate(AbDateUtil.getCurrentDate("yyyy-MM-dd"));
        d8.setEndDate(DateTools.formatDate(DateTools.getLastDateOfSeason(date))+" 23:59:59");
        CurrentDate.add(d8);

        TimeDataBean d9=new TimeDataBean();
        d9.setType(9);
        d9.setDateTitle("上 季");
        d9.setStartDate("2018-04-30 00:00:00");
        d9.setEndDate("2018-06-30 23:59:59");
        CurrentDate.add(d9);

        TimeDataBean d10=new TimeDataBean();
        d10.setType(10);
        d10.setDateTitle("今 年");
        d10.setStartDate(AbDateUtil.getBeginDayOfYear());
        d10.setEndDate(AbDateUtil.getEndDayOfYear());
        CurrentDate.add(d10);

        TimeDataBean d11=new TimeDataBean();
        d11.setType(11);
        d11.setDateTitle("去 年");
        d11.setStartDate(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        d11.setEndDate(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        CurrentDate.add(d11);

        return CurrentDate;
    }

    public static List<TimeDataBean> getCurrentDates(){
        CurrentDates=new ArrayList<>();

        TimeDataBean d=new TimeDataBean();
        d.setType(0);
        d.setDateTitle("上一天");
        d.setStartDate(AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"));
        d.setEndDate(AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"));
        CurrentDates.add(d);

        TimeDataBean d1=new TimeDataBean();
        d1.setType(1);
        d1.setDateTitle("下一天");
        d1.setStartDate(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        d1.setEndDate(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        CurrentDates.add(d1);

        TimeDataBean d2=new TimeDataBean();
        d2.setType(2);
        d2.setDateTitle("最近14天");
        d2.setStartDate(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        d2.setEndDate(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        CurrentDates.add(d2);

        TimeDataBean d3=new TimeDataBean();
        d3.setType(3);
        d3.setDateTitle("最近30天");
        d3.setStartDate(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        d3.setEndDate(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        CurrentDates.add(d3);

        TimeDataBean d4=new TimeDataBean();
        d4.setType(4);
        d4.setDateTitle("上一周");
        d4.setStartDate(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        d4.setEndDate(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        CurrentDates.add(d4);

        TimeDataBean d5=new TimeDataBean();
        d5.setType(5);
        d5.setDateTitle("下一周");
        d5.setStartDate(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        d5.setEndDate(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        CurrentDates.add(d5);

        TimeDataBean d6=new TimeDataBean();
        d6.setType(6);
        d6.setDateTitle("上一月");
        d6.setStartDate(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        d6.setEndDate(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        CurrentDates.add(d6);

        TimeDataBean d7=new TimeDataBean();
        d7.setType(7);
        d7.setDateTitle("下一月");
        d7.setStartDate(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        d7.setEndDate(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        CurrentDates.add(d7);

        TimeDataBean d8=new TimeDataBean();
        d8.setType(8);
        d8.setDateTitle("上一季");
        d8.setStartDate(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        d8.setEndDate(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        CurrentDates.add(d8);

        TimeDataBean d9=new TimeDataBean();
        d9.setType(9);
        d9.setDateTitle("下一季");
        d9.setStartDate(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        d9.setEndDate(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        CurrentDates.add(d9);

        TimeDataBean d10=new TimeDataBean();
        d10.setType(10);
        d10.setDateTitle("上一年");
        d10.setStartDate(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        d10.setEndDate(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        CurrentDates.add(d10);

        TimeDataBean d11=new TimeDataBean();
        d11.setType(11);
        d11.setDateTitle("下一年");
        d11.setStartDate(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        d11.setEndDate(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        CurrentDates.add(d11);

        return CurrentDates;
    }
}
