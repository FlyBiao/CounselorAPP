package com.cesaas.android.java.bean;

import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2018/5/24 11:15
 * Version 1.0
 */

public class BaseUserSessionBean {

    public static JSONObject getUserSession(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("sessionKey",AbPrefsUtil.getInstance().getString(Constant.SPF_AUTHKEY));
            obj.put("sessionAuth", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
//            obj.put("sessionKey","iDIztlKPsXXAMNvZcBxFZg==");
//            obj.put("sessionAuth", "iDIztlKPsXV4gs2N86gSJyrhfQNFhOFzV2W78oiIe7A=");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
