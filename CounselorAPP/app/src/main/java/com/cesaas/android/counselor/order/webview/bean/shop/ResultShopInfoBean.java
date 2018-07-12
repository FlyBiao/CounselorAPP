package com.cesaas.android.counselor.order.webview.bean.shop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/8/17 10:18
 * Version 1.0
 */

public class ResultShopInfoBean {

    private int type;
    private JSONArray param;


    public JSONObject getShopInfResult(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("type",getType());
            obj.put("param",getParam());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public JSONArray getParam() {
        return param;
    }

    public void setParam(JSONArray param) {
        this.param = param;
    }
}
