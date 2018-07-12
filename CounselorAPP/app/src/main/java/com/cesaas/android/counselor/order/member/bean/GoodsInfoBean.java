package com.cesaas.android.counselor.order.member.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 商品信息Bean
 * Created at 2017/11/8 17:25
 * Version 1.0
 */

public class GoodsInfoBean implements Serializable {

    private String ImageUrl;
    private String SampleCode;
    private int SampleId;
    private String Color;
    private String Size;
    private int Num;
    private double Price;
    private double Payment;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getSampleCode() {
        return SampleCode;
    }

    public void setSampleCode(String sampleCode) {
        SampleCode = sampleCode;
    }

    public int getSampleId() {
        return SampleId;
    }

    public void setSampleId(int sampleId) {
        SampleId = sampleId;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getPayment() {
        return Payment;
    }

    public void setPayment(double payment) {
        Payment = payment;
    }
}
