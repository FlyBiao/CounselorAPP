package com.cesaas.android.counselor.order.shoptask.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shoptask.bean.ResultShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.bean.ResultTransferBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created 2017/4/20 17:53
 * Version 1.zero
 */
public class TaskTransferNet extends BaseNet {
    public TaskTransferNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/SW/ShopTask/Transfer";
    }

    public void setData(int FlowId,int SalesId,String SalesName){
        try{
            data.put("FlowId",FlowId);
            data.put("SalesId",SalesId);
            data.put("SalesName",SalesName);
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
        ResultTransferBean bean= JsonUtils.fromJson(rJson,ResultTransferBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
