package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyAgreeBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 群发
 * Created at 2018/3/10 15:37
 * Version 1.0
 */

public class SendSmsmsgsNet extends BaseNet {
    public SendSmsmsgsNet(Context context) {
        super(context, true);
        this.uri="User/Sw/Msg/SendSmsmsgs";
    }

    public void setData(String Title,String Msg	,String Mobiles){
        try{
            data.put("Title",Title);
            data.put("Msg",Msg);
            data.put("Mobiles",Mobiles);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }
    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultMemberApplyAgreeBean bean= JsonUtils.fromJson(rJson,ResultMemberApplyAgreeBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
