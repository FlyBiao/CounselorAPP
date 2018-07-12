package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * 用户详情信息
 * Created by FGB on 2016/3/10.
 */
public class UserBean implements Serializable{
    public String Mobile;//手机号
    public String Icon;//用户头像
    public String Name;//用户名称
    public String NickName;//用户昵称
    public String Sex;//性别
    public String ShopId;//店铺ID
    public String ShopName;//店铺名称
    public String Siganature;//签名
    public String ShopMobile;//店铺电话
    public String TypeName;//用户身份：店员，店长
    public String TypeId;//1：店长，2：店员
    public int VipId;
    public String Ticket;//生成拉粉二维码用票
    public String ImToken;
    public String CounselorId;//顾问ID
    public String GzNo;//公众号GzNo
    public int tId;
    public String OrganizationId;//机构id
    
    public String ShopPostCode;//商品提交码
    public String ShopProvince;//商品所在省
    public String ShopAddress;//商品所在地址
    public String ShopArea;//商品所在区域
    public String ShopCity;//商品所在城市


    public String BrandName;
    public int BrandId;
    
}
