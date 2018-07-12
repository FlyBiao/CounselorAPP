package com.cesaas.android.counselor.order.boss.bean.member;

import java.io.Serializable;

/**
 * Author FGB
 * Description 会员服务PACD统计(按顾问服务统计分析报表)
 * Created at 2018/4/27 17:41
 * Version 1.0
 */

public class TaskByCounserlorListsBean implements Serializable {

    private int CounserlorId;
    private int ServerFinishNums;
    private int ServerNums;
    private int GoShopNums;
    private int BillNums;
    private double TaskSums;
    private String Name;
    private int FansCount;
    private int ShopId;

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getFansCount() {
        return FansCount;
    }

    public void setFansCount(int fansCount) {
        FansCount = fansCount;
    }

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
