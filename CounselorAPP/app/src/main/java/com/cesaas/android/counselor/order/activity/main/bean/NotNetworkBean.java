package com.cesaas.android.counselor.order.activity.main.bean;

/**
 * Author FGB
 * Description
 * Created at 2017/8/23 14:22
 * Version 1.0
 */

public class NotNetworkBean {

    private boolean isNetwork;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isNetwork() {
        return isNetwork;
    }

    public void setNetwork(boolean network) {
        isNetwork = network;
    }
}
