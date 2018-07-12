package com.cesaas.android.counselor.order.member.bean.service.visit;

import java.io.Serializable;

/**
 * Author FGB
 * Description 销售回访服务列表查询
 * Created at 2018/3/6 15:22
 * Version 1.0
 */

public class SalesVisitServiceBean implements Serializable{
    private int Id;//服务Id
    private int Status;//状态 10.待处理20.已完成30.已关闭
    private String Image;//会员头像
    private String Name;//会员名称
    private String Title;//标题
    private String Date;//发布时间
    private String Remark;//自定义备注信息
    private String Desc;//自定义备注信息
    private String BuyDate;//购买时间
    private String Phone;//手机号码
    private int VipId;

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public int getVipId() {
        return VipId;
    }

    public void setVipId(int vipId) {
        VipId = vipId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
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

    public String getBuyDate() {
        return BuyDate;
    }

    public void setBuyDate(String buyDate) {
        BuyDate = buyDate;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
