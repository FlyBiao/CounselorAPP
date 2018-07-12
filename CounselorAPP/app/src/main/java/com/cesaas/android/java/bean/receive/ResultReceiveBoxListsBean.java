package com.cesaas.android.java.bean.receive;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.move.MoveDeliveryBoxListBean;

import java.util.List;

/**
 * Author FGB
 * Description 获取箱列表 /收货
 * Created at 2018/6/2 17:01
 * Version 1.0
 */

public class ResultReceiveBoxListsBean extends ErrorCode {
    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends BaseBean {
        public RecordsBean records;

    }
    public class RecordsBean{
        public List<MoveDeliveryBoxListBean> value;
    }
}
