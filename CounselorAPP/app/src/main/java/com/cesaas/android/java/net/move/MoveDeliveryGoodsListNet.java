package com.cesaas.android.java.net.move;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryGoodsListBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 调货管理-商品列表
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class MoveDeliveryGoodsListNet extends TestBaseNet {

    private int type;
    public MoveDeliveryGoodsListNet(Context context,int type) {
        super(context, true);
        this.type=type;
        if(type==1){
            this.uri="drp/refundDeliveryGoodsList";
        }else{
            this.uri="drp/moveDeliveryGoodsList";
        }
    }

    public void setData(long pid,long boxId){
        try {
            data.put("pId", pid);
            data.put("boxId", boxId);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    public void setData(long pid){
        try {
            data.put("pId", pid);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultMoveDeliveryGoodsListBean bean= JsonUtils.fromJson(rJson,ResultMoveDeliveryGoodsListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
