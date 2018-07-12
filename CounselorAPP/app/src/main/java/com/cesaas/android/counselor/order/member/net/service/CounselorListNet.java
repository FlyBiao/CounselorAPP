package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.ResultCounselorListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceCompleteListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceTimeOutListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 查询店铺导购
 * Created at 2018/3/7 17:09
 * Version 1.0
 */

public class CounselorListNet extends BaseNet {
    public CounselorListNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/Project/GetCounselorList";
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
        ResultCounselorListBean bean= JsonUtils.fromJson(rJson,ResultCounselorListBean.class);
        EventBus.getDefault().post(bean);

    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
