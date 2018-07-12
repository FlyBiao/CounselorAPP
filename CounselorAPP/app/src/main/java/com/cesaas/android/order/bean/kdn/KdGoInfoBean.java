package com.cesaas.android.order.bean.kdn;

import java.util.List;

/**
 * Author FGB
 * Description KdGo
 * Created at 2018/4/20 10:53
 * Version 1.0
 */

public class KdGoInfoBean {

    private String OrderCode;//订单编号(自定义，不可重复)
    private String ShipperCode;//快递公司编码
    private String Remark;//备注
    private String MonthCode;//月结号
    private int PayType;//运费支付方式:1-现付，2-到付，3-月结，4-第三方付
    private int ExpType;//快递类型：1-标准快件详细快递类型参考《快递公司快递业务类型.xlsx》
    private int IsNotice;//是否通知快递员上门揽件：0-通知；1-不通知；不填则默认为 1
    private int IsSendMessage;//是否订阅短信：0-不需要；1-需要

    private ReceiverCompany Receiver;//收件人公司信息
    private SenderCompany Sender;//发件人公司信息
    private List<CommodityBean> Commodity;//商品

    public List<CommodityBean> getCommodity() {
        return Commodity;
    }

    public void setCommodity(List<CommodityBean> commodity) {
        Commodity = commodity;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String shipperCode) {
        ShipperCode = shipperCode;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getPayType() {
        return PayType;
    }

    public void setPayType(int payType) {
        PayType = payType;
    }

    public int getExpType() {
        return ExpType;
    }

    public void setExpType(int expType) {
        ExpType = expType;
    }

    public String getMonthCode() {
        return MonthCode;
    }

    public void setMonthCode(String monthCode) {
        MonthCode = monthCode;
    }

    public int getIsNotice() {
        return IsNotice;
    }

    public void setIsNotice(int isNotice) {
        IsNotice = isNotice;
    }

    public int getIsSendMessage() {
        return IsSendMessage;
    }

    public void setIsSendMessage(int isSendMessage) {
        IsSendMessage = isSendMessage;
    }

    public ReceiverCompany getReceiver() {
        return Receiver;
    }

    public void setReceiver(ReceiverCompany receiver) {
        Receiver = receiver;
    }

    public SenderCompany getSender() {
        return Sender;
    }

    public void setSender(SenderCompany sender) {
        Sender = sender;
    }
}
