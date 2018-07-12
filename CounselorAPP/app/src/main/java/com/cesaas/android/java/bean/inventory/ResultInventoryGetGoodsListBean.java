package com.cesaas.android.java.bean.inventory;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.inventory.InventoryGetGoodsListBean;

import java.util.List;

/**
 * Author FGB
 * Description 获取盘点单款式列表
 * Created at 2018/5/26 17:31
 * Version 1.0
 */

public class ResultInventoryGetGoodsListBean extends ErrorCode {
    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends BaseBean {
        public RecordsBean records;

    }
    public class RecordsBean{
        public List<InventoryGetGoodsListBean> value;
    }
}
