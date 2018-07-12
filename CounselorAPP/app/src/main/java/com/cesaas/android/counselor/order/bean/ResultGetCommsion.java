package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 我的收益佣金Bean
 * @author FGB
 *
 */
public class ResultGetCommsion extends BaseBean{

	public ArrayList<GetCommsionBean> TModel;
	
	public class GetCommsionBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int BonusStatus;//佣金结算状态：zero:暂时冻结1:可以结算2:已经收3:不能结算
		public int BounsType;//佣金类型:zero 推荐 1 发货。
		public int CounselorId;//顾问id
		public String CounselorName;//顾问名称
		public String CreateDate;//下单时间
		public int DisOrderStatus;//
		public int ExpressType;//发货方式 0快递，1到店自提。
		public double OrderBonus;////订单佣金
		public int OrderDistId;
		public int OrderStatus;//订单状态
		public String ReserveDate;//取货时间
		public String TradeId;//订单ID
		public String VipId;//
		@Override
		public String toString() {
			return "GetCommsionBean [BonusStatus=" + BonusStatus
					+ ", BounsType=" + BounsType + ", CounselorId="
					+ CounselorId + ", CounselorName=" + CounselorName
					+ ", CreateDate=" + CreateDate + ", DisOrderStatus="
					+ DisOrderStatus + ", ExpressType=" + ExpressType
					+ ", OrderBonus=" + OrderBonus + ", OrderDistId="
					+ OrderDistId + ", OrderStatus=" + OrderStatus
					+ ", ReserveDate=" + ReserveDate + ", TradeId=" + TradeId
					+ ", VipId=" + VipId + "]";
		}
		
		
		
		
		
		
		
		
		
	}
}
