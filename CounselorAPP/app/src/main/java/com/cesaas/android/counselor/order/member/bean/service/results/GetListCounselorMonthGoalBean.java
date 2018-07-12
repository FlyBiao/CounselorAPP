package com.cesaas.android.counselor.order.member.bean.service.results;

import java.io.Serializable;

/**
 * Author FGB
 * Description 查询店员月度目标分配情况
 * Created at 2018/3/29 9:22
 * Version 1.0
 */

public class GetListCounselorMonthGoalBean implements Serializable {

    private int CounselorId;//顾问Id
    private String Name;//用户名称
    private String Image;//用户头像
    private double SalesTarget;//销售目标
    private double SalesSurpass;//挑战目标
    private int CardTarget;//开卡目标

    public int getCounselorId() {
        return CounselorId;
    }

    public void setCounselorId(int counselorId) {
        CounselorId = counselorId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public double getSalesTarget() {
        return SalesTarget;
    }

    public void setSalesTarget(double salesTarget) {
        SalesTarget = salesTarget;
    }

    public double getSalesSurpass() {
        return SalesSurpass;
    }

    public void setSalesSurpass(double salesSurpass) {
        SalesSurpass = salesSurpass;
    }

    public int getCardTarget() {
        return CardTarget;
    }

    public void setCardTarget(int cardTarget) {
        CardTarget = cardTarget;
    }
}
