package com.cesaas.android.java.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 根据pid获取款号列表
 * Created at 2018/5/26 15:27
 * Version 1.0
 */

public class GetStyleNoByPidBean implements Serializable {

    private String imageUrl;
    private String stylyNo;
    private String skuValue1;
    private String skuValue2;
    private String skuValue3;
    private int inventoryNum;
    private int differenceNum;
    private String title;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStylyNo() {
        return stylyNo;
    }

    public void setStylyNo(String stylyNo) {
        this.stylyNo = stylyNo;
    }

    public String getSkuValue1() {
        return skuValue1;
    }

    public void setSkuValue1(String skuValue1) {
        this.skuValue1 = skuValue1;
    }

    public String getSkuValue2() {
        return skuValue2;
    }

    public void setSkuValue2(String skuValue2) {
        this.skuValue2 = skuValue2;
    }

    public String getSkuValue3() {
        return skuValue3;
    }

    public void setSkuValue3(String skuValue3) {
        this.skuValue3 = skuValue3;
    }

    public int getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(int inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public int getDifferenceNum() {
        return differenceNum;
    }

    public void setDifferenceNum(int differenceNum) {
        this.differenceNum = differenceNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}