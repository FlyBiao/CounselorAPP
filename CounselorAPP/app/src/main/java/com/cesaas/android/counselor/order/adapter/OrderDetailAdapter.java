package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.ShopDetailActivity;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderItemBean;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * 订单详情数据适配器
 * @author FGB
 *
 */
public class OrderDetailAdapter extends BaseAdapter{

private static final String TAG = "OrderDetailAdapter";
	
	private Context ct;
	private List<OrderDetailBean> data=new ArrayList<OrderDetailBean>();
	private List<DistributionOrderBean> data2=new ArrayList<DistributionOrderBean>();
	
	public static BitmapUtils bitmapUtils;
	
	private TextView tv_orderDetail_State;
	private TextView tv_send_order_state;
	private LinearLayout ll_title;
	private LinearLayout ll_order_price;
	private RelativeLayout rl_shop_list;
	
	
	private Activity activity;
	
	private ListView lv;
	private List<OrderItemBean> list = new ArrayList<OrderItemBean>();
	
	public OrderDetailAdapter(Context ct,Activity activity,List<OrderDetailBean> data,List<DistributionOrderBean> data2){
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
	public void updateListView(List<OrderDetailBean> list,List<DistributionOrderBean> lsit2) {
		this.data = list;
		this.data2=lsit2;
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
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_order_detail, null);
			viewHolder.ll_order_consignee_name=(RelativeLayout) convertView.findViewById(R.id.ll_order_consignee_name);
			viewHolder.tv_order_user = (TextView) convertView.findViewById(R.id.tv_order_user);
			viewHolder.tv_order_user_mobile = (TextView) convertView.findViewById(R.id.tv_order_user_mobile);
			viewHolder.tv_order_remark = (TextView) convertView.findViewById(R.id.tv_order_remark);
			viewHolder.tv_order_pay_price=(TextView) convertView.findViewById(R.id.tv_order_pay_price);
			viewHolder.tv_order_total_price=(TextView) convertView.findViewById(R.id.tv_order_total_price);
			viewHolder.tv_commision_develop=(TextView) convertView.findViewById(R.id.tv_commision_develop);
			viewHolder.tv_send_commision=(TextView) convertView.findViewById(R.id.tv_send_commision);
			viewHolder.tv_orderid=(TextView) convertView.findViewById(R.id.tv_r_orderId);
			viewHolder.tv_order_create_date=(TextView) convertView.findViewById(R.id.tv_order_createDate);
			viewHolder.tv_expressType=(TextView) convertView.findViewById(R.id.tv_expressType);
			viewHolder.tv_order_address=(TextView) convertView.findViewById(R.id.tv_order_address);
			viewHolder.tv_r_daate=(TextView) convertView.findViewById(R.id.tv_r_daate);
			viewHolder.ll_order_address=(LinearLayout) convertView.findViewById(R.id.ll_order_address);
			viewHolder.ll_r_date=(LinearLayout) convertView.findViewById(R.id.ll_r_date);
			viewHolder.tv_express_ship_no=(TextView) convertView.findViewById(R.id.tv_express_ship_no);
			viewHolder.tv_receive_order_user=(TextView)convertView.findViewById(R.id.tv_receive_order_user);
			viewHolder.ll_receive_order_user=(RelativeLayout)convertView.findViewById(R.id.ll_receive_order_user);
			viewHolder.ll_order_user_mobile=(LinearLayout) convertView.findViewById(R.id.ll_order_user_mobile);
			viewHolder.ll_express_ship_no=(LinearLayout) convertView.findViewById(R.id.ll_express_ship_no);
			viewHolder.ll_commision_develop=(LinearLayout) convertView.findViewById(R.id.ll_order_commision_develop);
			viewHolder.ll_send_commision=(LinearLayout) convertView.findViewById(R.id.ll_order_send_commision);
			
			tv_send_order_state=(TextView) convertView.findViewById(R.id.tv_send_order_state);
			tv_orderDetail_State=(TextView) convertView.findViewById(R.id.tv_orderDetail_State);
			ll_title=(LinearLayout) convertView.findViewById(R.id.ll_title);
			ll_order_price=(LinearLayout) convertView.findViewById(R.id.ll_order_price);
			rl_shop_list=(RelativeLayout) convertView.findViewById(R.id.rl_shop_list);
			
			lv=(ListView)convertView.findViewById(R.id.list_order_detail_things);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(data.size()!=0){
			OrderDetailBean detail=data.get(position);
			viewHolder.tv_orderid.setText(""+detail.OrderId);
			viewHolder.tv_order_create_date.setText(detail.CreateDate);
			
			if(detail!=null){
				
				if(detail.OrderStatus==10){//未支付
					tv_orderDetail_State.setText("未支付");
					tv_send_order_state.setText("未支付");
				}
				else if(detail.OrderStatus == 11){//待审核
					tv_orderDetail_State.setText("待审核");
					tv_send_order_state.setText("待审核");
				}
				else if(detail.OrderStatus==20){//支付中
					tv_orderDetail_State.setText("未支付");
					tv_send_order_state.setText("未支付");
					
				}else if(detail.OrderStatus==30){//已付款未发货
					tv_orderDetail_State.setText("已支付");
					tv_send_order_state.setText("已支付");
					
									
				}else if(detail.OrderStatus==40 ){//已发货
					tv_send_order_state.setText("已发货");
					tv_orderDetail_State.setText("已发货");
					
				}
				else if(detail.OrderStatus==80){//已退款
					tv_orderDetail_State.setText("已退款");
					tv_send_order_state.setText("已退款");
				}
				else if(detail.OrderStatus==81){
					tv_orderDetail_State.setText("订单已取消");
					tv_send_order_state.setText("订单已取消");
				}
				else if(detail.OrderStatus==100){//订单完成
					tv_orderDetail_State.setText("订单完成");
					tv_send_order_state.setText("订单完成");
				}
				
				viewHolder.tv_order_user.setText(detail.ConsigneeName);
				viewHolder.tv_order_user_mobile.setText(detail.Mobile);
				viewHolder.tv_order_pay_price.setText(Double.toString(detail.PayPrice));
				viewHolder.tv_order_total_price.setText(Double.toString(detail.TotalPrice));
				
				if(detail.OrderRemark!=null){
					viewHolder.tv_order_remark.setText(detail.OrderRemark);
				}else{
					viewHolder.tv_order_remark.setText("暂无备注！");
				}
				
				if(detail.ExpressType==0){//快递方式取货
					
					if(detail.ExpressShipNo!=null){
						viewHolder.ll_express_ship_no.setVisibility(View.VISIBLE);
						viewHolder.tv_express_ship_no.setText(detail.ExpressShipNo);
					}else{
						viewHolder.ll_express_ship_no.setVisibility(View.GONE);
					}
					
					if(detail.SendDate!=null){
						viewHolder.ll_r_date.setVisibility(View.VISIBLE);
						viewHolder.tv_r_daate.setText(detail.SendDate);
						
					}else{
						viewHolder.ll_r_date.setVisibility(View.GONE);
					}
					viewHolder.ll_receive_order_user.setVisibility(View.GONE);
					viewHolder.ll_order_user_mobile.setVisibility(View.VISIBLE);
					viewHolder.ll_order_consignee_name.setVisibility(View.VISIBLE);
					viewHolder.tv_expressType.setText("快递");
					viewHolder.tv_expressType.setTextColor(ct.getResources().getColor(R.color.forestgreen));
					viewHolder.ll_order_address.setVisibility(View.VISIBLE);
					viewHolder.tv_order_address.setText(detail.Province+detail.City+detail.District+detail.Address);
					
					ll_title.setBackgroundDrawable(ct.getResources().getDrawable(R.drawable.order_item_bg));
					ll_order_price.setBackgroundDrawable(ct.getResources().getDrawable(R.drawable.order_item_bg));
					rl_shop_list.setBackgroundDrawable(ct.getResources().getDrawable(R.drawable.order_item_bg));
					
				}else if(detail.ExpressType==1){//到店自提取货
					viewHolder.ll_order_consignee_name.setVisibility(View.GONE);
					viewHolder.ll_order_address.setVisibility(View.GONE);
					viewHolder.ll_order_user_mobile.setVisibility(View.GONE);
					viewHolder.tv_expressType.setText("到店自提");
					viewHolder.tv_expressType.setTextColor(ct.getResources().getColor(R.color.color_title_bar));
					viewHolder.ll_receive_order_user.setVisibility(View.VISIBLE);
					viewHolder.tv_receive_order_user.setText(detail.NickName);
					viewHolder.ll_r_date.setVisibility(View.VISIBLE);
					viewHolder.tv_r_daate.setText(detail.ReserveDate);
					
					ll_title.setBackgroundDrawable(ct.getResources().getDrawable(R.drawable.order_item_bg));
					ll_order_price.setBackgroundDrawable(ct.getResources().getDrawable(R.drawable.order_item_bg));
					rl_shop_list.setBackgroundDrawable(ct.getResources().getDrawable(R.drawable.order_item_bg));
				}
			}
			
			list = new ArrayList<OrderItemBean>();
			
			for(int i=0;i<detail.OrderItem.size();i++){
				
				OrderItemBean itemBean=new ResultOrderDetailBean().new OrderItemBean();
				itemBean=detail.OrderItem.get(i);
				
				list.add(itemBean);
			}
			
			OrderDetailThingsAdapter adapter=new OrderDetailThingsAdapter(activity, list);
			int totalHeight = 0;
			for (int i = 0; i < adapter.getCount(); i++) {
				View listItem = adapter.getView(i, null, lv);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}

			ViewGroup.LayoutParams params = lv.getLayoutParams();
			params.height = totalHeight + (lv.getDividerHeight() * (adapter.getCount() - 1));
			((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
			lv.setLayoutParams(params);
			lv.setAdapter(adapter);
			
			/**
			 * 查看订单详情
			 */
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

					Bundle bundle = new Bundle();
					bundle.putString("Title", list.get(i).Title);
					bundle.putString("ImageUrl", list.get(i).ImageUrl);
					bundle.putString("StyleCode", list.get(i).StyleCode);
					bundle.putString("BarcodeCode", list.get(i).BarcodeCode);
					bundle.putDouble("Price", list.get(i).Price);
					bundle.putString("Attr", list.get(i).Attr);
					
					Skip.mNextFroData(activity, ShopDetailActivity.class, bundle);
				}
			});
		}
		
