package com.cesaas.android.counselor.order.calendar;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetOneSaleBean;
import com.cesaas.android.counselor.order.net.GetOneSaleNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.number.calendar.entity.DateInfoBean;

/**
 * 报数详情
 * @author FGB
 *
 */
public class CountOffDetailActivity extends BasesActivity{
	
	private TextView tv_curr_date,tv_curr_sale_value,tv_sale_order_count,tv_sale_month_sum
	,tv_completion_lv,tv_lastyear_sale,tv_same_period_balance,tv_guest_unit_price,tv_joint_rate;
	private ListView lv_peer_list;
	private LinearLayout count_off_detail_back;
	
	private String CountOffDate;
	
	private GetOneSaleNet getOneSaleNet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_countoff_detail_layout);
		
		initView();
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			CountOffDate=bundle.getString("CountOffDate");
			tv_curr_date.setText(CountOffDate);
		}
		
		getOneSaleNet=new GetOneSaleNet(mContext);
		getOneSaleNet.setData(CountOffDate);
	}
	
	public void onEventMainThread(ResultGetOneSaleBean msg) {
		if(msg.IsSuccess==true){
			tv_curr_sale_value.setText(msg.TModel.SaleValue+"");
			tv_sale_order_count.setText(msg.TModel.ProductCount+"/"+msg.TModel.OrderCount);
			
		}else{
			ToastFactory.getLongToast(mContext,"获取数据失败!"+ msg.Message);
		}
	}
	
	
	/**
	 * 初始化视图控件
	 */
	public void initView(){
		lv_peer_list=(ListView) findViewById(R.id.lv_peer_list);
		tv_joint_rate=(TextView) findViewById(R.id.tv_joint_rate);
		tv_guest_unit_price=(TextView) findViewById(R.id.tv_guest_unit_price);
		tv_same_period_balance=(TextView) findViewById(R.id.tv_same_period_balance);
		tv_lastyear_sale=(TextView) findViewById(R.id.tv_lastyear_sale);
		tv_completion_lv=(TextView) findViewById(R.id.tv_completion_lv);
		tv_sale_month_sum=(TextView) findViewById(R.id.tv_sale_month_sum);
		tv_sale_order_count=(TextView) findViewById(R.id.tv_sale_order_count);
		tv_curr_sale_value=(TextView) findViewById(R.id.tv_curr_sale_value);
		tv_curr_date=(TextView) findViewById(R.id.tv_curr_date);
		count_off_detail_back=(LinearLayout) findViewById(R.id.count_off_detail_back);
		
		//调用返回上一个页面方法
		mBack();
	}
	
	/**
	 * 返回上一个页面
	 */
	public void mBack(){
		count_off_detail_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
}
