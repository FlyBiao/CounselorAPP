package com.cesaas.android.counselor.order.report.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 顾问销售结果Bean
 * Created at 2017/5/8 14:04
 * Version 1.0
 */

public class ResultCounselorSaleBean extends BaseBean{

    public CounselorSaleBean TModel;

    public class CounselorSaleBean implements Serializable{
        private int CounselorId;
        private int ShopId;
        private double SalesAmount;

        public int getCounselorId() {
            return CounselorId;
        }

        public void setCounselorId(int counselorId) {
            CounselorId = counselorId;
        }

        public int getShopId() {
            return ShopId;
        }

        public void setShopId(int shopId) {
            ShopId = shopId;
        }

        public double getSalesAmount() {
            return SalesAmount;
        }

        public void setSalesAmount(double salesAmount) {
            SalesAmount = salesAmount;
        }
    }

}
