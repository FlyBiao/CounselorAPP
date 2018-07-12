package com.cesaas.android.counselor.order.base;

/**
 * Author FGB
 * Description 用于接接收底部tab切换时的对应tabNAME
 * Created at 2017/6/30 10:57
 * Version 1.0
 */

public class BaseTabsBean {



    private int tabType;//0:商品，1“样衣
    private String tabName;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTabType() {
        return tabType;
    }

    public void setTabType(int tabType) {
        this.tabType = tabType;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
}
