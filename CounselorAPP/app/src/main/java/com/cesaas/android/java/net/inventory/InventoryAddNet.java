package com.cesaas.android.java.net.inventory;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryAddBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 新增盘点单
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class InventoryAddNet extends TestBaseNet {

    public InventoryAddNet(Context context) {
        super(context, true);
        this.uri="drp/inventoryAdd";
    }

    public void setData(long shopId,String shopName,String pdDate,int type){
        try {
            data.put("shopId",shopId);
            data.put("shopName",shopName);
            data.put("pdDate",pdDate);
            data.put("type",type);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultInventoryAddBean bean= JsonUtils.fromJson(rJson,ResultInventoryAddBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
