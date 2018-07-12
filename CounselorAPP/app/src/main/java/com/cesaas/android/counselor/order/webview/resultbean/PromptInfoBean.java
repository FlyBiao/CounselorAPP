package com.cesaas.android.counselor.order.webview.resultbean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/5/12 19:05
 * Version 1.0
 */

public class PromptInfoBean {

    private int ok;
    private JSONArray items;

    public JSONObject getPromptInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("ok",getOk());
            obj.put("items",getItems());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    public JSONArray getItems() {
        return items;
    }

    public void setItems(JSONArray items) {
        this.items = items;
    }
}