		if(data2.size()!=0){
			final DistributionOrderBean detail2=data2.get(0);
			
			for (int i = 0; i < data2.size(); i++) {
				
				if(data2.get(i).BonusType==0){//推荐佣金
					if(detail2.OrderBonus==0.0){
						viewHolder.ll_commision_develop.setVisibility(View.GONE);
					}else{
						viewHolder.tv_commision_develop.setText(""+detail2.OrderBonus);
						viewHolder.ll_commision_develop.setVisibility(View.VISIBLE);
					}
				}
				
				if(data2.get(i).BonusType==1){//发货佣金
					if (detail2.OrderBonus==0.0) {
						viewHolder.ll_send_commision.setVisibility(View.GONE);
					}else{
						viewHolder.ll_send_commision.setVisibility(View.VISIBLE);
						viewHolder.tv_send_commision.setText(""+detail2.OrderBonus);
					}
				}
			}
				
			}
		
		return convertView;
	}

	static class ViewHolder {
		TextView tv_order_user;//用户
		TextView tv_order_user_mobile;//订单用户手机
		TextView tv_order_remark;//订单备注
		TextView tv_expressType;
		TextView tv_order_pay_price;//支付金额
		TextView tv_order_total_price;//总金额
		TextView tv_commision_develop;
		TextView tv_send_commision;
		TextView tv_orderid;
		TextView tv_order_create_date;
		TextView tv_order_address;
		TextView tv_r_daate;
		TextView tv_express_ship_no;
		LinearLayout ll_order_address;
		LinearLayout ll_r_date;
		RelativeLayout ll_order_consignee_name;
		RelativeLayout ll_receive_order_user;
		TextView tv_receive_order_user;
		LinearLayout ll_order_user_mobile;
		LinearLayout ll_express_ship_no;
		LinearLayout ll_commision_develop;
		LinearLayout ll_send_commision;
	}

}
