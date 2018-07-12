package com.cesaas.android.counselor.order.member.bean.service.volume;

import java.io.Serializable;

/**
 * Author FGB
 * Description 推送卷
 * Created at 2018/5/15 15:31
 * Version 1.0
 */

public class PushBean implements Serializable {
    private int VipId;
    private String VipName;
    private int MemberId;
    private String Mobile;
    private int GradeId;
    private String Birthday;
    private int Status;
    private String Message;

    public int getVipId() {
        return VipId;
    }

    public void setVipId(int vipId) {
        VipId = vipId;
    }

    public String getVipName() {
        return VipName;
    }

    public void setVipName(String vipName) {
        VipName = vipName;
    }

    public int getMemberId() {
        return MemberId;
    }

    public void setMemberId(int memberId) {
        MemberId = memberId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public int getGradeId() {
        return GradeId;
    }

    public void setGradeId(int gradeId) {
        GradeId = gradeId;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
