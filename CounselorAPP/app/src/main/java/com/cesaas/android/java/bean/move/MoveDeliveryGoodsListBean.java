package com.cesaas.android.java.bean.move;

import java.io.Serializable;

/**
 * Author FGB
 * Description 调货管理-商品列表
 * Created at 2018/5/31 17:22
 * Version 1.0
 */

public class MoveDeliveryGoodsListBean implements Serializable {

    /**
     * id : 4317
     * imageUrl :
     * no : 105431G
     * title : 半袖T恤
     * listPrice : 55
     * num : 15
     * settlePrice : 55
     * costPrice : 0
     * remark :
     * boxId : 0
     * boxNo :
     * _classname : Drp.GoodsModel
     */

    private int id;
    private String imageUrl;
    private String no;
    private String title;
    private double listPrice;
    private int num;
    private double settlePrice;
    private double costPrice;
    private String remark;
    private int boxId;
    private String boxNo;
    private String _classname;

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

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(double settlePrice) {
        this.settlePrice = settlePrice;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public String get_classname() {
        return _classname;
    }

    public void set_classname(String _classname) {
        this._classname = _classname;
    }
}
