package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/11/11 11:23
 * Version 1.0
 */

public class VipOrderListBean implements Serializable {

   private String CheckDate;
    private String CreateName;
    private String CreateTime;
    private String PayAmount;
    private int RetailCheck;
    private int RetailFrom;
    private int RetailId;

    private List<SubOrder> OrderItem;

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String checkDate) {
        CheckDate = checkDate;
    }

    public String getCreateName() {
        return CreateName;
    }

    public void setCreateName(String createName) {
        CreateName = createName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getPayAmount() {
        return PayAmount;
    }

    public void setPayAmount(String payAmount) {
        PayAmount = payAmount;
    }

    public int getRetailCheck() {
        return RetailCheck;
    }

    public void setRetailCheck(int retailCheck) {
        RetailCheck = retailCheck;
    }

    public int getRetailFrom() {
        return RetailFrom;
    }

    public void setRetailFrom(int retailFrom) {
        RetailFrom = retailFrom;
    }

    public int getRetailId() {
        return RetailId;
    }

    public void setRetailId(int retailId) {
        RetailId = retailId;
    }

    public List<SubOrder> getOrderItem() {
        return OrderItem;
    }

    public void setOrderItem(List<SubOrder> orderItem) {
        OrderItem = orderItem;
    }
}
