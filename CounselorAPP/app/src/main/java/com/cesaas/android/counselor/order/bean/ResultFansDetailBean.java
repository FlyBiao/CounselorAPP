package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.cesaas.android.counselor.order.bean.ResultLabelListBean.labelListBean;

/**
 * 粉丝详情Bean
 * @author FGB
 *
 */
public class ResultFansDetailBean extends BaseBean{

	public FansDetailBean TModel;
	
	public class FansDetailBean implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String FANS_ID;//粉丝ID
		public String FANS_NAME;//粉丝姓名
		public String FANS_NICKNAME;//粉丝昵称
		public String FANS_SEX;//性别
		public String FANS_ICON;//头像
		public String FANS_MOBILE;//电话
		public String FANS_BIRTHDAY;//粉丝生日
		public String FANS_POINT;//积分
		public String FANS_OPENID;//微信粉丝ID
		public String FANS_SHOPID;//店铺ID，zero：店员，1：店长
		public String FANS_ISCANCEL;//是否取消关注，zero：关注，1：取消关注
		public String FANS_LASTMSG;//最后一条信息，聊天显示
		public String FANS_REMARK;//粉丝备注
		public int COUNSELOR_ID;//顾问id
		public String COUNSELOR_NAME;//所属顾问
		public String FANS_GRADE;//会员等级
		
		public ArrayList<TagBean> TAGS;
	}
	
	public class TagBean implements Serializable{
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
