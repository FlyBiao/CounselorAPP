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
import com.cesaas.android.counselor.order.bean.ResultBiaoGetByCounselorBean.GetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultBiaoGetByCounselorBean.GetByCounselorBeanItemBean;
import com.lidroid.xutils.BitmapUtils;

/**
 * 我的未处理订单Adapter
 */

public class MyPendingOrderThingsAdapter extends BaseAdapter {
	TextView tvTypeCode;
	TextView tvCheck;
	private TextView tv_pending_express_send;

	List<GetByCounselorBeanItemBean> list = new ArrayList<GetByCounselorBeanItemBean>();
	List<GetByCounselorBean> lists = new ArrayList<GetByCounselorBean>();
	Context context;
	static BitmapUtils bitmapUtils;
	
	private String styleCode;//款号

	public MyPendingOrderThingsAdapter(Context ct, List<GetByCounselorBeanItemBean> data) {
		this.context = ct;
		this.list = data;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.item_receive_pending_order_things, parent, false);
		tvTypeCode = (TextView) convertView.findViewById(R.id.tv_pending_reveice_type_code);
		tvCheck = (TextView) convertView.findViewById(R.id.tv_pending_reveice_check);
		tv_pending_express_send=(TextView) convertView.findViewById(R.id.tv_pending_express_send);
		
		styleCode=list.get(position).StyleCode;
		tvTypeCode.setText(styleCode);
		
		return convertView;
	}
}
