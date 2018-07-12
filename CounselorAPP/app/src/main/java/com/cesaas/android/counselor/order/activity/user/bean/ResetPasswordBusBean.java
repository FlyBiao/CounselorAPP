package com.cesaas.android.counselor.order.activity.user.bean;

/**
 * Author FGB
 * Description
 * Created at 2017/5/13 22:26
 * Version 1.0
 */

public class ResetPasswordBusBean {
    private boolean isCheck=false;
    private int isNew;

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
