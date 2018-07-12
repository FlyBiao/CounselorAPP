package com.cesaas.android.java.bean.inventory;

import java.io.Serializable;

/**
 * Author FGB
 * Description 盘点货架列表
 * Created at 2018/5/25 16:15
 * Version 1.0
 */

public class InventoryGetScopeListBean implements Serializable {

    private long id;
    private String title;
    private String _classname;

    private int inventoryNum;
    private String createName;
    private int num;
    private String createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setInventoryNum(int inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getInventoryNum() {
        return inventoryNum;
    }

    public String getCreateName() {
        return createName;
    }

    public int getNum() {
        return num;
    }

    public String getCreateTime() {
        return createTime;
    }
}
