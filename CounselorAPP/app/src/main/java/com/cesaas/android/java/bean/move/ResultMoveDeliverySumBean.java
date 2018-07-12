package com.cesaas.android.java.bean.move;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.StatisticModelBean;

/**
 * Author FGB
 * Description 调货发货汇总
 * Created at 2018/5/24 17:19
 * Version 1.0
 */

public class ResultMoveDeliverySumBean extends ErrorCode {

    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;

    }

    public class RespBean extends BaseBean {
        public StatisticModelBean model;
    }

}
