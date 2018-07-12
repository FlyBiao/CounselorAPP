package com.cesaas.android.counselor.order.base;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/5/10 21:05
 * Version 1.0
 */

public class BaseWebViewRequestBean {
    private int type;
    private JSONArray param;

    public JSONObject getWebViewrRequestParam() {
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
