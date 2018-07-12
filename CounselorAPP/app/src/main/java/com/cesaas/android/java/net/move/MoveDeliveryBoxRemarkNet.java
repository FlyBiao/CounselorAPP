package com.cesaas.android.java.net.move;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryBoxRemarkBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 新增修改箱备注
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class MoveDeliveryBoxRemarkNet extends TestBaseNet {

    private int type;
    public MoveDeliveryBoxRemarkNet(Context context,int type) {
        super(context, true);
        if(type==1){
            this.uri="drp/refundDeliveryBoxRemark";
        }else{
            this.uri="drp/moveDeliveryBoxRemark";
        }
    }

    public void setData(long boxId,String remark){
        try {
            data.put("boxId",boxId);
            data.put("remark",remark);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultMoveDeliveryBoxRemarkBean bean= JsonUtils.fromJson(rJson,ResultMoveDeliveryBoxRemarkBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
