package com.cesaas.android.counselor.order.member.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.ResultMemberDistributionBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取已分配会员Net
 * Created 2017/4/21 16:26
 * Version 1.zero
 */
public class AlreadyDistributionMemberNet extends BaseNet{
    public AlreadyDistributionMemberNet(Context context) {
        super(context, true);
        this.uri="User/SW/Counselor/getList";
    }

    public void setData(int PageIndex,int status){

        try{
            data.put("PageIndex",PageIndex);
            data.put("Status",status);
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
        ResultMemberDistributionBean bean= JsonUtils.fromJson(rJson,ResultMemberDistributionBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
