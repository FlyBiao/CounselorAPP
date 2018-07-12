package com.cesaas.android.java.net.inventory;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryScanBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 扫描盘点
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class InventoryScanNet extends TestBaseNet {

    public InventoryScanNet(Context context) {
        super(context, true);
        this.uri="drp/inventoryScan";
    }

    public void setData(long pid,long shelfId,String barcodeNo,int num){
        try {
            data.put("pId", pid);
            data.put("shelfId", shelfId);
            data.put("barcodeNo", barcodeNo);
            data.put("num", num);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultInventoryScanBean bean= JsonUtils.fromJson(rJson,ResultInventoryScanBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
