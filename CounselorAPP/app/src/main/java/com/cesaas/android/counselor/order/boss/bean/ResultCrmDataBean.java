package com.cesaas.android.counselor.order.boss.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 店铺及业绩-拉粉数量/目标
 * Created at 2017/8/18 11:29
 * Version 1.0
 */

public class ResultCrmDataBean extends BaseBean {

    public ShopDataBean TModel;

    public class ShopDataBean implements Serializable{
        private int Quantity;//招募数
        private int TargetQuantity;//目标数

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int quantity) {
            Quantity = quantity;
        }

        public int getTargetQuantity() {
            return TargetQuantity;
        }

        public void setTargetQuantity(int targetQuantity) {
            TargetQuantity = targetQuantity;
        }
    }
}
