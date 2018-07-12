package com.cesaas.android.counselor.order.member.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/11/11 15:24
 * Version 1.0
 */

public class VipGetOneBean implements Serializable {

    /**
     * Sex : 1
     * QQ : null
     * VipFrom : Vivian
     * City : 深圳市
     * ConsumeAmount : 3882.02
     * CounselorName : null
     * Country : 中国
     * Address : null
     * CardCreateTime : 2017-06-07 18:28:45
     * Mobile : 13760205391
     * CardName : 普通会员
     * Point : 2144
     * LastBuy : null
     * IsFocus : 0
     * CounselorId : 0
     * GzNo : null
     * Image : http://wx.qlogo.cn/mmopen/MzNANjUnjZLnspSgwHmXeBicl3H69Az43v9EgtTnsE4wrv35dtRdlicemI5BaQb6XeP9YGqYR9EuXs87icYFWEObnWC7GnUga1X/0
     * MemberId : 0
     * NickName : kk
     * OpenId : oOpmhv1ihakswISFgUGqqTzz_26k
     * TId : 0
     * VipGradeId : 0
     * VipId : 1485664
     * BirthDay : 2017-06-16 00:00:00
     * Birthday : 2017/6/16 0:00:00
     * IsCancel : 0
     */

    private String Sex;
    private String QQ;
    private String VipFrom;
    private String City;
    private double ConsumeAmount;
    private String CounselorName;
    private String Country;
    private String Address;
    private String CardCreateTime;
    private String Mobile;
    private String CardName;
    private int Point;
    private String LastBuy;
    private int IsFocus;
    private int CounselorId;
    private String GzNo;
    private String Image;
    private int MemberId;
    private String NickName;
    private String FansName;
    private String OpenId;
    private int TId;
    private int VipGradeId;
    private int VipId;
    private String BirthDay;
    private String Birthday;
    private int IsCancel;
    private String ShopName;
    private String Remark;
    private String Content;
    private String Desc;

    private List<Tags> Tags;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getFansName() {
        return FansName;
    }

    public void setFansName(String fansName) {
        FansName = fansName;
    }

    public List<com.cesaas.android.counselor.order.member.bean.Tags> getTags() {
        return Tags;
    }

    public void setTags(List<com.cesaas.android.counselor.order.member.bean.Tags> tags) {
        Tags = tags;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getVipFrom() {
        return VipFrom;
    }

    public void setVipFrom(String vipFrom) {
        VipFrom = vipFrom;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public double getConsumeAmount() {
        return ConsumeAmount;
    }

    public void setConsumeAmount(double consumeAmount) {
        ConsumeAmount = consumeAmount;
    }

    public String getCounselorName() {
        return CounselorName;
    }

    public void setCounselorName(String counselorName) {
        CounselorName = counselorName;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCardCreateTime() {
        return CardCreateTime;
    }

    public void setCardCreateTime(String cardCreateTime) {
        CardCreateTime = cardCreateTime;
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

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public String getLastBuy() {
        return LastBuy;
    }

    public void setLastBuy(String lastBuy) {
        LastBuy = lastBuy;
    }

    public int getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(int isFocus) {
        IsFocus = isFocus;
    }

    public int getCounselorId() {
        return CounselorId;
    }

    public void setCounselorId(int counselorId) {
        CounselorId = counselorId;
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

    public int getMemberId() {
        return MemberId;
    }

    public void setMemberId(int memberId) {
        MemberId = memberId;
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

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public int getIsCancel() {
        return IsCancel;
    }

    public void setIsCancel(int isCancel) {
        IsCancel = isCancel;
    }
}
