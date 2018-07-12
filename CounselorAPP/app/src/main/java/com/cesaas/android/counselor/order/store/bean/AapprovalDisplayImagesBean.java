package com.cesaas.android.counselor.order.store.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 审核上传的陈列图片Bean
 * Created by FGB on 2017/3/1.
 */

public class AapprovalDisplayImagesBean {
    private String Id;
    private String Url;
    private String Comment;

    public JSONObject getAapprovalDisplayImagesArray() {
        JSONObject json = new JSONObject();
        try {
            json.put("Id",getId());
            json.put("Url",getUrl());
            json.put("Comment", getComment());
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

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
