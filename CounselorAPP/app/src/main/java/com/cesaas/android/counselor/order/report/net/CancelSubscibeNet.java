package com.cesaas.android.counselor.order.report.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.report.bean.ResultCancelSubscibeBean;
import com.cesaas.android.counselor.order.report.bean.ResultSubscibeBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 取消订阅报表
 * Created 2017/3/25 14:34
 * Version 1.zero
 */
public class CancelSubscibeNet extends BaseNet {
    public CancelSubscibeNet(Context context) {
        super(context, true);
        this.uri="User/Sw/ReportCustom/Subscibe";
    }

    public void setData(int reportId,int isSubscribe){
        try{
            data.put("reportId",reportId);
            data.put("isSubscribe",isSubscribe);//是否订阅 zero：取消订阅，1：订阅
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultCancelSubscibeBean bean= JsonUtils.fromJson(rJson,ResultCancelSubscibeBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
