package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * 查询物流订单Bean
 * @author fgb
 *
 */
public class ResultOrderQueryBean extends BaseBean{

	public OrderQuery TModel;
	
	public class OrderQuery implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		//目的地代码
		private String DestCode;
		
		//筛单结果： 1-人工确认，2-可收派 3-不可以收派 ,zero-筛单初始化中
		//（调用快速下单后约 5 分钟会返回顺丰运单号，如查询返回 zero ,请稍候重试）
		private String FilterResult;
		
		//顺丰运单号，一个订单只能有一个主单号，如果是子母单的情况，
		//请以半角逗号分隔，主单号在第一个位置，如“755123456789,001123456789,002123456789”
		private String MailNo;
		
		//客户运单号
		private String OrderId;
		
		//原寄地代码
		private String OriginCode;
		
		//备注：1-收方超范围， 2-派方超范围， 3-其他原因
		private String Remark;

		public String getDestCode() {
			return DestCode;
		}

		public String getFilterResult() {
			return FilterResult;
		}

		public String getMailNo() {
			return MailNo;
		}

		public String getOrderId() {
			return OrderId;
		}

		public String getOriginCode() {
			return OriginCode;
		}

		public String getRemark() {
			return Remark;
		}
		
		
		
	}
}
