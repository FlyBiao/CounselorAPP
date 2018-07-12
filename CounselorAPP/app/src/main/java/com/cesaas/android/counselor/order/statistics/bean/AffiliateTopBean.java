package com.cesaas.android.counselor.order.statistics.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

/**
 * 加盟TopBean
 * Created by cebsaas on 2017/2/22.
 */

public class AffiliateTopBean extends BaseBean{
    private int sequence;
    private String title;
    private double price;

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
