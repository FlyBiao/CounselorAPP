package com.cesaas.android.counselor.order.workbench.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.workbench.bean.ResultReturnVisitBean;
import com.cesaas.android.counselor.order.workbench.bean.ResultReturnVisitDetailBean;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 客户回访详情Net
 * Created 2017/4/12 17:18
 * Version 1.zero
 */
public class ReturnVisitDetailNet extends BaseNet{
    public ReturnVisitDetailNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/SW/TaskVisit/GetMessageAndList";
    }

    public void setData(int RuleId,int VipId){
        try{

            data.put("RuleId",RuleId);
            data.put("VipId",VipId);
            data.put("UserTicket",
                    AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }


    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultReturnVisitDetailBean bean= JsonUtils.fromJson(rJson,ResultReturnVisitDetailBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
