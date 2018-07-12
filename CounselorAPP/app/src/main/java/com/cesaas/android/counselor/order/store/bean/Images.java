package com.cesaas.android.counselor.order.store.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 完成成列图片Bean
 * Created 2017/3/4 20:44
 * Version 1.zero
 */
public class Images implements Serializable {

    private String Id;//陈列图片Id
    private String Url;//陈列图片地址
    private String Describe;//提交陈列任务备注



    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }
}
