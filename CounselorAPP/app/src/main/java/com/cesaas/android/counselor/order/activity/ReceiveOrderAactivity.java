package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.OrderDetailAdapter;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.fragment.ReceiveOrderFragment;
import com.cesaas.android.counselor.order.net.GetDistributionOrderNet;
import com.cesaas.android.counselor.order.net.OrderDetailNet;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 接单订单详情
 * @author FGB
 *
 */
@ContentView(R.layout.order_detail_layout)
public class ReceiveOrderAactivity extends BasesActivity{

	@ViewInject(R.id.lv_order_detail)
	private ListView lv_order_detail;
	@ViewInject(R.id.iv_orderdetail_back)
	private LinearLayout back;
	
	public static BitmapUtils bitmapUtils;
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
			TradeId=bundle.getString("OrderId");
		}
		super.onCreate(savedInstanceState);
		bitmapUtils = BitmapHelp.getBitmapUtils(getApplicationContext().getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(getApplicationContext()).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);

		detailNet=new OrderDetailNet(mContext);
		detailNet.setData(TradeId);
		distributionOrderNet=new GetDistributionOrderNet(mContext);
		distributionOrderNet.setData(TradeId);
		
		initBack();
		
	}
	
	public void initData() {
		if (orderDetailList.size() >0) {
			detailAdapter = new OrderDetailAdapter(mContext,mActivity,orderDetailList,orderDetailList2);
			lv_order_detail.setAdapter(detailAdapter);
		}
	}
	public void onEventMainThread(ResultOrderDetailBean msg) {
		this.orderDetailList.addAll(msg.TModel);
		initData();
	}
	public void onEventMainThread(ResultDistributionOrderBean msg) {
		orderDetailList2.addAll(msg.TModel);
		initData();
	}
	
	/**
	 * 返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ReceiveOrderFragment.handler.obtainMessage().sendToTarget();
				
			}
		}, 100);
		
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 返回上一个页面
	 */
	public void initBack(){
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						GetUnReceiveOrderActivity.handler.obtainMessage().sendToTarget();
						
					}
				}, 100);
				
			}
		});
	}
}
