package com.cesaas.android.java.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

/**
 * Author FGB
 * Description java 公共参数
 * Created at 2018/6/2 9:30
 * Version 1.0
 */

public class BaseJavaBean extends ErrorCode{
    public ArgumentsBean arguments;
    public class ArgumentsBean {
        public RespBean resp;

    }
    public class RespBean extends BaseBean {

    }
}
