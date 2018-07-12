package com.cesaas.android.counselor.order.member.bean.service.query;

import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.member.bean.Tags;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description 返回会员查询模型-筛选会员使用
 * Created at 2018/3/10 11:32
 * Version 1.0
 */

public class ResultQueryMemberBean implements Serializable {

    private String Title;
    private String BeginDate;
    private String EndDate;
    private double MoneyAreaMin;
    private double MoneyAreaMax;
    private String BuyDateAreaMin;
    private String BuyDateAreaMax;
    private String BirthdayAreaMin;
    private String BirthdayAreaMax;
    private String Remark;
    private int NoBuyCount;
    private List<Grades> Grades;
    private int PointsAreaMin;
    private int PointsAreaMax;
    private String CreateAreaMin;
    private String CreateAreaMax;
    private int SendQuestion;
    private List<GetTagListBean> Tags;
    private List<String> VipIds;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(String beginDate) {
        BeginDate = beginDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
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

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getNoBuyCount() {
        return NoBuyCount;
    }

    public void setNoBuyCount(int noBuyCount) {
        NoBuyCount = noBuyCount;
    }

    public List<com.cesaas.android.counselor.order.member.bean.service.query.Grades> getGrades() {
        return Grades;
    }

    public void setGrades(List<com.cesaas.android.counselor.order.member.bean.service.query.Grades> grades) {
        Grades = grades;
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

    public int getSendQuestion() {
        return SendQuestion;
    }

    public void setSendQuestion(int sendQuestion) {
        SendQuestion = sendQuestion;
    }

    public List<GetTagListBean> getTags() {
        return Tags;
    }

    public void setTags(List<GetTagListBean> tags) {
        Tags = tags;
    }

    public List<String> getVipIds() {
        return VipIds;
    }

    public void setVipIds(List<String> vipIds) {
        VipIds = vipIds;
    }
}
