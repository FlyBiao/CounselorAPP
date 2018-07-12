package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 顾问Bean
 * @author FGB
 *
 */
public class ResultGetCounselorListBean extends BaseBean{

	public ArrayList<CounselorListBean> TModel;
	
	public class CounselorListBean implements Serializable{
		
		private static final long serialVersionUID = 1L;
		public int COUNSELOR_ID;//顾问ID
		public String COUNSELOR_NAME;//顾问名称
		public int FANS_ID;//粉丝ID
		public int FANS_ISCANCEL;////是否取消关注，zero：关注，1：取消关注
		public String FANS_OPENID;//店铺ID，zero：店员，1：店长
		public int FANS_POINT;//粉丝积分
		public int FANS_SHOPID;//粉丝店铺ID
	}
}
