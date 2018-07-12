package com.cesaas.android.counselor.order.member.bean.service.member;

/**
 * Author FGB
 * Description
 * Created at 2018/3/23 11:15
 * Version 1.0
 */

public class FocusEventBusBean {
    private int MemberType;
    private int VipId;
    private int FocusType;
    private String Mobile;

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public int getFocusType() {
        return FocusType;
    }

    public void setFocusType(int focusType) {
        FocusType = focusType;
    }

    public int getVipId() {
        return VipId;
    }

    public void setVipId(int vipId) {
        VipId = vipId;
    }

    public int getMemberType() {
        return MemberType;
    }

    public void setMemberType(int memberType) {
        MemberType = memberType;
    }
}
