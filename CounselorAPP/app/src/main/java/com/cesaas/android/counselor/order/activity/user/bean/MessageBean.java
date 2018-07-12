package com.cesaas.android.counselor.order.activity.user.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/5/22 16:11
 * Version 1.0
 */

public class MessageBean implements Serializable {
    private int Id;
    private String Description;
    private String NotifyTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getNotifyTime() {
        return NotifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        NotifyTime = notifyTime;
    }
}
