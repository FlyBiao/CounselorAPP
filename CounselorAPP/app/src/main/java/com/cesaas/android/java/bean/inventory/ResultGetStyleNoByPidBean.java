package com.cesaas.android.java.bean.inventory;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.GetStyleNoByPidBean;

import java.util.List;

/**
 * Author FGB
 * Description 根据pid获取款号列表
 * Created at 2018/5/26 15:25
 * Version 1.0
 */

public class ResultGetStyleNoByPidBean extends ErrorCode {

    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends BaseBean {
        public RecordsBean StyleNoList;

    }
    public class RecordsBean{
        public List<GetStyleNoByPidBean> value;
    }
}
