package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyAgreeBean;
import com.cesaas.android.counselor.order.member.bean.service.volume.ResultSumTickeUsetReportBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 券分析追踪-总计
 * Created at 2018/3/10 15:37
 * Version 1.0
 */

public class SumTickeUsetReportNet extends BaseNet {
    public SumTickeUsetReportNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/Ticket/SumTicketUseReport";
    }

    public void setData(String StartTime,String EndTime){
        try{
            data.put("StartTime",StartTime);
            data.put("EndTime",EndTime);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }
    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultSumTickeUsetReportBean bean= JsonUtils.fromJson(rJson,ResultSumTickeUsetReportBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
