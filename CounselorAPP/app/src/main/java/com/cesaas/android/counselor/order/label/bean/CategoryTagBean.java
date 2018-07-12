package com.cesaas.android.counselor.order.label.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/5/24 11:08
 * Version 1.0
 */

public class CategoryTagBean implements Serializable {


    public int CategoryId;//父类ID
    public String CategoryName;

    public List<GetTagListBean> getTagList=new ArrayList<>();

    public String getCategoryName() {
        return CategoryName;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
