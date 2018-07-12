package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 佣金统计
 * @author fgb
 *
 */
public class ResultCommsionStatisticBean extends BaseBean{

	public ArrayList<CommsionStatisticBean > TModel;
	
	public class CommsionStatisticBean  implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public double Develop;//推荐佣金
		public int DevelopStatus;//推荐佣金状态
		public double Send;//发货佣金
		public int SentStatus;//发货佣金状态
	}
}
