package com.cesaas.android.java.bean.require;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;

/**
 * Author FGB
 * Description 补货详情
 * Created at 2018/6/5 10:46
 * Version 1.0
 */

public class ResultRequireDetailBean extends ErrorCode {
    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends BaseBean {
        public RecordsBean model;

    }
    public class RecordsBean{
        public RequireListBean value;
    }
}
