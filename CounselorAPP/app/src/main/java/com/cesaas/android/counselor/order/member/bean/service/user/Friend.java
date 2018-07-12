package com.cesaas.android.counselor.order.member.bean.service.user;

/**
 * Author FGB
 * Description
 * Created at 2018/3/31 15:14
 * Version 1.0
 */

public class Friend {
    private String userId;

    private String userName;

    private String portraitUri;

    public Friend(String userId,String userName,String portraitUri){
        this.userId = userId;
        this.userName = userName;
        this.portraitUri = portraitUri;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }

}
