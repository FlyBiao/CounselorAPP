package com.cesaas.android.counselor.order.power.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/1/30 17:51
 * Version 1.0
 */

public class ActionPowerBean implements Serializable {
    /**
     * ACTION_NO : a_y_b_d
     * MENU_NO : a_y_b
     * ROLE_MENU_ID : 14587
     * isDetached : false
     * isError : false
     */

    private String ACTION_NO;//动作权限编号
    private String MENU_NO;//菜单编号
    private int ROLE_MENU_ID;//权限菜单Id
    private boolean isDetached;
    private boolean isError;

    public void setACTION_NO(String ACTION_NO) {
        this.ACTION_NO = ACTION_NO;
    }

    public void setMENU_NO(String MENU_NO) {
        this.MENU_NO = MENU_NO;
    }

    public void setROLE_MENU_ID(int ROLE_MENU_ID) {
        this.ROLE_MENU_ID = ROLE_MENU_ID;
    }

    public void setIsDetached(boolean isDetached) {
        this.isDetached = isDetached;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public String getACTION_NO() {
        return ACTION_NO;
    }

    public String getMENU_NO() {
        return MENU_NO;
    }

    public int getROLE_MENU_ID() {
        return ROLE_MENU_ID;
    }

    public boolean getIsDetached() {
        return isDetached;
    }

    public boolean getIsError() {
        return isError;
    }
}
