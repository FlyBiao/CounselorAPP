package com.cesaas.android.java.bean.receive;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;

import java.util.List;

/**
 * Author FGB
 * Description 收货差异列表Bean
 * Created at 2018/6/2 17:01
 * Version 1.0
 */

public class ResultReceiveGetDiffListBean extends ErrorCode {
    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends BaseBean {
        public RecordsBean records;

    }
    public class RecordsBean{
        public List<ReceiveGetDiffListBean> value;
    }
}
