package com.cesaas.android.java.net.move;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryAddBoxBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 新增一箱
 * Created at 2018/6/1 10:00
 * Version 1.0
 */

public class MoveDeliveryAddBoxNet extends TestBaseNet {

    private int type;
    public MoveDeliveryAddBoxNet(Context context,int type) {
        super(context, true);
        this.type=type;
        if(type==1){
            this.uri="drp/refundDeliveryAddBox";
        }else{
            this.uri="drp/moveDeliveryAddBox";
        }
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
