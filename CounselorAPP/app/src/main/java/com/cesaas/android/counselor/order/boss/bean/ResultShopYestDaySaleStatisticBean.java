package com.cesaas.android.counselor.order.boss.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/8/22 13:44
 * Version 1.0
 */

public class ResultShopYestDaySaleStatisticBean extends BaseBean {

    public ShopYestDaySaleStatistic TModel;


    public class ShopYestDaySaleStatistic implements Serializable{
        private double LastPayment;
        private double Payment;

        public double getLastPayment() {
            return LastPayment;
        }

        public void setLastPayment(double lastPayment) {
            LastPayment = lastPayment;
        }

        public double getPayment() {
            return Payment;
        }

        public void setPayment(double payment) {
            Payment = payment;
        }
    }
}
