package com.cesaas.android.counselor.order.signin.bean;

/**
 * Author FGB
 * Description
 * Created 2017/3/20 11:10
 * Version 1.zero
 */
public class SigninTypeBean {

    private String TypeName;//	签到状态（名称）
    private int TypeNumber;//签到状态（对应标记）

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public int getTypeNumber() {
        return TypeNumber;
    }

    public void setTypeNumber(int typeNumber) {
        TypeNumber = typeNumber;
    }
}
