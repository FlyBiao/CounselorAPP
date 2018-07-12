package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 话术
 * @author fgb
 *
 */
public class ResultSalesTalkBean extends BaseBean{

	public ArrayList<SalesTalkBean> TModel;
	
	public class SalesTalkBean implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String Content;//话术内容
	}
}
