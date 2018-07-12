package com.cesaas.android.counselor.order.global;

import java.io.Serializable;

/**
 * App菜单权限
 * @author FGB
 *
 */
public class AppMenuPower implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//订单
	public static String APP_ORDER="APP_Order";
	//会员
	public static String APP_MENBER="APP_Member";
	//微信拉粉
	public static String APP_WX_LAFANS="APP_WeChat powder";
	//店务
	public static String APP_SHOP="APP_Shop";
	//统计数据中心
	public static String APP_DATA_CENTER="APP_datacenter";
	//报数
	public static String APP_NUMBER="APP_Number";
	//商品
	public static String APP_COMMODITY="APP_Commodity";
	//商学院
	public static String APP_COLLEGE="APP_College";
	//POS收银
	public static String APP_POS_CASHIER="APP_POSCashier";
	//个人中心
	public static String APP_PERSONAL_CENTER="APP_Personal Center";
	//设置
	public static String APP_SET_UP="APP_Set up";
	//店员管理
	public static String APP_SHOP_STAFF_MANGER="APP_Shop_staff";
	
	public int APPORDER;
    public int APPMENBER;
    public int APPWXLAFANS;
    public int APPSHOP;
    public int APPDATACENTER;
    public int APPNUMBER;
    public int APPCOMMODITY;
    public int APPCOLLEGE;
    public int APPPOS_CASHIER;
    public int APPPERSONALCENTER;
    public int APPSET_UP;
    public int APP_SHOPSTAFF_MANGER;
}
