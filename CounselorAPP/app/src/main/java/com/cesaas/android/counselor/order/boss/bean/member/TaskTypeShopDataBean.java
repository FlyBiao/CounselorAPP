package com.cesaas.android.counselor.order.boss.bean.member;


/**
 * Author FGB
 * Description 会员服务PACD统计(服务类别按店铺列表)
 * Created at 2018/4/26 20:55
 * Version 1.0
 */

public class TaskTypeShopDataBean{
    private int ShopIds;//店铺id
    private String ShopNames;//店铺名称
    private String OrgNames;//机构名称
    private int ServerNumss;//服务总数
    private int ServerFinishNumss;//完成服务数
    private int GoShopNumss;//愿意到店数
    private double ShopTaskSumss;//服务产生的销售金额
    private double ShopSumss;//销售金额

    public int getShopIds() {
        return ShopIds;
    }

    public void setShopIds(int shopIds) {
        ShopIds = shopIds;
    }

    public String getShopNames() {
        return ShopNames;
    }

    public void setShopNames(String shopNames) {
        ShopNames = shopNames;
    }

    public String getOrgNames() {
        return OrgNames;
    }

    public void setOrgNames(String orgNames) {
        OrgNames = orgNames;
    }

    public int getServerNumss() {
        return ServerNumss;
    }

    public void setServerNumss(int serverNumss) {
        ServerNumss = serverNumss;
    }

    public int getServerFinishNumss() {
        return ServerFinishNumss;
    }

    public void setServerFinishNumss(int serverFinishNumss) {
        ServerFinishNumss = serverFinishNumss;
    }

    public int getGoShopNumss() {
        return GoShopNumss;
    }

    public void setGoShopNumss(int goShopNumss) {
        GoShopNumss = goShopNumss;
    }

    public double getShopTaskSumss() {
        return ShopTaskSumss;
    }

    public void setShopTaskSumss(double shopTaskSumss) {
        ShopTaskSumss = shopTaskSumss;
    }

    public double getShopSumss() {
        return ShopSumss;
    }

    public void setShopSumss(double shopSumss) {
        ShopSumss = shopSumss;
    }
}
