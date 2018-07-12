package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hugo.android.scanner.CaptureActivity;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultExpressBean;
import com.cesaas.android.counselor.order.bean.ResultExpressBean.ExpressBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.ExpressNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 物流公司页面
 * @author fgb
 *
 */
@ContentView(R.layout.activity_express_layout)
public class ExpressActivity extends BasesActivity{

	public static final String TAGS="ExpressActivity";
	
	@ViewInject(R.id.lv_express)
	private ListView lv_express;
	@ViewInject(R.id.tv_scan_sendOrder)
	private TextView tv_scan_sendOrder;
	@ViewInject(R.id.et_order_id)
	private EditText et_order_id;
	@ViewInject(R.id.btn_send)
	private Button btn_send;
	@ViewInject(R.id.btn_online)
	private Button btn_online;
	
	private int REQUEST_CONTACT = 20;
	final int RESULT_CODE=101;
	private String expressId;//物流公司编号
	private String WayBillNo;//快递编号
	private String expressOrderId;//订单号
	
	private ExpressNet expressNet;
	private OrderExpressAdapter expressAdapter;
	private ArrayList<ExpressBean> list= new ArrayList<ExpressBean>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			expressOrderId=bundle.getString("expressOrderId");
			
		}
		super.onCreate(savedInstanceState);
		expressNet=new ExpressNet(mContext);
		expressNet.setData();
		
		initView();
	}
	
	/**
	 * 初始化数据
	 */
	public void initData() {
		if (list.size() >0) {
			expressAdapter = new OrderExpressAdapter(mContext);
			lv_express.setAdapter(expressAdapter);
			expressAdapter.updateListView(list);	
		}
	}
	
	public void onEventMainThread(ResultExpressBean msg) {
		list=msg.TModel;
		initData();
	}
	
	
	/**
	 * 初始化视图
	 */
	public void initView(){
		
		/**
		 * 扫描发货订单号
		 */
		tv_scan_sendOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//调用扫描二维码$条码
				Skip.mActivityResult(mActivity, CaptureActivity.class,REQUEST_CONTACT);
			}
		});
		
		
		/**
		 * 物流发货
		 */
		btn_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(et_order_id.getText().toString())){
					ToastFactory.show(mActivity, "物流单号不能为空！", ToastFactory.CENTER);
				}
				else{
					Bundle bundle=new Bundle();
					bundle.putString("CodeId", expressOrderId);
					bundle.putString("SendType", Constant.EXPRESS_SEND);
					bundle.putString("expressId", expressId);
					bundle.putString("WayBillNo", WayBillNo);
					Skip.mNextFroData(mActivity, ScanSendDetailActivity.class, bundle);

				}
			}
		});
		
		/**
		 * 在线发货
		 */
		btn_online.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("CodeId", expressOrderId);
				bundle.putString("SendType", Constant.ONLIN_SEND);
				Skip.mNextFroData(mActivity, ScanSendDetailActivity.class, bundle);
			}
		});
	}
	
	/**
	 * 处理扫描Activity返回的数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode==RESULT_CODE) {
			WayBillNo=data.getStringExtra("resultCode");
			et_order_id.setText(WayBillNo);
		}
		else{
			String et_id=et_order_id.getText().toString();
			et_order_id.setText(et_id);
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	
	/**
	 * 物流公司数据适配器
	 * @author fgb
	 *
	 */
	public class OrderExpressAdapter extends BaseAdapter{

		private Context ct;
		private int index = -1;//// 标记用户当前选择的那一快递
		private List<ExpressBean> data=new ArrayList<ExpressBean>();
		
		public OrderExpressAdapter(Context ct){
			this.ct=ct;
		}
		
		/**
		 * 当ListView数据发生变化时,调用此方法来更新ListView
		 * 
		 * @param list
		 */
		public void updateListView(List<ExpressBean> list) {
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
			
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(ct).inflate(R.layout.item_express_list, null);
				viewHolder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
				viewHolder.selectBtn=(RadioButton) convertView.findViewById(R.id.radio);
				viewHolder.rl_radio_bg=(RelativeLayout) convertView.findViewById(R.id.rl_radio_bg);
				
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			final ExpressBean bean=data.get(position);
			viewHolder.tv_name.setText(bean.Name);
			
			viewHolder.selectBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked) {
						expressId=bean.Id;//物流编号
						index = position;
						notifyDataSetChanged();
					}
				}

			});
			
			if (index == position) {//判断选中的条目和当前的条目是否相等
				viewHolder.selectBtn.setChecked(true);
			} else {
				viewHolder.selectBtn.setChecked(false);
			}
			
			return convertView;
		}
		
		public class ViewHolder {
			TextView tv_name;
			RadioButton selectBtn;
			RelativeLayout rl_radio_bg;
		}
	}
	
	
}
