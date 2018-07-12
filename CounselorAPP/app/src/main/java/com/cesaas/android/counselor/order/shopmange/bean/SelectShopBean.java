package com.cesaas.android.counselor.order.shopmange.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created 2017/4/27 19:02
 * Version 1.0
 */
public class SelectShopBean implements Serializable {

//    "Id": 635,
//            "ImageUrl": "http://shenzhentesting.oss-cn-shenzhen.aliyuncs.com/images/3/2017/2/21/1da3aa20-f80c-11e6-bf61-2142d57fd9f8.jpg",
//            "IsAttention": 0,
//            "No": "10000101",
//            "SalesPrice": 460.00,
//            "SalesVolume": 0,
//            "StylePrice": 460.00,
//            "Title": "职业套装"

    private int Id;
    private int IsAttention;
    private String No;
    private double SalesPrice;
    private double StylePrice;
    private String ImageUrl;
    private String Title;
    private String Desc;
    private String Style;
    private int Status;
    private int SalesVolume;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSalesVolume() {
        return SalesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        SalesVolume = salesVolume;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIsAttention() {
        return IsAttention;
    }

    public void setIsAttention(int isAttention) {
        IsAttention = isAttention;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public double getSalesPrice() {
        return SalesPrice;
    }

    public void setSalesPrice(double salesPrice) {
        SalesPrice = salesPrice;
    }

    public double getStylePrice() {
        return StylePrice;
    }

    public void setStylePrice(double stylePrice) {
        StylePrice = stylePrice;
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

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getStyle() {
        return Style;
    }

    public void setStyle(String style) {
        Style = style;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
