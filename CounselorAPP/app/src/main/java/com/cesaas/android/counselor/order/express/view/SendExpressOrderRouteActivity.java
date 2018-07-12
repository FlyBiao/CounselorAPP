package com.cesaas.android.counselor.order.express.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.OrderDetailActivity;
import com.cesaas.android.counselor.order.activity.main.NewMainActivity;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultOfflineConfrimBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultOrderQueryBean;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.GetDistributionOrderNet;
import com.cesaas.android.counselor.order.net.OfflineConfrimNet;
import com.cesaas.android.counselor.order.net.OrderDetailNet;
import com.cesaas.android.counselor.order.net.OrderQueryNet;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.adapter.ReceiveOrderDetailAdapter;
import com.cesaas.android.order.bean.ResultReceiveOrderDetailsBean;
import com.cesaas.android.order.bean.ResultSendOfflineOrderBean;
import com.cesaas.android.order.net.SendOfflineOrderNet;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

import java.util.ArrayList;

import cn.hugo.android.scanner.CaptureActivity;

/**
 * 发货物流
 * 
 * @author FGB
 * 
 */
@ContentView(R.layout.activity_send_message)
public class SendExpressOrderRouteActivity extends BasesActivity {

	@ViewInject(R.id.fl_send_realtabcontent)
	private FrameLayout fl_send_realtabcontent;
	@ViewInject(R.id.ll_express_info)
	private LinearLayout ll_express_info;
	@ViewInject(R.id.ll_send_back)
	private LinearLayout ll_send_back;
	@ViewInject(R.id.rl_express_send_order)
	private RelativeLayout rl_express_send_order;
	@ViewInject(R.id.rl_send)
	private RelativeLayout rl_send;
	@ViewInject(R.id.tv_express_send)
	private TextView tv_express_send;
	@ViewInject(R.id.btn_express_message)
	private Button btn_express_message;
	@ViewInject(R.id.tv_express_type)
	private TextView tv_express_type;
	@ViewInject(R.id.tv_message_title)
	private TextView tv_message_title;
	@ViewInject(R.id.tv_scan_express_order)
	private TextView tv_scan_express_order;
	@ViewInject(R.id.et_express_order)
	private EditText et_express_order;
	@ViewInject(R.id.et_express_shop_weight)
	private EditText et_express_shop_weight;
	@ViewInject(R.id.et_express_shop_postage)
	private TextView et_express_shop_postage;

	@ViewInject(R.id.tv_send_express_user_name)
	private TextView tv_send_express_user_name;
	@ViewInject(R.id.tv_send_express_user_mobile)
	private TextView tv_send_express_user_mobile;
	@ViewInject(R.id.tv_express_address)
	private TextView tv_express_address;
	
	private double postage=0.0;
	private double weight=0.0;

	private String type;// 是否支持在线发货物流类型[zero:普通物流 1：在线快速下单]

	private String PushExpressOrder;
	private String tradeId;// 订单ID
	private static String tempTradeId;
	private String wayBillNo;// 扫描单号
	private String Id;// 物流ID
	private String ExpressName;// 物流ID
	private String ExpressCode;// 物流公司编码
	private String MonthCode;// 月结号

	//收货方信息
	private String receiverCompany;
	private String receiverName;
	private String receiverMobile;
	private String receiverProvinceName;
	private String receiverCityName;
	private String receiverExpAreaName;
	private String receiverAddress;

	// 寄件方信息
	private String country;// 寄件人所在县/区
	private String province;// 寄件方所在省份
	private String city;// 寄件方所属城市名称
	private String address;// 寄件方详细地址
	private String mobile;// 寄件方手机
	private String tel;// 寄件方联系电话
	private String shipperCode;// 寄件方邮政编码
	private String contact;// 寄件方联系人
	private String company;// 寄件方公司

	private String cargo="商品";// 货物名称

	private int REQUEST_CONTACT = 20;
	final int RESULT_CODE = 101;
	private int RESULTCODE=99;
	
	private ReceiveOrderDetailsNetTest detailNet;
	private GetDistributionOrderNet distributionOrderNet;
	private ArrayList<DistributionOrderBean> orderDetailList2 = new ArrayList<DistributionOrderBean>();
	
	private UserInfoNet userInfoNet;
	private SendOfflineOrderNet sendOfflineOrderNet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();

		super.onCreate(savedInstanceState);

		if (bundle != null) {
			type = bundle.getString("Type");
			PushExpressOrder = bundle.getString("PushExpressOrder");
			ExpressCode = bundle.getString("ExpressCode");
			MonthCode = bundle.getString("MonthCode");
			tradeId = bundle.getString("TradeId");
			tempTradeId=OrderDetailActivity.tempTradeId;
			Id = bundle.getString("Id");
			ExpressName = bundle.getString("ExpressName");
		}

