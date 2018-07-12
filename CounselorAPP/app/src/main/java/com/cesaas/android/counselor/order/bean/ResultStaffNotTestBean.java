package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 店员考试列表Bean
 * @author FGB
 *
 */
public class ResultStaffNotTestBean extends BaseBean{

	public ArrayList<StaffNotTestBean> TModel;
	
	/**
	 * 考试Bean
	 * @author fgb
	 *
	 */
	public class StaffNotTestBean implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String BegDate;//店员本次考试开始时间
		public int Count;//考试次数
		public String EndDate;//店员本次考试结束时间
		public String EndTime;//截止时间   
		public int TimeLimit;//考试时长
		public int FullScore;//试卷总分
		public int IsMust;//是否必考
		public int IsRandom;
		public int Nums;//总题数
		public int LevelId;
		public String LevelName;//试卷等级
		public int PaperId;//试卷Id  
		public int PassScore;//通过分数
		public int Priority;//优先级
		public int SaleManId;
		public int Score;//本次考试得分  
		public int Status;//状态Id zero：未开始考试 1：开始考试 2：考试完成
		public String StatusName;
		public String Title;//试券标题
		public int tId;
	}
}
