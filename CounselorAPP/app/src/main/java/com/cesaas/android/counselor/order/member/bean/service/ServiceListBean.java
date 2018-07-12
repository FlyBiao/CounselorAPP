package com.cesaas.android.counselor.order.member.bean.service;

import java.io.Serializable;

/**
 * Author FGB
 * Description 已发布服务列表查询-服务检查
 * Created at 2018/3/9 15:11
 * Version 1.0
 */

public class ServiceListBean implements Serializable {

    private int Id;
    private String Title;
    private int Status;//	10.进行中20.已完成30.已关闭
    private String BeginDate;//开始时间
    private String EndDate;//结束时间
    private String CreateDate;//发布时间
    private String Remark;//自定义备注
    private int Quantity;//查询项目总数
    private int Finish;//已完成数

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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(String beginDate) {
        BeginDate = beginDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getFinish() {
        return Finish;
    }

    public void setFinish(int finish) {
        Finish = finish;
    }
}
