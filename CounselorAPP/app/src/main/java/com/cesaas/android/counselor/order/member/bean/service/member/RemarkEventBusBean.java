package com.cesaas.android.counselor.order.member.bean.service.member;

/**
 * Author FGB
 * Description
 * Created at 2018/3/23 11:15
 * Version 1.0
 */

public class RemarkEventBusBean {
    private int MemberType;
    private int VipId;
    private String Remark;

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

    public int getMemberType() {
        return MemberType;
    }

    public void setMemberType(int memberType) {
        MemberType = memberType;
    }
}
