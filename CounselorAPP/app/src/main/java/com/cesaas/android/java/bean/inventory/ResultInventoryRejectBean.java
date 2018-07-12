package com.cesaas.android.java.bean.inventory;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;

/**
 * Author FGB
 * Description 提交盘点单驳回
 * Created at 2018/5/24 17:19
 * Version 1.0
 */

public class ResultInventoryRejectBean extends ErrorCode {

    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;

    }
    public class RespBean extends BaseBean {

    }
}
