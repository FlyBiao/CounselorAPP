package com.cesaas.android.counselor.order.webview.bean.shop;

/**
 * Author FGB
 * Description
 * Created at 2018/4/9 14:08
 * Version 1.0
 */

public class QueryGoodsEventBean {
    private int  QueryType;
    private int Type;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getQueryType() {
        return QueryType;
    }

    public void setQueryType(int queryType) {
        QueryType = queryType;
    }
}
