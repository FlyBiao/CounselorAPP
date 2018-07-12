package com.cesaas.android.java.net.inventory;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryGetGoodsListBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取盘点单款式列表
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class InventoryGetGoodsListNet extends TestBaseNet {

    public InventoryGetGoodsListNet(Context context) {
        super(context, true);
        this.uri="drp/inventoryGetGoodsList";
    }

    public void setData(long pid,long shelfId){
        try {
            data.put("pId", pid);
            data.put("shelfId",shelfId);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }


    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultInventoryGetGoodsListBean bean= JsonUtils.fromJson(rJson,ResultInventoryGetGoodsListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
