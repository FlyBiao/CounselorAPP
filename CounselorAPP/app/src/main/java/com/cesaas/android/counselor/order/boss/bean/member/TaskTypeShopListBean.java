package com.cesaas.android.counselor.order.boss.bean.member;

import java.io.Serializable;

/**
 * Author FGB
 * Description 会员服务PACD统计(服务类别按店铺列表)
 * Created at 2018/4/26 20:55
 * Version 1.0
 */

public class TaskTypeShopListBean extends TaskTypeShopDataBean implements Serializable {
    private int ShopId;
    private String ShopName;
    private String OrgName;
    private int ServerNums;
    private int ServerFinishNums;
    private int GoShopNums;

    public int getGoShopNums() {
        return GoShopNums;
    }

    public void setGoShopNums(int goShopNums) {
        GoShopNums = goShopNums;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

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

}
