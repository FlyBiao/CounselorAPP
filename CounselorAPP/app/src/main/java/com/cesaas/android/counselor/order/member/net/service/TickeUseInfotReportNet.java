package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyAgreeBean;
import com.cesaas.android.counselor.order.member.bean.service.volume.ResultTickeUseInfotReportBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 券分析追踪-列表
 * Created at 2018/3/10 15:37
 * Version 1.0
 */

public class TickeUseInfotReportNet extends BaseNet {
    public TickeUseInfotReportNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/Ticket/TicketUseInfoReport";
    }

    public void setData(String StartTime,String EndTime,String SearchKey,int PageIndex){
        try{
            data.put("StartTime",StartTime);
            data.put("EndTime",EndTime);
            data.put("SearchKey",SearchKey);
            data.put("PageIndex",PageIndex);
            data.put("PageSize",Constant.PAGE_SIZE);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(String StartTime,String EndTime,String SearchKey,int PageIndex,int Type){
        try{
            data.put("StartTime",StartTime);
            data.put("EndTime",EndTime);
            data.put("SearchKey",SearchKey);
            data.put("PageIndex",PageIndex);
            data.put("Type",Type);//不传，查所有，1已使用，0未使用
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
        ResultTickeUseInfotReportBean bean= JsonUtils.fromJson(rJson,ResultTickeUseInfotReportBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
