package com.cesaas.android.counselor.order.adapter;

import io.rong.imkit.RongIM;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.ShopDetailActivity;
import com.cesaas.android.counselor.order.bean.ResultGetO2OOrderListBean.GetO2OOrderListBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean.GetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean.OrderItemBean;
import com.cesaas.android.counselor.order.fragment.ReceiveOrderFragment;
import com.cesaas.android.counselor.order.net.OrderBackNet;
import com.cesaas.android.counselor.order.net.ReceivingFonfirmOrderNet;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.haozhang.lib.SlantedTextView;
import com.lidroid.xutils.BitmapUtils;

/**
 * 订单数据适配器
 * 
 * @author FGB
 * 
 */
public class OrderAdapter extends BaseAdapter {

	private Context ct;
	public double sum = 0;

	private List<GetUnReceiveOrderBean> data = new ArrayList<GetUnReceiveOrderBean>();
	public static BitmapUtils bitmapUtils;

	private OrderBackNet backNet;// 标识无货
	private ReceivingFonfirmOrderNet fonfirmOrderNet;// 标识有货
	
	private AbPrefsUtil abpUtil;
	private Activity activity;
	private TextView tv_order_create_date;
	private TextView tv_order_number;
	private TextView btn_rob_order;
	private TextView tv_not_huo;
	private ImageView tv_huihua;
	private TextView tv_order_expressType;
	private TextView tv_receive_user;
	private TextView tv_send_orders;
	private SlantedTextView slv_right_tri_express;
	private SlantedTextView slv_right_tri_self_lift;
	
	private LinearLayout ll_un_receive_order;

	private TextView tvCheck;

	private ListView lv;
	private List<OrderItemBean> list = new ArrayList<OrderItemBean>();

	public OrderAdapter(Context ct, Activity activity) {
		this.ct = ct;
		this.activity = activity;
	}

	public OrderAdapter(Context ct, List<GetUnReceiveOrderBean> data) {
		this.ct = ct;
		this.data = data;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<GetUnReceiveOrderBean> list) {
		this.data = list;
		notifyDataSetChanged();
	}

	public void remove(GetO2OOrderListBean order) {
		this.data.remove(order);
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

		convertView = LayoutInflater.from(ct).inflate(R.layout.item_user_order, parent, false);
		tv_order_create_date = (TextView) convertView.findViewById(R.id.tv_order_create_date);

		tv_order_number = (TextView) convertView.findViewById(R.id.tv_order_no);
		tv_receive_user = (TextView) convertView.findViewById(R.id.tv_receive_user);
//		btn_rob_order = (TextView) convertView.findViewById(R.id.btn_rob_order);
		tv_not_huo = (TextView) convertView.findViewById(R.id.tv_not_huo);
		tv_huihua = (ImageView) convertView.findViewById(R.id.tv_huihua);
		tv_order_expressType = (TextView) convertView.findViewById(R.id.tv_express);
		lv = (ListView) convertView.findViewById(R.id.list_order_things);
		tv_send_orders=(TextView)convertView.findViewById(R.id.tv_send_orders);
		ll_un_receive_order=(LinearLayout) convertView.findViewById(R.id.ll_un_receive_order);
		slv_right_tri_express= (SlantedTextView) convertView.findViewById(R.id.slv_right_tri_express);
		slv_right_tri_self_lift= (SlantedTextView) convertView.findViewById(R.id.slv_right_tri_self_lift);

		final GetUnReceiveOrderBean bean = data.get(position);
		
		tv_order_create_date.setText("下单时间:" + bean.CreateDate);
		tv_order_number.setText("订单号:" + bean.TradeId);

		if (bean.ExpressType == 0) {
			tv_receive_user.setText(bean.NickName+"("+bean.Mobile+")");
			tv_send_orders.setVisibility(View.VISIBLE);
			slv_right_tri_express.setVisibility(View.VISIBLE);
			
			//物流you货
			tv_send_orders.setVisibility(View.VISIBLE);
			tv_send_orders.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					fonfirmOrderNet = new ReceivingFonfirmOrderNet(ct);
					fonfirmOrderNet.setData(bean.TradeId);
					
					OrderAdapter.this.data.clear();
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							ReceiveOrderFragment.handler.obtainMessage().sendToTarget();
//							ByCounselorNet byCounselorNet=new ByCounselorNet(activity);
//							byCounselorNet.setData(1,30,1);
							
							//跳回到首页
//							Skip.mNext(activity, HomeActivity.class);
						}
					}, 1000);

				}
			});

		} else if (bean.ExpressType == 1) {
			tv_receive_user.setText(bean.NickName);
			tv_huihua.setVisibility(View.VISIBLE);
//			btn_rob_order.setVisibility(View.VISIBLE);
			slv_right_tri_self_lift.setVisibility(View.VISIBLE);
		}

		list = new ArrayList<OrderItemBean>();
		for (int i = 0; i < bean.OrderItem.size(); i++) {
			
			OrderItemBean itemBean = new ResultGetUnReceiveOrderBean().new OrderItemBean();
			itemBean=bean.OrderItem.get(i);
			list.add(itemBean);
		}

		OrderThingsAdapter adapter = new OrderThingsAdapter(activity, list);

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
				bundle.putString("Title", list.get(i).getTitle());
				bundle.putString("ImageUrl", list.get(i).getImageUrl());
				bundle.putString("StyleCode", list.get(i).getStyleCode());
				bundle.putString("BarcodeCode", list.get(i).getBarcodeCode());
				bundle.putDouble("Price", list.get(i).getPrice());
				bundle.putString("Attr", list.get(i).getAttr());
				
				Skip.mNextFroData(activity, ShopDetailActivity.class, bundle);
			}
		});

		/**
		 * 标识有货
		 */
//		btn_rob_order.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (bean.OrderStatus != 40 || bean.OrderStatus != 30) {
//
//					fonfirmOrderNet = new ReceivingFonfirmOrderNet(ct);
//					fonfirmOrderNet.setData(bean.TradeId);
//
//					OrderAdapter.this.data.clear();
//					new Handler().postDelayed(new Runnable() {
//
//						@Override
//						public void run() {
//							ReceiveOrderFragment.handler.obtainMessage().sendToTarget();
////							ByCounselorNet byCounselorNet=new ByCounselorNet(activity);
////							byCounselorNet.setData(1,30,1);
//							Skip.mNext(activity, MainActivity.class);
//
//						}
//					}, 1000);
//				}
//			}
//		});

		/**
		 * 标识无货
		 */
		tv_not_huo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (bean.OrderStatus != 40 || bean.OrderStatus != 30) {
					backNet = new OrderBackNet(ct);
					backNet.setData(bean.TradeId);

					OrderAdapter.this.data.clear();
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							ReceiveOrderFragment.handler.obtainMessage().sendToTarget();

						}
					}, 1000);

				}

			}
		});

		
		/**
		 * 会话
		 */
		tv_huihua.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 启动单聊会话界面
				if (RongIM.getInstance() != null)
					RongIM.getInstance().startPrivateChat(ct, bean.VipId, "title");
			}
		});

		return convertView;
	}

}
