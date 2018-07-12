package com.cesaas.android.counselor.order.signin.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.signin.bean.ResultGetSignInTypeBean;
import com.cesaas.android.counselor.order.signin.bean.ResultSigninBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取签到类型Net
 * Created 2017/3/20 14:48
 * Version 1.zero
 */
public class GetSignInTypeNet extends BaseNet {
    public GetSignInTypeNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/SignIn/GetSignInType";
    }

    public void setData(){
        try{
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
        ResultGetSignInTypeBean bean=gson.fromJson(rJson,ResultGetSignInTypeBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
