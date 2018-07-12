package com.cesaas.android.java.bean.move;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author FGB
 * Description 调拨单
 * Created at 2018/5/24 17:23
 * Version 1.0
 */

public class MoveDeliveryListBeanBean implements Serializable {

    //0:无1：发货2：调拨3：退货4：跨区调拨
    private int category;
    private int refundType;
    private long applyId;
    private String applyNo;
    private int checkId;
    private String checkNo;
    private String shipmentsDate;
    private String noticeTime;
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
    private double settlePriceAmount;
    private int hasNum;
    private int differenceNum;
    private int shipmentsNum;
    private int receivingNum;
    private int fromType;
    private long id;
    private String no;
    //1：入库单2：次品退厂3：需求单4：配货单5：发货单6:收货单7:申请单8:审批单9:通知单10:调拨11:盘点单
    private int type;
    //0:制单(未生差异)10:生成差异、通知捡货15 开始捡货20:捡货完成、确认差异30:提交40:确认50:驳回60停用
    private int status;
    private String remark;
    private int num;
    private double listPriceAmount;
    private String createName;
    private String createTime;
    private String submitTime;
    private String submitName;
    private String sureName;
    private String sureTime;
    private String _classname;

    //新增调货
    public JSONObject addMoveDelivery(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("applyId",getApplyId());
            obj.put("applyNo",getApplyNo());
            obj.put("originShopId",getOriginShopId());
            obj.put("originOrganizationId",getOriginOrganizationId());
            obj.put("receiveShopId",getReceiveShopId());
            obj.put("receiveOrganizationId",getReceiveOrganizationId());
            obj.put("shipmentsDate",getShipmentsDate());
            obj.put("type",getType());
            obj.put("remark",getRemark());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj;
    }

    public JSONObject addMoveDeliveryAddByO2O(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("originShopId",getOriginShopId());
            obj.put("originOrganizationId",getOriginOrganizationId());
            obj.put("receiveShopId",getReceiveShopId());
            obj.put("receiveOrganizationId",getReceiveOrganizationId());
            obj.put("remark",getRemark());
            obj.put("fromType",getFromType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj;
    }

    public int getReceivingNum() {
        return receivingNum;
    }

    public void setReceivingNum(int receivingNum) {
        this.receivingNum = receivingNum;
    }

    public int getShipmentsNum() {
        return shipmentsNum;
    }

    public void setShipmentsNum(int shipmentsNum) {
        this.shipmentsNum = shipmentsNum;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getRefundType() {
        return refundType;
    }

    public void setRefundType(int refundType) {
        this.refundType = refundType;
    }

    public long getApplyId() {
        return applyId;
    }

    public void setApplyId(long applyId) {
        this.applyId = applyId;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getShipmentsDate() {
        return shipmentsDate;
    }

    public void setShipmentsDate(String shipmentsDate) {
        this.shipmentsDate = shipmentsDate;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
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

    public double getSettlePriceAmount() {
        return settlePriceAmount;
    }

    public void setSettlePriceAmount(double settlePriceAmount) {
        this.settlePriceAmount = settlePriceAmount;
    }

    public int getHasNum() {
        return hasNum;
    }

    public void setHasNum(int hasNum) {
        this.hasNum = hasNum;
    }

    public int getDifferenceNum() {
        return differenceNum;
    }

    public void setDifferenceNum(int differenceNum) {
        this.differenceNum = differenceNum;
    }

    public int getFromType() {
        return fromType;
    }

    public void setFromType(int fromType) {
        this.fromType = fromType;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public double getListPriceAmount() {
        return listPriceAmount;
    }

    public void setListPriceAmount(double listPriceAmount) {
        this.listPriceAmount = listPriceAmount;
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

    public String get_classname() {
        return _classname;
    }

    public void set_classname(String _classname) {
        this._classname = _classname;
    }
}
