package com.cesaas.android.counselor.order.activity.main.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created 2017/4/6 20:00
 * Version 1.zero
 */
public class MyWorkBean implements Serializable{

    private String title;
    private String content;
    private String date;
    private int status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
