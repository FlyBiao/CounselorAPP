package com.cesaas.android.counselor.order.shopmange.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/5/14 11:26
 * Version 1.0
 */

public class ShopTagBean implements Serializable {
    private String CategoryName;
    private String TagName;
    private int TagId;
    private int CategoryId;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getTagName() {
        return TagName;
    }

    public void setTagName(String tagName) {
        TagName = tagName;
    }

    public int getTagId() {
        return TagId;
    }

    public void setTagId(int tagId) {
        TagId = tagId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }
}
