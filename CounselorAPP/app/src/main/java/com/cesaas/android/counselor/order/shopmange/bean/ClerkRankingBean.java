package com.cesaas.android.counselor.order.shopmange.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 店员排名
 * Created at 2018/5/4 9:53
 * Version 1.0
 */

public class ClerkRankingBean implements Serializable {
    private int CounnselorId;
    private String CounselorName;
    private int ShopId;
    private String ShopName;
    private int Quantity;
    private double PayMent;
    private double ActualSalePayment;
    private int SaleGoal;
    private double Discount;
    private String City;
    private double Completion;//完成率【自定义】

    public double getCompletion() {
        return Completion;
    }

    public void setCompletion(double completion) {
        Completion = completion;
    }

    public int getCounnselorId() {
        return CounnselorId;
    }

    public void setCounnselorId(int counnselorId) {
        CounnselorId = counnselorId;
    }

    public String getCounselorName() {
        return CounselorName;
    }

    public void setCounselorName(String counselorName) {
        CounselorName = counselorName;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getPayMent() {
        return PayMent;
    }

    public void setPayMent(double payMent) {
        PayMent = payMent;
    }

    public double getActualSalePayment() {
        return ActualSalePayment;
    }

    public void setActualSalePayment(double actualSalePayment) {
        ActualSalePayment = actualSalePayment;
    }

    public int getSaleGoal() {
        return SaleGoal;
    }

    public void setSaleGoal(int saleGoal) {
        SaleGoal = saleGoal;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
