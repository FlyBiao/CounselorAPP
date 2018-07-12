package com.cesaas.android.counselor.order.member.bean.service.member;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/3/21 9:01
 * Version 1.0
 */

public class MemberServiceListBean implements Serializable {
    private int VipId;//会员Id
    private String Image;//头像
    private String Name;//名称
    private String Mobile;//手机
    private String Grade;//等级
    private int GradeId;
    private String Remark;//等级
    private String Birthday;//生日
    private String LastBuyDate;//最后购买时间
    private String LastServerDate;//最后服务时间
    private int IsFaceBind;//1、0	是否绑定面部识别ID
    private int IsFocus;//1、0	是否关注该会员
    private int MemberId;
    public boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getGradeId() {
        return GradeId;
    }

    public void setGradeId(int gradeId) {
        GradeId = gradeId;
    }

    public int getMemberId() {
        return MemberId;
    }

    public void setMemberId(int memberId) {
        MemberId = memberId;
    }

    public String getLastServerDate() {
        return LastServerDate;
    }

    public void setLastServerDate(String lastServerDate) {
        LastServerDate = lastServerDate;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getLastBuyDate() {
        return LastBuyDate;
    }

    public void setLastBuyDate(String lastBuyDate) {
        LastBuyDate = lastBuyDate;
    }

    public int getIsFaceBind() {
        return IsFaceBind;
    }

    public void setIsFaceBind(int isFaceBind) {
        IsFaceBind = isFaceBind;
    }

    public int getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(int isFocus) {
        IsFocus = isFocus;
    }
}
