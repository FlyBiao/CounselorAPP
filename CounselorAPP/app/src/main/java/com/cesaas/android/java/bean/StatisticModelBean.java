package com.cesaas.android.java.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 汇总数据
 * Created at 2018/5/26 11:09
 * Version 1.0
 */

public class StatisticModelBean{
    private int count;
    private int receiveNum;
    private int originNum;
    private int transitNum;
    private double settlePriceAmount;
    private double listPriceAmount;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(int receiveNum) {
        this.receiveNum = receiveNum;
    }

    public int getOriginNum() {
        return originNum;
    }

    public void setOriginNum(int originNum) {
        this.originNum = originNum;
    }

    public int getTransitNum() {
        return transitNum;
    }

    public void setTransitNum(int transitNum) {
        this.transitNum = transitNum;
    }

    public double getSettlePriceAmount() {
        return settlePriceAmount;
    }

    public void setSettlePriceAmount(double settlePriceAmount) {
        this.settlePriceAmount = settlePriceAmount;
    }

    public double getListPriceAmount() {
        return listPriceAmount;
    }

    public void setListPriceAmount(double listPriceAmount) {
        this.listPriceAmount = listPriceAmount;
    }
}
