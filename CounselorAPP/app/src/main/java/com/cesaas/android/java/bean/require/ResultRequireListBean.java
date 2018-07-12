package com.cesaas.android.java.bean.require;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;

import java.util.List;

/**
 * Author FGB
 * Description 补货列表
 * Created at 2018/6/5 10:10
 * Version 1.0
 */

public class ResultRequireListBean extends ErrorCode {
    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends BaseBean {
        public RecordsBean records;

    }
    public class RecordsBean{
        public List<RequireListBean> value;
    }
}
