package com.cesaas.android.counselor.order.task.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 我的任务记录Bean
 * Created 2017/3/27 15:37
 * Version 1.zero
 */
public class MyTaskBean implements Serializable{
    private String date;
    private String title;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
