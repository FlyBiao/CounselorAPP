package com.cesaas.android.counselor.order.net.okhttp;

/**
 * Author FGB
 * Description okhttp 请求参数类
 * Created 2017/3/19 11:42
 * Version 1.zero
 */
public class RequestParam {

    private String key;
    private Object obj;

    public RequestParam(String key, Object obj) {
        this.key = key;
        this.obj = obj;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
