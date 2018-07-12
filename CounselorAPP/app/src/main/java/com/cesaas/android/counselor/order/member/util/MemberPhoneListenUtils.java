package com.cesaas.android.counselor.order.member.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.cesaas.android.counselor.order.activity.main.NewMainActivity;
import com.cesaas.android.counselor.order.member.bean.service.multipleservice.MultipleServiceBean;
import com.cesaas.android.counselor.order.member.net.SaveVisitRecordNet;
import com.cesaas.android.counselor.order.member.service.MemberServiceResultActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceResultActivitys;
import com.cesaas.android.counselor.order.utils.Skip;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author FGB
 * Description
 * Created at 2017/5/21 10:10
 * Version 1.0
 */

public class MemberPhoneListenUtils extends PhoneStateListener {
    private final Context context;
    private Activity mActivity;
    //获取本次通话的时间(单位:秒)
    int time = 0;
    //判断是否正在通话
    boolean isCalling;
    //控制循环是否结束
    boolean isFinish;
    private ExecutorService service;

    private int VipId;
    private String VipName;
    private String Phone;
    private String sex;
    private int id;
    private List<MultipleServiceBean> mData;

    public MemberPhoneListenUtils(Context context, Activity mActivity,int VipId, String VipName,String Phone,String sex,int id,List<MultipleServiceBean> mData) {
        this.context = context;
        this.VipId=VipId;
        this.VipName=VipName;
        this.id=id;
        this.Phone=Phone;
        this.sex=sex;
        this.mActivity=mActivity;
        this.mData=mData;
        service = Executors.newSingleThreadExecutor();
    }

    public MemberPhoneListenUtils(Context context, Activity mActivity,int VipId, String VipName,String Phone,String sex,int id) {
        this.context = context;
        this.VipId=VipId;
        this.VipName=VipName;
        this.id=id;
        this.Phone=Phone;
        this.sex=sex;
        this.mActivity=mActivity;
        service = Executors.newSingleThreadExecutor();
    }

    public MemberPhoneListenUtils(Context context, Activity mActivity,int VipId, String VipName,String Phone) {
        this.context = context;
        this.VipId=VipId;
        this.VipName=VipName;
        this.Phone=Phone;
        this.mActivity=mActivity;
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
//                    if(VipId!=0){
//                        Bundle bundle=new Bundle();
//                        bundle.putInt("Id",VipId);
//                        bundle.putString("Name",VipName);
//                        bundle.putString("Phone",Phone);
//                        bundle.putString("sex","1");
//                        bundle.putSerializable("MultipleServiceList", (Serializable)mData);
//                        Skip.mNextFroData(mActivity, MemberServiceResultActivitys.class,bundle);
//                    }else{
//                        //跳转到添加服务结果页面
//                        Bundle bundle=new Bundle();
//                        bundle.putInt("Id",id);
//                        bundle.putString("Name",VipName);
//                        bundle.putString("Phone",Phone);
//                        bundle.putString("sex",sex);
////                        bundle.putSerializable("MultipleServiceList", (Serializable)mData);
//                        Skip.mNextFroData(mActivity, MemberServiceResultActivity.class,bundle);
//                        time = 0;
//                    }

                    Skip.mNext(mActivity,NewMainActivity.class,true);
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
