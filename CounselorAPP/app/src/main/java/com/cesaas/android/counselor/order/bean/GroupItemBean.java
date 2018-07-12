package com.cesaas.android.counselor.order.bean;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/1/10 13:51
 * Version 1.0
 */

public class GroupItemBean {

    private String name;
    private List<GroupItem> groupItem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GroupItem> getGroupItem() {
        return groupItem;
    }

    public void setGroupItem(List<GroupItem> groupItem) {
        this.groupItem = groupItem;
    }
}
