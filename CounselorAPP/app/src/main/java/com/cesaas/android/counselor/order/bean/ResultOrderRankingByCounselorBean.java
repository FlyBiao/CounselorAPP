package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * 销售统计
 * @author FGB
 *
 */
public class ResultOrderRankingByCounselorBean extends BaseBean{

	public OrderRankingByCounselorBean TModel;
	
	public class OrderRankingByCounselorBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public int OrderCountMonth;//本月订单数
		public int OrderCountToday;//今日订单数
		public double OrderSumsMonth;//本月成交额
		public double OrderSumsToday;//今日成交额
		
	}
}
