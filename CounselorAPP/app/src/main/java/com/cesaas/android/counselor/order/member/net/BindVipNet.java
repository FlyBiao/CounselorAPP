package com.cesaas.android.counselor.order.member.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.ResultBinVipBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 分配绑定会员Net
 * Created 2017/4/21 17:46
 * Version 1.zero
 */
public class BindVipNet extends BaseNet {
    public BindVipNet(Context context) {
        super(context, true);
        this.uri="User/Sw/VipCounselor/BindVip";
    }

    public void setData(int CounselorId, JSONArray BindVip){
        try{
            data.put("BindVip",BindVip);
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
        ResultBinVipBean bean= JsonUtils.fromJson(rJson,ResultBinVipBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
