package com.cesaas.android.java.net.inventory;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryGetGoodsListBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryGetSubListBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取盘点单款式列表
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class InventoryGetSubListNet extends TestBaseNet {

    public InventoryGetSubListNet(Context context) {
        super(context, true);
        this.uri="drp/inventoryGetSubList";
    }

    public void setData(JSONObject filterConditionList){
        try {
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
        ResultInventoryGetSubListBean bean= JsonUtils.fromJson(rJson,ResultInventoryGetSubListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
