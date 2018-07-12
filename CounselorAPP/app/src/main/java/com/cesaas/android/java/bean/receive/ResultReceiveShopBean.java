package com.cesaas.android.java.bean.receive;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.JavaCodeErrorBean;
import com.cesaas.android.java.bean.ShopListBean;

import java.util.List;

/**
 * Author FGB
 * Description 收货权限店铺列表
 * Created at 2018/5/26 15:25
 * Version 1.0
 */

public class ResultReceiveShopBean extends ErrorCode {

    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends BaseBean {
        public RecordsBean records;

    }
    public class RecordsBean{
        public List<ShopListBean> value;
    }
}
