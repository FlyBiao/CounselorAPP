package com.cesaas.android.counselor.order.label.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.cesaas.android.counselor.order.bean.BaseBean;

/**
 * 获取标签分类列表
 * @author fgb
 *
 */
public class ResultGetCategoryListBean extends BaseBean{

	
	public ArrayList<GetCategoryListBean> TModel;
	
	public  class GetCategoryListBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int CategoryId;//标签分类编号
		public String CategoryName;//标签分类名称
		public int ControllerType;//标签分类类型
		public int ParentId;//标签分类父级编号
		
		public ArrayList<GetCategoryListBean> arraySub;
		
	}
}
