package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/1/17 12:01
 * Version 1.0
 */

public class SortList implements Serializable {

    /**
     * Id : 103
     * ParentId : 0
     * Title : 上装
     * Type : 1
     */

    private int Id;
    private int ParentId;
    private String Title;
    private int Type;

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setParentId(int ParentId) {
        this.ParentId = ParentId;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public int getId() {
        return Id;
    }

    public int getParentId() {
        return ParentId;
    }

    public String getTitle() {
        return Title;
    }

    public int getType() {
        return Type;
    }
}
