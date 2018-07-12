package com.cesaas.android.counselor.order.boss.bean.member;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/4/28 17:11
 * Version 1.0
 */

public class TaskChartListBean implements Serializable {
    private int ServerNums;
    private int ServerFinishNums;
    private int VaildServerNums;
    private int GoShopNums;
    private int BillNums;

    public int getServerNums() {
        return ServerNums;
    }

    public void setServerNums(int serverNums) {
        ServerNums = serverNums;
    }

    public int getServerFinishNums() {
        return ServerFinishNums;
    }

    public void setServerFinishNums(int serverFinishNums) {
        ServerFinishNums = serverFinishNums;
    }

    public int getVaildServerNums() {
        return VaildServerNums;
    }

    public void setVaildServerNums(int vaildServerNums) {
        VaildServerNums = vaildServerNums;
    }

    public int getGoShopNums() {
        return GoShopNums;
    }

    public void setGoShopNums(int goShopNums) {
        GoShopNums = goShopNums;
    }

    public int getBillNums() {
        return BillNums;
    }

    public void setBillNums(int billNums) {
        BillNums = billNums;
    }
}
