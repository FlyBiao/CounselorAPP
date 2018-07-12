package com.cesaas.android.java.bean.goods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2018/6/24 11:32
 * Version 1.0
 */

public class AddBarcodeModelItemBean {
    private JSONArray value;

    public JSONObject getAddBarcodeModelItem(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("value",getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public JSONArray getValue() {
        return value;
    }

    public void setValue(JSONArray value) {
        this.value = value;
    }
}
