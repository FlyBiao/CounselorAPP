package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetO2OOrderListBean.GetO2OOrderListBean;

/**
 * 发送订单数据适配器
 * 
 * @author fgb
 * 
 */
public class GetO2OOrderList1Adapter extends BaseAdapter {

	private Context ct;

	private List<GetO2OOrderListBean> data = new ArrayList<GetO2OOrderListBean>();

	public GetO2OOrderList1Adapter(Context ct) {
		this.ct = ct;
	}

	public GetO2OOrderList1Adapter(Context ct, List<GetO2OOrderListBean> data) {
		this.ct = ct;
		this.data = data;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<GetO2OOrderListBean> list) {
		this.data = list;
		notifyDataSetChanged();
	}

	public void remove(GetO2OOrderListBean order) {
		this.data.remove(order);
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

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_geto2o_order_list, parent, false);
			holder.tv_o2o_order_number=(TextView) convertView.findViewById(R.id.tv_o2o_order_number);
			holder.tv_o2o_order_create_date=(TextView) convertView.findViewById(R.id.tv_o2o_order_create_date);
			holder.tv_o2o_order_bonus=(TextView) convertView.findViewById(R.id.tv_o2o_order_bonus);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		GetO2OOrderListBean bean=data.get(position);
		holder.tv_o2o_order_number.setText("订单号:"+bean.TradeId);
		holder.tv_o2o_order_create_date.setText("下单时间:"+bean.CreateDate);
		holder.tv_o2o_order_bonus.setText("￥"+bean.OrderBonus);
		
		return convertView;
	}

	static class ViewHolder {
		TextView tv_o2o_order_number;
		TextView tv_o2o_order_create_date;
		TextView tv_o2o_order_bonus;
	}

}
