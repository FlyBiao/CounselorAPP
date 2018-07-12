package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.ResultFaceBindBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyAgreeBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 人脸绑定
 * Created at 2018/3/10 15:37
 * Version 1.0
 */

public class FaceBindNet extends BaseNet {
    public FaceBindNet(Context context) {
        super(context, true);
        this.uri="Order/Sw/Camera/Bind";
    }

    public void setData(int VipId,String Mobile,String Name,String ImagePath,String ImageUrl){
        try{
            data.put("VipId",VipId);
            data.put("Mobile",Mobile);
            data.put("Name",Name);
            data.put("ImagePath",ImagePath);
            data.put("ImageUrl",ImageUrl);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }
    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultFaceBindBean bean= JsonUtils.fromJson(rJson,ResultFaceBindBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
