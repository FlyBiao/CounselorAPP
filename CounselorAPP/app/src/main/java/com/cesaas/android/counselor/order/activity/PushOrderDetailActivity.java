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
import com.cesaas.android.counselor.order.OrderActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.OrderDetailAdapter;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultOrderBackBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultReceivingFonfirmOrderBean;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserBean;
import com.cesaas.android.counselor.order.net.GetDistributionOrderNet;
import com.cesaas.android.counselor.order.net.OrderBackNet;
import com.cesaas.android.counselor.order.net.OrderDetailNet;
import com.cesaas.android.counselor.order.net.ReceivingFonfirmOrderNet;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 推送订单状态详情
 * @author FGB
 *
 */
@ContentView(R.layout.push_order_detail_layout)
public class PushOrderDetailActivity extends BasesActivity{

	@ViewInject(R.id.lv_push_order_detail)
	private ListView lv_push_order_detail;
	@ViewInject(R.id.iv_push_orderdetail_back)
	private LinearLayout iv_push_backDetail;
	
	@ViewInject(R.id.ll_push_youhuo)
	private LinearLayout ll_push_youhuo;
	@ViewInject(R.id.tv_push_instock)
	private TextView tv_push_instock;
	@ViewInject(R.id.tv_push_notorder)
	private TextView tv_push_notorder;
	@ViewInject(R.id.tv_push_order_sessions)
	private TextView tv_push_order_session;
	
	@ViewInject(R.id.ll_push_order_session)
	LinearLayout ll_push_order_session;
	@ViewInject(R.id.tv_push_send_order_session)
	private TextView tv_push_send_order_session;
	
	@ViewInject(R.id.ll_push_session)
	private LinearLayout ll_push_session;
	@ViewInject(R.id.ll_push_express_order)
	private LinearLayout ll_push_express_order;
	@ViewInject(R.id.tv_push_express_send_order)
	private TextView tv_push_express_send_order;
	@ViewInject(R.id.tv_push_order_detail_session)
	private TextView tv_push_order_detail_session;
	@ViewInject(R.id.tv_push_scan_send_order)
	private TextView tv_push_scan_send_order;
	@ViewInject(R.id.tv_push_order_express_session)
	private TextView tv_push_order_express_session;
	@ViewInject(R.id.rl_push_order_hid_send)
	private RelativeLayout rl_push_order_hid_send;
	
	private String TradeId;
	public static String tempTradeId;
	private String orderId;
	private int orderStatus;//订单状态
	private int expressType;//物流类型  1：到店自提，zero：物流发货
	private String vipId;
	private String userVipId;
	private String nickName;
	private int orderStutas;
	private int disOrderStatus;
	
	private OrderDetailBean bean;
	
	private OrderDetailNet detailNet;
	private GetDistributionOrderNet distributionOrderNet;
	
	private OrderBackNet backNet;// 标识无货
	private ReceivingFonfirmOrderNet fonfirmOrderNet;// 标识有货
	
	private OrderDetailAdapter detailAdapter;
	private ArrayList<OrderDetailBean> orderDetailList= new ArrayList<OrderDetailBean>();
	private ArrayList<DistributionOrderBean> orderDetailList2= new ArrayList<DistributionOrderBean>();
	
	private UserInfoNet userInfoNet;
    private UserBean userBean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			TradeId=bundle.getString("TradeId");
			tempTradeId=TradeId;
		}
		
		userInfoNet=new UserInfoNet(mContext);
		userInfoNet.setData();
//		
		detailNet=new OrderDetailNet(mContext);
		detailNet.setData(tempTradeId);
		
		distributionOrderNet=new GetDistributionOrderNet(mContext);
		distributionOrderNet.setData(tempTradeId);
		
