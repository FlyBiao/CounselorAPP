package com.cesaas.android.counselor.order.utils;

import android.content.Context;

import com.cesaas.android.counselor.order.bean.UserInfo;
import com.cesaas.android.counselor.order.net.UserInfoNet;

/**
 * Author FGB
 * Description JavascriptInterface 工具类【WebView专用】
 * Created 2017/3/27 17:22
 * Version 1.zero
 */
public class JavascriptInterfaceUtils {

    public String mobile;//手机号
    public String icon;//用户头像
    public String name;//用户名称
    public String nickName;//用户昵称
    public String sex;//性别
    public String shopId;//店铺ID
    public String shopName;//店铺名称
    public String shopMobile;//店铺电话
    public String typeName;//用户身份：店员，店长
    public String typeId;//1：店长，2：店员
    public String vipId;
    public String ticket;//生成拉粉二维码用票
    public String imToken;
    public String counselorId;//顾问ID
    public String gzNo;//公众号GzNo
    public int tId;

    public String shopPostCode;//商品提交码
    public String shopProvince;//商品所在省
    public String shopAddress;//商品所在地址
    public String shopArea;//商品所在区域
    public String shopCity;//商品所在城市

    private UserInfoNet userInfoNet;
    private UserInfo userInfo;


    public Context mContext;
    public AbPrefsUtil prefs;

    JavascriptInterfaceUtils(Context c,AbPrefsUtil prefs) {
        mContext = c;
        this.prefs=prefs;
    }

    /**
     * 返回UserToken
     * @return UserToken
     */
    @android.webkit.JavascriptInterface
    public String getUserInfo() {

        return prefs.getString("token");
    }

    /**
     * 返回用户信息
     * @return
     */
    @android.webkit.JavascriptInterface
    public String appUserInfo(){

        userInfo=new UserInfo();
        userInfo.setCounselorId(counselorId);
        userInfo.setGzNo(gzNo);
        userInfo.setIcon(icon);
        userInfo.setImToken(imToken);
        userInfo.setName(name);
        userInfo.setMobile(mobile);
        userInfo.setNickName(nickName);
        userInfo.setSex(sex);
        userInfo.setShopAddress(shopAddress);
        userInfo.setShopArea(shopArea);
        userInfo.setShopCity(shopCity);
        userInfo.setShopId(shopId);
        userInfo.setShopMobile(shopMobile);
        userInfo.setShopName(shopName);
        userInfo.setShopPostCode(shopPostCode);
        userInfo.setShopProvince(shopProvince);
        userInfo.setTicket(ticket);
        userInfo.setTypeId(typeId);
        userInfo.setTypeName(typeName);
        userInfo.setVipId(vipId);
        userInfo.settId(tId);

        return JsonUtils.toJson(userInfo);
    }
}
