package com.cesaas.android.counselor.order.report.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.report.bean.ResultCounselorSaleBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 顾问销售业绩Net
 * Created at 2017/5/8 13:41
 * Version 1.0
 */

public class CounselorSaleNet extends BaseNet{
    public CounselorSaleNet(Context context) {
        super(context, true);
        this.uri="Order/Sw/Report/CounselorSale";
    }

    public void setData(){
        try{
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i("test","=========当天实时销售========="+rJson);
        ResultCounselorSaleBean bean= JsonUtils.fromJson(rJson,ResultCounselorSaleBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
