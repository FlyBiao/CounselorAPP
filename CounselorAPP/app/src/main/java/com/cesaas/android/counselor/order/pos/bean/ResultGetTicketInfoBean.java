package com.cesaas.android.counselor.order.pos.bean;

import java.io.Serializable;

import com.cesaas.android.counselor.order.bean.BaseBean;

/**
 * 优惠券查询Bean
 * @author FGB
 *
 */
public class ResultGetTicketInfoBean extends BaseBean{

	public GetTicketInfoBean TModel;
	
	public class GetTicketInfoBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String Title;//优惠券标题
		private String DateActive ;//过期时间
		private String UseRule;//使用规则描述
		private String Id;//优惠券id
		private String UniqueCode;//优惠券唯一码
		private String FansId;//优惠券所属人id
		private double Money;//优惠券面额
		public String getTitle() {
			return Title;
		}
		public void setTitle(String title) {
			Title = title;
		}
		public String getDateActive() {
			return DateActive;
		}
		public void setDateActive(String dateActive) {
			DateActive = dateActive;
		}
		public String getUseRule() {
			return UseRule;
		}
		public void setUseRule(String useRule) {
			UseRule = useRule;
		}
		public String getId() {
			return Id;
		}
		public void setId(String id) {
			Id = id;
		}
		public String getUniqueCode() {
			return UniqueCode;
		}
		public void setUniqueCode(String uniqueCode) {
			UniqueCode = uniqueCode;
		}
		public String getFansId() {
			return FansId;
		}
		public void setFansId(String fansId) {
			FansId = fansId;
		}
		public double getMoney() {
			return Money;
		}
		public void setMoney(double money) {
			Money = money;
		}
		
		
		
	}
}
