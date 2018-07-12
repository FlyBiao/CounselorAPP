package com.cesaas.android.java.bean.review;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.JavaCodeErrorBean;

import java.util.List;

/**
 * Author FGB
 * Description 查看多个评价
 * Created at 2018/6/21 9:44
 * Version 1.0
 */

public class ResultGetEvaluationModelBean extends ErrorCode {

    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends BaseBean {
        public RecordsBean records;

    }
    public class RecordsBean{
        public List<GetEvaluationModelBean> value;
    }

}
