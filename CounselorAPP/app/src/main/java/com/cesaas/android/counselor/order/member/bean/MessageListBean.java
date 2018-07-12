package com.cesaas.android.counselor.order.member.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created 2017/4/15 11:55
 * Version 1.zero
 */
public class MessageListBean implements Serializable {
    private String CreateTime;
    private String Title;
    private String Msg;
    private int I;

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public int getI() {
        return I;
    }

    public void setI(int i) {
        I = i;
    }
}
