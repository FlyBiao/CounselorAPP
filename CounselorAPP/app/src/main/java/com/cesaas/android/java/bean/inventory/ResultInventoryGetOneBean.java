package com.cesaas.android.java.bean.inventory;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.inventory.InventoryGetOneBean;

/**
 * Author FGB
 * Description 盘点单详情
 * Created at 2018/5/24 17:19
 * Version 1.0
 */

public class ResultInventoryGetOneBean extends ErrorCode {

    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;

    }
    public class RespBean extends BaseBean {
        public InventoryGetOneBean oneInventory;
    }
}
