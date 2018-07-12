package com.cesaas.android.counselor.order.member.bean.service.volume;

import java.io.Serializable;

/**
 * Author FGB
 * Description 推送卷列表
 * Created at 2018/5/15 12:02
 * Version 1.0
 */

public class TicketListBean implements Serializable {
    private int TICKET_ID;
    private String TICKET_TITLE;
    private int TICKET_TRADETYPE;//券类型:0:现金券 1:礼品券 2:抵值券 3:折扣券
    private int TICKET_TYPE;//0:普通券 1：生日券
    private String TICKET_STARTDATE;//有效时间
    private String TICKET_ENDDATE;//截止时间
    private int TICKET_RANDOM;//金额是否随机:0 否 1是
    private double TICKET_MONEY;
    private double TICKET_MIN_MONEY;//券最小金额
    private double TICKET_MAX_MONEY;	//券最大金额;//券最da金额
    private int TICKET_NUMS;//券总数量
    private int TICKET_GET_NUMS;//券已领取数量
    private String TICKET_USEREMARK;
    public boolean isSelect;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getTICKET_ID() {
        return TICKET_ID;
    }

    public void setTICKET_ID(int TICKET_ID) {
        this.TICKET_ID = TICKET_ID;
    }

    public String getTICKET_TITLE() {
        return TICKET_TITLE;
    }

    public void setTICKET_TITLE(String TICKET_TITLE) {
        this.TICKET_TITLE = TICKET_TITLE;
    }

    public int getTICKET_TRADETYPE() {
        return TICKET_TRADETYPE;
    }

    public void setTICKET_TRADETYPE(int TICKET_TRADETYPE) {
        this.TICKET_TRADETYPE = TICKET_TRADETYPE;
    }

    public int getTICKET_TYPE() {
        return TICKET_TYPE;
    }

    public void setTICKET_TYPE(int TICKET_TYPE) {
        this.TICKET_TYPE = TICKET_TYPE;
    }

    public String getTICKET_STARTDATE() {
        return TICKET_STARTDATE;
    }

    public void setTICKET_STARTDATE(String TICKET_STARTDATE) {
        this.TICKET_STARTDATE = TICKET_STARTDATE;
    }

    public String getTICKET_ENDDATE() {
        return TICKET_ENDDATE;
    }

    public void setTICKET_ENDDATE(String TICKET_ENDDATE) {
        this.TICKET_ENDDATE = TICKET_ENDDATE;
    }

    public int getTICKET_RANDOM() {
        return TICKET_RANDOM;
    }

    public void setTICKET_RANDOM(int TICKET_RANDOM) {
        this.TICKET_RANDOM = TICKET_RANDOM;
    }

    public double getTICKET_MONEY() {
        return TICKET_MONEY;
    }

    public void setTICKET_MONEY(double TICKET_MONEY) {
        this.TICKET_MONEY = TICKET_MONEY;
    }

    public double getTICKET_MIN_MONEY() {
        return TICKET_MIN_MONEY;
    }

    public void setTICKET_MIN_MONEY(double TICKET_MIN_MONEY) {
        this.TICKET_MIN_MONEY = TICKET_MIN_MONEY;
    }

    public double getTICKET_MAX_MONEY() {
        return TICKET_MAX_MONEY;
    }

    public void setTICKET_MAX_MONEY(double TICKET_MAX_MONEY) {
        this.TICKET_MAX_MONEY = TICKET_MAX_MONEY;
    }

    public int getTICKET_NUMS() {
        return TICKET_NUMS;
    }

    public void setTICKET_NUMS(int TICKET_NUMS) {
        this.TICKET_NUMS = TICKET_NUMS;
    }

    public int getTICKET_GET_NUMS() {
        return TICKET_GET_NUMS;
    }

    public void setTICKET_GET_NUMS(int TICKET_GET_NUMS) {
        this.TICKET_GET_NUMS = TICKET_GET_NUMS;
    }

    public String getTICKET_USEREMARK() {
        return TICKET_USEREMARK;
    }

    public void setTICKET_USEREMARK(String TICKET_USEREMARK) {
        this.TICKET_USEREMARK = TICKET_USEREMARK;
    }
}
