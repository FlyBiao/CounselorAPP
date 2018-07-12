package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.GetO2OOrderList1Adapter;
import com.cesaas.android.counselor.order.bean.ResultGetO2OOrderListBean;
import com.cesaas.android.counselor.order.bean.ResultGetO2OOrderListBean.GetO2OOrderListBean;
import com.cesaas.android.counselor.order.net.GetO2OOrderList1Net;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 发送订单列表页面
 * @author fgb
 *
 */
@ContentView(R.layout.activity_geto2o_order_list_layout)
public class GetO2OOrderListActivity extends BasesActivity{

	@ViewInject(R.id.lv_geto2o_order_list)
	private ListView lv_geto2o_order_list;
	@ViewInject(R.id.tv_geto2o_order_no_data)
	private TextView tv_geto2o_order_no_data;
	
	private GetO2OOrderList1Net orderList1Net;
	private GetO2OOrderList1Adapter adapter;
	private ArrayList<GetO2OOrderListBean> o2oOrderList= new ArrayList<GetO2OOrderListBean>();
	public double PayPrice=0;
	public int RESULT_CODE=101;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		orderList1Net=new GetO2OOrderList1Net(mContext);
		orderList1Net.setData();
		
		initOnItemClick();
	}
	
	public void initData() {
		if (o2oOrderList.size() >0) {
			adapter = new GetO2OOrderList1Adapter(mContext,o2oOrderList);
			lv_geto2o_order_list.setAdapter(adapter);
		}else{
			tv_geto2o_order_no_data.setVisibility(View.VISIBLE);
		}
	}
	public void onEventMainThread(ResultGetO2OOrderListBean msg) {
		this.o2oOrderList.addAll(msg.TModel);
		initData();
	}

	private void initOnItemClick() {
		
		lv_geto2o_order_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent=new Intent();
		        intent.putExtra("OrderStatus", o2oOrderList.get(position).OrderStatus);
		        intent.putExtra("ExpressType", o2oOrderList.get(position).ExpressType);
		        intent.putExtra("CreateDate", o2oOrderList.get(position).CreateDate);
		        intent.putExtra("TradeId", o2oOrderList.get(position).TradeId);
		        
		        setResult(RESULT_CODE, intent);
		        finish();
			}
		});
	}
}
