package com.cesaas.android.order.bean.retail;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/5/23 18:02
 * Version 1.0
 */

public class RetailOrderBean implements Serializable {

    /**
     * RetailId : 11857
     * PayAmount : 66.5
     * RetailSure : 0
     * RetailFrom : 0
     * CheckDate : 2018-05-22 19:16:21
     * CreateTime : 2018-05-22 19:16:21
     * RetailCheck : 1
     * CreateName : swzx@13728882731
     * OrderItem : null
     * SaleName : null
     * SaleId : 251
     * VipId : 544041
     * VipName : null
     * ActivityId : 0
     * ActivityName : null
     * Remark : null
     * Quantity : 1
     * Payment : 66.5
     * SyncCode : POS-24-16194-201805221916-2
     * Mobile : 18675601207
     */

    private int RetailId;
    private double PayAmount;
    private int RetailSure;//0:未登账 1:已登账
    private int RetailFrom;
    private String CheckDate;
    private String CreateTime;
    private int RetailCheck;//0:未审核 1:审核
    private String CreateName;
    private String OrderItem;
    private String SaleName;
    private int SaleId;
    private int VipId;
    private String VipName;
    private int ActivityId;
    private String ActivityName;
    private String Remark;
    private int Quantity;
    private double Payment;
    private String SyncCode;
    private String Mobile;

    public int getRetailId() {
        return RetailId;
    }

    public void setRetailId(int retailId) {
        RetailId = retailId;
    }

    public double getPayAmount() {
        return PayAmount;
    }

    public void setPayAmount(double payAmount) {
        PayAmount = payAmount;
    }

    public int getRetailSure() {
        return RetailSure;
    }

    public void setRetailSure(int retailSure) {
        RetailSure = retailSure;
    }

    public int getRetailFrom() {
        return RetailFrom;
    }

    public void setRetailFrom(int retailFrom) {
        RetailFrom = retailFrom;
    }

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String checkDate) {
        CheckDate = checkDate;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public int getRetailCheck() {
        return RetailCheck;
    }

    public void setRetailCheck(int retailCheck) {
        RetailCheck = retailCheck;
    }

    public String getCreateName() {
        return CreateName;
    }

    public void setCreateName(String createName) {
        CreateName = createName;
    }

    public String getOrderItem() {
        return OrderItem;
    }

    public void setOrderItem(String orderItem) {
        OrderItem = orderItem;
    }

    public String getSaleName() {
        return SaleName;
    }

    public void setSaleName(String saleName) {
        SaleName = saleName;
    }

    public int getSaleId() {
        return SaleId;
    }

    public void setSaleId(int saleId) {
        SaleId = saleId;
    }

    public int getVipId() {
        return VipId;
    }

    public void setVipId(int vipId) {
        VipId = vipId;
    }

    public String getVipName() {
        return VipName;
    }

    public void setVipName(String vipName) {
        VipName = vipName;
    }

    public int getActivityId() {
        return ActivityId;
    }

    public void setActivityId(int activityId) {
        ActivityId = activityId;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getPayment() {
        return Payment;
    }

    public void setPayment(double payment) {
        Payment = payment;
    }

    public String getSyncCode() {
        return SyncCode;
    }

    public void setSyncCode(String syncCode) {
        SyncCode = syncCode;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
