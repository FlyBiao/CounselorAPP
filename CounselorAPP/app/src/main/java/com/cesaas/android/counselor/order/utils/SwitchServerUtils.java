package com.cesaas.android.counselor.order.utils;

import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;

/**
 * Author FGB
 * Description 切换服务器
 * Created at 2018/5/8 14:36
 * Version 1.0
 */

public class SwitchServerUtils {
    public static String getServerUrl(){
        String serverIp= Constant.IP;
//
        if(App.mCache.getAsString(Constant.IS_SWITCH_SERVER)!=null && !"".equals(App.mCache.getAsString(Constant.IS_SWITCH_SERVER))){
            if(App.mCache.getAsString(Constant.IS_SWITCH_SERVER).equals("true")){
                serverIp=Constant.IP;
            }else if(App.mCache.getAsString(Constant.IS_SWITCH_SERVER).equals("false")){
                serverIp=Constant.Test_IP;
            }else if(App.mCache.getAsString(Constant.IS_SWITCH_SERVER).equals("pos")){
                serverIp=Constant.Pos_IP;
            }
            else{
                serverIp=Constant.IP;
            }
        }

        return serverIp;
    }

    public static String getJavaServerUrl(){
        String serverJavaIp= Constant.JAVA_IP;
//
        if(App.mCache.getAsString(Constant.IS_SWITCH_SERVER)!=null && !"".equals(App.mCache.getAsString(Constant.IS_SWITCH_SERVER))){
            if(App.mCache.getAsString(Constant.IS_SWITCH_SERVER).equals("true")){
                serverJavaIp= Constant.JAVA_IP;
            }else if(App.mCache.getAsString(Constant.IS_SWITCH_SERVER).equals("false")){
                serverJavaIp= Constant.JAVA_TEST_IP;
            }else if(App.mCache.getAsString(Constant.IS_SWITCH_SERVER).equals("pos")){
                serverJavaIp= Constant.JAVA_IP;
            }
            else{
                serverJavaIp= Constant.JAVA_IP;
            }
    }
        return serverJavaIp;
    }
}
