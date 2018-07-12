package com.cesaas.android.counselor.order.member.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.ResultGetMsgPageListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 已发送短信消息列表
 * Created 2017/4/27 10:55
 * Version 1.0
 */
public class GetMsgPageByVipIdNet extends BaseNet{
    public GetMsgPageByVipIdNet(Context context) {
        super(context, true);
        this.uri="User/Sw/Msg/PlanList";
    }

    public void setData(int PageIndex){
        try{
            data.put("PageIndex",PageIndex);
            data.put("PageSize",Constant.PAGE_SIZE);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultGetMsgPageListBean bean= JsonUtils.fromJson(rJson,ResultGetMsgPageListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"短信群发列表："+err);
    }
}
