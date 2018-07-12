package com.cesaas.android.counselor.order.member.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 会员列表Bean
 * Created at 2017/5/16 14:27
 * Version 1.0
 */

public class VipListBean implements Serializable {
    private String Mobile;
    private String CardName;
    private String LastBuy;
    private String IsFocus;
    private String GzNo;
    private String Image;
    private String NickName;
    private String OpenId;
    private String BirthDay;
    private int Point;
    private int CounselorId;
    private int MemberId;
    private int TId;
    private int VipGradeId;
    private int VipId;

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

    public String getLastBuy() {
        return LastBuy;
    }

    public void setLastBuy(String lastBuy) {
        LastBuy = lastBuy;
    }

    public String getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(String isFocus) {
        IsFocus = isFocus;
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

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String openId) {
        OpenId = openId;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public int getCounselorId() {
        return CounselorId;
    }

    public void setCounselorId(int counselorId) {
        CounselorId = counselorId;
    }

    public int getMemberId() {
        return MemberId;
    }

    public void setMemberId(int memberId) {
        MemberId = memberId;
    }

    public int getTId() {
        return TId;
    }

    public void setTId(int TId) {
        this.TId = TId;
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
}
