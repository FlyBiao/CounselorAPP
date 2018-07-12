package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.ScanResultActivity;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean.OrderItemBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * Created by flybiao on 2016/7/5.
 */

public class ReceiveOrderDetailThingsAdapter extends BaseAdapter {
	ImageView ivImg;
	TextView tvName;
	TextView tvAttr;
	TextView tvTypeCode;
	TextView tvQuantity;
	TextView tvCheck;

	List<OrderItemBean> list = new ArrayList<OrderItemBean>();
	Context context;
	static BitmapUtils bitmapUtils;

	public ReceiveOrderDetailThingsAdapter(Context ct, List<OrderItemBean> data) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.item_order_detail_things, parent, false);
		ivImg = (ImageView) convertView.findViewById(R.id.iv_order_detail_things_img);
		tvName = (TextView) convertView.findViewById(R.id.tv_order_detail_things_name);
		tvAttr = (TextView) convertView.findViewById(R.id.tv_order_detail_things_attr);
		tvTypeCode = (TextView) convertView.findViewById(R.id.tv_order_detail_things_code);
		tvQuantity = (TextView) convertView.findViewById(R.id.tv_order_detail_things_quantity);
		tvCheck = (TextView) convertView.findViewById(R.id.tv_order_detail_things_check);
		
		
		tvName.setText(list.get(position).getTitle());
		tvAttr.setText(list.get(position).getAttr());
		tvTypeCode.setText("款号"+list.get(position).getStyleCode());
		tvQuantity.setText("x" + list.get(position).getQuantity());
		if(!"".equals(list.get(position).getImageUrl())){
			bitmapUtils.display(ivImg, list.get(position).getImageUrl(), App.mInstance().bitmapConfig);
		}else{
			ivImg.setImageDrawable(context.getResources().getDrawable(R.drawable.load_error));
		}
		
		final String barcodeCode=list.get(position).getBarcodeCode().toString();
		
		/**
		 * 扫描验货
		 */
		tvCheck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Bundle bundle=new Bundle();
				bundle.putString("barcodeCode", barcodeCode);
				Skip.mNextFroData((Activity)context, ScanResultActivity.class,bundle);
			}
		});

		return convertView;
	}
}
