package com.cesaas.android.java.bean.inventory;

import java.io.Serializable;

/**
 * Author FGB
 * Description 盘点单详情
 * Created at 2018/5/25 15:49
 * Version 1.0
 */

public class InventoryGetOneBean implements Serializable {

    private int id;
    private String no;
    private int shopId;
    private String shopName;
    private String pdDate;
    private int num;
    private String listPriceAmount;
    private String differenceAmount;
    private String remark;
    private String createName;
    private String createTime;
    private String sureNmae;
    private String sureTime;
    private String submitName;
    private String submitTime;
    private int status;
    private int type;
    private String _classname;

    public void setId(int id) {
        this.id = id;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setPdDate(String pdDate) {
        this.pdDate = pdDate;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setListPriceAmount(String listPriceAmount) {
        this.listPriceAmount = listPriceAmount;
    }

    public void setDifferenceAmount(String differenceAmount) {
        this.differenceAmount = differenceAmount;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setSureNmae(String sureNmae) {
        this.sureNmae = sureNmae;
    }

    public void setSureTime(String sureTime) {
        this.sureTime = sureTime;
    }

    public void setSubmitName(String submitName) {
        this.submitName = submitName;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void set_classname(String _classname) {
        this._classname = _classname;
    }

    public int getId() {
        return id;
    }

    public String getNo() {
        return no;
    }

    public int getShopId() {
        return shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public String getPdDate() {
        return pdDate;
    }

    public int getNum() {
        return num;
    }

    public String getListPriceAmount() {
        return listPriceAmount;
    }

    public String getDifferenceAmount() {
        return differenceAmount;
    }

    public String getRemark() {
        return remark;
    }

    public String getCreateName() {
        return createName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getSureNmae() {
        return sureNmae;
    }

    public String getSureTime() {
        return sureTime;
    }

    public String getSubmitName() {
        return submitName;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public int getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }

    public String get_classname() {
        return _classname;
    }
}
