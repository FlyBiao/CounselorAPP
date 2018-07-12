package com.cesaas.android.counselor.order.member.bean.service.check;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2018/3/26 15:44
 * Version 1.0
 */

public class QueryVipBean {
    private JSONObject Filters;

    public JSONObject getVipInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("Filters",getFilters());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public JSONObject getFilters() {
        return Filters;
    }

    public void setFilters(JSONObject filters) {
        Filters = filters;
    }
}
