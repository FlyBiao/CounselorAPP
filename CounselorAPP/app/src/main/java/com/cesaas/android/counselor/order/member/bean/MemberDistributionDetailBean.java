package com.cesaas.android.counselor.order.member.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 会员分配Bean
 * Created 2017/4/11 18:35
 * Version 1.zero
 */
public class MemberDistributionDetailBean implements Serializable {

    private String NickName;
    private String Mobile;
    private String CardName;
    private int CounselorId;
    private int Point;
    private int VipGradeId;
    private int VipId;
    private String VipName;
    private String VipInfoPageCrate;
    private String Birthday;
    private String GzNo;
    private String Image;
    private String LastBuy;


    public String getVipName() {
        return VipName;
    }

    public void setVipName(String vipName) {
        VipName = vipName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public int getCounselorId() {
        return CounselorId;
    }

    public void setCounselorId(int counselorId) {
        CounselorId = counselorId;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public int getVipGradeId() {
        return VipGradeId;
    }

    public void setVipGradeId(int vipGradeId) {
        VipGradeId = vipGradeId;
    }

    public int getVipId() {
        return VipId;
    }

    public void setVipId(int vipId) {
        VipId = vipId;
    }

    public String getVipInfoPageCrate() {
        return VipInfoPageCrate;
    }

    public void setVipInfoPageCrate(String vipInfoPageCrate) {
        VipInfoPageCrate = vipInfoPageCrate;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getGzNo() {
        return GzNo;
    }

    public void setGzNo(String gzNo) {
        GzNo = gzNo;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getLastBuy() {
        return LastBuy;
    }

    public void setLastBuy(String lastBuy) {
        LastBuy = lastBuy;
    }
}
