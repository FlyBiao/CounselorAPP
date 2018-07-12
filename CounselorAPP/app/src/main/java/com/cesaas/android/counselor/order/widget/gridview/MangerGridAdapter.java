package com.cesaas.android.counselor.order.widget.gridview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.jauker.widget.BadgeView;

/**
 * 九宫格GridAdapter
 * @author FGB
 *
 */
public class MangerGridAdapter extends BaseAdapter {
	private Context mContext;
	private BadgeView badgeView;

	private TextView tv;
	private ImageView iv;
	
	private List<String> menu;
	private List<Integer> imgs;
	
	private int receiveOrderSount=0;
	private int vipMsgCount=0;

	public MangerGridAdapter(Context mContext,List<String> menuName,List<Integer> imgs) {
		super();
		this.mContext = mContext;
		this.menu=menuName;
		this.imgs=imgs;
	}

	@Override
	public int getCount() {
		return menu.size();
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
		
		tv = BaseViewHolder.get(convertView, R.id.tv_item);
		iv = BaseViewHolder.get(convertView, R.id.iv_item);
		
		if(menu.get(position).contains("订单")){
			//添加红点提示
			badgeView=new BadgeView(mContext);
			//绑定红点要显示的控件视图
			badgeView.setTargetView(iv);
			//设置红点提示数量
			badgeView.setBadgeCount(receiveOrderSount);
		}
		if(menu.get(position).contains("会员")){
			//添加红点提示
			badgeView=new BadgeView(mContext);
			//绑定红点要显示的控件视图
			badgeView.setTargetView(iv);
			//设置红点提示数量
			badgeView.setBadgeCount(vipMsgCount);
		}
		
		
		iv.setBackgroundResource(imgs.get(position));
		tv.setText(menu.get(position));
		
		return convertView;
	}

}
