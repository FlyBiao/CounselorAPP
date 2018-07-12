package com.cesaas.android.counselor.order.webview.bean;

/**
 * Author FGB
 * Description 页面跳转
 * Created at 2017/5/12 17:25
 * Version 1.0
 */

public class SkipBean extends BaseTypeBean{
    private Skip param;

    public Skip getParam() {
        return param;
    }

    public void setParam(Skip param) {
        this.param = param;
    }

    public class Skip{
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
