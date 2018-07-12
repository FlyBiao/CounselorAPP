package com.cesaas.android.java.net.inventory;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryCheckBean;
import com.cesaas.android.java.bean.inventory.ResultInventorySubmitBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 确认盘点
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class InventoryCheckNet extends TestBaseNet {

    public InventoryCheckNet(Context context) {
        super(context, true);
        this.uri="drp/inventoryCheck";
    }

    public void setData(long id,int status){
        try {
            data.put("id",id);
            data.put("status",status);
            data.put("remark","盘点单确认");
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultInventoryCheckBean bean= JsonUtils.fromJson(rJson,ResultInventoryCheckBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
