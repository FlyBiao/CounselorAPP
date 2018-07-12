package com.cesaas.android.counselor.order.report.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 报表中心Bean
 * Created 2017/3/25 11:28
 * Version 1.zero
 */
public class ReportCenterBean implements Serializable{

    private int Id;

    private String Title;

    private int IsUsed;

    private int BigSortId;

    private int SmallSortId;

    private String BigSortTitle;

    private String SmallSortTitle;

    private int Subscription;

    private String Remark;

    private int Grade;

    private int Type;

    private String ImageUrl;

    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }
    public void setTitle(String Title){
        this.Title = Title;
    }
    public String getTitle(){
        return this.Title;
    }
    public void setIsUsed(int IsUsed){
        this.IsUsed = IsUsed;
    }
    public int getIsUsed(){
        return this.IsUsed;
    }
    public void setBigSortId(int BigSortId){
        this.BigSortId = BigSortId;
    }
    public int getBigSortId(){
        return this.BigSortId;
    }
    public void setSmallSortId(int SmallSortId){
        this.SmallSortId = SmallSortId;
    }
    public int getSmallSortId(){
        return this.SmallSortId;
    }
    public void setBigSortTitle(String BigSortTitle){
        this.BigSortTitle = BigSortTitle;
    }
    public String getBigSortTitle(){
        return this.BigSortTitle;
    }
    public void setSmallSortTitle(String SmallSortTitle){
        this.SmallSortTitle = SmallSortTitle;
    }
    public String getSmallSortTitle(){
        return this.SmallSortTitle;
    }
    public void setSubscription(int Subscription){
        this.Subscription = Subscription;
    }
    public int getSubscription(){
        return this.Subscription;
    }
    public void setRemark(String Remark){
        this.Remark = Remark;
    }
    public String getRemark(){
        return this.Remark;
    }
    public void setGrade(int Grade){
        this.Grade = Grade;
    }
    public int getGrade(){
        return this.Grade;
    }
    public void setType(int Type){
        this.Type = Type;
    }
    public int getType(){
        return this.Type;
    }
    public void setImageUrl(String ImageUrl){
        this.ImageUrl = ImageUrl;
    }
    public String getImageUrl(){
        return this.ImageUrl;
    }
}
