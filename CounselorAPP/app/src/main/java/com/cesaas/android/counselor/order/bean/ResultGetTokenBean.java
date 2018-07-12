package com.cesaas.android.counselor.order.bean;

/**
 * 获取融云ToKen
 * @author FGB
 *
 */
public class ResultGetTokenBean extends BaseBean{

	public GetTokenBean TModel;
	
	public class GetTokenBean extends BaseBean{
		public String appkey;
		public String code;//
		public String token;//融云ToKen
		public int userId;//
	}
}
