package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetCodeOrderBean.GetCodeOrderBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * 扫描发货订单详情数据适配器
 * @author FGB
 *
 */
public class ScanSendDetailAdapter extends BaseAdapter{

private static final String TAG = "OrderDetailAdapter";
	
	private Context ct;
//	private List<OrderDetailBean> data=new ArrayList<OrderDetailBean>();
	private List<GetCodeOrderBean> data=new ArrayList<GetCodeOrderBean>();
	private List<DistributionOrderBean> data2=new ArrayList<DistributionOrderBean>();
	private RelativeLayout rl_user_order_operation;
	
	public static BitmapUtils bitmapUtils;
	
	
	private AbPrefsUtil abpUtil;
	private Activity activity;
	private LinearLayout ll_send_order_detail_user;
	

	public ScanSendDetailAdapter(Context ct,Activity activity,List<GetCodeOrderBean> data,List<DistributionOrderBean> data2){
		this.ct = ct;
		this.activity=activity;
		this.data = data;
		this.data2=data2;
		bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<GetCodeOrderBean> list,List<DistributionOrderBean> lsit2) {
		this.data = list;
		this.data2=lsit2;
//		notifyDataSetChanged();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_scan_send_detail, null);
			viewHolder.tv_scan_order_user = (TextView) convertView.findViewById(R.id.tv_scan_order_user);
			viewHolder.tv_scan_order_user_mobile = (TextView) convertView.findViewById(R.id.tv_scan_order_user_mobile);
			viewHolder.tv_scan_order_remark = (TextView) convertView.findViewById(R.id.tv_scan_order_remark);
			viewHolder.tv_scan_order_pay_price=(TextView) convertView.findViewById(R.id.tv_scan_order_pay_price);
			viewHolder.tv_scan_order_total_price=(TextView) convertView.findViewById(R.id.tv_scan_order_total_price);
			viewHolder.tv_scan_order_commision_develop=(TextView) convertView.findViewById(R.id.tv_scan_order_commision_develop);
			viewHolder.tv_scan_order_send_commision=(TextView) convertView.findViewById(R.id.tv_scan_order_send_commision);
			viewHolder.tv_scan_orderid=(TextView) convertView.findViewById(R.id.tv_scan_orderid);
			viewHolder.tv_scan_order_create_date=(TextView) convertView.findViewById(R.id.tv_scan_order_create_date);
			viewHolder.iv_scan_goods_img=(ImageView) convertView.findViewById(R.id.iv_scan_goods_img);
			viewHolder.tv_scan_uantity=(TextView) convertView.findViewById(R.id.tv_scan_uantity);
			viewHolder.tv_scan_attr=(TextView) convertView.findViewById(R.id.tv_scan_attr);
			viewHolder.tv_scan_goods_name=(TextView) convertView.findViewById(R.id.tv_scan_goods_name);
			viewHolder.tv_scan_expressType=(TextView) convertView.findViewById(R.id.tv_scan_expressType);
			viewHolder.tv_scan_order_address=(TextView) convertView.findViewById(R.id.tv_scan_order_address);
			viewHolder.tv_scan_r_date=(TextView) convertView.findViewById(R.id.tv_scan_r_date);
			viewHolder.ll_scan_order_address=(LinearLayout) convertView.findViewById(R.id.ll_scan_order_address);
			viewHolder.ll_scan_r_date=(LinearLayout) convertView.findViewById(R.id.ll_scan_r_date);
			viewHolder.tv_scan_order_shop_barcodecode=(TextView) convertView.findViewById(R.id.tv_scan_order_shop_barcodecode);
			viewHolder.tv_scan_order_shop_stylecode=(TextView) convertView.findViewById(R.id.tv_scan_order_shop_stylecode);
			
			
			ll_send_order_detail_user=(LinearLayout) convertView.findViewById(R.id.ll_send_order_detail_user);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(data.size()!=0){
			final GetCodeOrderBean detail=data.get(position);
			
			if(detail!=null){
				viewHolder.tv_scan_orderid.setText("订单号:"+detail.OrderId);
				viewHolder.tv_scan_order_create_date.setText("下单时间:"+detail.CreateDate);
				viewHolder.tv_scan_order_user.setText(detail.ConsigneeName);
				viewHolder.tv_scan_order_user_mobile.setText(detail.Mobile);
				viewHolder.tv_scan_order_remark.setText(detail.OrderRemark);
				viewHolder.tv_scan_order_pay_price.setText(Double.toString(detail.PayPrice));
				viewHolder.tv_scan_order_total_price.setText(Double.toString(detail.TotalPrice));
				
				if(detail.ExpressType==0){//快递方式取货
					ll_send_order_detail_user.setVisibility(View.VISIBLE);
					viewHolder.tv_scan_expressType.setText("快递");
					viewHolder.ll_scan_order_address.setVisibility(View.VISIBLE);
					viewHolder.tv_scan_order_address.setText(detail.Province+detail.City+detail.District+detail.Address);
					
				}else if(detail.ExpressType==1){//到店自提取货
					viewHolder.tv_scan_expressType.setText("到店自提");
					viewHolder.ll_scan_r_date.setVisibility(View.VISIBLE);
					viewHolder.tv_scan_r_date.setText(detail.ReserveDate);
				}
			}
		}
		
		if(data2.size()!=0){
			final DistributionOrderBean detail2=data2.get(0);
			for (int i = 0; i < data2.size(); i++) {
				if(data2.get(i).BonusType==0){//推荐佣金
					viewHolder.tv_scan_order_commision_develop.setText(""+detail2.OrderBonus);
				}else{
					viewHolder.tv_scan_order_commision_develop.setText("zero.zero");
				}
				if(data2.get(i).BonusType==1){//发货佣金
					viewHolder.tv_scan_order_send_commision.setText(""+detail2.OrderBonus);
				}else{
					viewHolder.tv_scan_order_send_commision.setText("zero.zero");
				}
			}
				
				for(int i=0;i<detail2.OrderItem.size();i++){
					bitmapUtils.display(viewHolder.iv_scan_goods_img, detail2.OrderItem.get(i).ImageUrl, App.mInstance().bitmapConfig);
					viewHolder.tv_scan_uantity.setText("x"+detail2.OrderItem.get(i).Quantity);
					viewHolder.tv_scan_attr.setText(detail2.OrderItem.get(i).Attr);
					viewHolder.tv_scan_goods_name.setText(detail2.OrderItem.get(i).Title);
					viewHolder.tv_scan_order_shop_barcodecode.setText("条码:"+detail2.OrderItem.get(i).BarcodeCode);
					viewHolder.tv_scan_order_shop_stylecode.setText("款号:"+detail2.OrderItem.get(i).StyleCode);
				}
				
			}
		
		
		return convertView;
	}
	
	
	static class ViewHolder {
		TextView tv_scan_order_user;//用户
		TextView tv_scan_order_user_mobile;//订单用户手机
		TextView tv_scan_order_remark;//订单备注
		TextView tv_scan_expressType;
		TextView tv_scan_order_pay_price;//支付金额
		TextView tv_scan_order_total_price;//总金额
		TextView tv_scan_order_commision_develop;
		TextView tv_scan_order_send_commision;
		TextView tv_scan_attr;
		TextView tv_scan_goods_name;
		TextView tv_scan_orderid;
		TextView tv_scan_order_create_date;
		ImageView iv_scan_goods_img;
		TextView tv_scan_uantity;
		TextView tv_scan_order_address;
		TextView tv_scan_r_date;
		TextView tv_scan_order_shop_barcodecode;
		TextView tv_scan_order_shop_stylecode;
		LinearLayout ll_scan_order_address;
		LinearLayout ll_scan_r_date;
		
	}

}
