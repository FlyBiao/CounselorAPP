package com.cesaas.android.counselor.order.stores;

import java.util.ArrayList;
import java.util.List;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.CompetingBean;
import com.cesaas.android.counselor.order.bean.Fans;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 企业竞品Adapter
 * @author FGB
 *
 */
public class CompetingAdapter extends BaseAdapter{
	
	private Context context; 
	
	public List<CompetingBean> competingList=new ArrayList<CompetingBean>();
	
	public CompetingAdapter(Context ct,List<CompetingBean> data){
		this.context = ct;
		this.competingList = data;
	}

	@Override
	public int getCount() {
		return competingList.size();
	}

	@Override
	public Object getItem(int position) {
		return competingList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unused")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder=null;
		if(holder==null){
			holder=new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_competing_list, null);
			holder.companyName=(TextView) convertView.findViewById(R.id.tv_company_name);
			holder.salesAmount=(TextView) convertView.findViewById(R.id.tv_sales_amount);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		CompetingBean bean=competingList.get(position);
		holder.companyName.setText(bean.getName());
		holder.salesAmount.setText(bean.getSaleValue());
		
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView companyName;
		TextView salesAmount;
		
	}

}
