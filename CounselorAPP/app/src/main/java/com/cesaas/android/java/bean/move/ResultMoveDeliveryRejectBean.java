package com.cesaas.android.java.bean.move;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.JavaBaseBean;

/**
 * Author FGB
 * Description 调拨驳回
 * Created at 2018/6/26 14:29
 * Version 1.0
 */

public class ResultMoveDeliveryRejectBean extends ErrorCode {
    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;

    }
    public class RespBean extends BaseBean {

    }
}
