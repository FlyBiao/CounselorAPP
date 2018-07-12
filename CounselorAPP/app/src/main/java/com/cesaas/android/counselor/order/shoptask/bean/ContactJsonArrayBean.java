package com.cesaas.android.counselor.order.shoptask.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/5/10 20:19
 * Version 1.0
 */

public class ContactJsonArrayBean {

    private int Id;
    private String ImageUrl;
    private String Name;

    public JSONObject jsonObject(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("Id",getId());
            obj.put("ImageUrl",getImageUrl());
            obj.put("Name",getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public JSONObject getImageUrlObject(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("ImageUrl",getImageUrl());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
