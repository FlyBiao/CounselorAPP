package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 标签列表Bean
 * @author FGB
 *
 */
public class ResultLabelListBean extends BaseBean{
	
	public ArrayList<labelListBean> TModel;

	public class labelListBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String BColor;
        public int Id;//标签ID
        public String Name;//标签名称
        public int IsSelected;//是否已选标签：zero：未选，1：已选
	}
}
