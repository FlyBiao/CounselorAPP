package com.cesaas.android.java.bean.inventory;

import java.io.Serializable;

/**
 * Author FGB
 * Description 获取盘点单款式列表
 * Created at 2018/5/26 17:32
 * Version 1.0
 */

public class InventoryGetGoodsListBean implements Serializable {
    private long id;
    private String imageUrl;
    private String no;
    private String title;
    private String listPrice;
    private int num;
    private int settlePrice;
    private int costPrice;
    private String skuValue1;

    public String getSkuValue1() {
        return skuValue1;
    }

    public void setSkuValue1(String skuValue1) {
        this.skuValue1 = skuValue1;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(int settlePrice) {
        this.settlePrice = settlePrice;
    }

    public int getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(int costPrice) {
        this.costPrice = costPrice;
    }
}
