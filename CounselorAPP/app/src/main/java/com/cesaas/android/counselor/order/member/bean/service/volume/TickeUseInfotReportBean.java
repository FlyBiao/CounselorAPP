package com.cesaas.android.counselor.order.member.bean.service.volume;

import java.io.Serializable;

/**
 * Author FGB
 * Description 券分析追踪-列表
 * Created at 2018/5/16 17:26
 * Version 1.0
 */

public class TickeUseInfotReportBean implements Serializable {
    private String Title;
    private String Name;
    private String CardName;
    private String Mobile;
    private String Price;
    private String StartDate;
    private String EndDate;
    private int IsUsed;
    private int TicketType;
    private String VipIcon;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public int getIsUsed() {
        return IsUsed;
    }

    public void setIsUsed(int isUsed) {
        IsUsed = isUsed;
    }

    public int getTicketType() {
        return TicketType;
    }

    public void setTicketType(int ticketType) {
        TicketType = ticketType;
    }

    public String getVipIcon() {
        return VipIcon;
    }

    public void setVipIcon(String vipIcon) {
        VipIcon = vipIcon;
    }
}
