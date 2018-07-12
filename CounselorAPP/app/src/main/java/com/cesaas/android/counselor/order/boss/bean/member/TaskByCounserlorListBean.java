package com.cesaas.android.counselor.order.boss.bean.member;

import java.io.Serializable;

/**
 * Author FGB
 * Description 会员服务PACD统计(按顾问服务统计分析报表)
 * Created at 2018/4/27 17:41
 * Version 1.0
 */

public class TaskByCounserlorListBean implements Serializable {

    private int CounserlorId;
    private int ServerFinishNums;
    private int ServerNums;
    private int GoShopNums;
    private int BillNums;
    private double TaskSums;

    public int getBillNums() {
        return BillNums;
    }

    public void setBillNums(int billNums) {
        BillNums = billNums;
    }

    public int getCounserlorId() {
        return CounserlorId;
    }

    public void setCounserlorId(int counserlorId) {
        CounserlorId = counserlorId;
    }

    public int getServerFinishNums() {
        return ServerFinishNums;
    }

    public void setServerFinishNums(int serverFinishNums) {
        ServerFinishNums = serverFinishNums;
    }

    public int getServerNums() {
        return ServerNums;
    }

    public void setServerNums(int serverNums) {
        ServerNums = serverNums;
    }

    public int getGoShopNums() {
        return GoShopNums;
    }

    public void setGoShopNums(int goShopNums) {
        GoShopNums = goShopNums;
    }

    public double getTaskSums() {
        return TaskSums;
    }

    public void setTaskSums(double taskSums) {
        TaskSums = taskSums;
    }
}
