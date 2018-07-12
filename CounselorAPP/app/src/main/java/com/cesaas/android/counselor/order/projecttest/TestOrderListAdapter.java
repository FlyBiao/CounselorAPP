package com.cesaas.android.counselor.order.projecttest;

//public class TestOrderListAdapter extends BaseAdapter {
//	
//	public static BitmapUtils bitmapUtils;
//	private Context ct;
//	private Activity activity;
//	
//	
//	 private List<GetByCounselorBean> data= new ArrayList<GetByCounselorBean>();
//	 private List<GetByCounselorBeanItemBean> list=new ArrayList<GetByCounselorBeanItemBean>();
//	 
//
//	 public TestOrderListAdapter(Context ct, Activity activity) {
//			this.ct = ct;
//			this.activity = activity;
//		}
//
//		public TestOrderListAdapter(Context ct, List<GetByCounselorBean> data) {
//			this.ct = ct;
//			this.data = data;
//		}
//		
//		/**
//		 * 当ListView数据发生变化时,调用此方法来更新ListView
//		 * 
//		 * @param list
//		 */
//		public void updateListView(List<GetByCounselorBean> list) {
//			this.data = list;
//			notifyDataSetChanged();
//		}
//
//		public void remove(GetO2OOrderListBean order) {
//			this.data.remove(order);
//			notifyDataSetChanged();
//		}
//	 
//	 
//	@Override
//	public int getCount() {
//		return data.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return data.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		ViewHolder holder;
//		SliderView slideView = (SliderView) convertView;
//		if (slideView == null) {
//			View itemView = LayoutInflater.from(ct).inflate(R.layout.item_pending_order, null);
//
//			slideView = new SliderView(ct);
//			slideView.setContentView(itemView);
//			holder = new ViewHolder(slideView);
//			slideView.setTag(holder);
//		} else {
//			holder = (ViewHolder) slideView.getTag();
//		}
//		
//		final GetByCounselorBean bean=data.get(position);
//		slideView.shrink();
//		holder.tv_pending_order_id.setText(bean.TradeId);
//		
//		if(bean.ExpressType==zero){//物流发货
//			
//			holder.tv_pending_order_state.setText("物流订单");
//			holder.ll_my_pending_receive_order.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.layer_pending_list2));
//			holder.tv_pending_order_state.setTextColor(activity.getResources().getColor(R.color.forestgreen));
//			holder.tv_pending_express_send.setVisibility(View.VISIBLE);
//			//物流订单发货点击事件监听
//			holder.tv_pending_express_send.setVisibility(View.VISIBLE);
//			holder.tv_pending_express_send.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					Bundle bundle = new Bundle();
//					bundle.putString("TradeId", bean.TradeId);
//					Skip.mNextFroData(activity, ExpressListActivity.class, bundle);
//				}
//			});
//			
//		}else if(bean.ExpressType==1){//到店自提扫描发货
//			holder.tv_pending_order_state.setText("到店自提");
//			holder.ll_my_pending_receive_order.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.layer_pending_list));
//			holder.tv_pending_order_state.setTextColor(activity.getResources().getColor(R.color.color_title_bar));
//			holder.tv_pending_express_send.setVisibility(View.GONE);
//		}
//		
//		list = new ArrayList<GetByCounselorBeanItemBean>();
//		for (int i = zero; i < bean.OrderItem.size(); i++) {
//			GetByCounselorBeanItemBean itemBean=new ResultGetByCounselorBean().new GetByCounselorBeanItemBean();
//			itemBean=bean.OrderItem.get(i);
//			list.add(itemBean);
//			holder.tv_shop_count.setText( "（"+list.size()+"件"+"）");
//		}
//		
//		MyPendingOrderThingsAdapter adapter=new MyPendingOrderThingsAdapter(ct, list);
//		int totalHeight = zero;
//		for (int i = zero; i < adapter.getCount(); i++) {
//			View listItem = adapter.getView(i, null, holder.lv);
//			listItem.measure(zero, zero);
//			totalHeight += listItem.getMeasuredHeight();
//		}
//		
//		ViewGroup.LayoutParams params = holder.lv.getLayoutParams();
//		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
//		holder.lv.setLayoutParams(params);
//		
//		holder.lv.setAdapter(adapter);
//		
//		holder.deleteHolder.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				data.remove(position);
//				ToastFactory.getLongToast(ct, "退单成功");
//				notifyDataSetChanged();
//			}
//		});
//		
//		return slideView;
//	}
//
//	private static class ViewHolder {
//		private HorizontalListView lv;
//		 private TextView tv_pending_order_id;
//		 private TextView tv_shop_count;
//		 private TextView tv_pending_order_state;
//		 private TextView tv_pending_express_send;
//		 private LinearLayout ll_my_pending_receive_order;
//		 public ViewGroup deleteHolder;
//
//		ViewHolder(View view) {
//			lv = (HorizontalListView) view.findViewById(R.id.list_pending_order_things);
//			tv_pending_order_id = (TextView) view.findViewById(R.id.tv_pending_order_id);
//			tv_shop_count = (TextView) view.findViewById(R.id.tv_shop_count);
//			tv_pending_order_state = (TextView) view.findViewById(R.id.tv_pending_order_state);
//			tv_pending_express_send = (TextView) view.findViewById(R.id.tv_pending_express_send);
//			ll_my_pending_receive_order = (LinearLayout) view.findViewById(R.id.ll_my_pending_receive_order);
//			
//			deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
//		}
//	}


//}
