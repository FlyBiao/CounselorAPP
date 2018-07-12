package com.cesaas.android.counselor.order.member.bean.service.multipleservice;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/4/25 10:07
 * Version 1.0
 */

public class MultipleServiceBean implements Serializable {
    private String Title;
    private int Type;
    private String TaskDate;
    private String TaskContent;
    private String CounselorName;
    private String Remark;
    private int TaskId;
    private int CounselorId;
    private  String Mobile;
    private int VipId;

    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getVipId() {
        return VipId;
    }

    public void setVipId(int vipId) {
        VipId = vipId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTaskDate() {
        return TaskDate;
    }

    public void setTaskDate(String taskDate) {
        TaskDate = taskDate;
    }

    public String getTaskContent() {
        return TaskContent;
    }

    public void setTaskContent(String taskContent) {
        TaskContent = taskContent;
    }

    public String getCounselorName() {
        return CounselorName;
    }

    public void setCounselorName(String counselorName) {
        CounselorName = counselorName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getTaskId() {
        return TaskId;
    }

    public void setTaskId(int taskId) {
        TaskId = taskId;
    }

    public int getCounselorId() {
        return CounselorId;
    }

    public void setCounselorId(int counselorId) {
        CounselorId = counselorId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
