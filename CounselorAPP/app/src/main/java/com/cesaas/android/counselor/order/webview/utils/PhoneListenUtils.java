package com.cesaas.android.counselor.order.webview.utils;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.cesaas.android.counselor.order.member.net.SaveVisitRecordNet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author FGB
 * Description
 * Created at 2017/5/21 10:10
 * Version 1.0
 */

public class PhoneListenUtils extends PhoneStateListener {
    private final Context context;
    //获取本次通话的时间(单位:秒)
    int time = 0;
    //判断是否正在通话
    boolean isCalling;
    //控制循环是否结束
    boolean isFinish;
    private ExecutorService service;

    private String VipId;
    private String VipName;

    public PhoneListenUtils(Context context,String VipId,String VipName) {
        this.context = context;
        this.VipId=VipId;
        this.VipName=VipName;
        service = Executors.newSingleThreadExecutor();
    }
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                if (isCalling) {
                    isCalling = false;
                    isFinish = true;
                    service.shutdown();
//                        Toast.makeText(context, "本次通话" + time + "秒",
//                                Toast.LENGTH_LONG).show();
                    //调用并保存回访通话时长记录
                    SaveVisitRecordNet recordNet=new SaveVisitRecordNet(context);
                    recordNet.setData(Integer.parseInt(VipId),VipName,1,time+"");

                    time = 0;
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                isCalling = true;
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        while (!isFinish) {
                            try {
                                Thread.sleep(1000);
                                time++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                isFinish = false;
                if(service.isShutdown()){
                    service = Executors.newSingleThreadExecutor();
                }
                break;
        }
    }
}
