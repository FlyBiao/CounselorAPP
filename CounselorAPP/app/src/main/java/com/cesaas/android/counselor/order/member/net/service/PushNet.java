package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyAgreeBean;
import com.cesaas.android.counselor.order.member.bean.service.volume.ResultPushBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 推送券
 * Created at 2018/3/10 15:37
 * Version 1.0
 */

public class PushNet extends BaseNet {
    public PushNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/Ticket/Push";
    }

    public void setData(int TicketId, JSONArray Vips){
        try{
            data.put("TicketId",TicketId);
            data.put("Vips",Vips);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultPushBean bean= JsonUtils.fromJson(rJson,ResultPushBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
