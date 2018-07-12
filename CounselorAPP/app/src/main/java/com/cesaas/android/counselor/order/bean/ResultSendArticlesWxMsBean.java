package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

public class ResultSendArticlesWxMsBean extends BaseBean{

	public SendTextWxMsBean TModel;
	
	public class SendTextWxMsBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int Total;//总消息条数
		public int ErrorCount;//错误数量
		public int SuccessCount;//成功数量
		public int Id;//消息编号
		
	}

}
