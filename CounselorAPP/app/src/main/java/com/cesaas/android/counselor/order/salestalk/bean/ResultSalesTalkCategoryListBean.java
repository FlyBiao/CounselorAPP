package com.cesaas.android.counselor.order.salestalk.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cesaas.android.counselor.order.bean.BaseBean;

/**
 * 获取分类下的话术列表
 * @author FGB
 *
 */
public class ResultSalesTalkCategoryListBean extends BaseBean{

	public List<SalesTalkCategoryListBean> TModel;
	public class SalesTalkCategoryListBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String Content;
		public String Question;
		public  int Id;
	}
}
