package com.cesaas.android.counselor.order.shopmange.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 最近购买商品Bean
 * Created 2017/4/28 9:53
 * Version 1.0
 */
public class RecentPurchaseBean implements Serializable{

    private String OrderId;
    private String OId;
    private String ImageUrl;
    private String Title;
    private String Quantity;
    private String Attr;
    private String OrderStatus;
    private String Price;
    private String ShopStyleId;
    private String BarcodeCode;
    private String StyleCode;
    private String PayPrice;
    private String CreateTime;
    private String BarcodeId;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOId() {
        return OId;
    }

    public void setOId(String OId) {
        this.OId = OId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getAttr() {
        return Attr;
    }

    public void setAttr(String attr) {
        Attr = attr;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getShopStyleId() {
        return ShopStyleId;
    }

    public void setShopStyleId(String shopStyleId) {
        ShopStyleId = shopStyleId;
    }

    public String getBarcodeCode() {
        return BarcodeCode;
    }

    public void setBarcodeCode(String barcodeCode) {
        BarcodeCode = barcodeCode;
    }

    public String getStyleCode() {
        return StyleCode;
    }

    public void setStyleCode(String styleCode) {
        StyleCode = styleCode;
    }

    public String getPayPrice() {
        return PayPrice;
    }

    public void setPayPrice(String payPrice) {
        PayPrice = payPrice;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getBarcodeId() {
        return BarcodeId;
    }

    public void setBarcodeId(String barcodeId) {
        BarcodeId = barcodeId;
    }
}
