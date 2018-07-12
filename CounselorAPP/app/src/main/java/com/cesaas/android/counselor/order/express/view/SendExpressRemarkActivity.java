package com.cesaas.android.counselor.order.express.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.CargoInfo;
import com.cesaas.android.counselor.order.bean.DeliverInfo;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.bean.kdn.CommodityBean;
import com.cesaas.android.order.bean.kdn.KdGoInfoBean;
import com.cesaas.android.order.bean.kdn.OrderOnlineByJsonUtils;
import com.cesaas.android.order.bean.kdn.ReceiverCompany;
import com.cesaas.android.order.bean.kdn.ResultKDNBean;
import com.cesaas.android.order.bean.kdn.SenderCompany;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 发货物流备注
 * 
 * @author FGB
 * 
 */
@ContentView(R.layout.activity_send_express_message)
public class SendExpressRemarkActivity extends BasesActivity implements OnClickListener{
	
	@ViewInject(R.id.et_express_remark)
	private EditText et_express_remark;
	@ViewInject(R.id.tv_express_sends)
	private TextView tv_express_sends;

	private LinearLayout ll_monthly;
	private LinearLayout ll_to_pay;
	private LinearLayout ll_the_third_pay;
	private LinearLayout ll_now_pay;
	
	private TextView tv_monthly;
	private TextView tv_to_pay;
	private TextView tv_the_third_pay;
	private TextView tv_now_pay;
	
	private TextView tv_c_monthly;
	private TextView tv_c_to_pay;
	private TextView tv_c_the_third_pay;
	private TextView tv_c_now_pay;
	
	private TextView tv_back_send;
	
	private int RESULTCODE=99;
	
	private ArrayList<LinearLayout> lls=new ArrayList<LinearLayout>();
	private ArrayList<TextView> tvImg=new ArrayList<TextView>();
	private ArrayList<TextView> tvColor=new ArrayList<TextView>();
	
	private int payMethod=3;

	//收货方信息
	private String receiverCompany;
	private String receiverName;
	private String receiverMobile;
	private String receiverProvinceName;
	private String receiverCityName;
	private String receiverExpAreaName;
	private String receiverAddress;
	
	//寄件方信息
	private String country;//寄件人所在县/区
	private String province;//寄件方所在省份
	private String city;//寄件方所属城市名称
	private String address;//寄件方详细地址
	private String mobile;//寄件方手机
	private String tel;//寄件方联系电话
	private String contact;//寄件方联系人
	private String company;//寄件方公司

	private String cargo;//货物名称

	private String tradeId;//订单ID
	private String ExpressCode;//物流公司编码
	private String MonthCode;//月结号

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
		mBack();
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			tradeId=bundle.getString("tradeId");
			country=bundle.getString("country");
			province=bundle.getString("province");
			city=bundle.getString("city");
			address=bundle.getString("address");
			mobile=bundle.getString("mobile");
			tel=bundle.getString("tel");
			contact=bundle.getString("contact");
			company=bundle.getString("company");
			cargo=bundle.getString("cargo");
			ExpressCode=bundle.getString("ExpressCode");
			MonthCode=bundle.getString("MonthCode");

