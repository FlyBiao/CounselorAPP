package com.cesaas.android.counselor.order.member.bean.service.check;

import java.io.Serializable;

/**
 * Author FGB
 * Description 检查服务详情Bean
 * Created at 2018/3/10 16:31
 * Version 1.0
 */

public class CheckServiceDetailsBean implements Serializable{
    private int Id;//服务项ID
    private int Status;//Status
    private String Name;//会员名称
    private String CounselorName;//顾问名称
//    private int Status;/状态
    private String Date;//服务时间
    private int ServiceType;//服务方式
    private int ServiceResult;//服务结果
    private int GoShop;//是否到店
    private String Remark;
    private int VipId;//会员ID


    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

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

    public String getCounselorName() {
        return CounselorName;
    }

    public void setCounselorName(String counselorName) {
        CounselorName = counselorName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getServiceType() {
        return ServiceType;
    }

    public void setServiceType(int serviceType) {
        ServiceType = serviceType;
    }

    public int getServiceResult() {
        return ServiceResult;
    }

    public void setServiceResult(int serviceResult) {
        ServiceResult = serviceResult;
    }

    public int getGoShop() {
        return GoShop;
    }

    public void setGoShop(int goShop) {
        GoShop = goShop;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getVipId() {
        return VipId;
    }

    public void setVipId(int vipId) {
        VipId = vipId;
    }
}
