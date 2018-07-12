package com.cesaas.android.counselor.order.webview.resultbean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/5/16 10:36
 * Version 1.0
 */

public class ShopMatchInfoBean {
    private int id;
    private String title;
    private String sell_point;
    private String image_url;

    public JSONObject getShopMatchInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("id",getId());
            obj.put("title",getTitle());
            obj.put("sell_point",getSell_point());
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSell_point() {
        return sell_point;
    }

    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
