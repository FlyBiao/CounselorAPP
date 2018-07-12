package com.cesaas.android.java.net.move;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryAddBean;
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

public class MoveDeliveryAddNet extends TestBaseNet {

    private int type;
    public MoveDeliveryAddNet(Context context,int type) {
        super(context, true);
        this.type=type;
        if(type==1){
            this.uri="drp/refundDeliveryAdd";
        }else{
            this.uri="drp/moveDeliveryAdd";
        }
    }

    public void setData(JSONObject model){
        try {
            data.put("model",model);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultMoveDeliveryAddBean bean= JsonUtils.fromJson(rJson,ResultMoveDeliveryAddBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
