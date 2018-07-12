package com.cesaas.android.counselor.order.member.bean.service;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 统计待完成服务数
 * Created at 2018/3/6 15:18
 * Version 1.0
 */

public class ResultProcessedServiceSumBean extends BaseBean{

    public ProcessedServiceSumBean TModel;

    public class ProcessedServiceSumBean implements Serializable{
        private int Visit;//销售回访统计数
        private int Birth;//生日祝福统计数;
        private int Festival;//节日安排统计数
        private int Dormancy;//休眠激活统计数
        private int Custom;//定制会员统计数
        private int Return;//退换货统计数

        public int getReturn() {
            return Return;
        }

        public void setReturn(int aReturn) {
            Return = aReturn;
        }

        public int getVisit() {
            return Visit;
        }

        public void setVisit(int visit) {
            Visit = visit;
        }

        public int getBirth() {
            return Birth;
        }

        public void setBirth(int birth) {
            Birth = birth;
        }

        public int getFestival() {
            return Festival;
        }

        public void setFestival(int festival) {
            Festival = festival;
        }

        public int getDormancy() {
            return Dormancy;
        }

        public void setDormancy(int dormancy) {
            Dormancy = dormancy;
        }

        public int getCustom() {
            return Custom;
        }

        public void setCustom(int custom) {
            Custom = custom;
        }
    }
}
