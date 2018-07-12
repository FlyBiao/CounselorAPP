package com.cesaas.android.java.net.move;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.move.MoveDeliveryDeleteByBarcodeNoBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 删除条码数量
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class MoveDeliveryDeleteByBarcodeNoNet extends TestBaseNet {

    private int type;
    public MoveDeliveryDeleteByBarcodeNoNet(Context context,int type) {
        super(context, true);
        this.type=type;
        if(type==1){
            this.uri="drp/refundDeliveryDeleteByBarcodeNo";
        }else{
            this.uri="drp/moveDeliveryDeleteByBarcodeNo";
        }
    }

    public void setData(long pId,long boxId,String barcodeNo){
        try {
            data.put("pId",pId);
            data.put("boxId",boxId);
            data.put("barcodeNo",barcodeNo);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        MoveDeliveryDeleteByBarcodeNoBean bean= JsonUtils.fromJson(rJson,MoveDeliveryDeleteByBarcodeNoBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
