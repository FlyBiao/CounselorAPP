package com.cesaas.android.java.bean.inventory;

import java.io.Serializable;

/**
 * Author FGB
 * Description 盘点单列表
 * Created at 2018/5/25 9:51
 * Version 1.0
 */

public class InventoryListBeanBean implements Serializable {
    private long id;
    private int type;//0, //全盘 1,//抽盘
    private int status;//0:制单(未生差异)10:生成差异、通知捡货15 开始捡货20:捡货完成、确认差异30:提交40:确认50:驳回停用
    private String no;
    private String pdDate;
    private int shopId;
    private String shopName;
    private int num;
    private String listPriceAmount;
    private int differenceNum;
    private String differenceAmount;
    private String remark;
    private String creamName;
    private String createTime;
    private String _classname;
    private String createName;

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setNo(String no) {
        this.no = no;
    }

    public void setPdDate(String pdDate) {
        this.pdDate = pdDate;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setListPriceAmount(String listPriceAmount) {
        this.listPriceAmount = listPriceAmount;
    }

    public void setDifferenceNum(int differenceNum) {
        this.differenceNum = differenceNum;
    }

    public void setDifferenceAmount(String differenceAmount) {
        this.differenceAmount = differenceAmount;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreamName(String creamName) {
        this.creamName = creamName;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void set_classname(String _classname) {
        this._classname = _classname;
    }

    public long getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNo() {
        return no;
    }

    public String getPdDate() {
        return pdDate;
    }

    public int getShopId() {
        return shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public int getNum() {
        return num;
    }

    public String getListPriceAmount() {
        return listPriceAmount;
    }

    public int getDifferenceNum() {
        return differenceNum;
    }

    public String getDifferenceAmount() {
        return differenceAmount;
    }

    public String getRemark() {
        return remark;
    }

    public String getCreamName() {
        return creamName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String get_classname() {
        return _classname;
    }
}
