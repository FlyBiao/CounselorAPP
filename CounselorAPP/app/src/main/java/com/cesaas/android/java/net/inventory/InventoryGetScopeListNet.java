package com.cesaas.android.java.net.inventory;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryGetScopeListBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 盘点货架列表
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class InventoryGetScopeListNet extends TestBaseNet {

    public InventoryGetScopeListNet(Context context) {
        super(context, true);
        this.uri="drp/inventoryGetScopeList";
    }

    public void setData(long pid){
        try {
            data.put("pId",pid);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultInventoryGetScopeListBean bean= JsonUtils.fromJson(rJson,ResultInventoryGetScopeListBean.class);
        EventBus.getDefault().post(bean);

    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
