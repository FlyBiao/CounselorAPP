package com.cesaas.android.counselor.order.statistics.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.statistics.bean.GetchievementReportBean;
import com.cesaas.android.counselor.order.statistics.bean.ResultGetchievementReportBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import junit.framework.Test;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 根据报表业务设计图获取当天业务报表数据
 * Created 2017/3/13 18:50
 * Version 1.zero
 */
public class GetchievementReportNet extends BaseNet {
    public GetchievementReportNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/AchievementReport/GetchievementReport";
    }

    public void setData(){
        try{

            data.put("UserTicket",
                    AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Gson gson=new Gson();
        ResultGetchievementReportBean bean=gson.fromJson(rJson,ResultGetchievementReportBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"报数："+err+"=="+e);
    }
}
