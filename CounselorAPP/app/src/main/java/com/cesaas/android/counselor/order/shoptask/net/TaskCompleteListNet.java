package com.cesaas.android.counselor.order.shoptask.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shoptask.bean.ResultShopTaskCompleteListBean;
import com.cesaas.android.counselor.order.shoptask.bean.ResultShopTaskListBean;
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
public class TaskCompleteListNet extends BaseNet {
    public TaskCompleteListNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/SW/ShopTask/TaskList";
    }

    public void setData(int PageIndex,int PageSize){
        try{
            data.put("PageIndex",PageIndex);
            data.put("PageSize",PageSize);
            data.put("UserTicket",
                    AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int PageIndex,int PageSize,int Status){
        try{
            data.put("PageIndex",PageIndex);
            data.put("PageSize",PageSize);
            data.put("Status",Status);
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
        Log.i(Constant.TAG,"SHOP_TASK:"+rJson);
        ResultShopTaskCompleteListBean bean= JsonUtils.fromJson(rJson,ResultShopTaskCompleteListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
