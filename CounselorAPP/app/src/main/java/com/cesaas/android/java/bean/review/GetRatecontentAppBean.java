package com.cesaas.android.java.bean.review;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description 查看导购评价
 * Created at 2018/6/21 9:55
 * Version 1.0
 */

public class GetRatecontentAppBean {

    private int totalValue;
    private int count;

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

//    private List<RatecontentApp> records;
//
//    public List<RatecontentApp> getRecords() {
//        return records;
//    }
//
//    public void setRecords(List<RatecontentApp> records) {
//        this.records = records;
//    }
}
