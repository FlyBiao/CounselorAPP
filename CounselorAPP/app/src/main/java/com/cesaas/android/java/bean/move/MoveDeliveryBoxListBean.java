package com.cesaas.android.java.bean.move;

import java.io.Serializable;

/**
 * Author FGB
 * Description 装箱列表
 * Created at 2018/5/31 18:13
 * Version 1.0
 */

public class MoveDeliveryBoxListBean implements Serializable {

    /**
     * boxId : 6
     * boxNo : 1
     * createName : swzx
     * createTime : 2018-05-28 14:20:07
     * num : 0
     * remark :
     * expressId : 0
     * expressName :
     * expressNo :
     * expressRemark :
     * status : 0
     * _classname : Drp.BoxModel
     */

    private long boxId;
    private int boxNo;
    private String createName;
    private String createTime;
    private int num;
    private String remark;
    private long expressId;
    private String expressName;
    private String expressNo;
    private String expressRemark;
    private int status;
    private String _classname;

    public long getBoxId() {
        return boxId;
    }

    public void setBoxId(long boxId) {
        this.boxId = boxId;
    }

    public int getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(int boxNo) {
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getExpressId() {
        return expressId;
    }

    public void setExpressId(long expressId) {
        this.expressId = expressId;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressRemark() {
        return expressRemark;
    }

    public void setExpressRemark(String expressRemark) {
        this.expressRemark = expressRemark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String get_classname() {
        return _classname;
    }

    public void set_classname(String _classname) {
        this._classname = _classname;
    }
}
