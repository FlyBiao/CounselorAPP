package com.cesaas.android.counselor.order.member.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.ResultSendMessageBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 短信群发
 * Created 2017/4/10 11:19
 * Version 1.zero
 */
public class SendSmsMsgNet extends BaseNet{
    public SendSmsMsgNet(Context context) {
        super(context, true);
        this.uri="User/Sw/Msg/SendSmsmsgs";
    }

    public void setData(String Title,String Msg,String Mobile){
        try{
            data.put("Title",Title);
            data.put("Msg",Msg);
            data.put("Mobiles",Mobile);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultSendMessageBean bean= JsonUtils.fromJson(rJson,ResultSendMessageBean.class);
        EventBus.getDefault().post(bean);
}

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
