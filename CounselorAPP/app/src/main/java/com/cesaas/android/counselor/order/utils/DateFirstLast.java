package com.cesaas.android.counselor.order.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Author FGB
 * Description 获取当前月第一天，最后一天；上个月第一天，最后一天；下个月第一天，最有一天。
 * Created at 2017/11/1 14:16
 * Version 1.0
 */

public class DateFirstLast {

    public static SimpleDateFormat df =null;
    public static Date date = null;

    /**
     * 上月最后一天
     */
    public static String a_day_last(String date1){
        df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = df.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar1 = Calendar.getInstance();  // 使用默认时区和语言环境获得一个日历。
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();

        calendar1.setTime(date);  //setTime(Date date) 使用给定的Date设置此Calendar的时间。
        calendar2.setTime(date);
        calendar3.setTime(date);

        calendar1.add(Calendar.MONTH, -1);    //上A
        calendar2.add(Calendar.MONTH, 0);     //<span style="font-family: Arial, Helvetica, sans-serif;">中C</span>
        calendar3.add(Calendar.MONTH, 1);     //<span style="font-family: Arial, Helvetica, sans-serif;">下B</span>

        calendar1.add(Calendar.MONTH, 1);        //加一个月
        calendar1.set(Calendar.DATE, 1);         //设置为该月第一天
        calendar1.add(Calendar.DATE, -1);        //再减一天即为上个月最后一天
        String a_day_last = df.format(calendar1.getTime());
        StringBuffer endStra = new StringBuffer().append(a_day_last);
        a_day_last = endStra.toString();
        return a_day_last;
    }

    /**
     * 本月第一天
     */
    public static String b_day_last(){
        Calendar calendar1 = Calendar.getInstance();  // 使用默认时区和语言环境获得一个日历。
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();

        calendar1.setTime(date);  //setTime(Date date) 使用给定的Date设置此Calendar的时间。
        calendar2.setTime(date);
        calendar3.setTime(date);

        calendar1.add(Calendar.MONTH, -1);    //上A
        calendar2.add(Calendar.MONTH, 0);     //<span style="font-family: Arial, Helvetica, sans-serif;">中C</span>
        Date Date2 = calendar2.getTime();

        calendar3.add(Calendar.MONTH, 1);     //<span style="font-family: Arial, Helvetica, sans-serif;">下B</span>

        GregorianCalendar gc2 = (GregorianCalendar) Calendar.getInstance();
        gc2.setTime(Date2);
        gc2.set(Calendar.DAY_OF_MONTH, 1);          //设置该月的第一天
        String b_day_first = df.format(gc2.getTime());
        StringBuffer str_b = new StringBuffer().append(b_day_first);
        b_day_first = str_b.toString();

        return b_day_first;
    }

