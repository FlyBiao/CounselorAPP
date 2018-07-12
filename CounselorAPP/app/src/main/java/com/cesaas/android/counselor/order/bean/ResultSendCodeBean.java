package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * 获取验证码结果bean
 * @author FGB
 *
 */
public class ResultSendCodeBean extends BaseBean{

	public SendCodeBean TModel;
	
	public class SendCodeBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String mobile;
	}
}
