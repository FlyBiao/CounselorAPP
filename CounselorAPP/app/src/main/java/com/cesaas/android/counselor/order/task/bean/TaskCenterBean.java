package com.cesaas.android.counselor.order.task.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 任务中心Bean
 * Created 2017/3/27 15:37
 * Version 1.zero
 */
public class TaskCenterBean implements Serializable{

    private static final long serialVersionUID = 1L;
    private String ImageUrl;//陈列标题图片【一张】
    private String Title;//陈列标题
    private String SheetId;//陈列ID
    private String FlowId;//流程Id
    private String TaskId;//任务ID
    private String FormId;
    private String CreteTime;//陈列截止／完成时间
    private int Status;//陈列状态:未完成、待审核、未通过、已通过、已过期
    private int EvenType;//事件类型
    private int RoleId;//角色编号

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }

    public String getFlowId() {
        return FlowId;
    }

    public void setFlowId(String flowId) {
        FlowId = flowId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
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

    public String getSheetId() {
        return SheetId;
    }

    public void setSheetId(String sheetId) {
        SheetId = sheetId;
    }

    public String getCreteTime() {
        return CreteTime;
    }

    public void setCreteTime(String creteTime) {
        CreteTime = creteTime;
    }

    public int getEvenType() {
        return EvenType;
    }

    public void setEvenType(int evenType) {
        EvenType = evenType;
    }

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int roleId) {
        RoleId = roleId;
    }

    public String getFormId() {
        return FormId;
    }

    public void setFormId(String formId) {
        FormId = formId;
    }
}
