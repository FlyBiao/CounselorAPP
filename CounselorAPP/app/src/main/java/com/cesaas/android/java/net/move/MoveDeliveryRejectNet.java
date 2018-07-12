package com.cesaas.android.java.net.move;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryConfirmBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryRejectBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 调拨驳回
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class MoveDeliveryRejectNet extends TestBaseNet {

    private int type;
    public MoveDeliveryRejectNet(Context context, int type) {
        super(context, true);
        this.type=type;
        if(type==1){
            this.uri="drp/refundDeliveryReject";
        }else{
            this.uri="drp/moveDeliveryReject";
        }
    }

    public void setData(long id){
        try {
            data.put("id",id);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultMoveDeliveryRejectBean bean= JsonUtils.fromJson(rJson,ResultMoveDeliveryRejectBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
