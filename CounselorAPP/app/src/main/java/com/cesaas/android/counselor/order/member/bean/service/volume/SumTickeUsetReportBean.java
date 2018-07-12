package com.cesaas.android.counselor.order.member.bean.service.volume;

import java.io.Serializable;

/**
 * Author FGB
 * Description 券分析追踪-总计
 * Created at 2018/5/16 17:08
 * Version 1.0
 */

public class SumTickeUsetReportBean implements Serializable {
    private int CreateNum;
    private int GetNum;
    private int UseNum;
    private int PayMent;

    public int getCreateNum() {
        return CreateNum;
    }

    public void setCreateNum(int createNum) {
        CreateNum = createNum;
    }

    public int getGetNum() {
        return GetNum;
    }

    public void setGetNum(int getNum) {
        GetNum = getNum;
    }

    public int getUseNum() {
        return UseNum;
    }

    public void setUseNum(int useNum) {
        UseNum = useNum;
    }

    public int getPayMent() {
        return PayMent;
    }

    public void setPayMent(int payMent) {
        PayMent = payMent;
    }
}
