package com.cesaas.android.counselor.order.bean.weather.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.weather.WeatherResultsBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.BaseWeatherNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 心知天气Net
 * Created 2017/4/20 18:58
 * Version 1.zero
 */
public class WeatherNet extends BaseWeatherNet{
    public WeatherNet(Context context) {
        super(context, true);
        this.uri= "daily.json?key=ud9km8mr0dczmojp&location=ip&days=2";
    }

    public void setData(){
        try{
            mPostNet();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        WeatherResultsBean bean= JsonUtils.fromJson(rJson,WeatherResultsBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
