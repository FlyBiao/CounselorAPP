package com.cesaas.android.counselor.order.member.bean.service;

import java.io.Serializable;

/**
 * Author FGB
 * Description 发布会员服务时，根据条件查询相关会员
 * Created at 2018/3/10 9:51
 * Version 1.0
 */

public class VipListBean implements Serializable{
    private int Position;
    private int VipId;//
    private int CounselorId;
    private String Name;//名称
    private String Phone;//手机
    private String Image;//头像
    private String GradeTitle;//等级
    private String CreateDate;//注册日期
    private String Brithday;//生日
    private int Points;//积分
    public boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public int getCounselorId() {
        return CounselorId;
    }

    public void setCounselorId(int counselorId) {
        CounselorId = counselorId;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public int getVipId() {
        return VipId;
    }

    public void setVipId(int vipId) {
        VipId = vipId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getGradeTitle() {
        return GradeTitle;
    }

    public void setGradeTitle(String gradeTitle) {
        GradeTitle = gradeTitle;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getBrithday() {
        return Brithday;
    }

    public void setBrithday(String brithday) {
        Brithday = brithday;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }
}
