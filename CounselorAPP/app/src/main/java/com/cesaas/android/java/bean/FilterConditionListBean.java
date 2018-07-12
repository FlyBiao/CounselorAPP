package com.cesaas.android.java.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2018/5/24 16:03
 * Version 1.0
 */

public class FilterConditionListBean {

    private JSONArray value;

    public JSONObject getFilterConditionList(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("value",getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj;
    }

    public JSONArray getValue() {
        return value;
    }

    public void setValue(JSONArray value) {
        this.value = value;
    }
}
