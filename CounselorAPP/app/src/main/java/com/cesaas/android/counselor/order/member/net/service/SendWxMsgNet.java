package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultSendMsgBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/3/26 18:21
 * Version 1.0
 */

public class SendWxMsgNet extends BaseNet {
    public SendWxMsgNet(Context context) {
        super(context, true);
        this.uri="User/Sw/Msg/SendWxMsg";
    }

    public void setData(String VipId,int Type,String Content,String Url){
        try{
            data.put("VipId",VipId);
            data.put("Type",Type);
            data.put("Content",Content);
            data.put("Url",Url);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(String VipId,int Type,String Url,int Duration){
        try{
            data.put("VipId",VipId);
            data.put("Type",Type);
            data.put("Url",Url);
            data.put("Duration",Duration);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultSendMsgBean bean= JsonUtils.fromJson(rJson,ResultSendMsgBean.class);

        if(bean.IsSuccess!=false){
            EventBus.getDefault().post(bean);
        }else{
            ToastFactory.getLongToast(mContext,"发送失败"+bean.Message);
        }
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
