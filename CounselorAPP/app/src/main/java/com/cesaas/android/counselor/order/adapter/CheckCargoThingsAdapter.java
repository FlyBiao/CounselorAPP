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
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderItemBean;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

public class CheckCargoThingsAdapter extends BaseAdapter{

	List<OrderItemBean> list = new ArrayList<OrderItemBean>();
	Context context;
	static BitmapUtils bitmapUtils;
	
	private TextView tv_check_cargo_quantity;
	private TextView tv_check_cargo_attr;
	private TextView tv_check_cargo_b_code;
	private TextView tv_check_cargo_title;
	
	public CheckCargoThingsAdapter(Context ct, List<OrderItemBean> data) {
		this.context = ct;
		this.list = data;
		bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.item_check_cargo, parent, false);
		tv_check_cargo_quantity=(TextView) convertView.findViewById(R.id.tv_check_cargo_quantity);
		tv_check_cargo_attr=(TextView) convertView.findViewById(R.id.tv_check_cargo_attr);
		tv_check_cargo_b_code=(TextView) convertView.findViewById(R.id.tv_check_cargo_b_code);
		tv_check_cargo_title=(TextView) convertView.findViewById(R.id.tv_check_cargo_title);
		
		tv_check_cargo_b_code.setText(list.get(position).BarcodeCode);
		tv_check_cargo_attr.setText(list.get(position).Attr);
		tv_check_cargo_title.setText(list.get(position).Title);
		tv_check_cargo_quantity.setText("x" + list.get(position).Quantity);
		
		return convertView;
	}


}
