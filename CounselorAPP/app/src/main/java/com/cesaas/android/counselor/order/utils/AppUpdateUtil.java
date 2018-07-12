package com.cesaas.android.counselor.order.utils;

import android.content.Context;

import com.cesaas.android.counselor.order.R;
import com.kcode.lib.UpdateWrapper;

/**
 * Author FGB
 * Description
 * Created at 2018/3/16 11:56
 * Version 1.0
 */

public class AppUpdateUtil {

    public static void checkUpdate(Context ct){
        UpdateWrapper updateWrapper = new UpdateWrapper.Builder(ct)
                //set interval Time
                .setTime(5 * 60 * 1000)
                //set notification icon
                .setNotificationIcon(R.mipmap.ic_launcher)
                //set update file url
                .setUrl("http://m.cesaas.com/Dowlond/Android/update.json").build();
        updateWrapper.start();
    }
}
