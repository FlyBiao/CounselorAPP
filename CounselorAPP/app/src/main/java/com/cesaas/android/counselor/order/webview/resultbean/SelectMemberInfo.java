package com.cesaas.android.counselor.order.webview.resultbean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/5/16 11:26
 * Version 1.0
 */

public class SelectMemberInfo {

    private int id;
    private String logo;
    private String name;
    private String grade;
    private String mobile;
    private int counselor_id;
    private int points;
    private String birthday;

    public JSONObject getMemberInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("id",getId());
            obj.put("logo",getLogo());
            obj.put("name",getName());
            obj.put("grade",getGrade());
            obj.put("mobile",getMobile());
            obj.put("counselor_id",getCounselor_id());
            obj.put("points",getPoints());
            obj.put("birthday",getBirthday());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getCounselor_id() {
        return counselor_id;
    }

    public void setCounselor_id(int counselor_id) {
        this.counselor_id = counselor_id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
