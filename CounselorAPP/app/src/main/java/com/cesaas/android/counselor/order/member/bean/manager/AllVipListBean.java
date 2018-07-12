package com.cesaas.android.counselor.order.member.bean.manager;

import java.io.Serializable;

/**
 * Author FGB
 * Description 会员列表
 * Created at 2018/5/8 15:51
 * Version 1.0
 */

public class AllVipListBean implements Serializable {
    private int VipId;
    private String Image;
    private String Name;
    private String Mobile;
    private String Grade;
    private String Birthday;
    private String LastBuyDate;
    private String IsFaceBind;
    private String Remark;
    private String LastServerDate;
    private String ShopName;
    private String DevelopShopName;
    private String CrDate;
    private String CounselorName;

    public String getCounselorName() {
        return CounselorName;
    }

    public void setCounselorName(String counselorName) {
        CounselorName = counselorName;
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

    public String getIsFaceBind() {
        return IsFaceBind;
    }

    public void setIsFaceBind(String isFaceBind) {
        IsFaceBind = isFaceBind;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getLastServerDate() {
        return LastServerDate;
    }

    public void setLastServerDate(String lastServerDate) {
        LastServerDate = lastServerDate;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getDevelopShopName() {
        return DevelopShopName;
    }

    public void setDevelopShopName(String developShopName) {
        DevelopShopName = developShopName;
    }

    public String getCrDate() {
        return CrDate;
    }

    public void setCrDate(String crDate) {
        CrDate = crDate;
    }
}
