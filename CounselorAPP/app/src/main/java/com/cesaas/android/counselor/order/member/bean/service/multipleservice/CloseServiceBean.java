package com.cesaas.android.counselor.order.member.bean.service.multipleservice;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/4/25 10:07
 * Version 1.0
 */

public class CloseServiceBean implements Serializable {
    private String Title;
    private String Date;
    public boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
