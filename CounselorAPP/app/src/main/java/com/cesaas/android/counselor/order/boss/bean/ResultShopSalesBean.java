package com.cesaas.android.counselor.order.boss.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/8/18 11:01
 * Version 1.0
 */

public class ResultShopSalesBean extends BaseBean {

    public ShopSalesBean TModel;

    public class ShopSalesBean implements Serializable{

        private int ShopQuantity;//店铺数
        private int StyleQuantity;//商品数
        private double Payment;//平均店销

        public int getShopQuantity() {
            return ShopQuantity;
        }

        public void setShopQuantity(int shopQuantity) {
            ShopQuantity = shopQuantity;
        }


        public int getStyleQuantity() {
            return StyleQuantity;
        }

        public void setStyleQuantity(int styleQuantity) {
            StyleQuantity = styleQuantity;
        }

        public double getPayment() {
            return Payment;
        }

        public void setPayment(double payment) {
            Payment = payment;
        }
    }
}
