package com.cesaas.android.counselor.order.shoptask.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 店铺任务Bean
 * Created 2017/4/20 18:05
 * Version 1.zero
 */
public class ShopTaskListBean implements Serializable{
    /**
     * Title : 测试任务_店长
     * CreteTime : 2018/1/23 11:22:07
     * Status : 1
     * DurationTime : 100
     * WorkId : 132
     * EvenType : 1
     * RoleId : 1057
     * FlowId : 128
     * TaskId : 281
     * FormId : 97
     * NotifyName : 谢伟伟
     * Description : null
     * Seore : 0
     * ShopId : 16178
     * ShopName : 东湖店
     * DoneDate : null
     * FlowStatus : 1
     */

    private String Title;
    private String CreteTime;
    private int Status;
    private int DurationTime;
    private int WorkId;
    private int EvenType;
    private int RoleId;
    private int FlowId;
    private int TaskId;
    private int FormId;
    private String NotifyName;
    private String Description;
    private int Seore;
    private int ShopId;
    private String ShopName;
    private String DoneDate;
    private int FlowStatus;
    private int TaskLevel;

    public int getTaskLevel() {
        return TaskLevel;
    }

    public void setTaskLevel(int taskLevel) {
        TaskLevel = taskLevel;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setCreteTime(String CreteTime) {
        this.CreteTime = CreteTime;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public void setDurationTime(int DurationTime) {
        this.DurationTime = DurationTime;
    }

    public void setWorkId(int WorkId) {
        this.WorkId = WorkId;
    }

    public void setEvenType(int EvenType) {
        this.EvenType = EvenType;
    }

    public void setRoleId(int RoleId) {
        this.RoleId = RoleId;
    }

    public void setFlowId(int FlowId) {
        this.FlowId = FlowId;
    }

    public void setTaskId(int TaskId) {
        this.TaskId = TaskId;
    }

    public void setFormId(int FormId) {
        this.FormId = FormId;
    }

    public void setNotifyName(String NotifyName) {
        this.NotifyName = NotifyName;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setSeore(int Seore) {
        this.Seore = Seore;
    }

    public void setShopId(int ShopId) {
        this.ShopId = ShopId;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public void setDoneDate(String DoneDate) {
        this.DoneDate = DoneDate;
    }

    public void setFlowStatus(int FlowStatus) {
        this.FlowStatus = FlowStatus;
    }

    public String getTitle() {
        return Title;
    }

    public String getCreteTime() {
        return CreteTime;
    }

    public int getStatus() {
        return Status;
    }

    public int getDurationTime() {
        return DurationTime;
    }

    public int getWorkId() {
        return WorkId;
    }

    public int getEvenType() {
        return EvenType;
    }

    public int getRoleId() {
        return RoleId;
    }

    public int getFlowId() {
        return FlowId;
    }

    public int getTaskId() {
        return TaskId;
    }

    public int getFormId() {
        return FormId;
    }

    public String getNotifyName() {
        return NotifyName;
    }

    public String getDescription() {
        return Description;
    }

    public int getSeore() {
        return Seore;
    }

    public int getShopId() {
        return ShopId;
    }

    public String getShopName() {
        return ShopName;
    }

    public String getDoneDate() {
        return DoneDate;
    }

    public int getFlowStatus() {
        return FlowStatus;
    }
//    private String Title;
//    private String CreteTime;
//    private int RoleId;
//    private int Status;//0待完成，1已完成了
//    private int EvenType;
//    private int FlowId;
//    private int TaskId;
//    private int FormId;
//    private int SheetId;
//    private int WorkId;
//    private int DurationTime;

}
