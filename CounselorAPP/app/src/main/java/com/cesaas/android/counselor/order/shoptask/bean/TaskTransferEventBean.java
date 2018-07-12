package com.cesaas.android.counselor.order.shoptask.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/3/31 9:59
 * Version 1.0
 */

public class TaskTransferEventBean implements Serializable {

    private int Type;
    private int FlowId;
    private String SalesName;
    private int SalesId;

    public String getSalesName() {
        return SalesName;
    }

    public void setSalesName(String salesName) {
        SalesName = salesName;
    }

    public int getSalesId() {
        return SalesId;
    }

    public void setSalesId(int salesId) {
        SalesId = salesId;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getFlowId() {
        return FlowId;
    }

    public void setFlowId(int flowId) {
        FlowId = flowId;
    }
}
