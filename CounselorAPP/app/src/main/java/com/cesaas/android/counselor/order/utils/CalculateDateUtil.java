package com.cesaas.android.counselor.order.utils;
import com.cesaas.android.counselor.order.utils.bean.DayCompare;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author FGB
 * Description
 * Created at 2018/3/12 14:25
 * Version 1.0
 */

public class CalculateDateUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Calendar startDate = Calendar.getInstance();
    private static Calendar endDate = Calendar.getInstance();
    private static DateFormat df = DateFormat.getDateInstance();
    private static Date earlydate = new Date();
    private static Date latedate = new Date();

    /**
     * 计算两个时间相差多少个年
     *
     * @param
     * @param
     * @return
     * @throws ParseException
     */
    public static int yearsBetween(String start, String end) throws ParseException {
        startDate.setTime(sdf.parse(start));
        endDate.setTime(sdf.parse(end));
        return (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR));
    }

    /**
     * 计算两个时间相差多少个月
     *
     * @param
     *            <String>
     * @param
     *            <String>
     * @return int
     * @throws ParseException
     */
    public static int monthsBetween(String start, String end) throws ParseException {
        startDate.setTime(sdf.parse(start));
        endDate.setTime(sdf.parse(end));
        int result = yearsBetween(start, end) * 12 + endDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH);
        return result == 0 ? 1 : Math.abs(result);

    }

    /**
     * 计算两个时间相差多少个天
     *
     * @param
     * @param
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String start, String end) throws ParseException {
        // 得到两个日期相差多少天
        return hoursBetween(start, end) / 24;
    }

    /**
     * 计算两个时间相差多少小时
     *
     * @param
     * @param
     * @return
     * @throws ParseException
     */
    public static int hoursBetween(String start, String end) throws ParseException {
        // 得到两个日期相差多少小时
        return minutesBetween(start, end) / 60;
    }

    /**
     * 计算两个时间相差多少分
     *
     * @param
     * @param
     * @return
     * @throws ParseException
     */
    public static int minutesBetween(String start, String end) throws ParseException {
        // 得到两个日期相差多少分
        return secondesBetween(start, end) / 60;
    }

    /**
     * 计算两个时间相差多少秒
     *
     * @param
     * @param
     * @return
     * @throws ParseException
     */
    public static int secondesBetween(String start, String end) throws ParseException {
        earlydate = df.parse(start);
        latedate = df.parse(end);
        startDate.setTime(earlydate);
        endDate.setTime(latedate);
        // 设置时间为0时
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        endDate.set(Calendar.HOUR_OF_DAY, 0);
        endDate.set(Calendar.MINUTE, 0);
        endDate.set(Calendar.SECOND, 0);
        // 得到两个日期相差多少秒
        return ((int) (endDate.getTime().getTime() / 1000) - (int) (startDate.getTime().getTime() / 1000));
    }

}
