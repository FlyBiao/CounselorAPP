package com.cesaas.android.counselor.order.pos.adapter;

import io.rong.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.GetByBarcodeCode;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.pos.ShopSliderView;
import com.cesaas.android.counselor.order.pos.dialog.SetNumberDialog;
import com.cesaas.android.counselor.order.pos.dialog.SetPriceDialog;

/**
 * 条码商品Adapter
 * @author FGB
 *
 */
public class BarcodeShopAdapter extends BaseAdapter{

	private Context context; 
	private Activity activity;
	private LayoutInflater inflater;
	//商品条码集合
	public List<GetByBarcodeCode> barcodeCodeList=new ArrayList<GetByBarcodeCode>();
	
	private int number;
	private double price;
	
	public BarcodeShopAdapter(Context context,Activity activity,List<GetByBarcodeCode> arr){
		this.context=context;
		this.activity=activity;
		this.barcodeCodeList=arr;
		inflater = LayoutInflater.from(context); 
	}
	
	@Override
	public int getCount() {
		return barcodeCodeList.size();
	}

	@Override
	public Object getItem(int position) {
		return barcodeCodeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		ShopSliderView slideView = (ShopSliderView) convertView;
		if (slideView == null) {
			View itemView = inflater.inflate(R.layout.item_barcode_shop_info, null);

			slideView = new ShopSliderView(context);
			slideView.setContentView(itemView);
			holder = new ViewHolder(slideView);
			slideView.setTag(holder);
		} else {
			holder = (ViewHolder) slideView.getTag();
		}
		
		final GetByBarcodeCode bean=barcodeCodeList.get(position);
		slideView.shrink();
		
		if(SetNumberDialog.number!=0){
			bean.setShopCount(number);
			holder.tv_shop_number.setText(SetNumberDialog.number+"");
			notifyDataSetChanged();
		}else{
			holder.tv_shop_number.setText(bean.getShopCount()+"");
		}
		
		if(SetPriceDialog.price!=0.0){
			bean.setPrice(price);
			holder.tv_barcode_shop_price.setText(SetPriceDialog.price+"");
			notifyDataSetChanged();
		}else{
			holder.tv_barcode_shop_price.setText(bean.getPrice()+"");
		}
		
		holder.tv_lists.setText(position+1+"");
		holder.tv_shop_barcode_code.setText(bean.getStyleCode());
		holder.tv_shift.setText(bean.getBarcodeCode());
		
		//删除
		holder.deleteHolder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new MyAlertDialog(context).mInitShow("温馨提示！", "是否确定删除该订单",
				"删除", "点错了", new ConfirmListener() {
					@Override
					public void onClick(Dialog dialog) {
						bean.position=position;
						barcodeCodeList.remove(position);
						EventBus.getDefault().post(bean);
						notifyDataSetChanged();
					}
				}, null);
			}
		});
		//数量
		holder.shop_number.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new SetNumberDialog(context,activity).mInitShow();
			}
		});
		//价格
		holder.shop_peice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new SetPriceDialog(context).mInitShow();
			}
		});
		
		return slideView;
	}
	
	/**
	 * 自定义ViewHolder  Adapter类
	 * @author fgb
	 *
	 */
	private static class ViewHolder {
		TextView tv_lists;
		TextView tv_shop_barcode_code;
		TextView tv_shift;
		TextView tv_shop_number;
		TextView tv_barcode_shop_price;
		TextView deleteHolder;
		TextView shop_number;
		TextView shop_peice;
		
		/**
		 * 查找视图控件
		 * @param view
		 */
		ViewHolder(View view) {
			tv_lists=(TextView) view.findViewById(R.id.tv_lists);
			tv_shop_barcode_code=(TextView) view.findViewById(R.id.tv_shop_barcode_code);
			tv_shift=(TextView) view.findViewById(R.id.tv_shift);
			tv_shop_number=(TextView) view.findViewById(R.id.tv_shop_number);
			tv_barcode_shop_price=(TextView) view.findViewById(R.id.tv_barcode_shop_price);
			deleteHolder = (TextView) view.findViewById(R.id.delete);
			shop_number = (TextView) view.findViewById(R.id.shop_number);
			shop_peice = (TextView) view.findViewById(R.id.shop_peice);
		}
	}
}
