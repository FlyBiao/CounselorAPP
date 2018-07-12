package com.cesaas.android.counselor.order.bean;

import com.cesaas.android.counselor.order.boss.bean.ShopListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/1/10 13:50
 * Version 1.0
 */

public class GroupBean {
    public String groupInfo = "";
    private List<ShopListBean> groupItemBeen=new ArrayList<>();

    public void add(ShopListBean bean){
        groupItemBeen.add(bean);
    }

    public List<ShopListBean> getGroupItemBeen() {
        return groupItemBeen;
    }

    public void setGroupItemBeen(List<ShopListBean> groupItemBeen) {
        this.groupItemBeen = groupItemBeen;
    }
}
