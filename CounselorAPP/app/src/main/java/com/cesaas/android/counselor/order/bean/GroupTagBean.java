package com.cesaas.android.counselor.order.bean;

import com.cesaas.android.counselor.order.boss.bean.ShopTagListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/1/10 13:50
 * Version 1.0
 */

public class GroupTagBean {
    public String groupInfo = "";
    public boolean isSelect;
    private List<ShopTagListBean> groupItemBeen=new ArrayList<>();

    public void add(ShopTagListBean bean){
        groupItemBeen.add(bean);
    }

    public List<ShopTagListBean> getGroupItemBeen() {
        return groupItemBeen;
    }

    public void setGroupItemBeen(List<ShopTagListBean> groupItemBeen) {
        this.groupItemBeen = groupItemBeen;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
