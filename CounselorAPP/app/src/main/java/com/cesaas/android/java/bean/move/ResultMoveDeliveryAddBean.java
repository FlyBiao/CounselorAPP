package com.cesaas.android.java.bean.move;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;

/**
 * Author FGB
 * Description 新增调拨发货
 * Created at 2018/5/31 15:09
 * Version 1.0
 */

public class ResultMoveDeliveryAddBean extends ErrorCode {
    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;

    }
    public class RespBean extends BaseBean {

    }
}
