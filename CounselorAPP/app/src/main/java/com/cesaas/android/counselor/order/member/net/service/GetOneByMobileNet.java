package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.ResultGetOneByMobileBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyAgreeBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 手机号查询用户信息
 * Created at 2018/3/10 15:37
 * Version 1.0
 */

public class GetOneByMobileNet extends BaseNet {
    public GetOneByMobileNet(Context context) {
        super(context, true);
        this.uri="User/Sw/Vip/GetOneByMobile";
    }

    public void setData(String Mobile){
        try{
            data.put("Mobile",Mobile);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }
    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultGetOneByMobileBean bean= JsonUtils.fromJson(rJson,ResultGetOneByMobileBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
