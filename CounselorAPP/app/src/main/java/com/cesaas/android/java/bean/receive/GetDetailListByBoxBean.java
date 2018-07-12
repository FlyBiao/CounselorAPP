package com.cesaas.android.java.bean.receive;

import java.io.Serializable;

/**
 * Author FGB
 * Description 通过收货箱获取商品
 * Created at 2018/6/4 16:53
 * Version 1.0
 */

public class GetDetailListByBoxBean implements Serializable {

    /**
     * barcodeId : 43178237940
     * barcodeNo : 105431GP16100
     * boxNum : 0
     * createName : Swzx
     * createTime : 2018-05-31 14:17:01
     * differenceNum : 1
     * id : 2
     * imageUrl :
     * listPrice : 55.00
     * no : 105431G
     * noticeNum : 0
     * num : 1
     * settlePrice : 55.00
     * skuValue1 : 玫红
     * skuValue2 : 100/46
     * skuValue3 :
     * title : 半袖T恤
     * _classname : Drp.ReceiveDetail
     */

    private long barcodeId;
    private String barcodeNo;
    private int boxNum;
    private String createName;
    private String createTime;
    private int differenceNum;
    private int id;
    private String imageUrl;
    private double listPrice;
    private String no;
    private int noticeNum;
    private int num;
    private int sendNum;
    private double settlePrice;
    private String skuValue1;
    private String skuValue2;
    private String skuValue3;
    private String title;
    private String _classname;

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

    public int getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
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

    public int getDifferenceNum() {
        return differenceNum;
    }

    public void setDifferenceNum(int differenceNum) {
        this.differenceNum = differenceNum;
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

    public int getNoticeNum() {
        return noticeNum;
    }

    public void setNoticeNum(int noticeNum) {
        this.noticeNum = noticeNum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSendNum() {
        return sendNum;
    }

    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
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

    public String get_classname() {
        return _classname;
    }

    public void set_classname(String _classname) {
        this._classname = _classname;
    }
}
