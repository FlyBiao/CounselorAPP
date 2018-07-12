package com.cesaas.android.counselor.order.shoptask.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 顾问列表Bean
 * Created at 2017/5/10 15:08
 * Version 1.0
 */

public class SelectCounselorListBean implements Serializable {

    private int COUNSELOR_ID;
    private int COUNSELOR_INUSE;
    private int COUNSELOR_TYPE;
    private int SHOP_ID;
    private int VIP_ID;
    private int FANS_NUM;
    private String COUNSELOR_LEVELNAME;
    private String COUNSELOR_NAME;
    private String COUNSELOR_ICON;
    private String COUNSELOR_SEX;
    private String COUNSELOR_NICKNAME;
    private String COUNSELOR_MOBILE;
    private String FANS_OPENID;
    private String COUNSELOR_TYPENAME;
    private String SHOP_NAME;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCOUNSELOR_ID() {
        return COUNSELOR_ID;
    }

    public void setCOUNSELOR_ID(int COUNSELOR_ID) {
        this.COUNSELOR_ID = COUNSELOR_ID;
    }

    public int getCOUNSELOR_INUSE() {
        return COUNSELOR_INUSE;
    }

    public void setCOUNSELOR_INUSE(int COUNSELOR_INUSE) {
        this.COUNSELOR_INUSE = COUNSELOR_INUSE;
    }

    public int getCOUNSELOR_TYPE() {
        return COUNSELOR_TYPE;
    }

    public void setCOUNSELOR_TYPE(int COUNSELOR_TYPE) {
        this.COUNSELOR_TYPE = COUNSELOR_TYPE;
    }

    public int getSHOP_ID() {
        return SHOP_ID;
    }

    public void setSHOP_ID(int SHOP_ID) {
        this.SHOP_ID = SHOP_ID;
    }

    public int getVIP_ID() {
        return VIP_ID;
    }

    public void setVIP_ID(int VIP_ID) {
        this.VIP_ID = VIP_ID;
    }

    public int getFANS_NUM() {
        return FANS_NUM;
    }

    public void setFANS_NUM(int FANS_NUM) {
        this.FANS_NUM = FANS_NUM;
    }

    public String getCOUNSELOR_LEVELNAME() {
        return COUNSELOR_LEVELNAME;
    }

    public void setCOUNSELOR_LEVELNAME(String COUNSELOR_LEVELNAME) {
        this.COUNSELOR_LEVELNAME = COUNSELOR_LEVELNAME;
    }

    public String getCOUNSELOR_NAME() {
        return COUNSELOR_NAME;
    }

    public void setCOUNSELOR_NAME(String COUNSELOR_NAME) {
        this.COUNSELOR_NAME = COUNSELOR_NAME;
    }

    public String getCOUNSELOR_ICON() {
        return COUNSELOR_ICON;
    }

    public void setCOUNSELOR_ICON(String COUNSELOR_ICON) {
        this.COUNSELOR_ICON = COUNSELOR_ICON;
    }

    public String getCOUNSELOR_SEX() {
        return COUNSELOR_SEX;
    }

    public void setCOUNSELOR_SEX(String COUNSELOR_SEX) {
        this.COUNSELOR_SEX = COUNSELOR_SEX;
    }

    public String getCOUNSELOR_NICKNAME() {
        return COUNSELOR_NICKNAME;
    }

    public void setCOUNSELOR_NICKNAME(String COUNSELOR_NICKNAME) {
        this.COUNSELOR_NICKNAME = COUNSELOR_NICKNAME;
    }

    public String getCOUNSELOR_MOBILE() {
        return COUNSELOR_MOBILE;
    }

    public void setCOUNSELOR_MOBILE(String COUNSELOR_MOBILE) {
        this.COUNSELOR_MOBILE = COUNSELOR_MOBILE;
    }

    public String getFANS_OPENID() {
        return FANS_OPENID;
    }

    public void setFANS_OPENID(String FANS_OPENID) {
        this.FANS_OPENID = FANS_OPENID;
    }

    public String getCOUNSELOR_TYPENAME() {
        return COUNSELOR_TYPENAME;
    }

    public void setCOUNSELOR_TYPENAME(String COUNSELOR_TYPENAME) {
        this.COUNSELOR_TYPENAME = COUNSELOR_TYPENAME;
    }

    public String getSHOP_NAME() {
        return SHOP_NAME;
    }

    public void setSHOP_NAME(String SHOP_NAME) {
        this.SHOP_NAME = SHOP_NAME;
    }
}
