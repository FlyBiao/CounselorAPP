package com.cesaas.android.counselor.order.bean.weather.bean;

/**
 * Author FGB
 * Description
 * Created at 2017/5/16 20:06
 * Version 1.0
 */

public class ChinaWeather
    {
        private int id;

        private String main;

        private String description;

        private String icon;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setMain(String main){
        this.main = main;
    }
    public String getMain(){
        return this.main;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }
    public String getIcon(){
        return this.icon;
    }

}
