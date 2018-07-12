package com.cesaas.android.counselor.order.activity.user.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 店铺销售金额-首页
 * Created at 2018/3/19 10:24
 * Version 1.0
 */

public class ResultShopSaleCompareBean extends BaseBean{
    public ShopSaleCompareBean TModel;

    public class ShopSaleCompareBean implements Serializable{
        private double SalesAmount;
        private double LastYearAmount;
        private double LastAmount;

        public double getSalesAmount() {
            return SalesAmount;
        }

        public void setSalesAmount(double salesAmount) {
            SalesAmount = salesAmount;
        }

        public double getLastYearAmount() {
            return LastYearAmount;
        }

        public void setLastYearAmount(double lastYearAmount) {
            LastYearAmount = lastYearAmount;
        }

        public double getLastAmount() {
            return LastAmount;
        }

        public void setLastAmount(double lastAmount) {
            LastAmount = lastAmount;
        }
    }
}
