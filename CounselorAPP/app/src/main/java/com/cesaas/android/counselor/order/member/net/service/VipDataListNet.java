package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyCompleteListBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyListBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyRefusedListBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyTimeOutListBean;
import com.cesaas.android.counselor.order.member.bean.service.birth.ResultBirthdayWishesServiceCompleteListBean;
import com.cesaas.android.counselor.order.member.bean.service.birth.ResultBirthdayWishesServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.birth.ResultBirthdayWishesServiceTimeOutListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 手机和生日修改申请列表
 * Created at 2018/3/7 17:09
 * Version 1.0
 */

public class VipDataListNet extends BaseNet {
    private int type;
    public VipDataListNet(Context context, int type) {
        super(context, true);
        this.uri="MemberTask/Sw/VipData/GetList";
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
            data.put("PageIndex",PageIndex);
            data.put("SearchKey",SearchKey);
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
            ResultMemberApplyListBean bean= JsonUtils.fromJson(rJson,ResultMemberApplyListBean.class);
            EventBus.getDefault().post(bean);
        }else if(type==2){
            ResultMemberApplyCompleteListBean bean= JsonUtils.fromJson(rJson,ResultMemberApplyCompleteListBean.class);
            EventBus.getDefault().post(bean);
        }else if(type==3){
            ResultMemberApplyRefusedListBean bean= JsonUtils.fromJson(rJson,ResultMemberApplyRefusedListBean.class);
            EventBus.getDefault().post(bean);
        }else if(type==4){
            ResultMemberApplyTimeOutListBean bean= JsonUtils.fromJson(rJson,ResultMemberApplyTimeOutListBean.class);
            EventBus.getDefault().post(bean);
        }
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
