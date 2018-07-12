package com.cesaas.android.counselor.order.boss.bean.member;

import java.io.Serializable;

/**
 * Author FGB
 * Description 按店铺查询店员列表
 * Created at 2018/5/7 18:04
 * Version 1.0
 */

public class ListByShopIdBean implements Serializable {

    /**
     * COUNSELOR_ICON : http://wx.qlogo.cn/mmopen/LcHvZXZs8HObqZUuU693LOiaOnxbX9GsUuOIlBVHFkllTCNtb2EOlBGicv8lgb1Vo70Lqj6WjEClQGZPhmLlxajQ/0
     * COUNSELOR_ID : 251
     * COUNSELOR_INUSE : 1
     * COUNSELOR_LEVELID : 0
     * COUNSELOR_MOBILE : 13430706607
     * COUNSELOR_NAME : 阿标
     * COUNSELOR_NICKNAME : 阿标
     * COUNSELOR_SEX : 男
     * COUNSELOR_TYPE : 0
     * FANS_NUM : 12
     * FANS_OPENID : o5R2XwoROfWVAqhmEoLGa24CehjM
     * SHOP_ID : 16178
     * SHOP_NAME : 东湖店
     * VIP_ID : 543868
     */

    private String COUNSELOR_ICON;
    private int COUNSELOR_ID;
    private int COUNSELOR_INUSE;
    private int COUNSELOR_LEVELID;
    private String COUNSELOR_MOBILE;
    private String COUNSELOR_NAME;
    private String COUNSELOR_NICKNAME;
    private String COUNSELOR_SEX;
    private int COUNSELOR_TYPE;
    private int FANS_NUM;
    private String FANS_OPENID;
    private int SHOP_ID;
    private String SHOP_NAME;
    private int VIP_ID;

    public void setCOUNSELOR_ICON(String COUNSELOR_ICON) {
        this.COUNSELOR_ICON = COUNSELOR_ICON;
    }

    public void setCOUNSELOR_ID(int COUNSELOR_ID) {
        this.COUNSELOR_ID = COUNSELOR_ID;
    }

    public void setCOUNSELOR_INUSE(int COUNSELOR_INUSE) {
        this.COUNSELOR_INUSE = COUNSELOR_INUSE;
    }

    public void setCOUNSELOR_LEVELID(int COUNSELOR_LEVELID) {
        this.COUNSELOR_LEVELID = COUNSELOR_LEVELID;
    }

    public void setCOUNSELOR_MOBILE(String COUNSELOR_MOBILE) {
        this.COUNSELOR_MOBILE = COUNSELOR_MOBILE;
    }

    public void setCOUNSELOR_NAME(String COUNSELOR_NAME) {
        this.COUNSELOR_NAME = COUNSELOR_NAME;
    }

    public void setCOUNSELOR_NICKNAME(String COUNSELOR_NICKNAME) {
        this.COUNSELOR_NICKNAME = COUNSELOR_NICKNAME;
    }

    public void setCOUNSELOR_SEX(String COUNSELOR_SEX) {
        this.COUNSELOR_SEX = COUNSELOR_SEX;
    }

    public void setCOUNSELOR_TYPE(int COUNSELOR_TYPE) {
        this.COUNSELOR_TYPE = COUNSELOR_TYPE;
    }

    public void setFANS_NUM(int FANS_NUM) {
        this.FANS_NUM = FANS_NUM;
    }

    public void setFANS_OPENID(String FANS_OPENID) {
        this.FANS_OPENID = FANS_OPENID;
    }

    public void setSHOP_ID(int SHOP_ID) {
        this.SHOP_ID = SHOP_ID;
    }

    public void setSHOP_NAME(String SHOP_NAME) {
        this.SHOP_NAME = SHOP_NAME;
    }

    public void setVIP_ID(int VIP_ID) {
        this.VIP_ID = VIP_ID;
    }

    public String getCOUNSELOR_ICON() {
        return COUNSELOR_ICON;
    }

    public int getCOUNSELOR_ID() {
        return COUNSELOR_ID;
    }

    public int getCOUNSELOR_INUSE() {
        return COUNSELOR_INUSE;
    }

    public int getCOUNSELOR_LEVELID() {
        return COUNSELOR_LEVELID;
    }

    public String getCOUNSELOR_MOBILE() {
        return COUNSELOR_MOBILE;
    }

    public String getCOUNSELOR_NAME() {
        return COUNSELOR_NAME;
    }

    public String getCOUNSELOR_NICKNAME() {
        return COUNSELOR_NICKNAME;
    }

    public String getCOUNSELOR_SEX() {
        return COUNSELOR_SEX;
    }

    public int getCOUNSELOR_TYPE() {
        return COUNSELOR_TYPE;
    }

    public int getFANS_NUM() {
        return FANS_NUM;
    }

    public String getFANS_OPENID() {
        return FANS_OPENID;
    }

    public int getSHOP_ID() {
        return SHOP_ID;
    }

    public String getSHOP_NAME() {
        return SHOP_NAME;
    }

    public int getVIP_ID() {
        return VIP_ID;
    }
}
