package com.cesaas.android.counselor.order.inventory.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.inventory.bean.ResultInventoryDetailsBean;
import com.cesaas.android.counselor.order.inventory.bean.ResultInventoryListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 盘点详情Net
 * Created at 2017/8/31 9:40
 * Version 1.0
 */

public class InventoryDetailsNet extends BaseNet{
    public InventoryDetailsNet(Context context) {
        super(context, true);
        this.uri="Distribution/Sw/Inventory/GetOne";
    }

    public void setData(int Id){
        try {
            data.put("Id",Id);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i(Constant.TAG,"盘点详情:"+rJson);
        Gson gson=new Gson();
        ResultInventoryDetailsBean bean= gson.fromJson(rJson,ResultInventoryDetailsBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,err);
    }
}
