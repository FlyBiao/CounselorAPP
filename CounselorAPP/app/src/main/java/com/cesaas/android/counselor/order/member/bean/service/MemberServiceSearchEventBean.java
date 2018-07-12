package com.cesaas.android.counselor.order.member.bean.service;

/**
 * Author FGB
 * Description
 * Created at 2018/4/12 16:59
 * Version 1.0
 */

public class MemberServiceSearchEventBean {
    private String SearchValue;
    private int Type;

    public String getSearchValue() {
        return SearchValue;
    }

    public void setSearchValue(String searchValue) {
        SearchValue = searchValue;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
