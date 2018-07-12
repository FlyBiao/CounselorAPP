package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.GetUnOrderDetailAdapter;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean.GetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean.OrderItemBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.net.GetDistributionOrderNet;
import com.cesaas.android.counselor.order.net.GetUnReceiveOrderNet;
import com.cesaas.android.counselor.order.net.OrderDetailNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 接单订单详情
 * @author FGB
 *
 */
@ContentView(R.layout.get_un_receive_order_activity)
public class GetUnReceiveOrderDetailActivity extends BasesActivity{

	public static final String TAG="GetUnReceiveOrderDetailActivity";
	
	@ViewInject(R.id.lv_un_order_detail)
	private ListView lv_un_order_detail;
	@ViewInject(R.id.iv_un_orderdetail_back)
	private LinearLayout iv_un_orderdetail_back;
	
	private String TradeId;
	
//	OrderItem
	private String createDate;
	private String attr;//商品规格
	private String imageUrl;//图片url
	private String oId;//
	private String orderId;//订单id
	private int orderStatus;//订单状态
	private double price;//商品价格
	private int quantity;//数量
	private String shopStyleId;//商品类型ID
	private String title;//商品名称
	private String barcodeCode;//条形码
	private String styleCode;//款号
	
	private GetUnReceiveOrderNet net;
	
	private OrderItemBean orderItemBean=new ResultGetUnReceiveOrderBean().new OrderItemBean();
	private ArrayList<GetUnReceiveOrderBean> orderItemList=new ArrayList<GetUnReceiveOrderBean>();
	
	private OrderDetailNet detailNet;
	private GetDistributionOrderNet distributionOrderNet;
	
	private GetUnOrderDetailAdapter detailAdapter;
	private ArrayList<OrderDetailBean> orderDetailList= new ArrayList<OrderDetailBean>();
	private ArrayList<DistributionOrderBean> orderDetailList2= new ArrayList<DistributionOrderBean>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			TradeId=bundle.getString("TradeId");
			
//			OrderItem
			createDate=bundle.getString("CreateDate");
			attr=bundle.getString("Attr");
			imageUrl=bundle.getString("ImageUrl");
			oId=bundle.getString("OId");
			orderId=bundle.getString("OrderId");
			orderStatus=bundle.getInt("OrderStatus");
			price=bundle.getDouble("Price");
			quantity=bundle.getInt("Quantity");
			shopStyleId=bundle.getString("ShopStyleId");
			title=bundle.getString("Title");
			barcodeCode=bundle.getString("BarcodeCode");
			styleCode=bundle.getString("StyleCode");
		}
		
		net=new GetUnReceiveOrderNet(mContext);
		net.setData();
		
		detailNet=new OrderDetailNet(mContext);
		detailNet.setData(TradeId);
		
		initBack();
	}
	
	/**
	 * 初始化数据
	 */
	public void initData() {
		if (orderDetailList.size() >0) {
			detailAdapter = new GetUnOrderDetailAdapter(mContext,mActivity,orderDetailList,orderItemList);
			lv_un_order_detail.setAdapter(detailAdapter);
		}
	}
		
	
	public void onEventMainThread(ResultGetUnReceiveOrderBean msg) {
	
		for (int i = 0; i < orderItemList.size(); i++) {
			
			msg.TModel.get(i).OrderItem.get(0).setTitle(title);
			msg.TModel.get(i).OrderItem.get(0).setAttr(attr);
			msg.TModel.get(i).OrderItem.get(0).setImageUrl(imageUrl);
			msg.TModel.get(i).OrderItem.get(0).setBarcodeCode(barcodeCode);
			msg.TModel.get(i).OrderItem.get(0).setOId(oId);
			msg.TModel.get(i).OrderItem.get(0).setOrderId(orderId);
			msg.TModel.get(i).OrderItem.get(0).setOrderStatus(orderStatus);
			msg.TModel.get(i).OrderItem.get(0).setQuantity(quantity);
			msg.TModel.get(i).OrderItem.get(0).setStyleCode(styleCode);
			msg.TModel.get(i).OrderItem.get(0).setShopStyleId(shopStyleId);
			msg.TModel.get(i).OrderItem.get(0).setPrice(price);
		}
		
		this.orderItemList.addAll(msg.TModel);
	}
	
	public void onEventMainThread(ResultOrderDetailBean msg) {
		this.orderDetailList.addAll(msg.TModel);
		initData();
	}
	
	/**
	 * 返回上一个页面
	 */
	public void initBack(){
		//
		iv_un_orderdetail_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
}
