package com.cesaas.android.counselor.order.report.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 我的订阅Bean
 * Created 2017/3/25 11:28
 * Version 1.zero
 */
public class MySubscriptionBean implements Serializable{

    private int BigSortId;

    private int Grade;

    private int Id;

    private int IsUsed;

    private String Remark;

    private int SmallSortId;

    private int Subscription;

    private String Title;

    private int Type;

    public void setBigSortId(int BigSortId){
        this.BigSortId = BigSortId;
    }
    public int getBigSortId(){
        return this.BigSortId;
    }
    public void setGrade(int Grade){
        this.Grade = Grade;
    }
    public int getGrade(){
        return this.Grade;
    }
    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }
    public void setIsUsed(int IsUsed){
        this.IsUsed = IsUsed;
    }
    public int getIsUsed(){
        return this.IsUsed;
    }
    public void setRemark(String Remark){
        this.Remark = Remark;
    }
    public String getRemark(){
        return this.Remark;
    }
    public void setSmallSortId(int SmallSortId){
        this.SmallSortId = SmallSortId;
    }
    public int getSmallSortId(){
        return this.SmallSortId;
    }
    public void setSubscription(int Subscription){
        this.Subscription = Subscription;
    }
    public int getSubscription(){
        return this.Subscription;
    }
    public void setTitle(String Title){
        this.Title = Title;
    }
    public String getTitle(){
        return this.Title;
    }
    public void setType(int Type){
        this.Type = Type;
    }
    public int getType(){
        return this.Type;
    }
}
