package com.cesaas.android.counselor.order.member.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/11/30 9:29
 * Version 1.0
 */

public class StyleRecommendtBean implements Serializable {


    /**
     * ShopStyleId : 4298
     * StyleNo : 101022B
     * StyleName : 背心
     * ImageUrl : null
     * ListPrice : 79.0
     * SellPrice : 79.0
     */

    private int ShopStyleId;
    private String StyleNo;
    private String StyleName;
    private String ImageUrl;
    private double ListPrice;
    private double SellPrice;

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

    public String getStyleName() {
        return StyleName;
    }

    public void setStyleName(String styleName) {
        StyleName = styleName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public double getListPrice() {
        return ListPrice;
    }

    public void setListPrice(double listPrice) {
        ListPrice = listPrice;
    }

    public double getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(double sellPrice) {
        SellPrice = sellPrice;
    }
}
