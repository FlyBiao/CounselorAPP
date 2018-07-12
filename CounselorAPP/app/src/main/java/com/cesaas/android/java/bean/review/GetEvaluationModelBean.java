package com.cesaas.android.java.bean.review;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description 查看多个评价
 * Created at 2018/6/21 9:45
 * Version 1.0
 */

public class GetEvaluationModelBean implements Serializable {
    private String clientName;
    private int totalValue;
    private int rateValue;
    private TitleValue records;
    private String suggestion;
    private String createTime;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    public int getRateValue() {
        return rateValue;
    }

    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
    }

    public TitleValue getRecords() {
        return records;
    }

    public void setRecords(TitleValue records) {
        this.records = records;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
