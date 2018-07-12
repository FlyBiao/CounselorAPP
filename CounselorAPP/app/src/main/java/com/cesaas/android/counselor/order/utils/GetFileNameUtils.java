package com.cesaas.android.counselor.order.utils;
/**
 * Author FGB
 * Description 从文件路径中提取文件名称
 * Created at 2017/6/9 16:27
 * Version 1.0
 */

public class GetFileNameUtils {

    /**
     * 从路径中提取文件名
     * @param pathandname
     * @return
     */
    public static String getFileName(String pathandname,AbPrefsUtil pre){
        StringBuilder sb=new StringBuilder();
        sb.append("images").append("/").append(pre.getString("VipId")).append("/").append(AbDateUtil.getFileDateYMD(AbDateUtil.getCurrentDate("yyyy/MM/dd HH:mm:ss"))).append("/").append(pathandname.substring(pathandname.lastIndexOf("/")+1));
        return sb.toString();
    }

    /**
     * 从路径中提取文件名
     * @param pathandname
     * @return
     */
    public static String getVoiceFileName(String pathandname,AbPrefsUtil pre){
        StringBuilder sb=new StringBuilder();
        sb.append("voice").append("/").append(pre.getString("VipId")).append("/").append(AbDateUtil.getFileDateYMD(AbDateUtil.getCurrentDate("yyyy/MM/dd HH:mm:ss"))).append("/").append(pathandname.substring(pathandname.lastIndexOf("/")+1));
        return sb.toString();
    }

}
