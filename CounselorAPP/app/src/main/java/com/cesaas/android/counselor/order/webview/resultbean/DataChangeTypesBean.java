package com.cesaas.android.counselor.order.webview.resultbean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/6/9 14:17
 * Version 1.0
 */

public class DataChangeTypesBean {

    private JSONArray arr;


    public JSONObject getDataChangeInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("types",getArr());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public JSONArray getArr() {
        return arr;
    }

    public void setArr(JSONArray arr) {
        this.arr = arr;
    }
}
