package com.cesaas.android.counselor.order.bean.weather.bean;

/**
 * Author FGB
 * Description
 * Created at 2017/5/16 20:01
 * Version 1.0
 */

public class BaseChinaWeatherBean {
    private String cod;
    private String message;
    private int cnt;
//    "cod": "200",
//            "message": 0.0098,
//            "cnt": 36,


    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
