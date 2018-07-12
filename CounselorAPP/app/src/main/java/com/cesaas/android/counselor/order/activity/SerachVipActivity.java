package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;

import android.os.Bundle;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.bean.ResultGetListByShopIdBean;
import com.cesaas.android.counselor.order.bean.ResultGetListByShopIdBean.FansListByShopIdBean;
import com.cesaas.android.counselor.order.net.GetListByShopIdNet;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 会员查询页面
 * @author FGB
 *
 */
public class SerachVipActivity extends BasesActivity{

	private String fansMobile;
	private String fansId;
	public static SerachVipActivity activity;
	
	private ArrayList<FansListByShopIdBean> fansListByShopId=new ArrayList<FansListByShopIdBean>();
	private GetListByShopIdNet byShopIdNet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle=getIntent().getExtras();
		if (bundle!=null) {
			fansMobile=bundle.getString("fansMobile");
		}
		
		activity=this;
		
		byShopIdNet=new GetListByShopIdNet(mContext);
		byShopIdNet.setData(1, fansMobile);
	}
	
	public void onEventMainThread(ResultGetListByShopIdBean msg) {
		
		this.fansListByShopId.addAll(msg.TModel);
		
		for (int i = 0; i < fansListByShopId.size(); i++) {
			fansId=fansListByShopId.get(i).FANS_ID;
		}
		
		Bundle bundle=new Bundle();
		bundle.putString("fansId", fansId);
		Skip.mNextFroData(mActivity,GetShopFansDetailActivity.class, bundle);
		
	}
}
