package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultChangeMemberMobileBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceCompleteListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceTimeOutListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 申请修改用户手机号码
 * Created at 2018/3/7 17:09
 * Version 1.0
 */

public class ChangeMemberMobileNet extends BaseNet {
    public ChangeMemberMobileNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/VipData/ChangeMobile";
    }

    public void setData(int VipId,String Mobile ,String Remark){
        try{
            data.put("VipId",VipId);
            data.put("Mobile",Mobile);
            data.put("Remark",Remark);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        ResultChangeMemberMobileBean bean= JsonUtils.fromJson(rJson,ResultChangeMemberMobileBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
