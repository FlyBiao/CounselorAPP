package com.cesaas.android.counselor.order.boss.bean.member;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/4/26 11:11
 * Version 1.0
 */

public class TimeDataBean implements Serializable {
    private int Type;
    private String DateTitle;
    private String StartDate;
    private String EndDate;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getDateTitle() {
        return DateTitle;
    }

    public void setDateTitle(String dateTitle) {
        DateTitle = dateTitle;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
}
