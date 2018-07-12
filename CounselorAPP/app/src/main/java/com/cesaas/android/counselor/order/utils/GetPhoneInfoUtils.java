package com.cesaas.android.counselor.order.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Author FGB
 * Description 获取手机信息
 * Created at 2018/5/8 10:36
 * Version 1.0
 */

public class GetPhoneInfoUtils {

    /**
     * 获取手机设备信息
     * @return
     */
    public static String getPhoneInfo(Context ct){
        TelephonyManager tm = (TelephonyManager)ct.getSystemService(Context.TELEPHONY_SERVICE);
        return  tm.getDeviceId();
    }
}
