package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 提现记录列表
 * @author fgb
 *
 */
public class DepositListBean extends BaseBean{

	public ArrayList<DepositList> TModel;
	
	public static class DepositList implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public String CreateTime ;
		public String FansName ;
		public String Income ;
		public String No ;
		public int  Status;
		
	}
}
