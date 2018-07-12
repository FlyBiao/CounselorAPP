package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cesaas.android.counselor.order.bean.ResultSalesInfoBean.TModel;

/**
 * 获取当天报数Bean
 * @author fgb
 *
 */
public class ResultGetOneSaleBean extends BaseBean{
	
	public TModel TModel;

	public class TModel implements Serializable
	{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int OrderCount;
	    public int ProductCount;
	    public int SaleValue;
	    public int IsYesterDay ;
	    public List<Competitor> Competitor;
	}
	
	public class Competitor implements Serializable
	{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String Name;
	    public int SaleValue;
	}
}
