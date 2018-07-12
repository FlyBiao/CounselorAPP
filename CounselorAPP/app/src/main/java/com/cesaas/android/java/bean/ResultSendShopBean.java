package com.cesaas.android.java.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.util.List;

/**
 * Author FGB
 * Description 发货权限店铺列表
 * Created at 2018/5/26 15:25
 * Version 1.0
 */

public class ResultSendShopBean {

    public ArgumentsBean arguments;
    private JavaCodeErrorBean error;

    public JavaCodeErrorBean getError() {
        return error;
    }

    public void setError(JavaCodeErrorBean error) {
        this.error = error;
    }

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
