package com.cesaas.android.counselor.order.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Author FGB
 * Description 拨打电话工具类
 * Created 2017/4/10 10:47
 * Version 1.zero
 */
public class CallUtils {

    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    public static void call(String phone, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }


    public static void phoneListen(Context ct,Activity activity){
        TelephonyManager phoneManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        phoneManager.listen(new PhoneListenUtils(ct),
                PhoneStateListener.LISTEN_CALL_STATE);
    }
}
