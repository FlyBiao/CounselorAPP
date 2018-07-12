package com.cesaas.android.counselor.order.label.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.cesaas.android.counselor.order.bean.BaseBean;

/**
 * 获取标签列表Bean
 * @author FGB
 *
 */
public class ResultGetTagListBean extends BaseBean{

	public ArrayList<GetTagListBean> TModel;
	
	public class GetTagListBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int TagId;
		public String TagName;
		public int ControllerType;//标签分类类型
		public int CategoryId;//父类ID
		public int Type;
		
	}
}
