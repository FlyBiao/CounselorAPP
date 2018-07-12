package com.cesaas.android.java.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.inventory.ResultDeliverySetExpressBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 设置（修改）快递
 * Created at 2018/6/1 10:00
 * Version 1.0
 */

public class DeliverySetExpressNet extends TestBaseNet {

    private int type;
    public DeliverySetExpressNet(Context context,int type) {
        super(context, true);
        this.type=type;
        if(type==1){
            this.uri="drp/refundDeliverySetExpress";
        }else{
            this.uri="drp/moveDeliverySetExpress";
        }
    }

    public void setData(long boxId,String expressName,String expressNo,String expressRemark){
        try {
            data.put("boxId",boxId);
            data.put("expressName",expressName);
            data.put("expressNo",expressNo);
            data.put("expressRemark",expressRemark);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultDeliverySetExpressBean bean= JsonUtils.fromJson(rJson,ResultDeliverySetExpressBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
