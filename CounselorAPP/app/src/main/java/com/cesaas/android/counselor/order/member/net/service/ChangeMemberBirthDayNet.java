package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultChangeMemberBirthDayBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultChangeMemberMobileBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 申请修改用户生日
 * Created at 2018/3/7 17:09
 * Version 1.0
 */

public class ChangeMemberBirthDayNet extends BaseNet {
    public ChangeMemberBirthDayNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/VipData/ChangeBirthDay";
    }

    public void setData(int VipId,String Birthday ,String Remark){
        try{
            data.put("VipId",VipId);
            data.put("Birthday",Birthday);
            data.put("Remark",Remark);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        ResultChangeMemberBirthDayBean bean= JsonUtils.fromJson(rJson,ResultChangeMemberBirthDayBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
