package com.cesaas.android.java.net.move;

import android.content.Context;

import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryListBeanBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 调货发货列表
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class MoveDeliveryListNet extends TestBaseNet {

    private int type;

    public MoveDeliveryListNet(Context context,int type) {
        super(context, true);
        this.type=type;
        if(type==1){
            this.uri="drp/refundDeliveryList";
        }else{
            this.uri="drp/moveDeliveryList";
        }
    }


    public void setData(int pageIndex){
        try {
            data.put("start", pageIndex);
            data.put("limit", Constant.PAGE_SIZE);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    public void setData(int pageIndex, JSONObject filterConditionList){
        try {
            data.put("start", pageIndex);
            data.put("limit", Constant.PAGE_SIZE);
            data.put("filterConditionList",filterConditionList);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultMoveDeliveryListBeanBean bean= JsonUtils.fromJson(rJson,ResultMoveDeliveryListBeanBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
