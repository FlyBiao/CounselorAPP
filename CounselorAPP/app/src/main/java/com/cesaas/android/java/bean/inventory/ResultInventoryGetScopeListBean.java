package com.cesaas.android.java.bean.inventory;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.inventory.InventoryGetScopeListBean;

import java.util.List;

/**
 * Author FGB
 * Description 盘点货架列表
 * Created at 2018/5/24 17:19
 * Version 1.0
 */

public class ResultInventoryGetScopeListBean extends ErrorCode {

    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends BaseBean {
        public RecordsBean records;
    }
    public class RecordsBean{
        public List<InventoryGetScopeListBean> value;
    }
}
