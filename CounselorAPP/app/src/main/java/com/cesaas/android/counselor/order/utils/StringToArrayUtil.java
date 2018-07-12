package com.cesaas.android.counselor.order.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description String字符串转数组工具类
 * Created 2017/3/21 13:41
 * Version 1.zero
 */
public class StringToArrayUtil {

    /**
     * 字符串转数组方法
     * @param str 原始字符串
     */
    public static String  stringToArrayUtil(String str){

        String[] arrayStr = new String[] {};// 字符数组
        List<String> list = new ArrayList<String>();// list

        arrayStr = str.split(",");// 字符串转字符数组
        list = java.util.Arrays.asList(arrayStr);// 字符数组转list
        String[] storeStr = list.toArray(new String[list.size()]);// list转成字符数组

        /* 输出字符数组的值 */
        for (String s : arrayStr) {
            Log.i("tes","输出字符数组的值:"+s);
        }
        /* 输出list值 */
        for (String s : list) {
            Log.i("tes","输出list值:"+s);
        }
        for (String s : storeStr) {
            Log.i("tes","s:"+s);
        }

        return null;
    }
}
