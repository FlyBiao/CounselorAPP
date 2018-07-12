package com.cesaas.android.counselor.order.bean.weather.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.weather.DailyBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbDateUtil;

import java.text.ParseException;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created 2017/4/25 14:48
 * Version 1.zero
 */
public class WeatherAdapter extends BaseAdapter {

    List<DailyBean> dailyList;
    private Context mContext;
    private boolean isToDay=true;

    private TextView tv_day,tv_wethers,tv_high_low;
    private ImageView iv_weather_img;

    public WeatherAdapter(List<DailyBean> dailyList,Context mContext){
        this.dailyList=dailyList;
        this.mContext=mContext;
    }

    @Override
    public int getCount() {
        return dailyList.size();
    }

    @Override
    public Object getItem(int position) {
        return dailyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_weather, parent, false);
        tv_day= (TextView) convertView.findViewById(R.id.tv_day);
        tv_wethers= (TextView) convertView.findViewById(R.id.tv_wethers);
        tv_high_low= (TextView) convertView.findViewById(R.id.tv_high_low);
        iv_weather_img= (ImageView) convertView.findViewById(R.id.iv_weather_img);

        DailyBean bean=dailyList.get(position);
        tv_wethers.setText(bean.getText_night());
        tv_high_low.setText(bean.getLow()+"至"+bean.getHigh()+"℃");
        try {
            if(AbDateUtil.IsToday(bean.getDate())==true){
                tv_day.setText("今天");
            }else{
                tv_day.setText("明天");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(bean.getCode_day().equals("0")){
            iv_weather_img.setImageResource(R.mipmap.zero);
        }else if(bean.getCode_day().equals("1")){
            iv_weather_img.setImageResource(R.mipmap.one);
        }else if(bean.getCode_day().equals("2")){
            iv_weather_img.setImageResource(R.mipmap.two);
        }else if(bean.getCode_day().equals("3")){
            iv_weather_img.setImageResource(R.mipmap.zero);
        }else if(bean.getCode_day().equals("4")){
            iv_weather_img.setImageResource(R.mipmap.four);
        }else if(bean.getCode_day().equals("5")){
            iv_weather_img.setImageResource(R.mipmap.five);
        }else if(bean.getCode_day().equals("6")){
            iv_weather_img.setImageResource(R.mipmap.six);
        }else if(bean.getCode_day().equals("7")){
            iv_weather_img.setImageResource(R.mipmap.seven);
        }else if(bean.getCode_day().equals("8")){
            iv_weather_img.setImageResource(R.mipmap.eight);
        }else if(bean.getCode_day().equals("9")){
            iv_weather_img.setImageResource(R.mipmap.nine);
        }else if(bean.getCode_day().equals("10")){
            iv_weather_img.setImageResource(R.mipmap.ten);
        }else if(bean.getCode_day().equals("11")){
            iv_weather_img.setImageResource(R.mipmap.eleven);
        }else if(bean.getCode_day().equals("12")){
            iv_weather_img.setImageResource(R.mipmap.twelve);
        }else if(bean.getCode_day().equals("13")){
            iv_weather_img.setImageResource(R.mipmap.thirteen);
        }else if(bean.getCode_day().equals("14")){
            iv_weather_img.setImageResource(R.mipmap.fourteen);
        }else if(bean.getCode_day().equals("15")){
            iv_weather_img.setImageResource(R.mipmap.shiwu);
        }else if(bean.getCode_day().equals("16")){
            iv_weather_img.setImageResource(R.mipmap.shiliu);
        }else if(bean.getCode_day().equals("17")){
            iv_weather_img.setImageResource(R.mipmap.shiqi);
        }else if(bean.getCode_day().equals("18")){
            iv_weather_img.setImageResource(R.mipmap.shiba);
        }else if(bean.getCode_day().equals("19")){
            iv_weather_img.setImageResource(R.mipmap.shijiu);
        }else if(bean.getCode_day().equals("20")){
            iv_weather_img.setImageResource(R.mipmap.ershi);
        }else if(bean.getCode_day().equals("21")){
            iv_weather_img.setImageResource(R.mipmap.ershiyi);
        }else if(bean.getCode_day().equals("22")){
            iv_weather_img.setImageResource(R.mipmap.ershier);
        }else if(bean.getCode_day().equals("23")){
            iv_weather_img.setImageResource(R.mipmap.ershisan);
        }else if(bean.getCode_day().equals("24")){
            iv_weather_img.setImageResource(R.mipmap.ershisi);
        }else if(bean.getCode_day().equals("25")){
            iv_weather_img.setImageResource(R.mipmap.ershiwu);
        }else if(bean.getCode_day().equals("26")){
            iv_weather_img.setImageResource(R.mipmap.ershiliu);
        }else if(bean.getCode_day().equals("27")){
            iv_weather_img.setImageResource(R.mipmap.ershiqi);
        }else if(bean.getCode_day().equals("28")){
            iv_weather_img.setImageResource(R.mipmap.ershiba);
        }else if(bean.getCode_day().equals("29")){
            iv_weather_img.setImageResource(R.mipmap.ershijiu);
        }else if(bean.getCode_day().equals("30")){
            iv_weather_img.setImageResource(R.mipmap.sanshi);
        }else if(bean.getCode_day().equals("31")){
            iv_weather_img.setImageResource(R.mipmap.sanshiyi);
        }else if(bean.getCode_day().equals("32")){
            iv_weather_img.setImageResource(R.mipmap.sanshier);
        }else if(bean.getCode_day().equals("33")){
            iv_weather_img.setImageResource(R.mipmap.sanshisan);
        }else if(bean.getCode_day().equals("34")){
            iv_weather_img.setImageResource(R.mipmap.sanshisi);
        }else if(bean.getCode_day().equals("35")){
            iv_weather_img.setImageResource(R.mipmap.sanshiwu);
        }else if(bean.getCode_day().equals("36")){
            iv_weather_img.setImageResource(R.mipmap.sanshiliu);
        }else if(bean.getCode_day().equals("37")){
            iv_weather_img.setImageResource(R.mipmap.sanshiqi);
        }else if(bean.getCode_day().equals("38")){
            iv_weather_img.setImageResource(R.mipmap.sanshiba);
        }else if(bean.getCode_day().equals("99")){
            iv_weather_img.setImageResource(R.mipmap.jiujiu);
        }

        return convertView;
    }
}
