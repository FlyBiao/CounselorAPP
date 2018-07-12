package com.cesaas.android.java.bean.move;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.move.MoveDeliveryGoodsDetailBean;

import java.util.List;

/**
 * Author FGB
 * Description 调货发货商品条码列表
 * Created at 2018/5/31 17:41
 * Version 1.0
 */

public class ResultMoveDeliveryGoodsDetailBean extends ErrorCode {
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
