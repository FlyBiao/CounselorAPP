package com.cesaas.android.java.bean.inventory;

import java.io.Serializable;

/**
 * Author FGB
 * Description 查看差异列表
 * Created at 2018/5/26 15:20
 * Version 1.0
 */

public class InventoryGetDiffSubListBean implements Serializable {
    private long id;
    private String no;
    private String title;
    private String imageUrl;
    private long barcodeId;
    private String barcodeNo;
    private String skuValue1;
    private String skuValue2;
    private String skuValue3;
    private int stockNum;// 库存数
    private int transitNum;// 在途数
    private int inventoryNum;// 盘点数
    private int differenceNum;// 差异数
    private double differenceAmount;// 差异金额
    private double costDifferenceAmount;// 成本差异金额
    private long pId;

    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
        this.pId = pId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(long barcodeId) {
        this.barcodeId = barcodeId;
    }

    public String getBarcodeNo() {
        return barcodeNo;
    }

    public void setBarcodeNo(String barcodeNo) {
        this.barcodeNo = barcodeNo;
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

    public int getStockNum() {
        return stockNum;
    }

    public void setStockNum(int stockNum) {
        this.stockNum = stockNum;
    }

    public int getTransitNum() {
        return transitNum;
    }

    public void setTransitNum(int transitNum) {
        this.transitNum = transitNum;
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

    public double getDifferenceAmount() {
        return differenceAmount;
    }

    public void setDifferenceAmount(double differenceAmount) {
        this.differenceAmount = differenceAmount;
    }

    public double getCostDifferenceAmount() {
        return costDifferenceAmount;
    }

    public void setCostDifferenceAmount(double costDifferenceAmount) {
        this.costDifferenceAmount = costDifferenceAmount;
    }

}
