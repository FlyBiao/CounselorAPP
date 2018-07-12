package com.cesaas.android.counselor.order.bean.weather.bean;

/**
 * Author FGB
 * Description
 * Created at 2017/5/16 20:07
 * Version 1.0
 */

public class Wind {
    private double speed;

    private double deg;

    public void setSpeed(double speed){
        this.speed = speed;
    }
    public double getSpeed(){
        return this.speed;
    }
    public void setDeg(double deg){
        this.deg = deg;
    }
    public double getDeg(){
        return this.deg;
    }
}
