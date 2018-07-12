package com.cesaas.android.counselor.order.webview.base;

import android.util.Log;

import com.cesaas.android.counselor.order.bean.TestUserInfo;
import com.cesaas.android.counselor.order.bean.UserInfo;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;

/**
 * Author FGB
 * Description
 * Created at 2017/5/20 19:21
 * Version 1.0
 */

public class BaseUserInfo {

    public static TestUserInfo getUserInfo(AbPrefsUtil prefs){
        TestUserInfo user=new TestUserInfo();
        user.settId(Integer.parseInt(prefs.getString("TId")));
        user.setTypeId(prefs.getString("TypeId"));
        user.setShopName(prefs.getString("shopName"));
        user.setShopId(prefs.getString("ShopId"));
        user.setVipId(prefs.getString("VipId"));
        user.setMobile(prefs.getString("Mobile"));
        user.setIcon(prefs.getString("Icon"));
        user.setName(prefs.getString("Name"));
        user.setNickName(prefs.getString("NickName"));
        user.setSex(prefs.getString("Sex"));
        user.setShopMobile(prefs.getString("shopMobile"));
        user.setTypeName(prefs.getString("TypeName"));
        user.setTicket(prefs.getString("Ticket"));
        user.setCounselorId(prefs.getString("CounselorId"));
        user.setGzNo(prefs.getString("GzNo"));

        return user;
    }
}
