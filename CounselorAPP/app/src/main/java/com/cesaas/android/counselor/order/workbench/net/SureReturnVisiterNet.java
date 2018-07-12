package com.cesaas.android.counselor.order.workbench.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.workbench.bean.ResultSureReturnVisiterBean;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 确认回访
 * Created 2017/4/27 19:58
 * Version 1.0
 */
public class SureReturnVisiterNet extends BaseNet{
    public SureReturnVisiterNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/SW/TaskVisit/UpdateVisiter";
    }

    public void setData(int Id){
        try{
            data.put("Id",Id);
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
        ResultSureReturnVisiterBean bean= JsonUtils.fromJson(rJson,ResultSureReturnVisiterBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
