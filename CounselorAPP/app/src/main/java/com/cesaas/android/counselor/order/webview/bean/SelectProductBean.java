package com.cesaas.android.counselor.order.webview.bean;

/**
 * Author FGB
 * Description 选择商品
 * Created at 2017/5/15 16:52
 * Version 1.0
 */

public class SelectProductBean extends BaseTypeBean{

    private ProductBean param;

    public ProductBean getParam() {
        return param;
    }

    public void setParam(ProductBean param) {
        this.param = param;
    }

    public class ProductBean{
        private int multi;

        public int getMulti() {
            return multi;
        }

        public void setMulti(int multi) {
            this.multi = multi;
        }

    }
}
