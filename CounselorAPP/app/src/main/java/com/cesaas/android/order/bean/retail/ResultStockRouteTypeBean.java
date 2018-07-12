package com.cesaas.android.order.bean.retail;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 查询店铺订单调货方式
 * Created at 2018/5/10 13:59
 * Version 1.0
 */

public class ResultStockRouteTypeBean extends BaseBean {

    public StockRouteTypeBean TModel;

    public class StockRouteTypeBean implements Serializable{
        private int Type;//	0:指定店铺，1：自动路由

        public int getType() {
            return Type;
        }

        public void setType(int type) {
            Type = type;
        }
    }
}
