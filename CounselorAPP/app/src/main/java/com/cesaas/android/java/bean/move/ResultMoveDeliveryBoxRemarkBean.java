package com.cesaas.android.java.bean.move;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;

/**
 * Author FGB
 * Description 新增修改箱备注
 * Created at 2018/6/1 11:18
 * Version 1.0
 */

public class ResultMoveDeliveryBoxRemarkBean extends ErrorCode {
    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;

    }
    public class RespBean extends BaseBean {

    }
}
