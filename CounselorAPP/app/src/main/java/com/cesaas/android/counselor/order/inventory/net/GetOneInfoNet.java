package com.cesaas.android.counselor.order.inventory.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.inventory.bean.ResultGetOneInfoBean;
import com.cesaas.android.counselor.order.inventory.bean.ResultInventoryListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取单个货架信息
 * Created at 2017/8/31 10:33
 * Version 1.0
 */

public class GetOneInfoNet extends BaseNet{
    public GetOneInfoNet(Context context) {
        super(context, true);
        this.uri="Distribution/Sw/Inventory/GetOneInfo";
    }

    public void setData(int Id,int ShelvesId){
        try {
            data.put("Id",Id);
            data.put("ShelvesId",ShelvesId);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i(Constant.TAG,"获取单个货架信息:"+rJson);
        ResultGetOneInfoBean bean= JsonUtils.fromJson(rJson,ResultGetOneInfoBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,err);
    }
}
