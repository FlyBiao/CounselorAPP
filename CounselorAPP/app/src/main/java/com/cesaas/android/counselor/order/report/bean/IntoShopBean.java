package com.cesaas.android.counselor.order.report.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created 2017/4/24 17:18
 * Version 1.zero
 */
public class IntoShopBean implements Serializable {

    private int Id;
    private String CreateTime;
    private int NumOfCustomer;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public int getNumOfCustomer() {
        return NumOfCustomer;
    }

    public void setNumOfCustomer(int numOfCustomer) {
        NumOfCustomer = numOfCustomer;
    }
}
