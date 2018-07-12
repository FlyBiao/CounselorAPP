package com.cesaas.android.counselor.order.earnings.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.OrderEarningsDetailAdapter;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.net.GetDistributionOrderNet;
import com.cesaas.android.counselor.order.net.OrderDetailNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 订单佣金详情页面
 * @author fgb
 *
 */
@ContentView(R.layout.order_earnings_detail_layout)
public class OrderEarningsDetailActivity extends BasesActivity{
	
	@ViewInject(R.id.lv_order_earnings_detail)
	private ListView lv_order_earnings_detail;
	@ViewInject(R.id.iv_backcommsion_list)
	private ImageView iv_backcommsion_list;
	
	private String TradeId;
	
	private OrderDetailNet detailNet;
	private GetDistributionOrderNet distributionOrderNet;
	
	private OrderEarningsDetailAdapter earningsDetailAdapter;
	private ArrayList<OrderDetailBean> orderDetailList= new ArrayList<OrderDetailBean>();
	private ArrayList<DistributionOrderBean> orderDetailList2= new ArrayList<DistributionOrderBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			TradeId=bundle.getString("TradeId");
		}
		super.onCreate(savedInstanceState);
		detailNet=new OrderDetailNet(mContext);
		detailNet.setData(TradeId);
		distributionOrderNet=new GetDistributionOrderNet(mContext);
		distributionOrderNet.setData(TradeId);
		
		onClickBack();
		
	}

	
	/**
	 * 初始化数据
	 */
	public void initData(){
		if (orderDetailList.size() >0) {
			earningsDetailAdapter = new OrderEarningsDetailAdapter(mContext,mActivity,orderDetailList,orderDetailList2);
			lv_order_earnings_detail.setAdapter(earningsDetailAdapter);
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
	private void onClickBack() {
		iv_backcommsion_list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});		
	}
}
