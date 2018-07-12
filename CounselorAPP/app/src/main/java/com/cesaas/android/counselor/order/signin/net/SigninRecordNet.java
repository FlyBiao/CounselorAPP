package com.cesaas.android.counselor.order.signin.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.signin.bean.ResultSigninRecordBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import junit.framework.Test;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created 2017/3/20 9:48
 * Version 1.zero
 */
public class SigninRecordNet  extends BaseNet {
    public SigninRecordNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/SignIn/SelectSignIn";
    }

    public void setData(int PageIndex,String StartTime,String EndTime){
        try{
            data.put("PageIndex",PageIndex);
            data.put("StartTime",StartTime);
            data.put("EndTime",EndTime);
            data.put("PageSize",20);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Gson gson=new Gson();
        ResultSigninRecordBean bean=gson.fromJson(rJson,ResultSigninRecordBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
