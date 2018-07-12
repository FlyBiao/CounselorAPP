package com.cesaas.android.counselor.order.webview;

/**
 * Author FGB
 * Description webview请求json返回
 * Created at 2017/5/12 15:57
 * Version 1.0
 */

public class WebViewRequestJsonBean {
    private String webviewParamJson;
    private int requestType;

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public String getWebviewParamJson() {
        return webviewParamJson;
    }

    public void setWebviewParamJson(String webviewParamJson) {
        this.webviewParamJson = webviewParamJson;
    }
}
