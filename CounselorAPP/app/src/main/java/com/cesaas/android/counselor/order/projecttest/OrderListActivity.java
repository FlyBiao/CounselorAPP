package com.cesaas.android.counselor.order.projecttest;

//public class OrderListActivity extends BasesActivity{
//	
//	private SilderListView mListView;
//	
//	private GetByCounselorNet byCounselorNet;
//	 private ArrayList<GetByCounselorBean> orderLis= new ArrayList<GetByCounselorBean>();
//	 private TestOrderListAdapter adapter2;
//	 
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.test_order_list_);
//		mListView=(SilderListView) findViewById(R.id.list);
//		
//		byCounselorNet=new GetByCounselorNet(mContext);
//		byCounselorNet.setData(1,30,1);
//		
//		initListener();
//	}
//	
//	public void onEventMainThread(ResultGetByCounselorBean msg) {
//		if (msg != null) {
//			orderLis.addAll(msg.TModel);
//			adapter2=new TestOrderListAdapter(mContext, mActivity);
//			mListView.setAdapter(adapter2);
//		}
//		
//		adapter2.updateListView(orderLis);
//		
//	}
//	
//	/**
//	 * 订单详情
//	 */
//	public void initListener() {
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Bundle bundle = new Bundle();
//				bundle.putString("TradeId", orderLis.get(position).TradeId);
//				Skip.mNextFroData(mActivity, OrderDetailActivity.class, bundle);
//			}
//		});
//	}
//}
