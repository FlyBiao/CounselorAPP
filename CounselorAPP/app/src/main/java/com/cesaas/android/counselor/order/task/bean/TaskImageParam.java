package com.cesaas.android.counselor.order.task.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Author FGB
 * Description 任务图片参数Bean
 * Created 2017/3/29 17:53
 * Version 1.zero
 */
public class TaskImageParam {
    private int type;
    private List<String> param;

//    public JSONObject getTaskImageParamArray() {
//        JSONObject json = new JSONObject();
//        try {
//            json.put("type",getType());
//            json.put("param", getParam());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return json;
//    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getParam() {
        return param;
    }

    public void setParam(List<String> param) {
        this.param = param;
    }
}
