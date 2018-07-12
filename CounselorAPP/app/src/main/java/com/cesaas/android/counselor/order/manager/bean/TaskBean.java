package com.cesaas.android.counselor.order.manager.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/9/12 16:43
 * Version 1.0
 */

public class TaskBean implements Serializable {

    private String month;
    private String day;
    private String title;
    private String date;
    private String week;
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
