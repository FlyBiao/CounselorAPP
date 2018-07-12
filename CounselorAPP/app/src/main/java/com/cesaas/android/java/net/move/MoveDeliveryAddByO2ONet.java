package com.cesaas.android.java.net.move;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryAddBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryAddByO2OBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 生成o2o调拨单(直接生成发货单和收货单并更新库存)
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class MoveDeliveryAddByO2ONet extends TestBaseNet {

    public MoveDeliveryAddByO2ONet(Context context) {
        super(context, true);
        this.uri="drp/moveDeliveryAddByO2O";
    }

    public void setData(JSONObject model, JSONObject items){
        try {
            data.put("model",model);
            data.put("items",items);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultMoveDeliveryAddByO2OBean bean= JsonUtils.fromJson(rJson,ResultMoveDeliveryAddByO2OBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
