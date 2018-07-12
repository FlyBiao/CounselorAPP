package com.cesaas.android.counselor.order.salestalk.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.cesaas.android.counselor.order.bean.BaseBean;

/**
 * 自定义话术列表Bean
 * @author fgb
 *
 */
public class ResultCustomListBean extends BaseBean{

	public ArrayList<CustomListBean> TModel;
	
	public class CustomListBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int Id;
		public String Content;
	}
	

}