    //获取指定日期【年】	2016-12-01
    public  static String getDateYear(String date){

        Date strDate;
        StringBuilder sb=new StringBuilder();
        try {
            strDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
            SimpleDateFormat sdf2= new SimpleDateFormat("dd");
            String str1 = sdf0.format(strDate);
            sb.append(str1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }


    //获取指定日期【月】	2016-12-01
    public  static String getDateMonth(String date){

        Date strDate;
        StringBuilder sb=new StringBuilder();
        try {
            strDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
            SimpleDateFormat sdf2= new SimpleDateFormat("dd");
            String str1 = sdf0.format(strDate);
            String str2 = sdf1.format(strDate);
            String str3 = sdf2.format(strDate);
            sb.append(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    //获取指定日期【日】	2016-12-01
    public  static String getDateDay(String date){

        Date strDate;
        StringBuilder sb=new StringBuilder();
        try {
            strDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
            SimpleDateFormat sdf2= new SimpleDateFormat("dd");
            String str1 = sdf0.format(strDate);
            String str2 = sdf1.format(strDate);
            String str3 = sdf2.format(strDate);
            sb.append(str3);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }


    public static Map<String, String> getDate(String date1) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(date1);

        Calendar calendar1 = Calendar.getInstance();  // 使用默认时区和语言环境获得一个日历。
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();

        calendar1.setTime(date);  //setTime(Date date) 使用给定的Date设置此Calendar的时间。
        calendar2.setTime(date);
        calendar3.setTime(date);

        calendar1.add(Calendar.MONTH, -1);    //上A
        Date Date1 = calendar1.getTime();

        calendar2.add(Calendar.MONTH, 0);     //<span style="font-family: Arial, Helvetica, sans-serif;">中C</span>

        Date Date2 = calendar2.getTime();

        calendar3.add(Calendar.MONTH, 1);     //<span style="font-family: Arial, Helvetica, sans-serif;">下B</span>

        Date Date3 = calendar3.getTime();


        /**
         * 上月第1天
         */
        //GregorianCalendar 是 Calendar 的一个具体子类
        GregorianCalendar gc1 = (GregorianCalendar) Calendar.getInstance();
        gc1.setTime(Date1);
        gc1.set(Calendar.DAY_OF_MONTH, 1);      //设置该月的第一天
        String a_day_first = df.format(gc1.getTime());
//        StringBuffer str_a = new StringBuffer().append(a_day_first).append(" 00:00:00");    //拼接 时分秒
        StringBuffer str_a = new StringBuffer().append(a_day_first);    //拼接 时分秒
        a_day_first = str_a.toString();
        Log.i("test","上月的第一天\t"+a_day_first);
        System.out.println("上月的第一天\t"+a_day_first);

        /**
         * 上月最后一天
         */
        calendar1.add(Calendar.MONTH, 1);        //加一个月
        calendar1.set(Calendar.DATE, 1);         //设置为该月第一天
        calendar1.add(Calendar.DATE, -1);        //再减一天即为上个月最后一天
        String a_day_last = df.format(calendar1.getTime());
//        StringBuffer endStra = new StringBuffer().append(a_day_last).append(" 23:59:59");//拼接 时分秒
        StringBuffer endStra = new StringBuffer().append(a_day_last);//拼接 时分秒
        a_day_last = endStra.toString();
        Log.i("test","上月最后一天\t"+a_day_last);
        System.out.println("上月最后一天\t"+a_day_last);

        /**
         * 本月第一天
         */
        GregorianCalendar gc2 = (GregorianCalendar) Calendar.getInstance();
        gc2.setTime(Date2);
        gc2.set(Calendar.DAY_OF_MONTH, 1);          //设置该月的第一天
        String b_day_first = df.format(gc2.getTime());
//        StringBuffer str_b = new StringBuffer().append(b_day_first).append(" 00:00:00");//拼接 时分秒
        StringBuffer str_b = new StringBuffer().append(b_day_first);//拼接 时分秒
        b_day_first = str_b.toString();
        System.out.println("本月的第一天\t"+b_day_first);

        /**
         * 本月最后一天
         */
        calendar2.add(Calendar.MONTH, 1);        //加一个月
        calendar2.set(Calendar.DATE, 1);         //设置为该月第一天
        calendar2.add(Calendar.DATE, -1);        //再减一天即为上个月最后一天
        String b_day_last= df.format(calendar2.getTime());
//        StringBuffer endStrb = new StringBuffer().append(b_day_last).append(" 23:59:59");   //拼接 时分秒
        StringBuffer endStrb = new StringBuffer().append(b_day_last);   //拼接 时分秒
        b_day_last = endStrb.toString();
        System.out.println("本月最后一天\t"+b_day_last);

        /**
         * 下月第1天
         */
        GregorianCalendar gc3 = (GregorianCalendar) Calendar.getInstance();
        gc3.setTime(Date3);
        gc3.set(Calendar.DAY_OF_MONTH, 1);
        String c_day_first = df.format(gc3.getTime());
//        StringBuffer str_c = new StringBuffer().append(c_day_first).append(" 00:00:00");//拼接 时分秒
        StringBuffer str_c = new StringBuffer().append(c_day_first);//拼接 时分秒
        c_day_first = str_c.toString();
        System.out.println("下月的第一天\t"+c_day_first);

        /**
         * 下月最后1天
         */
        calendar3.add(Calendar.MONTH, 1);        //加一个月
        calendar3.set(Calendar.DATE, 1);         //设置为该月第一天
        calendar3.add(Calendar.DATE, -1);        //再减一天即为上个月最后一天
        String c_day_last= df.format(calendar3.getTime());
//        StringBuffer endStrc = new StringBuffer().append(c_day_last).append(" 23:59:59"); //拼接 时分秒
        StringBuffer endStrc = new StringBuffer().append(c_day_last); //拼接 时分秒
        c_day_last = endStrc.toString();
        System.out.println("下月最后一天\t"+c_day_last);

        Map<String, String> map = new HashMap<String, String>();

        map.put("a_day_first", a_day_first);
        map.put("a_day_last", a_day_last);

        map.put("b_day_first", b_day_first);
        map.put("b_day_last", b_day_last);


        map.put("c_day_first", c_day_first);
        map.put("c_day_last", c_day_last);
        return map;
    }

//    public static void main(String[] args) throws ParseException {
//        Map<String, String> map  =   getDate("2017-11-01");
//        System.out.println(map);
//
//    }
}
