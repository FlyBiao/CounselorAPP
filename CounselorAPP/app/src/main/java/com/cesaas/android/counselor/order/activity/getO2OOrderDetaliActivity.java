package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.OrderDetailAdapter;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.net.GetDistributionOrderNet;
import com.cesaas.android.counselor.order.net.OrderDetailNet;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.o2oorder_detail_layout)
public class getO2OOrderDetaliActivity extends BasesActivity{
	
	@ViewInject(R.id.lv_o2oorder_detail)
	private ListView lv_o2oorder_detail;
	
private String TradeId;
	
	private OrderDetailNet detailNet;
	private GetDistributionOrderNet distributionOrderNet;
	
	private OrderDetailAdapter detailAdapter;
	private ArrayList<OrderDetailBean> orderDetailList= new ArrayList<OrderDetailBean>();
	private ArrayList<DistributionOrderBean> orderDetailList2= new ArrayList<DistributionOrderBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			TradeId=bundle.getString("orderId");
		}
		super.onCreate(savedInstanceState);
		
		detailNet=new OrderDetailNet(mContext);
		detailNet.setData(TradeId);
		distributionOrderNet=new GetDistributionOrderNet(mContext);
		distributionOrderNet.setData(TradeId);
		
//		initBack();
	}
	
	public void initData() {
		if (orderDetailList.size() >0) {
			detailAdapter = new OrderDetailAdapter(mContext,mActivity,orderDetailList,orderDetailList2);
			lv_o2oorder_detail.setAdapter(detailAdapter);
		}
	}
	public void onEventMainThread(ResultOrderDetailBean msg) {
		this.orderDetailList.addAll(msg.TModel);
//		detailAdapter.notifyDataSetChanged();
		initData();
	}
	public void onEventMainThread(ResultDistributionOrderBean msg) {
		orderDetailList2.addAll(msg.TModel);
//		detailAdapter.notifyDataSetChanged();
		initData();
	}
	
	/**
	 * 返回上一个页面
	 */
//	public void initBack(){
//		//
//		iv_backDetail.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Skip.mBack(mActivity);
//			}
//		});
//	}

}
