package com.cesaas.android.counselor.order.workbench.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 回访列表结果Bean
 * Created 2017/4/11 15:07
 * Version 1.zero
 */
public class ReturnVisitBean implements Serializable {

    private String Image;//会员头像
    private String NickName;//会员名
    private String Time;//购买时间
    private String Grade;//会员等级
    private int RuleId;//回访列表ID
    private int VipId;//会员ID
    private String Mobile;

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public int getRuleId() {
        return RuleId;
    }

    public void setRuleId(int ruleId) {
        RuleId = ruleId;
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

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }
}
