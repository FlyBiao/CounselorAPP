package com.cesaas.android.java.bean.notice;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;

import java.util.List;

/**
 * Author FGB
 * Description  通知单商品条码列表
 * Created at 2018/6/5 14:42
 * Version 1.0
 */

public class ResultNoticeGoodsDetailBean extends ErrorCode {
    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends BaseBean {
        public RecordsBean records;

    }
    public class RecordsBean{
        public List<NoticeGoodsDetailBean> value;
    }
}
