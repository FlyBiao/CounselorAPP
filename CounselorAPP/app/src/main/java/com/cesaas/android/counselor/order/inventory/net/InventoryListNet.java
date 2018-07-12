package com.cesaas.android.counselor.order.inventory.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.inventory.bean.ResultCreateInventoryBean;
import com.cesaas.android.counselor.order.inventory.bean.ResultInventoryListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 盘点列表Net
 * Created at 2017/8/31 9:40
 * Version 1.0
 */

public class InventoryListNet extends BaseNet{
    public InventoryListNet(Context context) {
        super(context, true);
        this.uri="Distribution/Sw/Inventory/GetList";
    }

    public void setData(int PageIndex){
        try {
            data.put("PageIndex",PageIndex);
            data.put("PageSize",30);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultInventoryListBean bean= JsonUtils.fromJson(rJson,ResultInventoryListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,err);
    }
}
