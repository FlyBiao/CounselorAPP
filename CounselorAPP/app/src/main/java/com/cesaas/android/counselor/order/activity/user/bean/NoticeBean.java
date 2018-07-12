package com.cesaas.android.counselor.order.activity.user.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 公告Bean
 * Created at 2017/6/26 15:44
 * Version 1.0
 */

public class NoticeBean implements Serializable{
    private int Id;
    private String Title;
    private String CreateTime;
    private int Status;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
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
