package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.hugo.android.scanner.CaptureActivity;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetOrderBean.OrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultGetOrderBean.OrderItemBean;
import com.cesaas.android.counselor.order.express.view.ExpressListActivity;
import com.cesaas.android.counselor.order.net.GetOrderNet;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * 验货Activity
 * @author FGB
 *
 */
public class CheckCargoActivity extends BasesActivity{

	private Button btn_scan_check_cargo;
	private EditText et_cargo_code;
	private TextView tv_scan_cargo;
	private LinearLayout ll_check_cargo_back;
	private ListView lv_check_cargo_info;
	private TextView tv_check_cargo_send;
	private TextView tv_is_check_cargo;
	
	private int REQUEST_CONTACT = 20;
	final int RESULT_CODE=101;
	private String scanCode;
	private String TradeId;
	
	private GetOrderNet detailNet;
	private CheckCargoAdapter adapter;
	CheckCargoThingsAdapter thingsAdapter;
	private ArrayList<OrderDetailBean> orderDetailList= new ArrayList<OrderDetailBean>();
	
	List<OrderItemBean> list= new ArrayList<OrderItemBean>();
	List<String> listItem;
	List<Boolean> success;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_cargo);
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			TradeId=bundle.getString("TradeId");
		}
		detailNet=new GetOrderNet(mContext);
		detailNet.setData(TradeId);
		
		initView();
		onClickListener();
		mBack();
	}
	
	public void initData() {
		
		if (orderDetailList.size() >0) {
			adapter = new CheckCargoAdapter(mContext,mActivity,orderDetailList);
			lv_check_cargo_info.setAdapter(adapter);
		}
	}
	
	public void onEventMainThread(ResultGetOrderBean msg) {
		this.orderDetailList.addAll(msg.TModel);
		initData();
	}
	
	private void initView() {
		btn_scan_check_cargo=(Button) findViewById(R.id.btn_scan_check_cargo);
		et_cargo_code=(EditText) findViewById(R.id.et_cargo_code);
		tv_scan_cargo=(TextView) findViewById(R.id.tv_scan_cargo);
		ll_check_cargo_back=(LinearLayout) findViewById(R.id.ll_check_cargo_back);
		lv_check_cargo_info=(ListView) findViewById(R.id.lv_check_cargo_info);
		tv_check_cargo_send=(TextView) findViewById(R.id.tv_check_cargo_send);
	}

	public void onClickListener(){
		
		//扫描验货
		tv_scan_cargo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mScanCheckCargoResult(mActivity, CaptureActivity.class, REQUEST_CONTACT);
			}
		});
		
		//验货
		btn_scan_check_cargo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listItem=new ArrayList<String>();
				for (int i=0;i<list.size();i++) {
					listItem.add(list.get(i).BarcodeCode);
				}
				
				if(!"".equals(et_cargo_code.getText().toString())){
						
					if(listItem.indexOf(et_cargo_code.getText().toString())!=-1){
						ToastFactory.getLongToast(mContext, "验货成功！");
						adapter.updateListItemView(listItem.indexOf(et_cargo_code.getText().toString()));
						thingsAdapter.updateListItemView(listItem.indexOf(et_cargo_code.getText().toString()));
						return;
					}
					else{
						ToastFactory.show(mContext, "验货失败,商品条码有误！", ToastFactory.CENTER);
					}
				}else{
					ToastFactory.getLongToast(mContext, "请输入商品编码验货！");
				}
			}
		});
		
		//发货
		tv_check_cargo_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				success=new ArrayList<Boolean>();
				for (int i=0;i<list.size();i++) {
					success.add(list.get(i).isSuccess());
				}
				if(success.contains(false)){//提示没有验证完
					ToastFactory.getLongToast(mContext, "请验货成功后再发货！");
				}
				else{//成功
					Bundle bundle = new Bundle();
					bundle.putString("TradeId", TradeId);
					bundle.putString("PushExpressOrder", "1001");
					Skip.mNextFroData(mActivity, ExpressListActivity.class, bundle);
					finish();
				}
			}
		});
	}
	
	public void mBack(){
		ll_check_cargo_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
	
	/**
	 * 获取处理扫描Activity返回的数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 获取扫描发货返回的condeID
		if (resultCode == RESULT_CODE && data.getStringExtra("resultCode")!=null) {
			
			if(data.getStringExtra("ScanCheckCargo")!=null){
				if(data.getStringExtra("ScanCheckCargo").equals("008")){
					listItem=new ArrayList<String>();
					for (int i=0;i<list.size();i++) {
						listItem.add(list.get(i).BarcodeCode);
					}
					scanCode= data.getStringExtra("resultCode");
					et_cargo_code.setText(scanCode);
					if(listItem.indexOf(scanCode)!=-1){
						ToastFactory.getLongToast(mContext, "验货成功！");
						adapter.updateListItemView(listItem.indexOf(et_cargo_code.getText().toString()));
						thingsAdapter.updateListItemView(listItem.indexOf(et_cargo_code.getText().toString()));
						
						return;
						
					}else{
						ToastFactory.show(mContext, "验货失败,商品条码有误！", ToastFactory.CENTER);
					}
				}
			}
			
		}else{
			ToastFactory.show(mContext, "没有获取扫描结果！！", ToastFactory.CENTER);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * 验货adapter
	 * @author fgb
	 *
	 */
	public class CheckCargoAdapter extends BaseAdapter{

		public BitmapUtils bitmapUtils;
		private Context ct;
		private Activity activity;
		private ListView lv;
		private List<OrderDetailBean> data=new ArrayList<OrderDetailBean>();
		private List<OrderItemBean> list = new ArrayList<OrderItemBean>();
		
		public CheckCargoAdapter(Context ct,Activity activity,List<OrderDetailBean> data){
			this.ct = ct;
			this.activity=activity;
			this.data = data;
			bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
			bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
			bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
		}
		
		/**
		 * 当ListView数据发生变化时,调用此方法来更新ListView
		 * 
		 * @param list
		 */
		public void updateListView(List<OrderDetailBean> list) {
			this.data = list;
		}
		
		public void updateListItemView(int item) {
			list.get(item).setSuccess(true);
			notifyDataSetChanged();
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
			if(convertView==null){
				convertView = LayoutInflater.from(ct).inflate(R.layout.check_cargo_list, null);
				lv=(ListView) convertView.findViewById(R.id.list_check_cargo);
				
				if(data.size()!=0){
					OrderDetailBean detail=data.get(position);
					list = new ArrayList<OrderItemBean>();
					
					for(int i=0;i<detail.OrderItem.size();i++){
						
						OrderItemBean itemBean=new ResultGetOrderBean().new OrderItemBean();
						itemBean=detail.OrderItem.get(i);
						
						list.add(itemBean);
					}
					
					thingsAdapter=new CheckCargoThingsAdapter(activity, list);
					int totalHeight = 0;
					for (int i = 0; i < thingsAdapter.getCount(); i++) {
						View listItem = thingsAdapter.getView(i, null, lv);
						listItem.measure(0, 0);
						totalHeight += listItem.getMeasuredHeight();
					}
			
					ViewGroup.LayoutParams params = lv.getLayoutParams();
					params.height = totalHeight + (lv.getDividerHeight() * (thingsAdapter.getCount() - 1));
					((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
					lv.setLayoutParams(params);
					lv.setAdapter(thingsAdapter);
				}
			}
			
			return convertView;
		}

	}
	
	/**
	 * 商品列表adapter
	 * @author fgb
	 *
	 */
	public class CheckCargoThingsAdapter extends BaseAdapter{
		Context context;
		private BitmapUtils bitmapUtils;
		
		private TextView tv_check_cargo_quantity;
		private TextView tv_check_cargo_attr;
		private TextView tv_check_cargo_b_code;
		private TextView tv_check_cargo_title;
		
		public CheckCargoThingsAdapter(Context ct, List<OrderItemBean> data) {
			this.context = ct;
			list = data;
//			this.list = data;
			bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
			bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
			bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
		}
		
		public void updateListItemView(int item) {
			list.get(item).setSuccess(true);
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_check_cargo, parent, false);
			tv_check_cargo_quantity=(TextView) convertView.findViewById(R.id.tv_check_cargo_quantity);
			tv_check_cargo_attr=(TextView) convertView.findViewById(R.id.tv_check_cargo_attr);
			tv_check_cargo_b_code=(TextView) convertView.findViewById(R.id.tv_check_cargo_b_code);
			tv_check_cargo_title=(TextView) convertView.findViewById(R.id.tv_check_cargo_title);
			tv_is_check_cargo=(TextView) convertView.findViewById(R.id.tv_is_check_cargo);
			
			tv_check_cargo_b_code.setText("条码:"+list.get(position).BarcodeCode);
			tv_check_cargo_attr.setText(list.get(position).Attr);
			tv_check_cargo_title.setText(list.get(position).Title);
			tv_check_cargo_quantity.setText("x" + list.get(position).Quantity);
			
			//如果第position 个item 的success 字段返回true 显示小绿勾
			if(list.get(position).isSuccess()){
					tv_is_check_cargo.setVisibility(View.VISIBLE);
			}else{
					tv_is_check_cargo.setVisibility(View.GONE);
			}
			
			return convertView;
		}
		
		
	}

}
