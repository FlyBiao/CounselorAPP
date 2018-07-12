package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.manager.ResultVipGradeNumsListBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyAgreeBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 查询会员等级数分组
 * Created at 2018/3/10 15:37
 * Version 1.0
 */

public class VipGradeNumsListNet extends BaseNet {
    public VipGradeNumsListNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/Vip/VipGradeNumsList";
    }

    public void setData(){
        try{
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }
    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultVipGradeNumsListBean bean= JsonUtils.fromJson(rJson,ResultVipGradeNumsListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
