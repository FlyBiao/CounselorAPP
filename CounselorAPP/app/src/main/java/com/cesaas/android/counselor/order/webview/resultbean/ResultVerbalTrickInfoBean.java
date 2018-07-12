package com.cesaas.android.counselor.order.webview.resultbean;

import com.cesaas.android.counselor.order.webview.bean.BaseTypeBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/5/17 13:56
 * Version 1.0
 */

public class ResultVerbalTrickInfoBean  {
    private int type;
    private JSONObject param;


    public JSONObject getVerbalTrickInfoResult(){
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
