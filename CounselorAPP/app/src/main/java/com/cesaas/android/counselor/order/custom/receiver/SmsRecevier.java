package com.cesaas.android.counselor.order.custom.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Author FGB
 * Description 短信广播接收者
 * Created at 2017/5/7 0:09
 * Version 1.0
 */

public class SmsRecevier extends BroadcastReceiver {
    private static final String TAG = "SmsRecevier";
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        for(Object pdu:pdus){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])pdu);
            String body = smsMessage.getDisplayMessageBody();
            Log.i(TAG, body);
        }
    }
}
