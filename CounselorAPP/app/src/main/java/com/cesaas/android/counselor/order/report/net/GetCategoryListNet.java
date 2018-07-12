package com.cesaas.android.counselor.order.report.net;

import android.content.Context;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.report.bean.ResultGetCategoryListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取报表中心分类net
 * Created 2017/3/25 14:30
 * Version 1.zero
 */
public class GetCategoryListNet extends BaseNet{
    public GetCategoryListNet(Context context) {
        super(context, true);
        this.uri="User/SW/ReportCustom/GetCategoryList";
    }

    public void setData(){
        try{

            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultGetCategoryListBean bean= JsonUtils.fromJson(rJson,ResultGetCategoryListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
