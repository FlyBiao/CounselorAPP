package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.multipleservice.ResultGetVipTaskBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获得会员进行中的服务
 * Created at 2018/3/10 15:37
 * Version 1.0
 */

public class GetVipTaskNet extends BaseNet {
    public GetVipTaskNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/User/GetVipTask";
    }

    public void setData(int VipId){
        try{
            data.put("VipId",VipId);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }
    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultGetVipTaskBean bean= JsonUtils.fromJson(rJson,ResultGetVipTaskBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
