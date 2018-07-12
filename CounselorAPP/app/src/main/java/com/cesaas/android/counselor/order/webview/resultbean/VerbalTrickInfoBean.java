package com.cesaas.android.counselor.order.webview.resultbean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/5/17 13:53
 * Version 1.0
 */

public class VerbalTrickInfoBean {

    private int id;
    private String question;
    private String content;
    private int group_id;
    private String group_name;

    public JSONObject getVerbalTrickInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("id",getId());
            obj.put("question",getQuestion());
            obj.put("content",getContent());
            obj.put("group_id",getGroup_id());
            obj.put("group_name",getGroup_name());
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}
