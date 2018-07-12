package com.cesaas.android.counselor.order.member.bean.service.continues;

import java.io.Serializable;

/**
 * Author FGB
 * Description 二次服务列表
 * Created at 2018/4/28 11:22
 * Version 1.0
 */

public class GetSecondListBean implements Serializable {
    private int Id;
    private String Image;
    private String Name;
    private String Title;
    private int Status;
    private String Date;
    private String Remark;
    private String LastTask;
    private String Phone;
    private int VipId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getLastTask() {
        return LastTask;
    }

    public void setLastTask(String lastTask) {
        LastTask = lastTask;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getVipId() {
        return VipId;
    }

    public void setVipId(int vipId) {
        VipId = vipId;
    }
}
