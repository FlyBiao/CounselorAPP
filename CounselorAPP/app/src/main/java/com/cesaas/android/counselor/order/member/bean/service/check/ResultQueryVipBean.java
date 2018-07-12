package com.cesaas.android.counselor.order.member.bean.service.check;

import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.member.bean.Tags;
import com.cesaas.android.counselor.order.member.bean.service.query.Grades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/3/26 15:39
 * Version 1.0
 */

public class ResultQueryVipBean {

    private int MemberType;
    private double MoneyAreaMin;
    private double MoneyAreaMax;
    private String BuyDateAreaMin;
    private String BuyDateAreaMax;
    private String BirthdayAreaMin;
    private String BirthdayAreaMax;
    private int NoBuyCount;
    private int PointsAreaMin;
    private int PointsAreaMax;
    private String CreateAreaMin;
    private String CreateAreaMax;
    private JSONArray Grades;
    private JSONArray Tags;
    private List<GetTagListBean> Tag;
    private List<Grades> Grade;
    private int TagsCount;
    private int IsVip;

    public JSONObject getVipInfo(int type){
        JSONObject obj=new JSONObject();
        try {
            obj.put("MoneyAreaMin",getMoneyAreaMin());
            obj.put("MoneyAreaMax",getMoneyAreaMax());
            obj.put("BuyDateAreaMin",getBuyDateAreaMin());
            obj.put("BuyDateAreaMax",getBuyDateAreaMax());
            obj.put("BirthdayAreaMin",getBirthdayAreaMin());
            obj.put("BirthdayAreaMax",getBirthdayAreaMax());
            obj.put("PointsAreaMin",getPointsAreaMin());
            obj.put("PointsAreaMax",getPointsAreaMax());
            obj.put("CreateAreaMin",getCreateAreaMin());
            obj.put("CreateAreaMax",getCreateAreaMax());
            obj.put("NoBuyCount",getNoBuyCount());
            obj.put("Grades",getGrades());
            obj.put("Tags",getTags());
            obj.put("TagsCount",getTagsCount());
            if(type!=0){
                obj.put("IsVip",getIsVip());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public JSONObject getVipInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("MoneyAreaMin",getMoneyAreaMin());
            obj.put("MoneyAreaMax",getMoneyAreaMax());
            obj.put("BuyDateAreaMin",getBuyDateAreaMin());
            obj.put("BuyDateAreaMax",getBuyDateAreaMax());
            obj.put("BirthdayAreaMin",getBirthdayAreaMin());
            obj.put("BirthdayAreaMax",getBirthdayAreaMax());
            obj.put("PointsAreaMin",getPointsAreaMin());
            obj.put("PointsAreaMax",getPointsAreaMax());
            obj.put("CreateAreaMin",getCreateAreaMin());
            obj.put("CreateAreaMax",getCreateAreaMax());
            obj.put("NoBuyCount",getNoBuyCount());
            obj.put("Grades",getGrades());
            obj.put("Tags",getTags());
            obj.put("TagsCount",getTagsCount());
            obj.put("IsVip",getIsVip());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public int getIsVip() {
        return IsVip;
    }

    public void setIsVip(int isVip) {
        IsVip = isVip;
    }

    public int getTagsCount() {
        return TagsCount;
    }

    public void setTagsCount(int tagsCount) {
        TagsCount = tagsCount;
    }

    public int getMemberType() {
        return MemberType;
    }

    public void setMemberType(int memberType) {
        MemberType = memberType;
    }

    public List<com.cesaas.android.counselor.order.member.bean.service.query.Grades> getGrade() {
        return Grade;
    }

    public void setGrade(List<com.cesaas.android.counselor.order.member.bean.service.query.Grades> grade) {
        Grade = grade;
    }

    public List<GetTagListBean> getTag() {
        return Tag;
    }

    public void setTag(List<GetTagListBean> tag) {
        Tag = tag;
    }

    public JSONArray getGrades() {
        return Grades;
    }

    public void setGrades(JSONArray grades) {
        Grades = grades;
    }

    public JSONArray getTags() {
        return Tags;
    }

    public void setTags(JSONArray tags) {
        Tags = tags;
    }

    public double getMoneyAreaMin() {
        return MoneyAreaMin;
    }

    public void setMoneyAreaMin(double moneyAreaMin) {
        MoneyAreaMin = moneyAreaMin;
    }

    public double getMoneyAreaMax() {
        return MoneyAreaMax;
    }

    public void setMoneyAreaMax(double moneyAreaMax) {
        MoneyAreaMax = moneyAreaMax;
    }

    public String getBuyDateAreaMin() {
        return BuyDateAreaMin;
    }

    public void setBuyDateAreaMin(String buyDateAreaMin) {
        BuyDateAreaMin = buyDateAreaMin;
    }

    public String getBuyDateAreaMax() {
        return BuyDateAreaMax;
    }

    public void setBuyDateAreaMax(String buyDateAreaMax) {
        BuyDateAreaMax = buyDateAreaMax;
    }

    public String getBirthdayAreaMin() {
        return BirthdayAreaMin;
    }

    public void setBirthdayAreaMin(String birthdayAreaMin) {
        BirthdayAreaMin = birthdayAreaMin;
    }

    public String getBirthdayAreaMax() {
        return BirthdayAreaMax;
    }

    public void setBirthdayAreaMax(String birthdayAreaMax) {
        BirthdayAreaMax = birthdayAreaMax;
    }

    public int getNoBuyCount() {
        return NoBuyCount;
    }

    public void setNoBuyCount(int noBuyCount) {
        NoBuyCount = noBuyCount;
    }

    public int getPointsAreaMin() {
        return PointsAreaMin;
    }

    public void setPointsAreaMin(int pointsAreaMin) {
        PointsAreaMin = pointsAreaMin;
    }

    public int getPointsAreaMax() {
        return PointsAreaMax;
    }

    public void setPointsAreaMax(int pointsAreaMax) {
        PointsAreaMax = pointsAreaMax;
    }

    public String getCreateAreaMin() {
        return CreateAreaMin;
    }

    public void setCreateAreaMin(String createAreaMin) {
        CreateAreaMin = createAreaMin;
    }

    public String getCreateAreaMax() {
        return CreateAreaMax;
    }

    public void setCreateAreaMax(String createAreaMax) {
        CreateAreaMax = createAreaMax;
    }
}
