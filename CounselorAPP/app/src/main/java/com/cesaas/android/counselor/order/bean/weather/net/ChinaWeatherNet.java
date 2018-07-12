package com.cesaas.android.counselor.order.bean.weather.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.weather.WeatherResultsBean;
import com.cesaas.android.counselor.order.bean.weather.bean.ResultChinaWeatherBean;
import com.cesaas.android.counselor.order.global.BaseChinaWeatherNet;
import com.cesaas.android.counselor.order.global.BaseWeatherNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 中国天气Net
 * Created 2017/4/20 18:58
 * Version 1.zero
 */
public class ChinaWeatherNet extends BaseChinaWeatherNet{
    public ChinaWeatherNet(Context context,double lat,double lon) {
        super(context, true);
        this.uri= "lat="+lat+"&lon="+lon+"&appid=bea4ff6dad416cefcbd1f0480c179f1c&lang=zh_cn";
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
        ResultChinaWeatherBean bean=JsonUtils.fromJson(rJson,ResultChinaWeatherBean.class);
        Log.i(Constant.TAG,"天气:"+bean.list.get(0).getWind().getDeg()+"=="+bean.list.get(0).getWeather().get(0).getDescription());
        Log.i(Constant.TAG,"天气:"+bean.list.get(1).getWind().getDeg()+"=="+bean.list.get(1).getWeather().get(1).getDescription());
//        for (int i=0;i<bean.list.size();i++){
//            Log.i(Constant.TAG,"天气:"+bean.list.get(i).getDt_txt()+"=="+Math.floor(bean.list.get(i).getWind().getDeg()));
//        }

    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
