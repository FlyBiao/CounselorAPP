package com.cesaas.android.counselor.order.webview.resultbean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description 店铺筛选
 * Created at 2017/5/12 18:07
 * Version 1.0
 */

public class ResultQueryShopBean {

    private int type;
    private JSONObject param;


    public JSONObject getQueryInfo(){
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

    public JSONObject getParam() {
        return param;
    }

    public void setParam(JSONObject param) {
        this.param = param;
    }
}
