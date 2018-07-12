package com.cesaas.android.counselor.order.webview.resultbean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/5/12 18:12
 * Version 1.0
 */
public class ClerkInfoBean {

    private int id;
    private String image_url;
    private String name;

    public JSONObject getClerkInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("id",getId());
            obj.put("name",getName());
            obj.put("image_url",getImage_url());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
