package com.cesaas.android.counselor.order.activity.user.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.activity.user.bean.ResultMessageBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 消息
 * Created at 2017/5/22 16:02
 * Version 1.0
 */

public class MessageNet extends BaseNet {
    public MessageNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/Sw/SmSNotify/MsgList";
    }

    public void setData(int NotifyType){
        try{
            data.put("NotifyType",NotifyType);//1.Task 2.System 3.Logistics
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
        ResultMessageBean bean= JsonUtils.fromJson(rJson,ResultMessageBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"消息err:"+err);
    }
}
