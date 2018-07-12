package com.cesaas.android.counselor.order.base;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/5/11 18:18
 * Version 1.0
 */

public class ContactJsonObject {

    private int type;
    private JSONObject param;
    private JSONArray array;

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

    public JSONObject getWebViewrRequestArrParam() {
        JSONObject obj=new JSONObject();
        try {
            obj.put("type",getType());
            obj.put("param",getArray());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }


    public JSONArray getArray() {
        return array;
    }

    public void setArray(JSONArray array) {
        this.array = array;
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
