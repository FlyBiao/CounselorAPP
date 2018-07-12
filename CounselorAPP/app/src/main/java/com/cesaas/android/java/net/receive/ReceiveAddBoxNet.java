package com.cesaas.android.java.net.receive;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryAddBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryAddBoxBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 新增调拨发货
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class ReceiveAddBoxNet extends TestBaseNet {

    public ReceiveAddBoxNet(Context context) {
        super(context, true);
        this.uri="drp/receiveAddBox";
    }

    public void setData(long pId,String boxNo){
        try {
            data.put("pId",pId);
            data.put("boxNo",boxNo);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultMoveDeliveryAddBoxBean bean= JsonUtils.fromJson(rJson,ResultMoveDeliveryAddBoxBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
