package com.cesaas.android.counselor.order.label.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.cesaas.android.counselor.order.bean.BaseBean;

public class ResultGetAllTagListBean extends BaseBean{

	
	public ArrayList<GetAllTagListBean> TModel;
	
	public class GetAllTagListBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int CategoryId;//标签分类ID
		public int ControllerType;//0单选，1多选
		public int TagId;//标签ID
		public String TagName;//标签名
		
	}
}
