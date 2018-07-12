package com.cesaas.android.counselor.order.statistics.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author FGB
 * Description
 * Created 2017/3/14 16:46
 * Version 1.zero
 */
public class ResultJoinAndDSalesBean extends BaseBean{
    public ArrayList<JoinAndDSalesBean> TModel;

    public class JoinAndDSalesBean implements Serializable{
        public int TopNumber;//排名号码
        public String TopShopName;//店铺名称
        public double TopGain;//实收


    }
}
