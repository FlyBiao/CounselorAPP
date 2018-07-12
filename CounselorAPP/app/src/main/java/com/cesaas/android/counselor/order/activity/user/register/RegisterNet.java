package com.cesaas.android.counselor.order.activity.user.register;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultRegistCompanyBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import junit.framework.Test;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2017/5/5 16:48
 * Version 1.0
 */

public class RegisterNet extends BaseNet {

    public RegisterNet(Context context) {
        super(context, true);
        this.uri="User/Sw/Regist/Regist";
    }

    public void setData(String remark,String mobile,String code,String name,String authCode,String ShopId){
        try {
            data.put("remark", remark);
            data.put("mobile", mobile);
            data.put("code", code);
            data.put("name", name);
            data.put("authCode",authCode);
            data.put("ShopId",ShopId);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        } catch (Exception e) {
            e.printStackTrace();
        }

        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Gson gson=new Gson();
        ResultRegistCompanyBean bean=gson.fromJson(rJson, ResultRegistCompanyBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG, "err:"+err+"=="+e);
    }
}
