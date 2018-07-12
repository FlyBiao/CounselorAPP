package com.cesaas.android.counselor.order.task.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.cesaas.android.counselor.order.bean.BaseBean;

/**
 * 共有任务Bean
 * @author fgb
 *
 */
public class ResultPublicTaskBean extends BaseBean{

	public ArrayList<PublicTaskBean> TModel;
	
	public class PublicTaskBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int CategoryId;//任务类别Id
		public String CreateTime;//任务创建时间
		public int Id;//任务ID
		public int IsUsed;//使用任务
		public int Leve;//任务优先级
		public int Pattern;//完成方式。0自主完成，1协同完成
		public int Status;//任务状态（0默认；1完成；2过期）
		public int TaskId;//任务ID
		public String Title;//任务标题
		public int Type;//任务类型
	}
}
