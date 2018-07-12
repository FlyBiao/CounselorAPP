package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.dormancy.ResultDormancyServiceCompleteListBean;
import com.cesaas.android.counselor.order.member.bean.service.dormancy.ResultDormancyServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.dormancy.ResultDormancyServiceTimeOutListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 休眠激活服务列表查询
 * Created at 2018/3/7 17:09
 * Version 1.0
 */

public class DormancyListNet extends BaseNet {
    private int type;
    public DormancyListNet(Context context,int type) {
        super(context, true);
        this.uri="MemberTask/Sw/Task/GetDormancyList";
        this.type=type;
    }

    public void setData(int PageIndex,int Status){
        try{
            data.put("Status",Status);//10.待处理20.已完成30.已关闭40.超时逾期
            data.put("PageIndex",PageIndex);
            data.put("PageSize",Constant.PAGE_SIZE);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }
    public void setData(int PageIndex,int Status,String SearchKey){
        try{
            data.put("Status",Status);//10.待处理20.已完成30.已关闭40.超时逾期
            data.put("SearchKey",SearchKey);
            data.put("PageIndex",PageIndex);
            data.put("PageSize",Constant.PAGE_SIZE);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int PageIndex,int Status,int ServiceType,int ServiceResult,int GoShop){
        try{
            data.put("Status",Status);//10.待处理20.已完成30.已关闭40.超时逾期
            data.put("ServiceType",ServiceType);//1.电话2.其它
            data.put("ServiceResult",ServiceResult);//1.接听或有回复2.没反馈3.拒绝沟通
            data.put("GoShop",GoShop);//1.愿意来店2.不愿意来店3.不确定
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
        if(type==1){
            ResultDormancyServiceListBean bean= JsonUtils.fromJson(rJson,ResultDormancyServiceListBean.class);
            EventBus.getDefault().post(bean);
        }else if(type==2){
            ResultDormancyServiceCompleteListBean bean= JsonUtils.fromJson(rJson,ResultDormancyServiceCompleteListBean.class);
            EventBus.getDefault().post(bean);
        }else if(type==3){
            ResultDormancyServiceTimeOutListBean bean= JsonUtils.fromJson(rJson,ResultDormancyServiceTimeOutListBean.class);
            EventBus.getDefault().post(bean);
        }
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
