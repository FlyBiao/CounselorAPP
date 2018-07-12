package com.cesaas.android.counselor.order.widget.indexbar;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by FGB .
 * Date: 16/08/28
 */

public class CityBean extends BaseIndexPinyinBean implements Serializable{

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
		
		public JSONObject getFansItem() {
			JSONObject obj = new JSONObject();
			try {
				obj.put("FansId", FANS_ID);
				obj.put("FansOpenId", FANS_OPENID);
				obj.put("FansName", FANS_NICKNAME);
				obj.put("FansIcon", FANS_ICON);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return obj;
		}
	
    private String city;//城市名字

    public CityBean() {
    }
    public CityBean(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getTarget() {
        return city;
    }
}
