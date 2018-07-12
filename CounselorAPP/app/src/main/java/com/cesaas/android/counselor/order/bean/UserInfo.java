package com.cesaas.android.counselor.order.bean;

/**
 * 用户详情信息
 * Created by FGB on 2016/3/10.
 */
public class UserInfo extends BaseBean{
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
    
	public int gettId() {
		return tId;
	}
	public void settId(int tId) {
		this.tId = tId;
	}
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
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
		return vipId;
	}
	public void setVipId(String vipId) {
		this.vipId = vipId;
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
