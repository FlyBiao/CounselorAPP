package com.cesaas.android.counselor.order.member.bean.service;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/3/14 11:29
 * Version 1.0
 */

public class SendMsgInfoBean implements Serializable{

    private int Id;
    private String Name;
    private String Phone;
    private String sex;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
