package com.cesaas.android.java.bean.receive;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.StatisticModelBean;

/**
 * Author FGB
 * Description 收获数据汇总
 * Created at 2018/5/24 17:19
 * Version 1.0
 */

public class ResultReceiveSumBean extends ErrorCode {

    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;

    }

    public class RespBean extends BaseBean {
        public StatisticModelBean model;
    }

}
