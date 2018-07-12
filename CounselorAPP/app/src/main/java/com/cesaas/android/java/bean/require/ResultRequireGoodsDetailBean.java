package com.cesaas.android.java.bean.require;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.move.MoveDeliveryGoodsDetailBean;

import java.util.List;

/**
 * Author FGB
 * Description 补货商品条码列表
 * Created at 2018/6/5 10:52
 * Version 1.0
 */

public class ResultRequireGoodsDetailBean extends ErrorCode {
    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends BaseBean {
        public RecordsBean records;

    }
    public class RecordsBean{
        public List<MoveDeliveryGoodsDetailBean> value;
    }
}
