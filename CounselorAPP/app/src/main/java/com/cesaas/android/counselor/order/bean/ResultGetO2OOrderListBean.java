package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultGetO2OOrderListBean extends BaseBean{

	public ArrayList<GetO2OOrderListBean> TModel;
	
	//订单对象
	public class GetO2OOrderListBean implements Serializable{
		
		public int OrderStatus;//分销订单状态
		public String OrderRemark;//订单备注
		public int DisOrderStatus;//订单状态
		public String OrderDistId;
		public String VipId;
		public int ExpressType;//zero:快递  1：到店
		public int CounselorId;
		public String CounselorName;
		public int BonusStatus;
		public int BonusType;
		public int OrderType;
		public double OrderBonus;
		public String ReserveDate;
		public String CreateDate;//下单时间
		public String TradeId;//订单id
		
		public ArrayList<OrderItemBean> OrderItem;
	}
	
	//订单Item数据对象
	public class OrderItemBean implements Serializable{
		private static final long serialVersionUID = 1L;
		public String Attr;//商品规格
		public String ImageUrl;//图片url
		public String OId;//
		public String OrderId;//订单id
		public int OrderStatus;//订单状态
		public double Price;//商品价格
		public String Quantity;//数量
		public String ShopStyleId;//商品类型ID
		public String Title;//商品名称
		public String BarcodeCode;//条码代码
		
	}
	
	
}
