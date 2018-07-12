package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultCounselorLevelBean extends BaseBean{

	public ArrayList<CounselorLevelBean> TModel;
	
	public class CounselorLevelBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
    	private int Id;//级别Id
    	private String Title;//级别名称
    	
		public int getId() {
			return Id;
		}
		public void setId(int id) {
			Id = id;
		}
		public String getTitle() {
			return Title;
		}
		public void setTitle(String title) {
			Title = title;
		}
    	
		
	}
}
