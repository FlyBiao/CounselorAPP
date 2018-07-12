package com.cesaas.android.counselor.order.inventory.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.inventory.bean.ResultGetShopAllBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;


/**
 * Author FGB
 * Description 获取该账号下所有店铺
 * Created at 2017/8/31 18:02
 * Version 1.0
 */

public class GetShopAllNet extends BaseNet {

    public GetShopAllNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/Shop/GetShopAll";
    }

    public void setData(){
        try {
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i(Constant.TAG,"获取该账号下所有店铺！"+rJson);
        ResultGetShopAllBean bean= JsonUtils.fromJson(rJson,ResultGetShopAllBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,err);
    }
}
