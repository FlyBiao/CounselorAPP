package com.cesaas.android.counselor.order.shopmange.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shopmange.bean.ResultShopTagBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取商品标签列表Net
 * Created at 2017/5/14 11:22
 * Version 1.0
 */

public class GetShopTagListNet extends BaseNet{
    public GetShopTagListNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/Style/GetTagByStyleId";
    }

    public void setData(String Id){
        try{
            data.put("Id",Id);
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
        Log.i(Constant.TAG,"商品标签:"+rJson);
//        ResultShopTagBean bean= JsonUtils.fromJson(rJson,ResultShopTagBean.class);
//        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"商品标签err:"+err);
    }
}
