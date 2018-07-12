package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetListByShopIdBean.FansListByShopIdBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

/**
 * 按店铺查询粉丝列表数据适配器
 * @author FGB
 *
 */
public class SearchListByShopIdAdapter extends AbstractAdapter<FansListByShopIdBean>{
	
	private ListView listView;
	private BitmapUtils bitmapUtils;
	
	private TextView tv_fans_counselor;
	private TextView tv_fans_nickNnme;
	private ImageView iv_fans_icon;

	private Context ct;
	
	public SearchListByShopIdAdapter(Context ct,ArrayList<? extends FansListByShopIdBean> data,ListView listview){
		super(ct, data);
		this.listView = listview;
		if (bitmapUtils == null) {
			bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
		}
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.default_img);
		// 设置最大宽高, 不设置时更具控件属性自适应.
		// bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(context).scaleDown(3));
		listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_fans_list_by_shop_id, null);
		}
		tv_fans_counselor=(TextView) convertView.findViewById(R.id.tv_fans_counselor);
		tv_fans_nickNnme=(TextView) convertView.findViewById(R.id.fans_nick);
		iv_fans_icon=(ImageView) convertView.findViewById(R.id.iv_fans_icon);
			
		FansListByShopIdBean bean=mData.get(position);
		
		if(bean.COUNSELOR_NAME!=null){
			tv_fans_counselor.setText("所属顾问:"+bean.COUNSELOR_NAME);
		}else{
			tv_fans_counselor.setText("未分配顾问");
		}
		
		tv_fans_nickNnme.setText(bean.FANS_NICKNAME);
		bitmapUtils.display(iv_fans_icon, bean.FANS_ICON, App.mInstance().bitmapConfig);
		
		return convertView;
	}
	
}
