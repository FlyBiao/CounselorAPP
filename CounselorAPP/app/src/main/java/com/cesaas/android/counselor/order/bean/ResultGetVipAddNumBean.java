package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/2/7 15:49
 * Version 1.0
 */

public class ResultGetVipAddNumBean extends BaseBean {

    public GetVipAddNumBean TModel;

    public class  GetVipAddNumBean implements Serializable{
        private int VipNum;//会员个数
        private int LastMonthVip;//上月会员个数
        private int LastYearVip;//	去年会员个数

        public int getVipNum() {
            return VipNum;
        }

        public void setVipNum(int vipNum) {
            VipNum = vipNum;
        }

        public int getLastMonthVip() {
            return LastMonthVip;
        }

        public void setLastMonthVip(int lastMonthVip) {
            LastMonthVip = lastMonthVip;
        }

        public int getLastYearVip() {
            return LastYearVip;
        }

        public void setLastYearVip(int lastYearVip) {
            LastYearVip = lastYearVip;
        }
    }
}
