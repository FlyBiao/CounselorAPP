package com.cesaas.android.counselor.order.manager.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/9/12 16:43
 * Version 1.0
 */

public class TaskListBean implements Serializable {


    /**
     * TId : 24
     * TaskId : 138
     * WorkId : 0
     * TaskTitle : 考虑考虑l
     * Descriptoin : null
     * StartDate : null
     * EndDate : null
     * Status : 0
     * CategoryId : 0
     * TaskLeve : 0
     * Inuse : 0
     * CreateTime : 0001-01-01 00:00:00
     * SyncCode : da353337-6b12-4701-befb-da9335b14496
     * CreateName : null
     * TemplateId : 0
     * ImageUrl : null
     * WorkQuantity : 1
     */

    private int TId;
    private int TaskId;
    private int WorkId;
    private String TaskTitle;
    private String Descriptoin;
    private String StartDate;
    private String EndDate;
    private int Status;
    private int CategoryId;
    private int TaskLeve;
    private int Inuse;
    private String CreateTime;
    private String SyncCode;
    private String CreateName;
    private int TemplateId;
    private String ImageUrl;
    private int WorkQuantity;
    private int FlowStatus;

    public int getFlowStatus() {
        return FlowStatus;
    }

    public void setFlowStatus(int flowStatus) {
        FlowStatus = flowStatus;
    }

    public int getTId() {
        return TId;
    }

    public void setTId(int TId) {
        this.TId = TId;
    }

    public int getTaskId() {
        return TaskId;
    }

    public void setTaskId(int taskId) {
        TaskId = taskId;
    }

    public int getWorkId() {
        return WorkId;
    }

    public void setWorkId(int workId) {
        WorkId = workId;
    }

    public String getTaskTitle() {
        return TaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        TaskTitle = taskTitle;
    }

    public String getDescriptoin() {
        return Descriptoin;
    }

    public void setDescriptoin(String descriptoin) {
        Descriptoin = descriptoin;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getTaskLeve() {
        return TaskLeve;
    }

    public void setTaskLeve(int taskLeve) {
        TaskLeve = taskLeve;
    }

    public int getInuse() {
        return Inuse;
    }

    public void setInuse(int inuse) {
        Inuse = inuse;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getSyncCode() {
        return SyncCode;
    }

    public void setSyncCode(String syncCode) {
        SyncCode = syncCode;
    }

    public String getCreateName() {
        return CreateName;
    }

    public void setCreateName(String createName) {
        CreateName = createName;
    }

    public int getTemplateId() {
        return TemplateId;
    }

    public void setTemplateId(int templateId) {
        TemplateId = templateId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getWorkQuantity() {
        return WorkQuantity;
    }

    public void setWorkQuantity(int workQuantity) {
        WorkQuantity = workQuantity;
    }
}
