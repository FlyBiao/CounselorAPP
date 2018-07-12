package com.cesaas.android.counselor.order.store.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author FGB
 * Description 完成成列图片Beans
 * Created 2017/3/4 20:44
 * Version 1.zero
 */
public class CompleteImages implements Serializable {

    private String Id;//陈列图片Id
    private String Url;//陈列图片地址
    private String Describe;//提交陈列任务备注

    public JSONObject getAapprovalDisplayImagesArray() {
        JSONObject json = new JSONObject();
        try {
            json.put("Id",getId());
            json.put("Url",getUrl());
            json.put("Comment", getDescribe());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }
}
