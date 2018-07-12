package com.cesaas.android.counselor.order.report.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.report.bean.ResultDeleteTryWearDateBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;


/**
 * Author FGB
 * Description 删除试穿款号数据
 * Created 2017/4/20 19:49
 * Version 1.zero
 */
public class DeleteTryWearStyleDateNet extends BaseNet {
    public DeleteTryWearStyleDateNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/StyleTry/Delete";
        }

    public void setData(String Id){
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
        ResultDeleteTryWearDateBean bean= JsonUtils.fromJson(rJson,ResultDeleteTryWearDateBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}

