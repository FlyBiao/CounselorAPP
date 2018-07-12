package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/11/11 11:26
 * Version 1.0
 */

public class SubOrder implements Serializable {


    /**
     * RetailSubId : 2013
     * RetailId : 11117
     * RetailSort : 0
     * BarcodeId : 42797937940
     * BarcodeNo : 100122BW01100
     * Quantity : 1
     * ListPrice : 69.9
     * CostPrice : 0.0
     * SellPrice : 69.9
     * RetailType : 0
     * PayMent : 69.9
     * Remark : null
     * SkuValue1 : 漂白
     * SkuValue2 : 100/46
     * SkuValue3 :
     * StyleName : 背心
     * ActualConsumption : 69.9
     * ShopStyleId : 4279
     * StyleNo : 100122B
     * RefundQuantity : 0
     * GetPoint : 0
     * ActivityId : 0
     * CanNotRefund : 0
     * ImageUrl : null
     * CanNotPoint : 0
     * CreateTime : 0001-01-01 00:00:00
     * ValidateInfo : null
     * isError : false
     * eMessage : null
     * isDetached : false
     */

    private int RetailSubId;
    private int RetailId;
    private int RetailSort;
    private long BarcodeId;
    private String BarcodeNo;
    private int Quantity;
    private double ListPrice;
    private double CostPrice;
    private double SellPrice;
    private int RetailType;
    private double PayMent;
    private String Remark;
    private String SkuValue1;
    private String SkuValue2;
    private String SkuValue3;
    private String StyleName;
    private double ActualConsumption;
    private int ShopStyleId;
    private String StyleNo;
    private int RefundQuantity;
    private int GetPoint;
    private int ActivityId;
    private int CanNotRefund;
    private String ImageUrl;
    private int CanNotPoint;
    private String CreateTime;
    private String ValidateInfo;
    private boolean isError;
    private String eMessage;
    private boolean isDetached;

    public SubOrder(String ImageUrl,String StyleName,String BarcodeNo,double PayMent,String SkuValue1,String SkuValue2,String SkuValue3,long BarcodeId){
        this.ImageUrl=ImageUrl;
        this.StyleName=StyleName;
        this.BarcodeNo=BarcodeNo;
        this.PayMent=PayMent;
        this.SkuValue1=SkuValue1;
        this.SkuValue2=SkuValue2;
        this.SkuValue3=SkuValue3;
        this.BarcodeId=BarcodeId;
    }

    public int getRetailSubId() {
        return RetailSubId;
    }

    public void setRetailSubId(int retailSubId) {
        RetailSubId = retailSubId;
    }

    public int getRetailId() {
        return RetailId;
    }

    public void setRetailId(int retailId) {
        RetailId = retailId;
    }

    public int getRetailSort() {
        return RetailSort;
    }

    public void setRetailSort(int retailSort) {
        RetailSort = retailSort;
    }

    public long getBarcodeId() {
        return BarcodeId;
    }

    public void setBarcodeId(long barcodeId) {
        BarcodeId = barcodeId;
    }

    public String getBarcodeNo() {
        return BarcodeNo;
    }

    public void setBarcodeNo(String barcodeNo) {
        BarcodeNo = barcodeNo;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getListPrice() {
        return ListPrice;
    }

    public void setListPrice(double listPrice) {
        ListPrice = listPrice;
    }

    public double getCostPrice() {
        return CostPrice;
    }

    public void setCostPrice(double costPrice) {
        CostPrice = costPrice;
    }

    public double getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(double sellPrice) {
        SellPrice = sellPrice;
    }

    public int getRetailType() {
        return RetailType;
    }

    public void setRetailType(int retailType) {
        RetailType = retailType;
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

    public String getSkuValue1() {
        return SkuValue1;
    }

    public void setSkuValue1(String skuValue1) {
        SkuValue1 = skuValue1;
    }

    public String getSkuValue2() {
        return SkuValue2;
    }

    public void setSkuValue2(String skuValue2) {
        SkuValue2 = skuValue2;
    }

    public String getSkuValue3() {
        return SkuValue3;
    }

    public void setSkuValue3(String skuValue3) {
        SkuValue3 = skuValue3;
    }

    public String getStyleName() {
        return StyleName;
    }

    public void setStyleName(String styleName) {
        StyleName = styleName;
    }

    public double getActualConsumption() {
        return ActualConsumption;
    }

    public void setActualConsumption(double actualConsumption) {
        ActualConsumption = actualConsumption;
    }

    public int getShopStyleId() {
        return ShopStyleId;
    }

    public void setShopStyleId(int shopStyleId) {
        ShopStyleId = shopStyleId;
    }

    public String getStyleNo() {
        return StyleNo;
    }

    public void setStyleNo(String styleNo) {
        StyleNo = styleNo;
    }

    public int getRefundQuantity() {
        return RefundQuantity;
    }

    public void setRefundQuantity(int refundQuantity) {
        RefundQuantity = refundQuantity;
    }

    public int getGetPoint() {
        return GetPoint;
    }

    public void setGetPoint(int getPoint) {
        GetPoint = getPoint;
    }

    public int getActivityId() {
        return ActivityId;
    }

    public void setActivityId(int activityId) {
        ActivityId = activityId;
    }

    public int getCanNotRefund() {
        return CanNotRefund;
    }

    public void setCanNotRefund(int canNotRefund) {
        CanNotRefund = canNotRefund;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getCanNotPoint() {
        return CanNotPoint;
    }

    public void setCanNotPoint(int canNotPoint) {
        CanNotPoint = canNotPoint;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getValidateInfo() {
        return ValidateInfo;
    }

    public void setValidateInfo(String validateInfo) {
        ValidateInfo = validateInfo;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String geteMessage() {
        return eMessage;
    }

    public void seteMessage(String eMessage) {
        this.eMessage = eMessage;
    }

    public boolean isDetached() {
        return isDetached;
    }

    public void setDetached(boolean detached) {
        isDetached = detached;
    }
}
