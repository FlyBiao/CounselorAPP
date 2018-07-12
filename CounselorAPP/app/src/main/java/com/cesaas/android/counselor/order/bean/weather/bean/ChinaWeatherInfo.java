package com.cesaas.android.counselor.order.bean.weather.bean;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/5/16 20:03
 * Version 1.0
 */

public class ChinaWeatherInfo {
    private int dt;

    private Main main;

    private List<ChinaWeather> weather;

    private Clouds clouds;

    private Wind wind;

    private Rain rain;

    private Sys sys;

    private String dt_txt;

    public void setDt(int dt){
        this.dt = dt;
    }
    public int getDt(){
        return this.dt;
    }
    public void setMain(Main main){
        this.main = main;
    }
    public Main getMain(){
        return this.main;
    }
    public void setWeather(List<ChinaWeather> weather){
        this.weather = weather;
    }
    public List<ChinaWeather> getWeather(){
        return this.weather;
    }
    public void setClouds(Clouds clouds){
        this.clouds = clouds;
    }
    public Clouds getClouds(){
        return this.clouds;
    }
    public void setWind(Wind wind){
        this.wind = wind;
    }
    public Wind getWind(){
        return this.wind;
    }
    public void setRain(Rain rain){
        this.rain = rain;
    }
    public Rain getRain(){
        return this.rain;
    }
    public void setSys(Sys sys){
        this.sys = sys;
    }
    public Sys getSys(){
        return this.sys;
    }
    public void setDt_txt(String dt_txt){
        this.dt_txt = dt_txt;
    }
    public String getDt_txt(){
        return this.dt_txt;
    }

}
