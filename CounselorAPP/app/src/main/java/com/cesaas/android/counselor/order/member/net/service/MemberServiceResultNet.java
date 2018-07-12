package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.ResultMemberServiceResultBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员服务记录记过查询
 * Created at 2018/3/7 17:09
 * Version 1.0
 */

public class MemberServiceResultNet extends BaseNet {
    public MemberServiceResultNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/Task/TaskResultList";
    }

    public void setData(int Id){
        try{
            data.put("VipId",Id);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultMemberServiceResultBean bean= JsonUtils.fromJson(rJson,ResultMemberServiceResultBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
