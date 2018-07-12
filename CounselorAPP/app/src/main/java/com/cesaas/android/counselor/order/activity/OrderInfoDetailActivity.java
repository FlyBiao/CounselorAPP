package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.net.GetDistributionOrderNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 订单详情信息页面
 * @author FGB
 *
 */
@ContentView(R.layout.activity_order_detail_info)
public class OrderInfoDetailActivity extends BasesActivity{

	@ViewInject(R.id.lv_order_detail_info)
	private ListView lv_order_detail_info;
	@ViewInject(R.id.iv_order_info_back)
	private LinearLayout iv_order_info_back;
	
	private String oId;
	private GetDistributionOrderNet distributionOrderNet;
	private ArrayList<DistributionOrderBean> orderDetailList= new ArrayList<DistributionOrderBean>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			oId=bundle.getString("oId");
		}
		super.onCreate(savedInstanceState);
		
		distributionOrderNet=new GetDistributionOrderNet(mContext);
		distributionOrderNet.setData(oId);
		
		initBack();
		
	}

	private void initAdapterAndSetData() {
		
		lv_order_detail_info.setAdapter(new CommonAdapter<DistributionOrderBean>(mContext, R.layout.item_order_detail_info, orderDetailList) {

			@Override
			public void convert(ViewHolder holder, DistributionOrderBean t,int postion) {
				for (int i = 0; i < t.OrderItem.size(); i++) {
					holder.setImageBitmapUtils(R.id.iv_order_info_img, bitmapUtils, t.OrderItem.get(i).ImageUrl);
					holder.setText(R.id.tv_order_info_title, t.OrderItem.get(i).Title);
					holder.setText(R.id.tv_info_stylecode, t.OrderItem.get(i).StyleCode);
					holder.setText(R.id.tv_order_info_code, t.OrderItem.get(i).BarcodeCode);
					holder.setText(R.id.tv_order_info_price, ""+t.OrderItem.get(i).Price);
					holder.setText(R.id.tv_order_info_attr, t.OrderItem.get(i).Attr);
				}
				
				
				
			}
		});
	}
	
	public void onEventMainThread(ResultDistributionOrderBean msg) {
		this.orderDetailList.addAll(msg.TModel);
		initAdapterAndSetData();
	}
	
	public void initBack(){
		iv_order_info_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
}
