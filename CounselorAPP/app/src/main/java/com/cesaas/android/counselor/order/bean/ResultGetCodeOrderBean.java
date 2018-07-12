package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultGetCodeOrderBean extends BaseBean{

public ArrayList<GetCodeOrderBean> TModel;
	
	//订单详情对象
	public class GetCodeOrderBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public String Address;//收货地址
		public String City;//城市
		public String ConsigneeName;//收货人
		public String CreateDate;//订单创建时间
		public String District;//区
		public int ExpressType;//取货方式：0快递，1到店自提
		public String Mobile;//手机
		public String NickName;//昵称
		public String OrderId;//订单号
		public int OrderStatus;//订单状态
		public double PayPrice;//支付价格
		public double TotalPrice;//总价格
		public String ReserveDate;//取货时间
		public String Province;//省
		public String OrderRemark;//备注
		
	}
}
