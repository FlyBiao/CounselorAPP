package com.cesaas.android.counselor.order.webview;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created 2017/4/10 10:27
 * Version 1.zero
 */
public class WebViewBaseBean implements Serializable{


    /**
     * param : [543592,"18566621563"]
     * type : 31
     */
    private List<String> param;
    private int type;

    public void setParam(List<String> param) {
        this.param = param;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getParam() {
        return param;
    }

    public int getType() {
        return type;
    }
}
