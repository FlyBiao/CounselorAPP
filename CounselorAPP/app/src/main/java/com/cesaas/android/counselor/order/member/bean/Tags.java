package com.cesaas.android.counselor.order.member.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/11/11 15:27
 * Version 1.0
 */

public class Tags implements Serializable {

    /**
     * TagId : 172
     * CategoryId : 74
     * TagName : 老VIP
     * TagTimes : null
     * CategoryName : 动态标签
     */

    private Integer TagId;
    private int CategoryId;
    private int TagType;//0：手动标签  1：动态标签
    private String TagName;
    private String TagTimes;
    private String CategoryName;

    public Integer getTagId() {
        return TagId;
    }

    public void setTagId(Integer tagId) {
        TagId = tagId;
    }

    public int getTagType() {
        return TagType;
    }

    public void setTagType(int tagType) {
        TagType = tagType;
    }


    public void setCategoryId(int CategoryId) {
        this.CategoryId = CategoryId;
    }

    public void setTagName(String TagName) {
        this.TagName = TagName;
    }

    public void setTagTimes(String TagTimes) {
        this.TagTimes = TagTimes;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public String getTagName() {
        return TagName;
    }

    public Object getTagTimes() {
        return TagTimes;
    }

    public String getCategoryName() {
        return CategoryName;
    }
}
