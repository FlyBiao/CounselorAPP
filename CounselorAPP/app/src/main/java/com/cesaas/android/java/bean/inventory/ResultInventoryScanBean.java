package com.cesaas.android.java.bean.inventory;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.inventory.InventoryScanBean;


/**
 * Author FGB
 * Description 扫描盘点
 * Created at 2018/5/26 17:46
 * Version 1.0
 */

public class ResultInventoryScanBean extends ErrorCode {
    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends BaseBean {
        public InventoryScanBean scan;

    }

}
