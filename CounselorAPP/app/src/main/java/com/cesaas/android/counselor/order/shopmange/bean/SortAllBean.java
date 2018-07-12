package com.cesaas.android.counselor.order.shopmange.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/5/5 11:55
 * Version 1.0
 */

public class SortAllBean implements Serializable {
    private String Id;
    private String Title;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
