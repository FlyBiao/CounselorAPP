package com.cesaas.android.counselor.order.member.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.ResultCancelBinVipBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 取消绑定会员
 * Created 2017/4/21 17:13
 * Version 1.zero
 */
public class CancelBindVipNet extends BaseNet{
    public CancelBindVipNet(Context context) {
        super(context, true);
        this.uri="User/Sw/VipCounselor/CancelBindVip";
    }

    public void setData(int VipId,int CounselorId){
        try{

            data.put("VipId",VipId);
            data.put("CounselorId",CounselorId);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultCancelBinVipBean bean= JsonUtils.fromJson(rJson,ResultCancelBinVipBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
