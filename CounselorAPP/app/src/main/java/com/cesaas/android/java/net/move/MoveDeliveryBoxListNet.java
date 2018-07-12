package com.cesaas.android.java.net.move;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryBoxListBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryBoxListsBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 装箱列表
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class MoveDeliveryBoxListNet extends TestBaseNet {

    private int type;
    private int ts;
    public MoveDeliveryBoxListNet(Context context,int type,int ts) {
        super(context, true);
        this.type=type;
        this.ts=ts;
        if(type==1){
            this.uri="drp/refundDeliveryBoxList";
        }else{
            this.uri="drp/moveDeliveryBoxList";
        }

    }

    public void setData(long pId){
        try {
            data.put("pId",pId);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        if(ts==1){
            ResultMoveDeliveryBoxListsBean bean= JsonUtils.fromJson(rJson,ResultMoveDeliveryBoxListsBean.class);
            EventBus.getDefault().post(bean);

        }else{
            ResultMoveDeliveryBoxListBean bean= JsonUtils.fromJson(rJson,ResultMoveDeliveryBoxListBean.class);
            EventBus.getDefault().post(bean);
        }

    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
