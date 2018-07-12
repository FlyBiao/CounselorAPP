package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 订单详情
 * 
 * @author FGB
 * 
 */
public class ResultDistributionOrderBean extends BaseBean {

	public ArrayList<DistributionOrderBean> TModel;

	// 订单对象
	public class DistributionOrderBean implements Serializable {
		private static final long serialVersionUID = 1L;

		// //
		public int BonusStatus;// 佣金结算状态：zero:暂时冻结1:可以结算2:已经收3:不能结算
		public int BonusType;// 佣金类型:zero 推荐 1 发货。
		public int CounselorId;// 顾问ID
		public int DisOrderStatus;// 订单状态
		public int ExpressType;// 发货方式 0快递，1到店自提。
		public String CreateDate;// 下单时间
		public double OrderBonus;// 订单佣金
		public String OrderDistId;
		public int OrderStatus;// 订单状态：
		public String ReserveDate;// 取货时间
		public String TradeId;// 订单id
		public String VipId;
		public String NickName;//下单用户昵称
		public String Mobile;//下单用户手机号

		public ArrayList<DistributionOrderItemBean> OrderItem;
	}

	// 订单Item数据对象
	public class DistributionOrderItemBean implements Serializable {
		private static final long serialVersionUID = 1L;
		public String Attr;// 商品规格
		public String ImageUrl;// 图片url
		public String OId;//
		public String OrderId;// 订单id
		public int OrderStatus;// 订单状态
		public double Price;// 商品价格
		public String Quantity;// 数量
		public String ShopStyleId;// 商品类型ID
		public String Title;// 商品名称
		public String StyleCode;//款号
		public String BarcodeCode;//条码

	}
}
