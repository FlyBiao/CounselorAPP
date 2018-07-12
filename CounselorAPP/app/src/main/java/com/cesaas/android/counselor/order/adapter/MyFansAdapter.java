package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.Fans;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * 我的粉丝Adapter
 * @author FGB
 *
 */
public class MyFansAdapter extends BaseAdapter{
	
	private Context ct;
	private Activity activity;
	private List<Fans> data= new ArrayList<Fans>();
	private static BitmapUtils bitmapUtils;
	
	// 用来控制CheckBox的选中状况 
    private static HashMap<Integer, Boolean> isSelected; 
	
	public MyFansAdapter(Context ct, Activity activity) {
		this.ct = ct;
		this.activity = activity;
	}

	public MyFansAdapter(Context ct, List<Fans> data) {
		this.ct = ct;
		this.data = data;
		
		bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
		
		isSelected = new HashMap<Integer, Boolean>(); 
		  // 初始化数据 
        initDate(); 
    } 
    // 初始化isSelected的数据 
    private void initDate() { 
        for (int i = 0; i < data.size(); i++) { 
            getIsSelected().put(i, false); 
        } 
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
		
		ViewHolder holder = null; 
        if (convertView == null) {
			// 获得ViewHolder对象
			holder = new ViewHolder();
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_my_fans, parent, false);
			holder.tv_fans_nick=(TextView) convertView.findViewById(R.id.tv_fans_nick);
			holder.tv_fans_mobile=(TextView) convertView.findViewById(R.id.tv_fans_mobile);
			holder.iv_fans_img=(MyImageViewWidget) convertView.findViewById(R.id.iv_fans_img);
			holder.item_cb=(CheckBox) convertView.findViewById(R.id.item_cb);
			// 为view设置标签
			convertView.setTag(holder);
		}else{
			// 取出holder
			holder = (ViewHolder) convertView.getTag();
		}

		Fans bean = data.get(position);
		if(bean.FANS_REMARK.equals("")){
			holder.tv_fans_nick.setText(bean.FANS_NICKNAME);

		}else{
			holder.tv_fans_nick.setText(bean.FANS_REMARK);
		}

		holder.tv_fans_mobile.setText(bean.FANS_MOBILE);
		bitmapUtils.display(holder.iv_fans_img, bean.FANS_ICON, App.mInstance().bitmapConfig);

		// 根据isSelected来设置checkbox的选中状况
//        holder.item_cb.setChecked(getIsSelected().get(position));

		return convertView;
	}
	
	 public static HashMap<Integer, Boolean> getIsSelected() { 
	        return isSelected; 
	    } 
	    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) { 
	        MyFansAdapter.isSelected = isSelected; 
	    } 

	    public class ViewHolder{
	    	private TextView tv_fans_nick,tv_fans_mobile;
	    	private MyImageViewWidget iv_fans_img;
	    	private CheckBox item_cb;
	    }
}
