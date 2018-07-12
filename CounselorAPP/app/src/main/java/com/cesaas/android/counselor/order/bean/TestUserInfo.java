package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * 用户详情信息
 * Created by FGB on 2016/3/10.
 */
public class TestUserInfo implements Serializable{
    private String mobile;//手机号
	private String icon;//用户头像
	private String name;//用户名称
	private String NickName;//用户昵称
	private String sex;//性别
	private String shopId;//店铺ID
	private String shopName;//店铺名称
	private String shopMobile;//店铺电话
	private String typeName;//用户身份：店员，店长
	private String typeId;//1：店长，2：店员
	private String VipId;
	private String ticket;//生成拉粉二维码用票
	private String imToken;
	private String counselorId;//顾问ID
	private String gzNo;//公众号GzNo
	private int tId;

	private String shopPostCode;//商品提交码
	private String shopProvince;//商品所在省
	private String shopAddress;//商品所在地址
	private String shopArea;//商品所在区域
	private String shopCity;//商品所在城市

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopMobile() {
		return shopMobile;
	}

	public void setShopMobile(String shopMobile) {
		this.shopMobile = shopMobile;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getVipId() {
		return VipId;
	}

	public void setVipId(String vipId) {
		VipId = vipId;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getImToken() {
		return imToken;
	}

	public void setImToken(String imToken) {
		this.imToken = imToken;
	}

	public String getCounselorId() {
		return counselorId;
	}

	public void setCounselorId(String counselorId) {
		this.counselorId = counselorId;
	}

	public String getGzNo() {
		return gzNo;
	}

	public void setGzNo(String gzNo) {
		this.gzNo = gzNo;
	}

	public int gettId() {
		return tId;
	}

	public void settId(int tId) {
		this.tId = tId;
	}

	public String getShopPostCode() {
		return shopPostCode;
	}

	public void setShopPostCode(String shopPostCode) {
		this.shopPostCode = shopPostCode;
	}

	public String getShopProvince() {
		return shopProvince;
	}

	public void setShopProvince(String shopProvince) {
		this.shopProvince = shopProvince;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopArea() {
		return shopArea;
	}

	public void setShopArea(String shopArea) {
		this.shopArea = shopArea;
	}

	public String getShopCity() {
		return shopCity;
	}

	public void setShopCity(String shopCity) {
		this.shopCity = shopCity;
	}
}
