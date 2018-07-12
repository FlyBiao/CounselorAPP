package com.cesaas.android.counselor.order.shopmange.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shopmange.bean.ResultCollectionShopBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 收藏
 * Created 2017/4/27 20:56
 * Version 1.0
 */
public class CollectionShopNet extends BaseNet {
    public CollectionShopNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/Style/Collect";
    }

    public void setData(int Id, JSONArray VipId){
        try{
            data.put("Id",Id);
            data.put("VipId",VipId);
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
        ResultCollectionShopBean bean= JsonUtils.fromJson(rJson,ResultCollectionShopBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
