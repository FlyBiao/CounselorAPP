package com.cesaas.android.java.net.receive;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.receive.ResultReceivingSyncNumBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 直接按箱收货
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class ReceivingSyncNumNet extends TestBaseNet {

    public ReceivingSyncNumNet(Context context) {
        super(context, true);
        this.uri="drp/receivingSyncNum";
    }

    public void setData(long pId,long boxId,int type){
        try {
            data.put("pId",pId);
            data.put("boxId",boxId);
            data.put("type",type);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultReceivingSyncNumBean bean= JsonUtils.fromJson(rJson,ResultReceivingSyncNumBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
