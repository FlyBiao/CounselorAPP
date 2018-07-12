package com.cesaas.android.counselor.order.member.bean.service.query;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/3/10 11:39
 * Version 1.0
 */

public class Grades implements Serializable {
    private int Id;
    private String Title;

    public JSONObject getGrades(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("Id",getId());
            obj.put("Title",getTitle());
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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
