package com.cesaas.android.counselor.order.inventory.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.inventory.bean.ResultCreateInventoryBean;
import com.cesaas.android.counselor.order.inventory.bean.ResultShelfDetailsBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 修改盘点单Net
 * Created at 2017/8/31 16:12
 * Version 1.0
 */

public class UpdateInventoryNet extends BaseNet {
    public UpdateInventoryNet(Context context) {
        super(context, true);
        this.uri="Distribution/Sw/Inventory/Update";
    }

    public void setData(int Id,int  ShopId,String ShopName,int Type,String Date){
        try {
            data.put("Id",Id);
            data.put("ShopId",ShopId);
            data.put("ShopName",ShopName);
            data.put("Type",Type);
            data.put("Date",Date);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i(Constant.TAG,"修改盘点单Net"+rJson);
        ResultCreateInventoryBean bean= JsonUtils.fromJson(rJson,ResultCreateInventoryBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,err);
    }
}
