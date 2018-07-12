package com.cesaas.android.counselor.order.net;

import android.content.Context;

import com.cesaas.android.counselor.order.bean.ResultGetStylePictureBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.statistics.bean.CacheJsonBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created 2017/3/24 9:38
 * Version 1.zero
 */
public class GetShopInfoStylePictureNet extends BaseNet {
    public GetShopInfoStylePictureNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/Style/GetStylePicture";
    }

    public void setData(JSONArray styleCode) {
        try {
            data.put("StyleCode",styleCode);//商品款号集合
            data.put("UserTicket",
                    AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Gson gson = new Gson();
        ResultGetStylePictureBean lbean = gson.fromJson(rJson, ResultGetStylePictureBean.class);
        EventBus.getDefault().post(lbean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }

}
