package com.cesaas.android.counselor.order.bean;

import java.util.List;

/**
 * 门店销售报数Info Bean
 * @author fgb
 *
 */
public class ResultSalesInfoBean extends BaseBean{
	
	public TModel TModel;
	
	public class TModel{
	    public List<SaleInfo> SaleInfo ;
	    public int Target ;
	}
	
	//报数记录
	public class SaleInfo{
	    public List<Competitor> Competitor;
	    public int Id;
	    public int OrderCount;
	    public int ProductCount ;
	    public int SaleValue ;
	    public String date ;
	}
	
	//竞争对手
	public class Competitor{
	    public String Name ;
	    public int SaleValue ;
	}

}
