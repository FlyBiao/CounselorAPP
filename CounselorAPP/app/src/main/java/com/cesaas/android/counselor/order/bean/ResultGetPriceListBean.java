package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 获取推荐商品Price
 * @author FGB
 *
 */
public class ResultGetPriceListBean extends BaseBean{

	public ArrayList<GetPriceListBean>  TModel;
	
	public class GetPriceListBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String ShopStyleId;//商品ID
		public int IsVip;//是否支持VIP折扣【zero：不支持，1：支持】
		public double ListPrice;//吊牌价格
		public double Price;//销售价
		public int Points;//积分
		public int Type;//1：积分类型，1：货币购买商品
		public double VipDisc;//VIP折扣  10不打折
		public double VipPrice;//VIP价格，小于等于0表示没有VIP价格
		
	}
}
