package com.cesaas.android.counselor.order.boss.bean.member;

import java.io.Serializable;

/**
 * Author FGB
 * Description 会员服务PACD统计(服务类别列表)
 * Created at 2018/4/27 11:35
 * Version 1.0
 */

public class TaskTypeListBean implements Serializable {
    private int ServiceType;
    private int ServerFinishNums;
    private int ServerNums;
    private int ShopNums;

    public int getServiceType() {
        return ServiceType;
    }

    public void setServiceType(int serviceType) {
        ServiceType = serviceType;
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

    public int getShopNums() {
        return ShopNums;
    }

    public void setShopNums(int shopNums) {
        ShopNums = shopNums;
    }
}
