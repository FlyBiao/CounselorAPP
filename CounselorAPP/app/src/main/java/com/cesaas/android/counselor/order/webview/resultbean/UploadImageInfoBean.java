package com.cesaas.android.counselor.order.webview.resultbean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/5/12 18:12
 * Version 1.0
 */
public class UploadImageInfoBean {

    private String image_url;

    public JSONObject getImageUrl(){
        JSONObject obj=new JSONObject();
        try {

            obj.put("image_url",getImage_url());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }


    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


}
