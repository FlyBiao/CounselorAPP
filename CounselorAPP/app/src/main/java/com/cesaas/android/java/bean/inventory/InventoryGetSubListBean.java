package com.cesaas.android.java.bean.inventory;

/**
 * Author FGB
 * Description
 * Created at 2018/6/25 20:05
 * Version 1.0
 */

public class InventoryGetSubListBean {

    /**
     * id : 4283
     * no : 100522B
     * title : 背心
     * listPrice : 78.00
     * imageUrl :
     * barcodeId : 42838007960
     * barcodeNo : 100522BY07120
     * skuValue1 : 灰橙
     * skuValue2 : 120
     * skuValue3 :
     * num : 4
     * _classname : Drp.GetSub
     */

    private long id;
    private String no;
    private String title;
    private String listPrice;
    private String imageUrl;
    private long barcodeId;
    private String barcodeNo;
    private String skuValue1;
    private String skuValue2;
    private String skuValue3;
    private int num;
    private String _classname;

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

    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String get_classname() {
        return _classname;
    }

    public void set_classname(String _classname) {
        this._classname = _classname;
    }
}
