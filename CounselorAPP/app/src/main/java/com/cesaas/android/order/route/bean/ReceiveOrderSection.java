package com.cesaas.android.order.route.bean;


import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Author FGB
 * Description
 * Created at 2017/8/30 15:37
 * Version 1.0
 */

public class ReceiveOrderSection extends SectionEntity<UnReceiveOrderBean.OrderItem> {
    private boolean isMore;
    private String CreateTime;
    private String CreateName;
    private String SyncCode;
    private int OrderId;
    private String Consignee;

    public ReceiveOrderSection(boolean isHeader, String header,boolean isMore,String CreateName,String CreateTime,String SyncCode,int OrderId,String Consignee) {
        super(isHeader, header);
        this.isMore=isMore;
        this.CreateName=CreateName;
        this.CreateTime=CreateTime;
        this.SyncCode=SyncCode;
        this.OrderId=OrderId;
        this.Consignee=Consignee;
    }

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String consignee) {
        Consignee = consignee;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public ReceiveOrderSection(UnReceiveOrderBean.OrderItem orderItem) {
        super(orderItem);
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getCreateName() {
        return CreateName;
    }

    public void setCreateName(String createName) {
        CreateName = createName;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public String getSyncCode() {
        return SyncCode;
    }

    public void setSyncCode(String syncCode) {
        SyncCode = syncCode;
    }
}
