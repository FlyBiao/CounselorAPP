package com.cesaas.android.java.bean.receive;

import java.io.Serializable;

/**
 * Author FGB
 * Description 收货差异
 * Created at 2018/6/4 14:12
 * Version 1.0
 */

public class ReceiveGetDiffListBean implements Serializable {
    private int allotNum;
    private long barcodeId;
    private String barcodeNo;
    private String costDifferenceAmount;
    private String createName;
    private String createTime;
    private int differenceAmount;
    private int differenceNum;
    private long id;
    private String imageUrl;
    private String listPrice;
    private String no;
    private int num;
    private int receivingNum;
    private String settlePrice;
    private int shipmentsNum;
    private String skuValue1;
    private String skuValue2;
    private String skuValue3;
    private String title;
    private String _classname;

    public int getAllotNum() {
        return allotNum;
    }

    public void setAllotNum(int allotNum) {
        this.allotNum = allotNum;
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

    public String getCostDifferenceAmount() {
        return costDifferenceAmount;
    }

    public void setCostDifferenceAmount(String costDifferenceAmount) {
        this.costDifferenceAmount = costDifferenceAmount;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDifferenceAmount() {
        return differenceAmount;
    }

    public void setDifferenceAmount(int differenceAmount) {
        this.differenceAmount = differenceAmount;
    }

    public int getDifferenceNum() {
        return differenceNum;
    }

    public void setDifferenceNum(int differenceNum) {
        this.differenceNum = differenceNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getReceivingNum() {
        return receivingNum;
    }

    public void setReceivingNum(int receivingNum) {
        this.receivingNum = receivingNum;
    }

    public String getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(String settlePrice) {
        this.settlePrice = settlePrice;
    }

    public int getShipmentsNum() {
        return shipmentsNum;
    }

    public void setShipmentsNum(int shipmentsNum) {
        this.shipmentsNum = shipmentsNum;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String get_classname() {
        return _classname;
    }

    public void set_classname(String _classname) {
        this._classname = _classname;
    }
}
