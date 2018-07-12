package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapters.OrderShopDetailAdapter;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.net.GetDistributionOrderNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 商品详情页面
 * @author FGB
 *
 */
@ContentView(R.layout.activity_order_shop_detail)
public class OrderShopDetailActivity extends BasesActivity{

	@ViewInject(R.id.lv_order_shop_detail)
	private ListView lv_order_shop_detail;
	@ViewInject(R.id.iv_order_shop_back)
	private LinearLayout iv_order_shop_back;
	
	private OrderShopDetailAdapter adapter;
	private GetDistributionOrderNet distributionOrderNet;
	private ArrayList<DistributionOrderBean> orderDetailList= new ArrayList<DistributionOrderBean>();
	
	private String oId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			oId=bundle.getString("oId");
		}
		super.onCreate(savedInstanceState);
		
		distributionOrderNet=new GetDistributionOrderNet(mContext);
		distributionOrderNet.setData(oId);
		
		initBack();
	}
	
	/**
	 * 初始化数据
	 */
	public void initData() {
		if (orderDetailList.size() >0) {
			adapter = new OrderShopDetailAdapter(mContext, mActivity, orderDetailList);
			lv_order_shop_detail.setAdapter(adapter);
		}
	}
	
	public void onEventMainThread(ResultDistributionOrderBean msg) {
		this.orderDetailList.addAll(msg.TModel);
		initData();
	}
	
	public void initBack(){
		iv_order_shop_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
}
