package com.cesaas.android.java.bean.require;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author FGB
 * Description 补货列表
 * Created at 2018/6/5 10:11
 * Version 1.0
 */

public class RequireListBean implements Serializable {
    private long id;
    private String no;
    private int type;
    private String remark;
    private int num;
    private int shipmentsNum;
    private int differenceNum;
    private String setRemark;
    private double listPriceAmount;
    private double settlePriceAmount;
    private String date;
    private int status;
    private long originShopId;
    private String originShopNo;
    private String originShopName;
    private long receiveShopId;
    private String receiveShopNo;
    private String receiveShopName;
    private long receiveOrganizationId;
    private String receiveOrganizationTitle;
    private long originOrganizationId;
    private String originOrganizationTitle;
    private String createName;
    private String createTime;
    private String submitTime;
    private String submitName;
    private String sureName;
    private String sureTime;


    public JSONObject getRequireInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("type",getType());
            obj.put("date",getDate());
            obj.put("originOrganizationId",getOriginOrganizationId());
            obj.put("originShopId",getOriginShopId());
            obj.put("receiveShopId",getReceiveShopId());
            obj.put("receiveOrganizationId",getReceiveOrganizationId());
            obj.put("remark",getRemark());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getShipmentsNum() {
        return shipmentsNum;
    }

    public void setShipmentsNum(int shipmentsNum) {
        this.shipmentsNum = shipmentsNum;
    }

    public int getDifferenceNum() {
        return differenceNum;
    }

    public void setDifferenceNum(int differenceNum) {
        this.differenceNum = differenceNum;
    }

    public String getSetRemark() {
        return setRemark;
    }

    public void setSetRemark(String setRemark) {
        this.setRemark = setRemark;
    }

    public double getListPriceAmount() {
        return listPriceAmount;
    }

    public void setListPriceAmount(double listPriceAmount) {
        this.listPriceAmount = listPriceAmount;
    }

    public double getSettlePriceAmount() {
        return settlePriceAmount;
    }

    public void setSettlePriceAmount(double settlePriceAmount) {
        this.settlePriceAmount = settlePriceAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getOriginShopId() {
        return originShopId;
    }

    public void setOriginShopId(long originShopId) {
        this.originShopId = originShopId;
    }

    public String getOriginShopNo() {
        return originShopNo;
    }

    public void setOriginShopNo(String originShopNo) {
        this.originShopNo = originShopNo;
    }

    public String getOriginShopName() {
        return originShopName;
    }

    public void setOriginShopName(String originShopName) {
        this.originShopName = originShopName;
    }

    public long getReceiveShopId() {
        return receiveShopId;
    }

    public void setReceiveShopId(long receiveShopId) {
        this.receiveShopId = receiveShopId;
    }

    public String getReceiveShopNo() {
        return receiveShopNo;
    }

    public void setReceiveShopNo(String receiveShopNo) {
        this.receiveShopNo = receiveShopNo;
    }

    public String getReceiveShopName() {
        return receiveShopName;
    }

    public void setReceiveShopName(String receiveShopName) {
        this.receiveShopName = receiveShopName;
    }

    public long getReceiveOrganizationId() {
        return receiveOrganizationId;
    }

    public void setReceiveOrganizationId(long receiveOrganizationId) {
        this.receiveOrganizationId = receiveOrganizationId;
    }

    public String getReceiveOrganizationTitle() {
        return receiveOrganizationTitle;
    }

    public void setReceiveOrganizationTitle(String receiveOrganizationTitle) {
        this.receiveOrganizationTitle = receiveOrganizationTitle;
    }

    public long getOriginOrganizationId() {
        return originOrganizationId;
    }

    public void setOriginOrganizationId(long originOrganizationId) {
        this.originOrganizationId = originOrganizationId;
    }

    public String getOriginOrganizationTitle() {
        return originOrganizationTitle;
    }

    public void setOriginOrganizationTitle(String originOrganizationTitle) {
        this.originOrganizationTitle = originOrganizationTitle;
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

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubmitName() {
        return submitName;
    }

    public void setSubmitName(String submitName) {
        this.submitName = submitName;
    }

    public String getSureName() {
        return sureName;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
    }

    public String getSureTime() {
        return sureTime;
    }

    public void setSureTime(String sureTime) {
        this.sureTime = sureTime;
    }
}
