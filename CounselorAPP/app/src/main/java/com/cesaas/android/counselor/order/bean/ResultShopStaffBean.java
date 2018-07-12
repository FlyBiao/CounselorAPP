package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 店员listBean
 * @author fgb
 *
 */
public class ResultShopStaffBean extends BaseBean{
	
	public ArrayList<ShopStaffBean> TModel;

   public class ShopStaffBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int COUNSELOR_ID;//
	public int COUNSELOR_INUSE; //
	public int COUNSELOR_TYPE;//店员等级：0店员，1：店长
	public int SHOP_ID;//店铺ID
	public String COUNSELOR_ICON;//头像
	public String COUNSELOR_MOBILE;//店员手机号
	public String COUNSELOR_NAME;//店员昵称
	public String COUNSELOR_NICKNAME;//店员昵称
	public String COUNSELOR_SEX; //店员性别：0男，1女
	public String FANS_OPENID;//penid
	public String SHOP_NAME;//店铺名称
	public int FANS_NUM;//粉丝数量
	
} 
    	 
}
