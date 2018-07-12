package com.cesaas.android.counselor.order.shopmange.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shopmange.bean.ResultAgreeClerkBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultClerkRankingBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 店员排名
 * Created at 2017/6/19 17:43
 * Version 1.0
 */

public class CounselorSaleListNet extends BaseNet {
    public CounselorSaleListNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/Report/CounselorSaleList";
    }

    public void setData(String StartTime, String EndTime, JSONArray ShopIds){
        try{
            data.put("StartTime",StartTime);
            data.put("EndTime",EndTime);
            data.put("ShopIds",ShopIds);
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
        ResultClerkRankingBean bean= JsonUtils.fromJson(rJson,ResultClerkRankingBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
