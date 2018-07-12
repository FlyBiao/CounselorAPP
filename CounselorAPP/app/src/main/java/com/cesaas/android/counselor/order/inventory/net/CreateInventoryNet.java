package com.cesaas.android.counselor.order.inventory.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.inventory.bean.ResultCreateInventoryBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 新建盘点单Net
 * Created at 2017/8/31 9:40
 * Version 1.0
 */

public class CreateInventoryNet extends BaseNet{
    public CreateInventoryNet(Context context) {
        super(context, true);
        this.uri="Distribution/Sw/Inventory/Add";
    }

    public void setData(int shopId,String shopName,int type,String date){
        try {
            data.put("ShopId",shopId);
            data.put("ShopName",shopName);
            data.put("Type",type);
            data.put("Date",date);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i(Constant.TAG,"新建盘点单："+rJson);
        ResultCreateInventoryBean bean= JsonUtils.fromJson(rJson,ResultCreateInventoryBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,err);
    }
}
