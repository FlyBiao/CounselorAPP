package com.cesaas.android.counselor.order.member.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 会员分配Bean
 * Created 2017/4/11 18:35
 * Version 1.zero
 */
public class MemberDistributionBean implements Serializable {


    /**
     * COUNSELOR_MOBILE : 13430706607
     * COUNSELOR_NICKNAME : 标哥
     * SHOP_ID : 11254
     * SHOP_NAME : 北京路新大新
     * COUNSELOR_NAME : 标哥
     * COUNSELOR_ID : 169
     * FANS_OPENID : ogkvtjpwvD_MhwltZxKVUNykyGLQ
     * COUNSELOR_ICON : http://wx.qlogo.cn/mmopen/Q3auHgzwzM5qvjm6I19UhFUeJo9P4rlYCapVAvkfib8HkpaKlKthDibkOhial6wYTPFXyrRdeGEsHx4JZx3MfO4Cg/0
     * VIP_ID : 543598
     * COUNSELOR_INUSE : 1
     * FANS_NUM : 10
     * COUNSELOR_LEVELID : zero
     * COUNSELOR_SEX : 男
     * COUNSELOR_TYPE : zero
     */
    private String COUNSELOR_MOBILE;
    private String COUNSELOR_NICKNAME;
    private int SHOP_ID;
    private String SHOP_NAME;
    private String COUNSELOR_NAME;
    private int COUNSELOR_ID;
    private String FANS_OPENID;
    private String COUNSELOR_ICON;
    private int VIP_ID;
    private int COUNSELOR_INUSE;
    private int FANS_NUM;
    private int COUNSELOR_LEVELID;
    private String COUNSELOR_SEX;
    private int COUNSELOR_TYPE;

    public void setCOUNSELOR_MOBILE(String COUNSELOR_MOBILE) {
        this.COUNSELOR_MOBILE = COUNSELOR_MOBILE;
    }

    public void setCOUNSELOR_NICKNAME(String COUNSELOR_NICKNAME) {
        this.COUNSELOR_NICKNAME = COUNSELOR_NICKNAME;
    }

    public void setSHOP_ID(int SHOP_ID) {
        this.SHOP_ID = SHOP_ID;
    }

    public void setSHOP_NAME(String SHOP_NAME) {
        this.SHOP_NAME = SHOP_NAME;
    }

    public void setCOUNSELOR_NAME(String COUNSELOR_NAME) {
        this.COUNSELOR_NAME = COUNSELOR_NAME;
    }

    public void setCOUNSELOR_ID(int COUNSELOR_ID) {
        this.COUNSELOR_ID = COUNSELOR_ID;
    }

    public void setFANS_OPENID(String FANS_OPENID) {
        this.FANS_OPENID = FANS_OPENID;
    }

    public void setCOUNSELOR_ICON(String COUNSELOR_ICON) {
        this.COUNSELOR_ICON = COUNSELOR_ICON;
    }

    public void setVIP_ID(int VIP_ID) {
        this.VIP_ID = VIP_ID;
    }

    public void setCOUNSELOR_INUSE(int COUNSELOR_INUSE) {
        this.COUNSELOR_INUSE = COUNSELOR_INUSE;
    }

    public void setFANS_NUM(int FANS_NUM) {
        this.FANS_NUM = FANS_NUM;
    }

    public void setCOUNSELOR_LEVELID(int COUNSELOR_LEVELID) {
        this.COUNSELOR_LEVELID = COUNSELOR_LEVELID;
    }

    public void setCOUNSELOR_SEX(String COUNSELOR_SEX) {
        this.COUNSELOR_SEX = COUNSELOR_SEX;
    }

    public void setCOUNSELOR_TYPE(int COUNSELOR_TYPE) {
        this.COUNSELOR_TYPE = COUNSELOR_TYPE;
    }

    public String getCOUNSELOR_MOBILE() {
        return COUNSELOR_MOBILE;
    }

    public String getCOUNSELOR_NICKNAME() {
        return COUNSELOR_NICKNAME;
    }

    public int getSHOP_ID() {
        return SHOP_ID;
    }

    public String getSHOP_NAME() {
        return SHOP_NAME;
    }

    public String getCOUNSELOR_NAME() {
        return COUNSELOR_NAME;
    }

    public int getCOUNSELOR_ID() {
        return COUNSELOR_ID;
    }

    public String getFANS_OPENID() {
        return FANS_OPENID;
    }

    public String getCOUNSELOR_ICON() {
        return COUNSELOR_ICON;
    }

    public int getVIP_ID() {
        return VIP_ID;
    }

    public int getCOUNSELOR_INUSE() {
        return COUNSELOR_INUSE;
    }

    public int getFANS_NUM() {
        return FANS_NUM;
    }

    public int getCOUNSELOR_LEVELID() {
        return COUNSELOR_LEVELID;
    }

    public String getCOUNSELOR_SEX() {
        return COUNSELOR_SEX;
    }

    public int getCOUNSELOR_TYPE() {
        return COUNSELOR_TYPE;
    }
}
