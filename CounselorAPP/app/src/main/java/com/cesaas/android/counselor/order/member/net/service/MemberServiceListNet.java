package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyCompleteListBean;
import com.cesaas.android.counselor.order.member.bean.service.member.ResultBirthdayMemberServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.member.ResultFocusMemberServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.member.ResultMemberServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.member.ResultNewMemberServiceListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员服务列表Net
 * Created at 2018/3/10 15:37
 * Version 1.0
 */

public class MemberServiceListNet extends BaseNet {
    private int dataType;
    private boolean isLoadMore;
    public MemberServiceListNet(Context context,int dataType) {
        super(context, true);
        this.uri="MemberTask/Sw/User/GetVipList";
        this.dataType=dataType;
    }

    public void setData(int PageIndex,int Type){
        try{
            data.put("Type",Type);
            data.put("PageIndex",PageIndex);
            data.put("PageSize",Constant.PAGE_SIZE);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }
    public void setData(int PageIndex,int Type,String SearchKey){
        try{
            data.put("Type",Type);
            data.put("PageIndex",PageIndex);
            data.put("PageSize",Constant.PAGE_SIZE);
            data.put("SearchKey",SearchKey);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int PageIndex,int Type,JSONObject Filters){
        try{
            data.put("Type",Type);
            data.put("PageIndex",PageIndex);
            data.put("PageSize",Constant.PAGE_SIZE);
            data.put("Filters",Filters);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        switch (dataType){
            case 1:
                ResultMemberServiceListBean bean= JsonUtils.fromJson(rJson,ResultMemberServiceListBean.class);
                EventBus.getDefault().post(bean);
                break;
            case 2:
                ResultBirthdayMemberServiceListBean bean1= JsonUtils.fromJson(rJson,ResultBirthdayMemberServiceListBean.class);
                EventBus.getDefault().post(bean1);
                break;
            case 3:
                ResultNewMemberServiceListBean bean2= JsonUtils.fromJson(rJson,ResultNewMemberServiceListBean.class);
                EventBus.getDefault().post(bean2);
                break;
            case 4:
                ResultFocusMemberServiceListBean bean3= JsonUtils.fromJson(rJson,ResultFocusMemberServiceListBean.class);
                EventBus.getDefault().post(bean3);
                break;
        }
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