		detailNet = new ReceiveOrderDetailsNetTest(mContext);
		detailNet.setData(Integer.parseInt(tradeId));

		distributionOrderNet = new GetDistributionOrderNet(mContext);
		distributionOrderNet.setData(tradeId);

		userInfoNet = new UserInfoNet(mContext);
		userInfoNet.setData();

		if (type.equals("1")) {// 在线发货
			rl_express_send_order.setVisibility(View.VISIBLE);
		}
		if (type.equals("0")) {// 普通物流发货
			rl_send.setVisibility(View.VISIBLE);
		}
		
		mBack();
	}

	public void onEventMainThread(ResultSendOfflineOrderBean msg) {
		if(msg.IsSuccess!=false){
			//发货成功 调转到首页面
			ToastFactory.getLongToast(mContext,"发货成功！");
			Skip.mNext(mActivity, NewMainActivity.class,true);
		}else{
			new MyAlertDialog(mContext).mSingleShow("发货失败", "订单:" + tradeId
					+ "发货错误！"+msg.Message, "返回", new ConfirmListener() {

				@Override
				public void onClick(Dialog dialog) {
					if(PushExpressOrder.equals("1001")){
						//返回首页
						Skip.mNext(mActivity, NewMainActivity.class,true);
					}else{
						Skip.mNext(mActivity, NewMainActivity.class,true);
					}
				}
			});
		}
	}

	public void onEventMainThread(ResultDistributionOrderBean msg) {
		
		this.orderDetailList2.addAll(msg.TModel);
		
		DistributionOrderBean detail=orderDetailList2.get(0);
		if(detail.OrderItem.size()>1){
			for(int i=0;i<detail.OrderItem.size();i++){
				cargo+=detail.OrderItem.get(i).Title+",";
			}
			//去除最后一个符号
			cargo=cargo.substring(0, cargo.length()-1);
		}
		else{
			cargo=detail.OrderItem.get(0).Title;
		}	
	}

	/**
	 * 接收用户信息
	 * 
	 * @param msg
	 */
	public void onEventMainThread(ResultUserBean msg) {
		if (msg != null) {
			contact = msg.TModel.Name;
			mobile = msg.TModel.Mobile;
			if(msg.TModel.ShopMobile!=null){
				tel = msg.TModel.ShopMobile;
			}else{
				tel = msg.TModel.Mobile;
			}
			company = msg.TModel.BrandName;
			shipperCode = msg.TModel.ShopPostCode;
			province = msg.TModel.ShopProvince;
			city = msg.TModel.ShopCity;
			country = msg.TModel.ShopArea;
			address = msg.TModel.ShopProvince + msg.TModel.ShopCity
					+ msg.TModel.ShopArea + msg.TModel.ShopAddress;
		}
		
	}

	@OnClick({ R.id.btn_express_message, R.id.rl_express_send_order,
			R.id.tv_scan_express_order, R.id.rl_send })
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_scan_express_order:// 扫描物流单号
			// 调用扫描二维码$条码
			Skip.mSendScanSendActivityResult(mActivity, CaptureActivity.class,
					REQUEST_CONTACT);
			break;

		case R.id.btn_express_message:// 快速在线通知物流
			Intent intent = new Intent(mActivity, SendExpressRemarkActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			intent.putExtra("tradeId", tradeId);// 订单ID
			intent.putExtra("expressId", Id);
			intent.putExtra("contact", contact);
			intent.putExtra("mobile", mobile);
			intent.putExtra("tel", tel);
			intent.putExtra("company", company);
			intent.putExtra("shipperCode", shipperCode);
			intent.putExtra("province", province);
			intent.putExtra("city", city);
			intent.putExtra("country", country);
			intent.putExtra("address", address);
			intent.putExtra("cargo", cargo);
			intent.putExtra("PushExpressOrder", PushExpressOrder);
			intent.putExtra("ExpressCode", ExpressCode);
			intent.putExtra("MonthCode", MonthCode);

			intent.putExtra("receiverCompany", receiverCompany);
			intent.putExtra("receiverName", receiverName);
			intent.putExtra("receiverMobile", receiverMobile);
			intent.putExtra("receiverProvinceName", receiverProvinceName);
			intent.putExtra("receiverCityName", receiverCityName);
			intent.putExtra("receiverExpAreaName", receiverExpAreaName);
			intent.putExtra("receiverAddress", receiverAddress);

			mActivity.startActivityForResult(intent, REQUEST_CONTACT);
			mActivity.overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_no_anim);
			break;

		case R.id.rl_express_send_order://普通物流发货
			if(TextUtils.isEmpty(et_express_shop_postage.getText().toString())){
				postage=0.0;
			}else{
				postage=Double.parseDouble(et_express_shop_postage.getText().toString());
			}
			if(TextUtils.isEmpty(et_express_shop_weight.getText().toString())){
				weight=0.0;
			}else{
				weight=Double.parseDouble(et_express_shop_weight.getText().toString());
			}
			if (TextUtils.isEmpty(et_express_order.getText().toString().trim())) {
				ToastFactory.show(mActivity, "物流单号不能为空！", ToastFactory.CENTER);
			}else {
				if(et_express_order.getText().toString().trim().length()<=30){
				sendOfflineOrderNet = new SendOfflineOrderNet(mContext);
				if(wayBillNo!=null){
					sendOfflineOrderNet.setData(tradeId, Id, wayBillNo,postage,weight,ExpressName);
				}else{
					sendOfflineOrderNet.setData(tradeId, Id, et_express_order.getText().toString(),postage,weight,ExpressName);
				}
				}else{
					ToastFactory.show(mActivity, "物流单号长度不能超过30！", ToastFactory.CENTER);
				}
			}
			break;

		case R.id.rl_send:// 普通物流发货
			if(TextUtils.isEmpty(et_express_shop_postage.getText().toString())){
				postage=0.0;
			}else{
				postage=Double.parseDouble(et_express_shop_postage.getText().toString());
			}
			
			if(TextUtils.isEmpty(et_express_shop_weight.getText().toString())){
				weight=0.0;
			}else{
				weight=Double.parseDouble(et_express_shop_weight.getText().toString());
			}
			
			if (TextUtils.isEmpty(et_express_order.getText().toString().trim()) && et_express_order.getText().toString().trim().length()>=30) {
				ToastFactory.show(mActivity, "物流单号不能为空或长度不能超过30！", ToastFactory.CENTER);
				
			} else {
				sendOfflineOrderNet = new SendOfflineOrderNet(mContext);
				if(wayBillNo!=null){
					sendOfflineOrderNet.setData(tradeId, Id, wayBillNo,postage,weight,ExpressName);
				}else{
					sendOfflineOrderNet.setData(tradeId, Id, et_express_order.getText().toString(),postage,weight,ExpressName);
				}
			break;
		}
		}
	}

	/**
	 * 处理扫描Activity返回的数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CODE) {//获取扫描条码结果
			if(data!=null){
				wayBillNo = data.getStringExtra("resultCode");
				et_express_order.setText(wayBillNo);
			}
		}
		if(resultCode==RESULTCODE){//预约快递成功访问查询订单号
			//执行查询物流单号的操作
			et_express_order.setText(data.getStringExtra("resultCode"));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void mBack() {
		ll_send_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}

	public class ReceiveOrderDetailsNetTest extends BaseNet {

		public ReceiveOrderDetailsNetTest(Context context) {
			super(context, true);
			this.uri="OrderRoute/sw/order/OrderDetail";
		}

		public void setData(int OrderId){
			try {
				data.put("OrderId",OrderId);
				data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			mPostNet(); // 开始请求网络
		}

		@Override
		protected void mSuccess(String rJson) {
			super.mSuccess(rJson);
			Gson gson=new Gson();
			ResultReceiveOrderDetailsBean msg=gson.fromJson(rJson,ResultReceiveOrderDetailsBean.class);
			if(msg.IsSuccess!=false){
				if(msg.TModel!=null && !"".equals(msg.TModel)){
					tv_send_express_user_name.setText(msg.TModel.getConsignee());
					tv_send_express_user_mobile.setText(msg.TModel.getMobile());
					tv_express_address.setText(msg.TModel.getProvince()+ msg.TModel.getCity()+ msg.TModel.getDistrict()+ msg.TModel.getAddress());

					receiverCompany="null";
					receiverName=msg.TModel.getConsignee();
					receiverMobile=msg.TModel.getMobile();
					receiverProvinceName=msg.TModel.getProvince();
					receiverCityName=msg.TModel.getCity();
					receiverExpAreaName=msg.TModel.getDistrict();
					receiverAddress=msg.TModel.getProvince()+ msg.TModel.getCity()+ msg.TModel.getDistrict()+ msg.TModel.getAddress();

				}else{
					ToastFactory.getLongToast(mContext,"获取订单详情数据为空！");
				}

			}else{
				ToastFactory.getLongToast(mContext,"msg:"+msg.Message);
			}
		}

		@Override
		protected void mFail(HttpException e, String err) {
			super.mFail(e, err);

		}
	}
}
