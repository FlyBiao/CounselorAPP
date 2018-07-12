package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.ResultColseServiceBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultServiceRemarkBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员服务方案备注
 * Created at 2018/3/7 17:45
 * Version 1.0
 */

public class ServiceRemarkNet extends BaseNet {
    public ServiceRemarkNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/Task/SetRemark";
    }

    public void setData(int Id,String Remark){
        try{
            data.put("Id",Id);
            data.put("Remark",Remark);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultServiceRemarkBean bean= JsonUtils.fromJson(rJson,ResultServiceRemarkBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
