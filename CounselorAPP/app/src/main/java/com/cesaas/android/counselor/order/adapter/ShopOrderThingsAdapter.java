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
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBeanItemBean;
import com.cesaas.android.counselor.order.bean.ResultGetShopOrderBean.GetShopOrderItemBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * Created by flybiao on 2016/7/4.
 */

public class ShopOrderThingsAdapter extends BaseAdapter {
	ImageView ivImg;
	TextView tvName;
	TextView tvAttr;
	TextView tvTypeCode;
	TextView tvQuantity;
	TextView tv_order_price;

	List<GetShopOrderItemBean> list = new ArrayList<GetShopOrderItemBean>();
	Context context;
	static BitmapUtils bitmapUtils;

	public ShopOrderThingsAdapter(Context ct, List<GetShopOrderItemBean> data) {
		this.context = ct;
		this.list = data;
		bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = LayoutInflater.from(context).inflate(R.layout.item_receive_order_things, parent, false);
		ivImg = (ImageView) convertView.findViewById(R.id.iv_reveice_img);
		tvName = (TextView) convertView.findViewById(R.id.tv_reveice_order_name);
		tvAttr = (TextView) convertView.findViewById(R.id.tv_reveice_order_attr);
		tvTypeCode = (TextView) convertView.findViewById(R.id.tv_reveice_type_code);
		tvQuantity = (TextView) convertView.findViewById(R.id.tv_reveice_quantity);
		tv_order_price=(TextView) convertView.findViewById(R.id.tv_order_price);
		
		
		tvName.setText(list.get(position).Title);
		tvAttr.setText(list.get(position).Attr);
		tvTypeCode.setText("款号"+list.get(position).StyleCode);
		tvQuantity.setText("x" + list.get(position).Quantity);
		tv_order_price.setText("￥"+list.get(position).Price);
		
		if(list.get(position).ImageUrl!=null){
			if(list.get(position).Title.contains("独立收银")){
				ivImg.setImageDrawable(context.getResources().getDrawable(R.drawable.no_shop_picture));
				
			}else{
				bitmapUtils.display(ivImg, list.get(position).ImageUrl, App.mInstance().bitmapConfig);
			}
			
		}else{
			ivImg.setImageDrawable(context.getResources().getDrawable(R.drawable.no_shop_picture));
		}
		
		return convertView;
	}
}
