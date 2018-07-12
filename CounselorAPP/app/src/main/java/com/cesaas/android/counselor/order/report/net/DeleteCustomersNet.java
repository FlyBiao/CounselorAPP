package com.cesaas.android.counselor.order.report.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.report.bean.ResultDeleteCustomersBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created 2017/4/25 17:08
 * Version 1.0
 */
public class DeleteCustomersNet extends BaseNet {
    public DeleteCustomersNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/Sw/NumberOfStores/DeleteCustomers";
    }

    public void setData(int Id){
        try{

            data.put("Id",Id);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);

        ResultDeleteCustomersBean bean= JsonUtils.fromJson(rJson,ResultDeleteCustomersBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