			receiverCompany=bundle.getString("receiverCompany");
			receiverName=bundle.getString("receiverName");
			receiverMobile=bundle.getString("receiverMobile");
			receiverProvinceName=bundle.getString("receiverProvinceName");
			receiverCityName=bundle.getString("receiverCityName");
			receiverExpAreaName=bundle.getString("receiverExpAreaName");
			receiverAddress=bundle.getString("receiverAddress");
		}
		sendCreateOrder();
	}

	public void sendCreateOrder(){
		//发货通知
		tv_express_sends.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (android.os.Build.VERSION.SDK_INT > 9) {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
				}

				SenderCompany send=new SenderCompany();
				send.setCompany(company);
				send.setProvinceName(province);
				if(city.indexOf("市")!=-1){
					send.setCityName(city);
				}else{
					send.setCityName(city+"市");
				}
				send.setExpAreaName(country);
				send.setAddress(address);
				send.setMobile(mobile);
				send.setName(contact);

				ReceiverCompany receiver=new ReceiverCompany();
				receiver.setProvinceName(receiverProvinceName);
				if(receiverCityName.indexOf("市")!=-1){
					receiver.setCityName(receiverCityName);
				}else{
					receiver.setCityName(receiverCityName+"市");
				}
				receiver.setExpAreaName(receiverExpAreaName);
				receiver.setAddress(receiverAddress);
				receiver.setName(receiverName);
				receiver.setMobile(receiverMobile);

				List<CommodityBean> commodity=new ArrayList<>();
				CommodityBean commodityBean=new CommodityBean();
				commodityBean.setGoodsName(cargo);
				commodity.add(commodityBean);

				final KdGoInfoBean goInfoBean=new KdGoInfoBean();
				if(payMethod==3){//月结号
					goInfoBean.setMonthCode(MonthCode);
				}
				goInfoBean.setShipperCode(ExpressCode);
				goInfoBean.setOrderCode(tradeId);
				goInfoBean.setIsNotice(0);
				goInfoBean.setIsSendMessage(1);
				goInfoBean.setPayType(payMethod);
				goInfoBean.setExpType(1);
				goInfoBean.setRemark(et_express_remark.getText().toString());
				goInfoBean.setSender(send);
				goInfoBean.setReceiver(receiver);
				goInfoBean.setCommodity(commodity);
				try {
					OrderOnlineByJsonUtils onlineByJsonUtils=new OrderOnlineByJsonUtils();
					String resultData =onlineByJsonUtils.orderOnlineByJson(JsonUtils.toJson(goInfoBean));
					final ResultKDNBean bean=JsonUtils.fromJson(resultData,ResultKDNBean.class);
					if(bean.isSuccess()!=false && bean.getResultCode().equals("100")){
						ToastFactory.getLongToast(mContext,"通知物流成功！");
						Intent intent=new Intent();
						intent.putExtra("resultCode",bean.Order.getLogisticCode());
						setResult(RESULTCODE, intent);
						finish();
					}else{
						new MyAlertDialog(mContext).mSingleShow("消息", "订单号:" + tradeId
								+ " 通知物流失败-"+bean.getReason(), "知道了", new ConfirmListener() {
							@Override
							public void onClick(Dialog dialog) {
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void initView(){
		tv_back_send=(TextView) findViewById(R.id.tv_back_send);
		//布局背景
		ll_monthly=(LinearLayout) findViewById(R.id.ll_monthly);
		ll_to_pay=(LinearLayout) findViewById(R.id.ll_to_pay);
		ll_the_third_pay=(LinearLayout) findViewById(R.id.ll_the_third_pay);
		ll_now_pay=(LinearLayout) findViewById(R.id.ll_now_pay);
		
		ll_monthly.setOnClickListener(this);
		ll_to_pay.setOnClickListener(this);
		ll_the_third_pay.setOnClickListener(this);
		ll_now_pay.setOnClickListener(this);

		lls.add(ll_monthly);
		lls.add(ll_to_pay);
		lls.add(ll_the_third_pay);
		lls.add(ll_now_pay);
		
		//tvImg
		tv_monthly=(TextView) findViewById(R.id.tv_monthly);
		tv_to_pay=(TextView) findViewById(R.id.tv_to_pay);
		tv_the_third_pay=(TextView) findViewById(R.id.tv_the_third_pay);
		tv_now_pay=(TextView) findViewById(R.id.tv_now_pay);
		
		tvImg.add(tv_monthly);
		tvImg.add(tv_to_pay);
		tvImg.add(tv_the_third_pay);
		tvImg.add(tv_now_pay);
		
		//字体颜色
		tv_c_monthly=(TextView) findViewById(R.id.tv_c_monthly);
		tv_c_to_pay=(TextView) findViewById(R.id.tv_c_to_pay);
		tv_c_the_third_pay=(TextView) findViewById(R.id.tv_c_the_third_pay);
		tv_c_now_pay=(TextView) findViewById(R.id.tv_c_now_pay);
		
		tvColor.add(tv_c_monthly);
		tvColor.add(tv_c_to_pay);
		tvColor.add(tv_c_the_third_pay);
		tvColor.add(tv_c_now_pay);
	}

	@Override
	public void onClick(View v) {
		int index=-1;
		
		switch(v.getId()){
		
		case R.id.ll_monthly:
			index=0;
			payMethod=3;
			
			break;
		
		case R.id.ll_to_pay:
			index=1;
			payMethod=2;
			break;
		
		case R.id.ll_the_third_pay:
			index=2;
			payMethod=4;
			break;
		
		case R.id.ll_now_pay:
			index=3;
			payMethod=1;
			break;
		}
		setBgColor(index);
		setTvImg(index);
		setTvColor(index);
		
	}
	
	public void mBack(){
		tv_back_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
	
	/**
	 * 背景
	 * @param index
	 */
	private void setBgColor(int index) {
		for(int i=0;i<lls.size();i++){
//			lls.get(i).setTextColor(mContext.getResources().getColor(R.color.color_title_bar));
			lls.get(i).setBackgroundColor(this.getResources().getColor(R.color.day_sign_content_text_white_30));
		}
//		lls.get(index).setTextColor(mContext.getResources().getColor(R.color.white));
		lls.get(index).setBackgroundDrawable(this.getResources().getDrawable(R.drawable.button_send_express_bg));

	}

	/**
	 * TvImg
	 * @param index
	 */
	private void setTvImg(int index) {
		for(int i=0;i<tvImg.size();i++){
			tvImg.get(i).setBackgroundColor(this.getResources().getColor(R.color.day_sign_content_text_white_30));
		}
		tvImg.get(index).setBackgroundDrawable(this.getResources().getDrawable(R.drawable.selector_img_express_pay));
	}
	
	/**
	 * 字体颜色
	 * @param index
	 */
	private void setTvColor(int index) {
		for(int i=0;i<tvColor.size();i++){
			tvColor.get(i).setTextColor(this.getResources().getColor(R.color.lightgrey));
		}
		tvColor.get(index).setTextColor(this.getResources().getColor(R.color.c1));
	}
	
}
