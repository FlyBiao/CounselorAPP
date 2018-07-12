package com.cesaas.android.counselor.order.workbench.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.workbench.bean.ResultReturnVisitBean;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 客户回访Net
 * Created 2017/4/12 17:18
 * Version 1.zero
 */
public class ReturnVisitNet extends BaseNet{
    public ReturnVisitNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/SW/TaskVisit/GetVip";
    }

    public void setData(int PageIndex){
        try{

            data.put("PageIndex",PageIndex);////当前页
            data.put("PageSize",30);
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
        Log.i(Constant.TAG,"回访列表:"+rJson);
        ResultReturnVisitBean bean= JsonUtils.fromJson(rJson,ResultReturnVisitBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
