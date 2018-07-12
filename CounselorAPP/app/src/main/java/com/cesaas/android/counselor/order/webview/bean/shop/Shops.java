package com.cesaas.android.counselor.order.webview.bean.shop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/8/17 11:09
 * Version 1.0
 */

public class Shops {

    private JSONArray shops;

    public JSONObject getResultShops(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("shops",getShops());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }


    public JSONArray getShops() {
        return shops;
    }

    public void setShops(JSONArray shops) {
        this.shops = shops;
    }
}
