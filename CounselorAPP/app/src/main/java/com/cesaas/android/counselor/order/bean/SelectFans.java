package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * Created by FGB on 2016/3/14.
 */
public class SelectFans implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String FANS_ID;//粉丝ID
	public String FANS_NAME;//粉丝姓名
	public String FANS_NICKNAME;//粉丝昵称
	public String FANS_SEX;//性别
	public String FANS_ICON;//头像
	public String FANS_MOBILE;//电话
	public String FANS_BIRTHDAY;//粉丝生日
	public String FANS_POINT;//积分
	public String FANS_OPENID;//微信粉丝ID
	public String FANS_SHOPID;//店铺ID，zero：店员，1：店长
	public String FANS_ISCANCEL;//是否取消关注，zero：关注，1：取消关注
	public String FANS_LASTMSG;//最后一条信息，聊天显示
	public String FANS_REMARK;//粉丝备注
	public String FANS_GRADE;//等级
	public int position;
	
	
}
