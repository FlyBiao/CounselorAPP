package com.cesaas.android.order.bean.kdn;

/**
 * Author FGB
 * Description
 * Created at 2018/4/20 13:55
 * Version 1.0
 */

public class KdnOrderBean {

    /**
     * DestinatioCode : 010
     * KDNOrderCode : KDN1804191200000350
     * LogisticCode : 619590076410
     * OrderCode : 012657700387
     * OriginCode : 021
     */

    private String DestinatioCode;//目的地区域编码
    private String KDNOrderCode;//
    private String LogisticCode;//快递单号
    private String OrderCode;//订单编号
    private String OriginCode;//始发地区域编码

    public void setDestinatioCode(String DestinatioCode) {
        this.DestinatioCode = DestinatioCode;
    }

    public void setKDNOrderCode(String KDNOrderCode) {
        this.KDNOrderCode = KDNOrderCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public void setOrderCode(String OrderCode) {
        this.OrderCode = OrderCode;
    }

    public void setOriginCode(String OriginCode) {
        this.OriginCode = OriginCode;
    }

    public String getDestinatioCode() {
        return DestinatioCode;
    }

    public String getKDNOrderCode() {
        return KDNOrderCode;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public String getOriginCode() {
        return OriginCode;
    }
}
