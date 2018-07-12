package com.cesaas.android.counselor.order.webview;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/5/11 9:02
 * Version 1.0
 */

public class WebViewJsonBean {

    private int type;
    private WebViewParam param;

    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }
    public void setParam(WebViewParam param){
        this.param = param;
    }
    public WebViewParam getParam(){
        return this.param;
    }
}
