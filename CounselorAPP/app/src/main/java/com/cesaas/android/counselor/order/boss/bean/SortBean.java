package com.cesaas.android.counselor.order.boss.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/8/18 14:21
 * Version 1.0
 */

public class SortBean {
    private String Payment;


    public JSONObject getSortIdInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("Payment",getPayment());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }


    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }
}
