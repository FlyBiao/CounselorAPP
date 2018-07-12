package com.cesaas.android.counselor.order.widget.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;

/**
 * 店员GridAdapter
 * @author FGB
 *
 */
public class ShopAssistantGridAdapter extends BaseAdapter {
	private Context mContext;

	public String[] img_text = { "店员管理", "订单管理", "店铺业绩", "粉丝管理"};
	public int[] imgs = { 
			R.drawable.shopassistant, R.drawable.ordermanger,
			R.drawable.shopyeji, R.drawable.fansmanger};

	public ShopAssistantGridAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return img_text.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.grid_item, parent, false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
		ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
		iv.setBackgroundResource(imgs[position]);

		tv.setText(img_text[position]);
		return convertView;
	}

}
