package com.cesaas.android.counselor.order.bean;

/**
 * 检查粉丝是否在线
 * @author fgb
 *
 */
public class ResultCheckOnlineBean extends BaseBean{

	public CheckOnlineBean TModel;
	
	public class CheckOnlineBean{
		
		public String code;
		public int status;//状态 zero:离线 1：在线
	}
}
