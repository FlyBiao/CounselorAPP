package com.cesaas.android.counselor.order.activity.user.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.activity.user.bean.ResultMessageBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultNoticeBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultNoticeListBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 公告
 * Created at 2017/6/26 15:51
 * Version 1.0
 */

public class NoticeNet extends BaseNet {

    public NoticeNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/SW/Notice/GetList";
    }

    public void setData(int PageIndex){
        try{
            data.put("PageSize",30);
            data.put("PageIndex",PageIndex);
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
        Log.i("ffff",rJson);
        ResultNoticeListBean bean= JsonUtils.fromJson(rJson,ResultNoticeListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"公告err:"+err);
    }
}
