package com.cesaas.android.order.route.bean;

/**
 * Author FGB
 * Description
 * Created at 2018/6/24 17:51
 * Version 1.0
 */

public class CreateRouteBean {
    private int ShopId;
    private int OrganizationId;
    private String SyncCode;

    public String getSyncCode() {
        return SyncCode;
    }

    public void setSyncCode(String syncCode) {
        SyncCode = syncCode;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public int getOrganizationId() {
        return OrganizationId;
    }

    public void setOrganizationId(int organizationId) {
        OrganizationId = organizationId;
    }
}
