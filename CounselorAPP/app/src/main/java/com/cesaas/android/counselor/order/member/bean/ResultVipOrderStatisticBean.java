package com.cesaas.android.counselor.order.member.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/11/18 14:57
 * Version 1.0
 */

public class ResultVipOrderStatisticBean extends BaseBean {

    public VipOrderStatisticBean TModel;

    public class VipOrderStatisticBean implements Serializable{

        /**
         * OrderQuantity : 90
         * Payment : 10161.22
         * Quantity : 84
         * RetailTotal : 11983.5
         * SaleId : 0
         */

        private int OrderQuantity;
        private double Payment;
        private int Quantity;
        private double RetailTotal;
        private int SaleId;

        public void setOrderQuantity(int OrderQuantity) {
            this.OrderQuantity = OrderQuantity;
        }

        public void setPayment(double Payment) {
            this.Payment = Payment;
        }

        public void setQuantity(int Quantity) {
            this.Quantity = Quantity;
        }

        public void setRetailTotal(double RetailTotal) {
            this.RetailTotal = RetailTotal;
        }

        public void setSaleId(int SaleId) {
            this.SaleId = SaleId;
        }

        public int getOrderQuantity() {
            return OrderQuantity;
        }

        public double getPayment() {
            return Payment;
        }

        public int getQuantity() {
            return Quantity;
        }

        public double getRetailTotal() {
            return RetailTotal;
        }

        public int getSaleId() {
            return SaleId;
        }
    }
}
