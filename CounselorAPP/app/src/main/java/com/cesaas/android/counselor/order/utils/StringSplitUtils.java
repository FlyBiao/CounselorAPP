package com.cesaas.android.counselor.order.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Author FGB
 * Description 根据逗号分隔String工具类
 * Created 2017/4/12 18:06
 * Version 1.zero
 */
public class StringSplitUtils {

    /**
     * 字符串拼接
     * @param collectIds 字符集合
     * @return String
     */
    public static String  stringSplite(List<String> collectIds){
        String str = collectIds.toString();
        int len = str.length() - 1;
        String ids = str.substring(1, len).replace(" ", "");//"keyids":”1,2,3”
        return ids;
    }

    /**
     * //list的结果就是[113,123,123,123]
     */
    public static List<String> listSplit(){
        String str="112,123,123,123";//根据逗号分隔到List数组中
        String str2=str.replace(" ", "");//去掉所用空格
        List<String> list= Arrays.asList(str2.split(","));
        return list;
    }
}
