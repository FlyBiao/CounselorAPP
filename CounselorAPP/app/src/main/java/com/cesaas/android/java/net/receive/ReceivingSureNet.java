package com.cesaas.android.java.net.receive;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryAddBoxBean;
import com.cesaas.android.java.bean.receive.ResultReceivingSureBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 收货确认
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class ReceivingSureNet extends TestBaseNet {

    public ReceivingSureNet(Context context) {
        super(context, true);
        this.uri="drp/receivingSure";
    }

    public void setData(long pId){
        try {
            data.put("id",pId);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultReceivingSureBean bean= JsonUtils.fromJson(rJson,ResultReceivingSureBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