//		session();
		scanSendOrder();
		initBack();
	}
	
	public void initData() {
		
		if (orderDetailList.size() >0) {
			detailAdapter = new OrderDetailAdapter(mContext,mActivity,orderDetailList,orderDetailList2);
			lv_push_order_detail.setAdapter(detailAdapter);
		}
	}
	
	public void onEventMainThread(ResultUserBean msg) {
    	userBean=msg.TModel;
    }
	
	public void onEventMainThread(ResultOrderBackBean msg) {
		if(msg.IsSuccess==true){
			ToastFactory.getLongToast(mContext, "确认无货成功！");
		}
		else{
			ToastFactory.getLongToast(mContext, "改订单已被确认无货！"+msg.Message);
		}
    	
    }
	
	public void onEventMainThread(ResultReceivingFonfirmOrderBean msg) {
		if(msg.IsSuccess==true){
			ToastFactory.getLongToast(mContext, "确认有货成功！");
			//跳转到首页
			Skip.mNext(mActivity, OrderActivity.class);
			finish();
		}
		else{
			ToastFactory.getLongToast(mContext, msg.Message);
		}
    	
    }
	
	public void onEventMainThread(ResultOrderDetailBean msg) {
		this.orderDetailList.addAll(msg.TModel);
		
		for (int i = 0; i < orderDetailList.size(); i++) {
			expressType=orderDetailList.get(i).ExpressType;
			orderId=orderDetailList.get(i).OrderId;
			orderStutas=orderDetailList.get(i).OrderStatus;
		}
		
		initData();
		sendOrder();
	}
	
	public void onEventMainThread(ResultDistributionOrderBean msg) {
		this.orderDetailList2.addAll(msg.TModel);
		sendOrder();
	}
	
	public void sendOrder(){
		if(orderDetailList2.size()<=0){
			ll_push_youhuo.setVisibility(View.VISIBLE);
			ll_push_express_order.setVisibility(View.GONE);
			ll_push_session.setVisibility(View.GONE);
			
		}else{
			
			for (int i = 0; i < orderDetailList2.size(); i++) {
				userVipId=orderDetailList2.get(i).VipId;
				nickName=orderDetailList2.get(i).NickName;
			}
				if(expressType==0){//快递方式取货
					if(userVipId.equals(userBean.VipId)){
						if(orderStutas==30 || orderStutas==0){
							ll_push_express_order.setVisibility(View.VISIBLE);
							
							 //物流发货
							tv_push_express_send_order.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Bundle bundle = new Bundle();
									bundle.putString("TradeId", orderId);
									bundle.putString("PushExpressOrder", "1001");
									Skip.mNextFroData(mActivity, CheckCargoActivity.class, bundle);
//									Skip.mNextFroData(mActivity, ExpressListActivity.class, bundle);
								}
							});
							
						}
						else{
							ll_push_express_order.setVisibility(View.GONE);
							ll_push_order_session.setVisibility(View.VISIBLE);
						}
				}
			}
			else if(expressType==1){//到店自提取货
					if(userVipId.equals(userBean.VipId)){
						if(orderStutas==30 || orderStutas==0){
							ll_push_session.setVisibility(View.VISIBLE);
							
							//扫描发货
							 tv_push_scan_send_order.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Bundle bundle = new Bundle();
									bundle.putString("PushscanOrder", "1001");
									Skip.mNextFroData(mActivity, ScanSendActivity.class, bundle);
								}
							});
							
						}
						
						else{
							ll_push_session.setVisibility(View.GONE);
							ll_push_order_session.setVisibility(View.VISIBLE);
						}
					}
				}
		session();
		}
	}
	
	/**
	 * 发货
	 */
	public void scanSendOrder(){
		
		 //有货
		 tv_push_instock.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fonfirmOrderNet = new ReceivingFonfirmOrderNet(mContext);
				fonfirmOrderNet.setData(orderId);
			}
		});
		 
		 //无货
		 tv_push_notorder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				backNet = new OrderBackNet(mContext);
				backNet.setData(orderId);
				
				finish();
			}
		});
		
	}
	
	/**
	 * 会话
	 */
	public void session(){
		
		tv_push_order_detail_session.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//启动单聊会话界面
            	if (RongIM.getInstance() != null)
            	   RongIM.getInstance().startPrivateChat(mContext, userVipId, nickName);
			}
		});
		
		tv_push_order_session.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//启动单聊会话界面
            	if (RongIM.getInstance() != null)
            	   RongIM.getInstance().startPrivateChat(mContext, "12323", "对方");
			}
		});
		
		tv_push_send_order_session.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//启动单聊会话界面
            	if (RongIM.getInstance() != null)
            	   RongIM.getInstance().startPrivateChat(mContext, userVipId, nickName);
			}
		});
		
		tv_push_order_express_session.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//启动单聊会话界面
            	if (RongIM.getInstance() != null)
            	   RongIM.getInstance().startPrivateChat(mContext, userVipId, nickName);
			}
		});
	}
	
	
	/**
	 * 返回上一个页面
	 */
	public void initBack(){
		//
		iv_push_backDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
	}

}
