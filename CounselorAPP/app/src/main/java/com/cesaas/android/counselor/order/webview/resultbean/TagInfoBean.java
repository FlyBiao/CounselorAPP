package com.cesaas.android.counselor.order.webview.resultbean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/5/14 11:29
 * Version 1.0
 */

public class TagInfoBean {

    private int id;
    private int category_id;
    private String title;
    private String category_name;

    public JSONObject getTagInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("id",getId());
            obj.put("title",getTitle());
            obj.put("category_name",getCategory_name());
            obj.put("category_id",getCategory_id());
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

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
