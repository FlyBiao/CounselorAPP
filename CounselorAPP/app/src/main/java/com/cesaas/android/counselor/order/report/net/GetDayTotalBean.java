package com.cesaas.android.counselor.order.report.net;

import java.io.Serializable;

/**
 * Author FGB
 * Description 获取店铺日目标Bean
 * Created at 2017/5/9 17:37
 * Version 1.0
 */

public class GetDayTotalBean implements Serializable {

    private String Card;//会员目标
    private String CounselorGoal;//店员目标
    private String ShopGoal;//店铺目标
    private String VipNum;//新增会员数

    public String getCard() {
        return Card;
    }

    public void setCard(String card) {
        Card = card;
    }

    public String getCounselorGoal() {
        return CounselorGoal;
    }

    public void setCounselorGoal(String counselorGoal) {
        CounselorGoal = counselorGoal;
    }

    public String getShopGoal() {
        return ShopGoal;
    }

    public void setShopGoal(String shopGoal) {
        ShopGoal = shopGoal;
    }

    public String getVipNum() {
        return VipNum;
    }

    public void setVipNum(String vipNum) {
        VipNum = vipNum;
    }
}
