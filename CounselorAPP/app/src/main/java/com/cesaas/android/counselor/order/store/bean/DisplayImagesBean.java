package com.cesaas.android.counselor.order.store.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 上传的陈列图片Bean
 * Created by FGB on 2017/3/1.
 */

public class DisplayImagesBean {
    private String ImageUrl;//陈列图片地址
    private String Describe;//提交陈列任务备注

    public JSONObject getDisplayImageArray() {
        JSONObject json = new JSONObject();
        try {
            json.put("Url",getImageUrl());
            json.put("Describe", getDescribe());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }
}
