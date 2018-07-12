package com.cesaas.android.counselor.order.boss.bean;

import java.io.Serializable;


/**
 * Author FGB
 * Description
 * Created at 2017/8/15 10:23
 * Version 1.0
 */

public class SelectShopListBean implements Serializable {

    private int AreaId;
    private String AreaName;
    private int OrganizationId;
    private String OrganizationName;
    private String ShopName;
    private String JoinShop;
    private int ShopType;
    private int ShopId;
    private int pos;

    public int getShopType() {
        return ShopType;
    }

    public void setShopType(int shopType) {
        ShopType = shopType;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public int getOrganizationId() {
        return OrganizationId;
    }

    public void setOrganizationId(int organizationId) {
        OrganizationId = organizationId;
    }

    public String getOrganizationName() {
        return OrganizationName;
    }

    public void setOrganizationName(String organizationName) {
        OrganizationName = organizationName;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getJoinShop() {
        return JoinShop;
    }

    public void setJoinShop(String joinShop) {
        JoinShop = joinShop;
    }
}
