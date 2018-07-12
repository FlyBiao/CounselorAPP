package com.cesaas.android.counselor.order.webview.bean;

/**
 * Author FGB
 * Description 右上角接口Bean
 * Created at 2017/5/12 16:29
 * Version 1.0
 */

public class ToRightBean extends BaseTypeBean{

    private ReturnParamBean param;

    public ReturnParamBean getParam() {
        return param;
    }

    public void setParam(ReturnParamBean param) {
        this.param = param;
    }

    //返回参数Bean
    public class ReturnParamBean{
        private String title;//显示的文字
        private String url;//跳转的地址

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
