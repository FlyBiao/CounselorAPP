package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.CheckCargoActivity;
import com.cesaas.android.counselor.order.bean.ResultBiaoGetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultBiaoGetByCounselorBean.GetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultBiaoGetByCounselorBean.GetByCounselorBeanItemBean;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.listview.HorizontalListView;
import com.cesaas.android.counselor.order.net.OrderStoreBackNet;
import com.cesaas.android.counselor.order.projecttest.list.SliderView;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.BitmapUtils;

/**
 * 待发货订单Adapter
 * @author FGB
 *
 */
public class WaitOutOrderListAdapter extends BaseAdapter {
	
	public static BitmapUtils bitmapUtils;
	private Context ct;
	private Activity activity;
	private int positions;
	
	 private List<GetByCounselorBean> data= new ArrayList<GetByCounselorBean>();
	 private List<GetByCounselorBeanItemBean> list=new ArrayList<GetByCounselorBeanItemBean>();

	 public WaitOutOrderListAdapter(Context ct, Activity activity) {
			this.ct = ct;
			this.activity = activity;
		}

		public WaitOutOrderListAdapter(Context ct, List<GetByCounselorBean> data) {
			this.ct = ct;
			this.data = data;
		}
		
		/**
		 * 当ListView数据发生变化时,调用此方法来更新ListView
		 * 
		 * @param list
		 */
		public void updateListView(List<GetByCounselorBean> list) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		positions=position;
		ViewHolder holder;
		SliderView slideView = (SliderView) convertView;
		if (slideView == null) {
			View itemView = LayoutInflater.from(ct).inflate(R.layout.item_pending_order, null);

			slideView = new SliderView(ct);
			slideView.setContentView(itemView);
			holder = new ViewHolder(slideView);
			slideView.setTag(holder);
		} else {
			holder = (ViewHolder) slideView.getTag();
		}
		
		final GetByCounselorBean bean=data.get(position);
		slideView.shrink();
		
		holder.tv_pending_order_id.setText(bean.TradeId);
		if(bean.ExpressType==0){//物流发货
			
			holder.tv_pending_order_state.setText("物流订单");
			holder.ll_my_pending_receive_order.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.layer_pending_list2));
			holder.tv_pending_order_state.setTextColor(activity.getResources().getColor(R.color.forestgreen));
			holder.tv_pending_express_send.setVisibility(View.VISIBLE);
			//物流订单发货点击事件监听
			holder.tv_pending_express_send.setVisibility(View.VISIBLE);
			holder.tv_pending_express_send.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putString("TradeId", bean.TradeId);
					Skip.mNextFroData(activity, CheckCargoActivity.class, bundle);
				}
			});
			
		}else if(bean.ExpressType==1){//到店自提扫描发货
			holder.tv_pending_order_state.setText("到店自提");
			holder.ll_my_pending_receive_order.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.layer_pending_list));
			holder.tv_pending_order_state.setTextColor(activity.getResources().getColor(R.color.color_title_bar));
			holder.tv_pending_express_send.setVisibility(View.GONE);
		}
		
		list = new ArrayList<GetByCounselorBeanItemBean>();
		for (int i = 0; i < bean.OrderItem.size(); i++) {
			GetByCounselorBeanItemBean itemBean=new ResultBiaoGetByCounselorBean().new GetByCounselorBeanItemBean();
			itemBean=bean.OrderItem.get(i);
			list.add(itemBean);
			holder.tv_shop_count.setText( "（"+list.size()+"件"+"）");
		}
		
		MyPendingOrderThingsAdapter adapter=new MyPendingOrderThingsAdapter(ct, list);
		int totalHeight = 0;
		for (int i = 0; i < adapter.getCount(); i++) {
			View listItem = adapter.getView(i, null, holder.lv);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		
		ViewGroup.LayoutParams params = holder.lv.getLayoutParams();
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		holder.lv.setLayoutParams(params);
		
		holder.lv.setAdapter(adapter);
		
		holder.deleteHolder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new MyAlertDialog(ct).mInitShow("温馨提示", "是否确定退单",
					"退单", "点错了", new ConfirmListener() {
						@Override
						public void onClick(Dialog dialog) {
							OrderStoreBackNet backNet=new OrderStoreBackNet(ct);
							backNet.setData(bean.TradeId);
							data.remove(position);
							ToastFactory.getLongToast(ct, "退单成功");
							notifyDataSetChanged();
							new Handler().postDelayed(new Runnable() {

								@Override
								public void run() {
									//刷新待处理单数
//									HomeFragment.handlerRefreshOrder();
								}
							}, 1500);
						}
					}, null);
			}
		});
		return slideView;
	}
	
	private static class ViewHolder {
		private HorizontalListView lv;
		 private TextView tv_pending_order_id;
		 private TextView tv_shop_count;
		 private TextView tv_pending_order_state;
		 private TextView tv_pending_express_send;
		 private LinearLayout ll_my_pending_receive_order;
		 public ViewGroup deleteHolder;

		ViewHolder(View view) {
			lv = (HorizontalListView) view.findViewById(R.id.list_pending_order_things);
			tv_pending_order_id = (TextView) view.findViewById(R.id.tv_pending_order_id);
			tv_shop_count = (TextView) view.findViewById(R.id.tv_shop_count);
			tv_pending_order_state = (TextView) view.findViewById(R.id.tv_pending_order_state);
			tv_pending_express_send = (TextView) view.findViewById(R.id.tv_pending_express_send);
			ll_my_pending_receive_order = (LinearLayout) view.findViewById(R.id.ll_my_pending_receive_order);
			
			deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
		}
	}


}
