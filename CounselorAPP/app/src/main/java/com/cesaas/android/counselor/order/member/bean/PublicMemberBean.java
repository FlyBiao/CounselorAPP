package com.cesaas.android.counselor.order.member.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 公共会员分配Bean
 * Created 2017/4/11 18:35
 * Version 1.zero
 */
public class PublicMemberBean implements Serializable {

    /**
     * CardName : 权杖卡
     * CounselorId : zero
     * VipId : 543593
     * GzNo : 2017021711400913
     * LastBuy : 最近一个月
     * Birthday : 2017/3/17 zero:00:00
     * Point : zero
     * Image : http://wx.qlogo.cn/mmopen/rdQp3hDG04nwHGS5EZP8fTeLLlhpQCQ3iafV0cBZ6XyTaLibEtZ1vuLotAsH1q5RYxVkABIOEvOjepS8ECs7F3zSayBWKppT6g/0
     * NickName : kk
     * VipGradeId : zero
     * VipInfoPageCrate : 0001-01-01 00:00:00
     * TId : 3
     */
    private String Mobile;
    private String CardName;
    private int CounselorId;
    private String RegisterDate;
    private int VipId;
    private String VipName;
    private String GzNo;
    private String LastBuy;
    private String Birthday;
    private int Point;
    private String Image;
    private String NickName;
    private int VipGradeId;
    private String VipInfoPageCrate;
    private int TId;

    public String getRegisterDate() {
        return RegisterDate;
    }

    public void setRegisterDate(String registerDate) {
        RegisterDate = registerDate;
    }

    public String getVipName() {
        return VipName;
    }

    public void setVipName(String vipName) {
        VipName = vipName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public void setCardName(String CardName) {
        this.CardName = CardName;
    }

    public void setCounselorId(int CounselorId) {
        this.CounselorId = CounselorId;
    }

    public void setVipId(int VipId) {
        this.VipId = VipId;
    }

    public void setGzNo(String GzNo) {
        this.GzNo = GzNo;
    }

    public void setLastBuy(String LastBuy) {
        this.LastBuy = LastBuy;
    }

    public void setBirthday(String Birthday) {
        this.Birthday = Birthday;
    }

    public void setPoint(int Point) {
        this.Point = Point;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public void setVipGradeId(int VipGradeId) {
        this.VipGradeId = VipGradeId;
    }

    public void setVipInfoPageCrate(String VipInfoPageCrate) {
        this.VipInfoPageCrate = VipInfoPageCrate;
    }

    public void setTId(int TId) {
        this.TId = TId;
    }

    public String getCardName() {
        return CardName;
    }

    public int getCounselorId() {
        return CounselorId;
    }

    public int getVipId() {
        return VipId;
    }

    public String getGzNo() {
        return GzNo;
    }

    public String getLastBuy() {
        return LastBuy;
    }

    public String getBirthday() {
        return Birthday;
    }

    public int getPoint() {
        return Point;
    }

    public String getImage() {
        return Image;
    }

    public String getNickName() {
        return NickName;
    }

    public int getVipGradeId() {
        return VipGradeId;
    }

    public String getVipInfoPageCrate() {
        return VipInfoPageCrate;
    }

    public int getTId() {
        return TId;
    }
}
