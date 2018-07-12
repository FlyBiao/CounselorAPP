package com.cesaas.android.counselor.order.webview.resultbean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/5/12 19:34
 * Version 1.0
 */

public class PromptValueBean {

    private String value;

    public JSONObject getPromptValue(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("value",getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
