package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultVipGradeBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceCompleteListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceTimeOutListBean;
import com.cesaas.android.counselor.order.utils.ACache;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员等级列表
 * Created at 2018/3/7 17:09
 * Version 1.0
 */

public class GradeListNet extends BaseNet {
    private ACache mCache;
    public GradeListNet(Context context,ACache mCache) {
        super(context, true);
        this.uri="MemberTask/Sw/Project/GetGradeList";
        this.mCache=mCache;
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
        mCache.put("GetGradeList",rJson);
        ResultVipGradeBean bean= JsonUtils.fromJson(rJson,ResultVipGradeBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
