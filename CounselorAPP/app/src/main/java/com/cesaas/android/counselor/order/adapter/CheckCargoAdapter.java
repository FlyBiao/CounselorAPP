package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderItemBean;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class CheckCargoAdapter extends BaseAdapter{

	public static BitmapUtils bitmapUtils;
	private Context ct;
	private Activity activity;
	private ListView lv;
	private List<OrderDetailBean> data=new ArrayList<OrderDetailBean>();
	private List<OrderItemBean> list = new ArrayList<OrderItemBean>();
	
	public CheckCargoAdapter(Context ct,Activity activity,List<OrderDetailBean> data){
		this.ct = ct;
		this.activity=activity;
		this.data = data;
		bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<OrderDetailBean> list) {
		this.data = list;
	}
	
	public void remove(OrderDetailBean user) {
		this.data.remove(user);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = LayoutInflater.from(ct).inflate(R.layout.check_cargo_list, null);
			lv=(ListView) convertView.findViewById(R.id.list_check_cargo);
			
			if(data.size()!=0){
				OrderDetailBean detail=data.get(position);
				list = new ArrayList<OrderItemBean>();
				
				for(int i=0;i<detail.OrderItem.size();i++){
					
					OrderItemBean itemBean=new ResultOrderDetailBean().new OrderItemBean();
					itemBean=detail.OrderItem.get(i);
					
					list.add(itemBean);
				}
				
				CheckCargoThingsAdapter adapter=new CheckCargoThingsAdapter(activity, list);
				int totalHeight = 0;
				for (int i = 0; i < adapter.getCount(); i++) {
					View listItem = adapter.getView(i, null, lv);
					listItem.measure(0, 0);
					totalHeight += listItem.getMeasuredHeight();
				}
		
				ViewGroup.LayoutParams params = lv.getLayoutParams();
				params.height = totalHeight + (lv.getDividerHeight() * (adapter.getCount() - 1));
				((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
				lv.setLayoutParams(params);
				lv.setAdapter(adapter);
			}
		}
		
		return convertView;
	}

}
