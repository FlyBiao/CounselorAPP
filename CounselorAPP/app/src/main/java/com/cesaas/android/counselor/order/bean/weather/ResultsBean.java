package com.cesaas.android.counselor.order.bean.weather;

import com.cesaas.android.counselor.order.bean.BaseWeatherResultsBean;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created 2017/4/25 14:27
 * Version 1.zero
 */
public class ResultsBean extends BaseWeatherResultsBean {

    private List<WeatherResultsBean> results;

    public void setResults(List<WeatherResultsBean> results){
        this.results = results;
    }
    public List<WeatherResultsBean> getResults(){
        return this.results;
    }
}
