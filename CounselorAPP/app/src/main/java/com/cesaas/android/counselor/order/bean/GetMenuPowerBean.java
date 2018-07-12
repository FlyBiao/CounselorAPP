package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/8/21 10:49
 * Version 1.0
 */

public class GetMenuPowerBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private String MENU_TUPIAN;
    private String MENU_NO;
    private String MENU_NAME;
    private String MENU_REMARK;
    private String MENU_URL;
    private String MENU_CONTROLLER;
    private String  MENU_ICON;
    private String MNEU_HELP_URL;
    private String MENU_PARENTNO;
    private String  ValidateInfo;
    private String eMessage;
    private int IS_INUSE;
    private int MENU_TYPE;
    private boolean isError;
    private boolean isDetached;



    public String getMENU_TUPIAN() {
        return MENU_TUPIAN;
    }
    public void setMENU_TUPIAN(String mENU_TUPIAN) {
        MENU_TUPIAN = mENU_TUPIAN;
    }
    public String getMENU_NO() {
        return MENU_NO;
    }
    public void setMENU_NO(String mENU_NO) {
        MENU_NO = mENU_NO;
    }
    public String getMENU_NAME() {
        return MENU_NAME;
    }
    public void setMENU_NAME(String mENU_NAME) {
        MENU_NAME = mENU_NAME;
    }
    public String getMENU_REMARK() {
        return MENU_REMARK;
    }
    public void setMENU_REMARK(String mENU_REMARK) {
        MENU_REMARK = mENU_REMARK;
    }
    public String getMENU_URL() {
        return MENU_URL;
    }
    public void setMENU_URL(String mENU_URL) {
        MENU_URL = mENU_URL;
    }
    public String getMENU_CONTROLLER() {
        return MENU_CONTROLLER;
    }
    public void setMENU_CONTROLLER(String mENU_CONTROLLER) {
        MENU_CONTROLLER = mENU_CONTROLLER;
    }
    public String getMENU_ICON() {
        return MENU_ICON;
    }
    public void setMENU_ICON(String mENU_ICON) {
        MENU_ICON = mENU_ICON;
    }
    public String getMNEU_HELP_URL() {
        return MNEU_HELP_URL;
    }
    public void setMNEU_HELP_URL(String mNEU_HELP_URL) {
        MNEU_HELP_URL = mNEU_HELP_URL;
    }
    public String getMENU_PARENTNO() {
        return MENU_PARENTNO;
    }
    public void setMENU_PARENTNO(String mENU_PARENTNO) {
        MENU_PARENTNO = mENU_PARENTNO;
    }
    public String getValidateInfo() {
        return ValidateInfo;
    }
    public void setValidateInfo(String validateInfo) {
        ValidateInfo = validateInfo;
    }
    public String geteMessage() {
        return eMessage;
    }
    public void seteMessage(String eMessage) {
        this.eMessage = eMessage;
    }
    public int getIS_INUSE() {
        return IS_INUSE;
    }
    public void setIS_INUSE(int iS_INUSE) {
        IS_INUSE = iS_INUSE;
    }
    public int getMENU_TYPE() {
        return MENU_TYPE;
    }
    public void setMENU_TYPE(int mENU_TYPE) {
        MENU_TYPE = mENU_TYPE;
    }
    public boolean isError() {
        return isError;
    }
    public void setError(boolean isError) {
        this.isError = isError;
    }
    public boolean isDetached() {
        return isDetached;
    }
    public void setDetached(boolean isDetached) {
        this.isDetached = isDetached;
    }


}
