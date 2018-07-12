package com.cesaas.android.counselor.order.bean;

import java.util.ArrayList;

public class GetListBean extends BaseBean{

	public ArrayList<GetList> TModel;
	
	public class GetList{
		
		public String AppId;
        public String  AppSecret;
        public String AppToken;
        public String ExpressId;
        public String CustCard;
		public String Id;
		public String Name;
		public String Type;
		public String ExpressCode;
		
	}
}
