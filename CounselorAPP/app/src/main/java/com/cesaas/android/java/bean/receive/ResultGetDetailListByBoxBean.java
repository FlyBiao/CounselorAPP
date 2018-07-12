package com.cesaas.android.java.bean.receive;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;

import java.util.List;

/**
 * Author FGB
 * Description 通过收货箱获取商品
 * Created at 2018/6/4 16:50
 * Version 1.0
 */

public class ResultGetDetailListByBoxBean extends ErrorCode {
    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends BaseBean {
        public RecordsBean receiveDetailList;

    }
    public class RecordsBean{
        public List<GetDetailListByBoxBean> value;
    }
}
