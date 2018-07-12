package com.cesaas.android.counselor.order.member.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.MemberDistributionDetailBean;
import com.cesaas.android.counselor.order.member.bean.ResultMemberDistributionDetailBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created 2017/4/21 16:49
 * Version 1.zero
 */
public class GetBindVipNet extends BaseNet {
    public GetBindVipNet(Context context) {
        super(context, true);
        this.uri="User/Sw/VipCounselor/GetBindVip";
    }

    public void setData(int PageIndex,int CounselorId){
        try{

            data.put("CounselorId",CounselorId);
            data.put("PageIndex",PageIndex);
            data.put("PageSize",Constant.PAGE_SIZE);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int PageIndex,int CounselorId,String SearchKey){
        try{

            data.put("CounselorId",CounselorId);
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
        ResultMemberDistributionDetailBean bean= JsonUtils.fromJson(rJson, ResultMemberDistributionDetailBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
