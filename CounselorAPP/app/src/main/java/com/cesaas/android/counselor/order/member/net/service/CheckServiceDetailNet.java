package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.birth.ResultBirthdayWishesServiceCompleteListBean;
import com.cesaas.android.counselor.order.member.bean.service.birth.ResultBirthdayWishesServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.birth.ResultBirthdayWishesServiceTimeOutListBean;
import com.cesaas.android.counselor.order.member.bean.service.check.ResultCheckCompleteServiceDetailsBean;
import com.cesaas.android.counselor.order.member.bean.service.check.ResultCheckDealServiceDetailsBean;
import com.cesaas.android.counselor.order.member.bean.service.check.ResultCheckGoShopServiceDetailsBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 服务检查-服务完成情况明细查询
 * Created at 2018/3/7 17:09
 * Version 1.0
 */

public class CheckServiceDetailNet extends BaseNet {
    private int type;
    public CheckServiceDetailNet(Context context, int type) {
        super(context, true);
        this.uri="MemberTask/Sw/Project/GetTaskList";
        this.type=type;
    }

    public void setData(int Id,int Status){
        try{
            data.put("Status",Status);//10.待处理20.已完成30.已关闭40.超时逾期
            data.put("Id",Id);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int Id, int Status, int ServiceType, int ServiceResult, int GoShop, JSONArray CounselorIds){
        try{
            data.put("Status",Status);//1.待处理2.已完成3.已关闭4.超时逾期
            data.put("Id",Id);//服务ID
            data.put("ServiceType",ServiceType);//1.电话2.其它
            data.put("ServiceResult",ServiceResult);//1.接听或有回复2.没反馈3.拒绝沟通
            data.put("GoShop",GoShop);//1.愿意来店2.不愿意来店3.不确定
            data.put("CounselorIds",CounselorIds);
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
            ResultCheckDealServiceDetailsBean bean= JsonUtils.fromJson(rJson,ResultCheckDealServiceDetailsBean.class);
            EventBus.getDefault().post(bean);
        }else if(type==2){
            ResultCheckCompleteServiceDetailsBean bean= JsonUtils.fromJson(rJson,ResultCheckCompleteServiceDetailsBean.class);
            EventBus.getDefault().post(bean);
        }else if(type==3){
            ResultCheckGoShopServiceDetailsBean bean= JsonUtils.fromJson(rJson,ResultCheckGoShopServiceDetailsBean.class);
            EventBus.getDefault().post(bean);
        }
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
