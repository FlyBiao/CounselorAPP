package com.cesaas.android.java.bean.move;

import java.io.Serializable;

/**
 * Author FGB
 * Description 调货发货商品条码列表
 * Created at 2018/5/31 17:42
 * Version 1.0
 */

public class MoveDeliveryGoodsDetailBean implements Serializable {

    /**
     * _classname : Drp.DeliveryBarcode
     * allotNum : 0
     * barcodeId : 43178237940
     * barcodeNo : 105431GP16100
     * boxId : 0
     * boxNo :
     * createName : swzx
     * createTime : 2018-05-25 14:14:16
     * id : 4317
     * imageUrl :
     * listPrice : 55
     * no : 105431G
     * num : 1
     * receivingNum : 0
     * settlePrice : 53
     * skuValue1 : 玫红
     * skuValue2 : 100/46
     * skuValue3 :
     * title : 半袖T恤
     */

    private String _classname;
    private int allotNum;
    private long barcodeId;
    private String barcodeNo;
    private long boxId;
    private String boxNo;
    private String createName;
    private String createTime;
    private int id;
    private String imageUrl;
    private double listPrice;
    private String no;
    private int num;
    private int receivingNum;
    private int shipmentsNum;
    private double settlePrice;
    private String skuValue1;
    private String skuValue2;
    private String skuValue3;
    private String title;

    public int getShipmentsNum() {
        return shipmentsNum;
    }

    public void setShipmentsNum(int shipmentsNum) {
        this.shipmentsNum = shipmentsNum;
    }

    public String get_classname() {
        return _classname;
    }

    public void set_classname(String _classname) {
        this._classname = _classname;
    }

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

    public long getBoxId() {
        return boxId;
    }

    public void setBoxId(long boxId) {
        this.boxId = boxId;
    }

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
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

    public double getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(double settlePrice) {
        this.settlePrice = settlePrice;
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
}
