package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.ResultServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceCompleteListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.visit.ResultVisitServiceTimeOutListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 已发布服务列表查询-服务检查
 * Created at 2018/3/7 17:09
 * Version 1.0
 */

public class ServiceListNet extends BaseNet {
    public ServiceListNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/Project/GetServerList";
    }

    public void setData(int PageIndex,int Status){
        try{
            data.put("Status",Status);//10.进行中20.已完成30.已关闭
            data.put("PageIndex",PageIndex);
            data.put("PageSize",Constant.PAGE_SIZE);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }public void setData(int PageIndex,int Status,String SearchKey){
        try{
            data.put("SearchKey",SearchKey);
            data.put("Status",Status);//10.进行中20.已完成30.已关闭
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
        ResultServiceListBean bean= JsonUtils.fromJson(rJson,ResultServiceListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
