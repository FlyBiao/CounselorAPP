package com.cesaas.android.counselor.order.net.okhttp.bena;

/**
 * Author FGB
 * Description
 * Created 2017/3/19 14:21
 * Version 1.zero
 */
public class Counselor {

    /**
     * apkName : Counselor.apk
     * appName : 应客助手
     * newInfo : 1.全新UI操作
     2.增加店员管理模块
     3.增加店务管理模块
     * verCode : 3
     */
    private String apkName;
    private String appName;
    private String newInfo;
    private int verCode;

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setNewInfo(String newInfo) {
        this.newInfo = newInfo;
    }

    public void setVerCode(int verCode) {
        this.verCode = verCode;
    }

    public String getApkName() {
        return apkName;
    }

    public String getAppName() {
        return appName;
    }

    public String getNewInfo() {
        return newInfo;
    }

    public int getVerCode() {
        return verCode;
    }
}
