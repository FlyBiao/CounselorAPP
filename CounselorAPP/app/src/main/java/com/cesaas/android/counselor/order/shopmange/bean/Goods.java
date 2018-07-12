package com.cesaas.android.counselor.order.shopmange.bean;

/**
 * Author FGB
 * Description 搭配商品
 * Created at 2017/5/16 16:23
 * Version 1.0
 */

public class Goods {
    private double Top;
    private double Left;
    private int Id;
    private String Title;
    private String No;

    public double getTop() {
        return Top;
    }

    public void setTop(double top) {
        Top = top;
    }

    public double getLeft() {
        return Left;
    }

    public void setLeft(double left) {
        Left = left;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }
}
