package com.cesaas.android.counselor.order.manager.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/9/12 16:43
 * Version 1.0
 */

public class TaskDetailsBean implements Serializable {


    /**
     * WorkName : 开始了
     * Status : 0
     * FormId : 0
     * ShopId : 16178
     * ImageUrl : https://pro.modao.cc/uploads3/images/980/9808564/raw_1495716178.jpeg
     * TId : 24
     * FlowId : 0
     * TaskId : 160
     * FlowName : null
     * NotifyName : null
     * FlowStatus : 0
     * IsDone : null
     * CounselorId : 0
     * DurationTime : 0
     * RoleId : 0
     * EvenType : 0
     * ProcessId : 0
     * SyncCode : null
     * CreateTime : 2017-09-19 10:10:49
     * PreviousId : 0
     * WorkId : 35
     * Description : null
     * ShopName : 东湖店
     */

    private String WorkName;
    private int Status;
    private int FormId;
    private int ShopId;
    private String ImageUrl;
    private int TId;
    private int FlowId;
    private int TaskId;
    private String FlowName;
    private String NotifyName;
    private int FlowStatus;
    private String IsDone;
    private int CounselorId;
    private int DurationTime;
    private int RoleId;
    private int EvenType;
    private int ProcessId;
    private Object SyncCode;
    private String CreateTime;
    private int PreviousId;
    private int WorkId;
    private String Description;
    private String ShopName;

    public String getWorkName() {
        return WorkName;
    }

    public void setWorkName(String workName) {
        WorkName = workName;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getFormId() {
        return FormId;
    }

    public void setFormId(int formId) {
        FormId = formId;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getTId() {
        return TId;
    }

    public void setTId(int TId) {
        this.TId = TId;
    }

    public int getFlowId() {
        return FlowId;
    }

    public void setFlowId(int flowId) {
        FlowId = flowId;
    }

    public int getTaskId() {
        return TaskId;
    }

    public void setTaskId(int taskId) {
        TaskId = taskId;
    }

    public String getFlowName() {
        return FlowName;
    }

    public void setFlowName(String flowName) {
        FlowName = flowName;
    }

    public String getNotifyName() {
        return NotifyName;
    }

    public void setNotifyName(String notifyName) {
        NotifyName = notifyName;
    }

    public int getFlowStatus() {
        return FlowStatus;
    }

    public void setFlowStatus(int flowStatus) {
        FlowStatus = flowStatus;
    }

    public String getIsDone() {
        return IsDone;
    }

    public void setIsDone(String isDone) {
        IsDone = isDone;
    }

    public int getCounselorId() {
        return CounselorId;
    }

    public void setCounselorId(int counselorId) {
        CounselorId = counselorId;
    }

    public int getDurationTime() {
        return DurationTime;
    }

    public void setDurationTime(int durationTime) {
        DurationTime = durationTime;
    }

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int roleId) {
        RoleId = roleId;
    }

    public int getEvenType() {
        return EvenType;
    }

    public void setEvenType(int evenType) {
        EvenType = evenType;
    }

    public int getProcessId() {
        return ProcessId;
    }

    public void setProcessId(int processId) {
        ProcessId = processId;
    }

    public Object getSyncCode() {
        return SyncCode;
    }

    public void setSyncCode(Object syncCode) {
        SyncCode = syncCode;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public int getPreviousId() {
        return PreviousId;
    }

    public void setPreviousId(int previousId) {
        PreviousId = previousId;
    }

    public int getWorkId() {
        return WorkId;
    }

    public void setWorkId(int workId) {
        WorkId = workId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }
}
