package com.cesaas.android.order.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/4/16 19:55
 * Version 1.0
 */

public class BackOrderBean implements Serializable {

    /**
     * TId : 24
     * SyncCode : 160814613396
     * ShopId : 16194
     * ShopNo : null
     * VipMobiel : null
     * RetailType : 0
     * CreateName : syncSystem
     * CreateTime : 2017-08-02 15:32:20
     * RetailDiscount : 0.0
     * OrderTotal : 2000.0
     * PayMent : 672.0
     * Remark : null
     * RetailEnd : 0
     * UpdateTime : 2017-08-02 15:32:20
     * IsDel : 0
     * RetailFrom : 0
     * RetailQuantity : 1
     * ActualPayMent : 0.0
     * Mobile : 尹少波96
     * Consignee : 尹少波96
     * Fright : 0.0
     * ExpressId : null
     * ExpressName : null
     * WayBillNo : null
     * City : 尹少波96
     * Province : 尹少波96
     * District : 尹少波96
     * Address : 尹少波96
     * OrderStatus : 3
     * SureDate : 2018-03-30 23:41:16
     * OrderId : 43
     * Message : null
     * UserTicket : null
     * AuthKey : null
     */

    private int TId;
    private String SyncCode;
    private int ShopId;
    private String ShopNo;
    private String VipMobiel;
    private int RetailType;
    private String CreateName;
    private String CreateTime;
    private double RetailDiscount;
    private double OrderTotal;
    private double PayMent;
    private String Remark;
    private int RetailEnd;
    private String UpdateTime;
    private int IsDel;
    private int RetailFrom;
    private int RetailQuantity;
    private double ActualPayMent;
    private String Mobile;
    private String Consignee;
    private double Fright;
    private String ExpressId;
    private String ExpressName;
    private String WayBillNo;
    private String City;
    private String Province;
    private String District;
    private String Address;
    private int OrderStatus;
    private String SureDate;
    private int OrderId;
    private String Message;
    private String UserTicket;
    private String AuthKey;

    private List<BackOrderItem> OrderItem;

    public List<BackOrderItem> getOrderItem() {
        return OrderItem;
    }

    public void setOrderItem(List<BackOrderItem> orderItem) {
        OrderItem = orderItem;
    }

    public int getTId() {
        return TId;
    }

    public void setTId(int TId) {
        this.TId = TId;
    }

    public String getSyncCode() {
        return SyncCode;
    }

    public void setSyncCode(String syncCode) {
        SyncCode = syncCode;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getShopNo() {
        return ShopNo;
    }

    public void setShopNo(String shopNo) {
        ShopNo = shopNo;
    }

    public String getVipMobiel() {
        return VipMobiel;
    }

    public void setVipMobiel(String vipMobiel) {
        VipMobiel = vipMobiel;
    }

    public int getRetailType() {
        return RetailType;
    }

    public void setRetailType(int retailType) {
        RetailType = retailType;
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

    public double getRetailDiscount() {
        return RetailDiscount;
    }

    public void setRetailDiscount(double retailDiscount) {
        RetailDiscount = retailDiscount;
    }

    public double getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        OrderTotal = orderTotal;
    }

    public double getPayMent() {
        return PayMent;
    }

    public void setPayMent(double payMent) {
        PayMent = payMent;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getRetailEnd() {
        return RetailEnd;
    }

    public void setRetailEnd(int retailEnd) {
        RetailEnd = retailEnd;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int isDel) {
        IsDel = isDel;
    }

    public int getRetailFrom() {
        return RetailFrom;
    }

    public void setRetailFrom(int retailFrom) {
        RetailFrom = retailFrom;
    }

    public int getRetailQuantity() {
        return RetailQuantity;
    }

    public void setRetailQuantity(int retailQuantity) {
        RetailQuantity = retailQuantity;
    }

    public double getActualPayMent() {
        return ActualPayMent;
    }

    public void setActualPayMent(double actualPayMent) {
        ActualPayMent = actualPayMent;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String consignee) {
        Consignee = consignee;
    }

    public double getFright() {
        return Fright;
    }

    public void setFright(double fright) {
        Fright = fright;
    }

    public String getExpressId() {
        return ExpressId;
    }

    public void setExpressId(String expressId) {
        ExpressId = expressId;
    }

    public String getExpressName() {
        return ExpressName;
    }

    public void setExpressName(String expressName) {
        ExpressName = expressName;
    }

    public String getWayBillNo() {
        return WayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        WayBillNo = wayBillNo;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getSureDate() {
        return SureDate;
    }

    public void setSureDate(String sureDate) {
        SureDate = sureDate;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getUserTicket() {
        return UserTicket;
    }

    public void setUserTicket(String userTicket) {
        UserTicket = userTicket;
    }

    public String getAuthKey() {
        return AuthKey;
    }

    public void setAuthKey(String authKey) {
        AuthKey = authKey;
    }
}
