package com.cesaas.android.counselor.order.activity;

import io.rong.imkit.RongIM;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.OrderDetailAdapter;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.express.view.ExpressListActivity;
import com.cesaas.android.counselor.order.net.GetDistributionOrderNet;
import com.cesaas.android.counselor.order.net.OrderDetailNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 订单状态详情
 * @author FGB
 *
 */
@ContentView(R.layout.order_detail_layout)
public class CustomSendOrderDetailActivity extends BasesActivity{

	@ViewInject(R.id.lv_order_detail)
	private ListView lv_order_detail;
	@ViewInject(R.id.iv_orderdetail_back)
	private LinearLayout iv_backDetail;
	
	@ViewInject(R.id.ll_session)
	private LinearLayout ll_session;
	@ViewInject(R.id.ll_express_order)
	private LinearLayout ll_express_order;
	@ViewInject(R.id.tv_express_send_order)
	private TextView tv_express_send_order;
	@ViewInject(R.id.tv_order_detail_session)
	private TextView tv_order_detail_session;
	@ViewInject(R.id.tv_scan_send_order)
	private TextView tv_scan_send_order;
	@ViewInject(R.id.tv_order_session)
	private TextView tv_order_session;
	@ViewInject(R.id.rl_order_hid_send)
	private RelativeLayout rl_order_hid_send;
	
	private String TradeId;
	public static String tempTradeId;
	private String orderId;
	private int orderStatus;//订单状态
	private int expressType;//物流类型  1：到店自提，zero：物流发货
	private String vipId;
	private String nickName;
	
	private OrderDetailNet detailNet;
	private GetDistributionOrderNet distributionOrderNet;
	
	private OrderDetailAdapter detailAdapter;
	private ArrayList<OrderDetailBean> orderDetailList= new ArrayList<OrderDetailBean>();
	private ArrayList<DistributionOrderBean> orderDetailList2= new ArrayList<DistributionOrderBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			TradeId=bundle.getString("TradeId");
			tempTradeId=TradeId;
		}
//		
		detailNet=new OrderDetailNet(mContext);
		detailNet.setData(tempTradeId);
		
		distributionOrderNet=new GetDistributionOrderNet(mContext);
		distributionOrderNet.setData(tempTradeId);
		
		session();
		scanSendOrder();
		initBack();
	}
		
	public void initData() {
		if (orderDetailList.size() >0) {
			detailAdapter = new OrderDetailAdapter(mContext,mActivity,orderDetailList,orderDetailList2);
			lv_order_detail.setAdapter(detailAdapter);
		}
	}
	public void onEventMainThread(ResultOrderDetailBean msg) {
		this.orderDetailList.addAll(msg.TModel);
		
		for (int i = 0; i < orderDetailList.size(); i++) {
			expressType=orderDetailList.get(i).ExpressType;
			orderId=orderDetailList.get(i).OrderId;
		}
		
		if(expressType==0){//快递方式取货
			ll_express_order.setVisibility(View.VISIBLE);
			
			 //物流发货
			tv_express_send_order.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putString("TradeId", orderId);
					Skip.mNextFroData(mActivity, ExpressListActivity.class, bundle);
				}
			});
			
		}else if(expressType==1){//到店自提取货
			ll_session.setVisibility(View.VISIBLE);
			
		}
		
		initData();
	}
	public void onEventMainThread(ResultDistributionOrderBean msg) {
		this.orderDetailList2.addAll(msg.TModel);
		
		for (int i = 0; i < orderDetailList2.size(); i++) {
			vipId=orderDetailList2.get(i).VipId;
			nickName=orderDetailList2.get(i).NickName;
			
			orderStatus=orderDetailList2.get(i).OrderStatus;
		}
		
		if(orderStatus!=10 || orderStatus==40 || orderStatus==81 || orderStatus==100){
			rl_order_hid_send.setVisibility(View.GONE);
		}
		
		initData();
	}
	
	/**
	 * 扫描发货
	 */
	public void scanSendOrder(){
		 tv_scan_send_order.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mNext(mActivity, ScanSendActivity.class);
			}
		});
		
	}
	
	/**
	 * 会话
	 */
	public void session(){
		
		tv_order_detail_session.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//启动单聊会话界面
            	if (RongIM.getInstance() != null)
            	   RongIM.getInstance().startPrivateChat(mContext, vipId, nickName);
			}
		});
		
		tv_order_session.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//启动单聊会话界面
            	if (RongIM.getInstance() != null)
            	   RongIM.getInstance().startPrivateChat(mContext, vipId, nickName);
			    }
		});
	}
	
	
	/**
	 * 返回上一个页面
	 */
	public void initBack(){
		//
		iv_backDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
	}

}
