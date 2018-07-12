package com.cesaas.android.counselor.order.boss.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/8/15 10:23
 * Version 1.0
 */

public class ShopTagListBean implements Serializable{

    private int ShopId;
    private String ShopName;
    private int AreaId;
    private String AreaName;
    private int OrganizationId;
    private String OrganizationName;
    private int ShopType;
    private int CategoryId;
    private String CategoryName;
    private int TagId;
    private String TagName;
    public boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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

    public int getShopType() {
        return ShopType;
    }

    public void setShopType(int shopType) {
        ShopType = shopType;
    }

    public void setCategoryId(int CategoryId) {
        this.CategoryId = CategoryId;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    public void setTagId(int TagId) {
        this.TagId = TagId;
    }

    public void setTagName(String TagName) {
        this.TagName = TagName;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public int getTagId() {
        return TagId;
    }

    public String getTagName() {
        return TagName;
    }
}
