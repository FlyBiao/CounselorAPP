package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/11/11 10:48
 * Version 1.0
 */

public class CartBean implements Serializable {

    /**
     * Id : 24774
     * ImageUrl : http://file.cesaas.com/images/101/2017/2/17/1e9e94e0-f4eb-11e6-a367-a971f1aaafca.jpg
     * Type : 2
     * Points : 0
     * Price : 2.0
     * ListPrice : 1099.0
     * Quantity : 1
     * Title : （测试）Marisfrolg玛丝菲尔 黑色打底羊毛针织毛衣 专柜正品春新女装
     * Attr : 颜色:黑色 尺码:S
     * ShopStyleId : 4274
     * GoodsType : 2
     * CreateTime : 2017-03-17 19:10:46
     */

    private int Id;
    private String ImageUrl;
    private int Type;
    private int Points;
    private double Price;
    private double ListPrice;
    private int Quantity;
    private String Title;
    private String Attr;
    private int ShopStyleId;
    private int GoodsType;
    private String CreateTime;

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public void setPoints(int Points) {
        this.Points = Points;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public void setListPrice(double ListPrice) {
        this.ListPrice = ListPrice;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setAttr(String Attr) {
        this.Attr = Attr;
    }

    public void setShopStyleId(int ShopStyleId) {
        this.ShopStyleId = ShopStyleId;
    }

    public void setGoodsType(int GoodsType) {
        this.GoodsType = GoodsType;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getId() {
        return Id;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public int getType() {
        return Type;
    }

    public int getPoints() {
        return Points;
    }

    public double getPrice() {
        return Price;
    }

    public double getListPrice() {
        return ListPrice;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getTitle() {
        return Title;
    }

    public String getAttr() {
        return Attr;
    }

    public int getShopStyleId() {
        return ShopStyleId;
    }

    public int getGoodsType() {
        return GoodsType;
    }

    public String getCreateTime() {
        return CreateTime;
    }
}
