package com.cesaas.android.counselor.order.member.bean;

import com.cesaas.android.counselor.order.bean.SubOrder;
import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Author FGB
 * Description
 * Created at 2017/11/11 11:41
 * Version 1.0
 */

public class VipOrderListSection extends SectionEntity<SubOrder> {

    private boolean isMore;
    private String CreateTime;

    public VipOrderListSection(boolean isHeader, String header,boolean isMore,String CreateTime) {
        super(isHeader, header);
        this.isMore=isMore;
        this.CreateTime=CreateTime;
    }

    public VipOrderListSection(SubOrder subOrder) {
        super(subOrder);
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

}
