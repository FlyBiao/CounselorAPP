package com.cesaas.android.counselor.order.manager.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 任务统计结果Bean
 * Created at 2017/9/14 11:39
 * Version 1.0
 */

public class ResultTaskStatisticBean extends BaseBean {

    public TaskStatisticBean TModel;

    public class TaskStatisticBean implements Serializable{

        /**
         * ShopQuantity : 1
         * ReadTaskQuantity : 0
         * UnDoQuantity : 0
         */

        private int ShopQuantity;
        private int ReadTaskQuantity;
        private int UnDoQuantity;

        public void setShopQuantity(int ShopQuantity) {
            this.ShopQuantity = ShopQuantity;
        }

        public void setReadTaskQuantity(int ReadTaskQuantity) {
            this.ReadTaskQuantity = ReadTaskQuantity;
        }

        public void setUnDoQuantity(int UnDoQuantity) {
            this.UnDoQuantity = UnDoQuantity;
        }

        public int getShopQuantity() {
            return ShopQuantity;
        }

        public int getReadTaskQuantity() {
            return ReadTaskQuantity;
        }

        public int getUnDoQuantity() {
            return UnDoQuantity;
        }
    }
}
