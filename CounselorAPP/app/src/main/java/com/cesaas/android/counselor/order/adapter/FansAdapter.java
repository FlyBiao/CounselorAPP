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
import com.cesaas.android.counselor.order.bean.Fans;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * 粉丝数据适配器
 * @author FGB
 *
 */
public class FansAdapter extends BaseAdapter{

private static final String TAG = "FansAdapter";
	
	private Context ct;
	private List<Fans> data=new ArrayList<Fans>();

	public static BitmapUtils bitmapUtils;
	
	public FansAdapter(Context ct){
		this.ct = ct;	
		bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
	}	

	public FansAdapter(Context ct,List<Fans> data){
		this.ct = ct;
		this.data = data;
//		bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
//		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
//		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<Fans> list) {
		this.data = list;
		notifyDataSetChanged();
	}

	public void remove(Fans user) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_user_fans, null);
			viewHolder.tv_fans_nick = (TextView) convertView.findViewById(R.id.tv_fans_nick);
			viewHolder.tv_fans_mobile = (TextView) convertView.findViewById(R.id.tv_fans_mobile);
			viewHolder.iv_fans_img=(MyImageViewWidget) convertView.findViewById(R.id.iv_fans_img);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Fans fans=data.get(position);
		viewHolder.tv_fans_nick.setText(fans.FANS_NICKNAME);
		viewHolder.tv_fans_mobile.setText(fans.FANS_MOBILE);
		bitmapUtils.display(viewHolder.iv_fans_img, fans.FANS_ICON, App.mInstance().bitmapConfig);
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView tv_fans_nick;
		TextView tv_fans_mobile;
		MyImageViewWidget iv_fans_img;
	}

}
