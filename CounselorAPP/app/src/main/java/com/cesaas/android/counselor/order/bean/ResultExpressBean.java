package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 快递公司
 * @author fgb
 *
 */
public class ResultExpressBean extends BaseBean{

	public ArrayList<ExpressBean> TModel;
	
	public static class ExpressBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public String Id;
		public String Name;
//		public int Type;
		
	}
}
