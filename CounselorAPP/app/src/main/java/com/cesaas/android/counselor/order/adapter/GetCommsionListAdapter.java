package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetCommsion.GetCommsionBean;

/**
 * 佣金数据适配器
 * @author fgb
 *
 */
public class GetCommsionListAdapter extends BaseAdapter{
	
	public static final String TAG="GetCommsionAdapter";

	private Context ct;
	private List<GetCommsionBean> data=new ArrayList<GetCommsionBean>();
	
	public GetCommsionListAdapter(Context ct,List<GetCommsionBean> data){
		this.ct = ct;
		this.data = data;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<GetCommsionBean> list) {
		this.data = list;
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
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_earnings_view_detail, null);
			viewHolder.tv_order_numbers=(TextView) convertView.findViewById(R.id.tv_order_numbers);
			viewHolder.tv_e_moneys=(TextView) convertView.findViewById(R.id.tv_e_moneys);
			viewHolder.tv_order_dates=(TextView) convertView.findViewById(R.id.tv_order_dates);
			viewHolder.tv_e_status=(TextView) convertView.findViewById(R.id.tv_e_status);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		GetCommsionBean commsionBean=data.get(position);
		
		viewHolder.tv_order_numbers.setText(commsionBean.TradeId);
		viewHolder.tv_e_moneys.setText("￥"+commsionBean.OrderBonus);
		viewHolder.tv_order_dates.setText(""+commsionBean.CreateDate);
		
		if(commsionBean.BonusStatus==0){
			viewHolder.tv_e_status.setText("暂时冻结");
			
		}else if(commsionBean.BonusStatus==1){
			viewHolder.tv_e_status.setText("待结算");
			
		}else if(commsionBean.BonusStatus==2){
			viewHolder.tv_e_status.setText("已收到");
			
		}else if(commsionBean.BonusStatus==3){
			viewHolder.tv_e_status.setText("不能结算");
			
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView tv_order_numbers;//订单号
		TextView tv_e_moneys;//佣金金额
		TextView tv_order_dates;//时间
		TextView tv_e_status;//佣金状态
		
	}

}
