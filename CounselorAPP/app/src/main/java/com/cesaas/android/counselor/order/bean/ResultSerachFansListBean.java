package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.cesaas.android.counselor.order.bean.ResultGetListByShopIdBean.FansListByShopIdBean;

/**
 * 按粉丝查询list Bean
 * @author FGB
 *
 */
public class ResultSerachFansListBean extends BaseBean{

public ArrayList<SerachFansListBean> TModel;
	
	public class SerachFansListBean implements Serializable{

		private static final long serialVersionUID = 1L;
		public int COUNSELOR_ID;//顾问ID
		public String COUNSELOR_NAME;//顾问姓名
		public String FANS_ICON;//粉丝头像
		public String FANS_ID;//粉丝ID
		public String FANS_GRADE;//会员等级
		public int FANS_ISCANCEL;//是否取消关注，zero：关注，1：取消关注
		public String FANS_MOBILE;//粉丝手机
		public String FANS_NICKNAME;//粉丝昵称
		public String FANS_OPENID;////微信粉丝ID
		public String FANS_POINT;//粉丝积分
		public String FANS_REMARK;//粉丝备注
		public String FANS_SEX;//粉丝性别
		public String FANS_SHOPID;////店铺ID，zero：店员，1：店长
	}
}
