package com.cesaas.android.counselor.order.boss.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 店铺及业绩-店铺销售/目标
 * Created at 2017/8/18 11:29
 * Version 1.0
 */

public class ResultShopDataBean extends BaseBean {

    public ShopDataBean TModel;

    public class ShopDataBean implements Serializable{
        private double Payment;//实际销售
        private double TargetAmount;//销售目标
        private double TotalSale;//总销售
        private int RetailTotal;//零售总
        private int ShopQuantity;//店铺数量
        private int OrderQuantity;//订单数
        private int Quantity;


        public double getTotalSale() {
            return TotalSale;
        }

        public void setTotalSale(double totalSale) {
            TotalSale = totalSale;
        }

        public double getPayment() {

            return Payment;
        }

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int quantity) {
            Quantity = quantity;
        }

        public void setPayment(double payment) {
            Payment = payment;
        }

        public double getTargetAmount() {
            return TargetAmount;
        }

        public void setTargetAmount(double targetAmount) {
            TargetAmount = targetAmount;
        }

        public int getRetailTotal() {
            return RetailTotal;
        }

        public void setRetailTotal(int retailTotal) {
            RetailTotal = retailTotal;
        }

        public int getShopQuantity() {
            return ShopQuantity;
        }

        public void setShopQuantity(int shopQuantity) {
            ShopQuantity = shopQuantity;
        }

        public int getOrderQuantity() {
            return OrderQuantity;
        }

        public void setOrderQuantity(int orderQuantity) {
            OrderQuantity = orderQuantity;
        }
    }
}
