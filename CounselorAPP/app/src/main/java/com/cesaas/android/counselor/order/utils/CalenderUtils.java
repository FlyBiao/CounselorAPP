package com.cesaas.android.counselor.order.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author FGB
 * Description 日期比较天 月 年
 * Created at 2017/11/29 11:31
 * Version 1.0
 */

public class CalenderUtils {

    public static void testtett(){
        String date = "2016-2-12 22:22:22";

        CalenderUtils.compareDate(date, null, 0);
        CalenderUtils.compareDate(date, null, 1);
        CalenderUtils.compareDate(date, null, 2);
    }


    /**
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式
     * @param date2 被比较的时间  为空(null)则为当前时间
     * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年
     * @return
     */
    public static int compareDate(String date1,String date2,int stype){
        int n = 0;

        String[] u = {"天","月","年"};
        String formatStyle = stype==1?"yyyy-MM HH:mm:ss":"yyyy-MM-dd HH:mm:ss";

        date2 = date2==null?CalenderUtils.getCurrentDate():date2;

        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (Exception e3) {
            System.out.println("wrong occured");
        }
        //List list = new ArrayList();
        while (!c1.after(c2)) {                     // 循环对比，直到相等，n 就是所要的结果
            //list.add(df.format(c1.getTime()));    // 这里可以把间隔的日期存到数组中 打印出来
            n++;
            if(stype==1){
                c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1
            }
            else{
                c1.add(Calendar.DATE, 1);           // 比较天数，日期+1
            }
        }

        n = n-1;

        if(stype==2){
            n = (int)n/365;
        }
        Log.i("test",date1+" -- "+date2+" 相差多少"+u[stype]+":"+n);
        return n;
    }

    /**
     * 得到当前日期
     * @return
     */
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simple.format(date);

    }

}
