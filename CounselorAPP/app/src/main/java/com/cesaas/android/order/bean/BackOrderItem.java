package com.cesaas.android.order.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/4/16 19:57
 * Version 1.0
 */

public class BackOrderItem implements Serializable {

    /**
     * RetailSubId : 43
     * OrderId : 43
     * RetailSort : 0
     * BarcodeId : 42808047950
     * BarcodeNo : 100222BY09110
     * Quantity : 1
     * ListPrice : 2000.0
     * CostPrice : 0.0
     * SellPrice : 672.0
     * RetailType : 0
     * PayMent : 672.0
     * Remark : null
     * SkuValue1 : 暖黄
     * SkuValue2 : 110
     * SkuValue3 : null
     * StyleName : null
     * ActualConsumption : 0.0
     * ShopStyleId : 4280
     * StyleNo : 100222B
     * Attr : 暖黄:110
     * ImageUrl : null
     */

    private int RetailSubId;
    private int OrderId;
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
    private String Attr;
    private String ImageUrl;

    public int getRetailSubId() {
        return RetailSubId;
    }

    public void setRetailSubId(int retailSubId) {
        RetailSubId = retailSubId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
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

    public String getAttr() {
        return Attr;
    }

    public void setAttr(String attr) {
        Attr = attr;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
