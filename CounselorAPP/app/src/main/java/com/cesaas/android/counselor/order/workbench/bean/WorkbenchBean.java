package com.cesaas.android.counselor.order.workbench.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 工作台Bean
 * Created 2017/4/7 15:57
 * Version 1.zero
 */
public class WorkbenchBean implements Serializable{

    private String title;
    private String content;
    private String imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
