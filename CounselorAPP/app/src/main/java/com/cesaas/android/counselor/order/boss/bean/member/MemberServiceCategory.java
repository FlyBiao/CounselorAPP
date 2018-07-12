package com.cesaas.android.counselor.order.boss.bean.member;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/4/26 9:01
 * Version 1.0
 */

public class MemberServiceCategory implements Serializable{
    private int Type;
    private String CategoryName;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
