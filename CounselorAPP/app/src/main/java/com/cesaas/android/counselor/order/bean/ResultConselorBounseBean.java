package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 获取提现金额结果Bean
 * Created at 2017/6/27 9:23
 * Version 1.0
 */

public class ResultConselorBounseBean extends BaseBean{

    public  Bounse TModel;


    public class Bounse implements Serializable{
        private double AllowAmount;

        public double getAllowAmount() {
            return AllowAmount;
        }

        public void setAllowAmount(double allowAmount) {
            AllowAmount = allowAmount;
        }
    }
}
