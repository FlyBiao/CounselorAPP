package com.cesaas.android.counselor.order.adapters;

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
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * 商品详情数据适配器
 * @author FGB
 *
 */
public class OrderShopDetailAdapter extends BaseAdapter{

	private Context ct;
	private List<DistributionOrderBean> data=new ArrayList<DistributionOrderBean>();
	
	public static BitmapUtils bitmapUtils;
	
	
	
	private AbPrefsUtil abpUtil;
	private Activity activity;
	
	public OrderShopDetailAdapter(Context ct,Activity activity,List<DistributionOrderBean> data){
		this.ct = ct;
		this.activity=activity;
		this.data = data;
		this.data=data;
		bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<DistributionOrderBean> list) {
		this.data = list;
		notifyDataSetChanged();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_receive_order_detail, null);
			viewHolder.tv_order_shop_title=(TextView) convertView.findViewById(R.id.tv_order_shop_title);
			viewHolder.tv_order_shop_stylecode=(TextView) convertView.findViewById(R.id.tv_shop_stylecode);
			viewHolder.tv_order_shop_code=(TextView) convertView.findViewById(R.id.tv_order_shop_code);
			viewHolder.tv_order_shop_price=(TextView) convertView.findViewById(R.id.tv_order_shop_price);
			viewHolder.tv_order_shop_attr=(TextView) convertView.findViewById(R.id.tv_order_shop_attr);
			viewHolder.iv_order_shop_img=(ImageView) convertView.findViewById(R.id.iv_order_shop_img);
			viewHolder.tv_check_shop_order=(TextView) convertView.findViewById(R.id.tv_check_shop_order);
			
			convertView.setTag(viewHolder);
			
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		DistributionOrderBean detail=data.get(position);
//		for (int i = zero; i < detail.OrderItem.size(); i++) {
			viewHolder.tv_order_shop_title.setText(detail.OrderItem.get(position).Title);
			viewHolder.tv_order_shop_stylecode.setText(detail.OrderItem.get(position).StyleCode);
			viewHolder.tv_order_shop_code.setText(detail.OrderItem.get(position).BarcodeCode);
			viewHolder.tv_order_shop_price.setText(""+detail.OrderItem.get(position).Price);
			viewHolder.tv_order_shop_attr.setText(detail.OrderItem.get(position).Attr);
			bitmapUtils.display(viewHolder.iv_order_shop_img, detail.OrderItem.get(position).ImageUrl, App.mInstance().bitmapConfig);
//		}
		
		//扫描验货
		viewHolder.tv_check_shop_order.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String barcodeCode=data.get(position).OrderItem.get(0).BarcodeCode.toString();
				Bundle bundle=new Bundle();
				bundle.putString("barcodeCode", barcodeCode);
				Skip.mNextFroData((Activity)ct, ScanResultActivity.class,bundle);
			}
		});
		
		return convertView;
	}
	
	static class ViewHolder {
		ImageView iv_order_shop_img;
		TextView tv_order_shop_title;
		TextView tv_order_shop_stylecode;
		TextView tv_order_shop_code;
		TextView tv_order_shop_price;
		TextView tv_order_shop_attr;
		
		TextView tv_check_shop_order;
	}

}
