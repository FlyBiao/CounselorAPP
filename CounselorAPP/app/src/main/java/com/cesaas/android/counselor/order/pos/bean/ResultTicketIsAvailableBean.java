package com.cesaas.android.counselor.order.pos.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

/**
 * 验证优惠券是否可以使用
 * @author FGB
 *
 */
public class ResultTicketIsAvailableBean extends BaseBean{

	public TicketIsAvailableBean TModel;
	
	public class TicketIsAvailableBean{
		private boolean isUse;//是否可以使用:默认true

		public boolean isUse() {
			return isUse;
		}

		public void setUse(boolean isUse) {
			this.isUse = isUse;
		}
		
	}
}
