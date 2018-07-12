package com.cesaas.android.counselor.order.express.view;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.GetListBean;
import com.cesaas.android.counselor.order.bean.GetListBean.GetList;
import com.cesaas.android.counselor.order.net.GetListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * 物流List
 * 
 * @author fgb
 * 
 */
@ContentView(R.layout.activity_express_list)
public class ExpressListOrderRouteActivity extends BasesActivity {

	@ViewInject(R.id.lv_express)
	private ListView lv_express;
	@ViewInject(R.id.il_send_message_back)
	private LinearLayout il_send_message_back;
	
	public static ExpressListOrderRouteActivity expressListActivity;

	private GetListNet getListNet;
	private ArrayList<GetList> getLis = new ArrayList<GetList>();
	
	private String TradeId;
	private String PushExpressOrder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			TradeId=bundle.getString("TradeId");
			PushExpressOrder=bundle.getString("PushExpressOrder");
		}
		
		getListNet = new GetListNet(mContext);
		getListNet.setData();
		
		expressListActivity=this;
		
		mBack();
	}
	
	
	public void onEventMainThread(GetListBean msg) {
		getLis=msg.TModel;
		initData();
	}
	
	public void initData(){
		lv_express.setAdapter(new CommonAdapter<GetList>(mContext,
				R.layout.item_express, getLis) {

			@Override
			public void convert(ViewHolder holder, final GetList getList, final int postion) {
				holder.setText(R.id.tv_express_name, getList.Name);
				
				lv_express.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

						Bundle bundle = new Bundle();
						bundle.putString("Type", getLis.get(i).Type);
						bundle.putString("Id", getLis.get(i).Id);
						bundle.putString("ExpressId", getLis.get(i).ExpressId);
						bundle.putString("TradeId", TradeId);
						bundle.putString("ExpressName", getLis.get(i).Name);
						bundle.putString("ExpressCode", getLis.get(i).ExpressCode);
						bundle.putString("MonthCode", getLis.get(i).CustCard);
						bundle.putString("PushExpressOrder", PushExpressOrder);
						Skip.mNextFroData(mActivity, SendExpressOrderRouteActivity.class, bundle);
					}
				});
			}

		});
		
	}
	
	public void mBack(){
		il_send_message_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}

}
