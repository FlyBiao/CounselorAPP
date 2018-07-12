package com.cesaas.android.counselor.order.activity.user.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.activity.user.bean.ResultSendResetCodeBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.SendCodeBaseNet;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 发送短信重置密码
 * Created at 2017/5/14 16:56
 * Version 1.0
 */

public class SendResetCodeNet extends SendCodeBaseNet {
    public SendResetCodeNet(Context context) {
        super(context, true);
        this.uri="User/Sw/Regist/SendResetCode";
    }

    public void setData(String Mobile){
        try{
            data.put("Mobile",Mobile);
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
        ResultSendResetCodeBean bean= JsonUtils.fromJson(rJson,ResultSendResetCodeBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"发送短信重置密码ERROR:"+err);
    }
}
