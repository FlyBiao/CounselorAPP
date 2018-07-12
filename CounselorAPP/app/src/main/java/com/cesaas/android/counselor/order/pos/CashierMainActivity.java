package com.cesaas.android.counselor.order.pos;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.hugo.android.scanner.CaptureActivity;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.GetByBarcodeCode;
import com.cesaas.android.counselor.order.bean.GetUserTicketListBean;
import com.cesaas.android.counselor.order.bean.MarketingActivityBean;
import com.cesaas.android.counselor.order.bean.ResultActivityResultBean;
import com.cesaas.android.counselor.order.bean.ResultGetByBarcodeCodeBean;
import com.cesaas.android.counselor.order.bean.ResultGetUserTicketListBean;
import com.cesaas.android.counselor.order.bean.ResultMarketingActivityBean;
import com.cesaas.android.counselor.order.bean.ShopVipBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.AactivityListNet;
import com.cesaas.android.counselor.order.net.CreateFromStoreNet;
import com.cesaas.android.counselor.order.net.GetActivityResultNet;
import com.cesaas.android.counselor.order.net.GetByBarcodeCodeNet;
import com.cesaas.android.counselor.order.net.GetFansInfoByMobileNet;
import com.cesaas.android.counselor.order.net.GetTicketInfoNet;
import com.cesaas.android.counselor.order.net.GetUseTicketNet;
import com.cesaas.android.counselor.order.pos.adapter.BarcodeShopAdapter;
import com.cesaas.android.counselor.order.pos.bean.GoodsArrayBean;
import com.cesaas.android.counselor.order.pos.bean.OrderItemBean;
import com.cesaas.android.counselor.order.pos.bean.ResultCreateFromStoreBean;
import com.cesaas.android.counselor.order.pos.dialog.GetOrderDialog;
import com.cesaas.android.counselor.order.pos.dialog.HangOrderDialog;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.cesaas.android.counselor.order.utils.MD5;
import com.cesaas.android.counselor.order.utils.RandomUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * Pos收银主页面
 * @author FGB
 *
 */
public class CashierMainActivity extends BasesActivity implements OnClickListener{
	
	private LinearLayout ll_hang_order;
	private LinearLayout ll_take_order;
	private LinearLayout ll_discount_order;
	private LinearLayout ll_coupons_order;
	private LinearLayout ll_cashier;
	private LinearLayout ll_activity;
	private LinearLayout ll_pos_order_back;
	private TextView tv_barcode_shop_sum;
	
	private TextView tv_barcode_order_no;
	private TextView tv_barcode_order_date;
	private TextView tv_pos_sales;
	private TextView tv_pos_vips;
	private TextView tv_pos_points;
	private TextView tv_vip_grade;
	
	private TextView tv_finally_price;
	private TextView tv_total_price;
	
	
	private TextView tv_add_vip;
	private TextView tv_add_barcode_shop;
	private ShopSilderListView slv_post_hang_order_list;
	private TextView tv_pos_order_shop_name;
	
	
	private int activityId;//活动ID
	private ListView mRecyclerView;
    public JSONArray styleArray=new JSONArray();
	private GetActivityResultNet activityResultNet;//使用活动结果Net
	private CustomMarketingActivityDialog customMarketingActivityDialog;//自定义营销活动Dialog
	private List<MarketingActivityBean> marketingActivityBeen=new ArrayList<MarketingActivityBean>();
	 
	private ListView rv_useticket_list;
	private CustomGetUseTickDialog customGetUseTickDialog;
	private List<GetUserTicketListBean> getUserTicketInfoBeanList;
	
	private EditText et_shop_discount;
	private Button bt_confirm_discount;
	private double discount;//全局商品折扣
	private double discountPrice=0.0;//折后结果
	
	private int REQUEST_CONTACT = 20;
	final int RESULT_CODE=101;
	private String scanCode ;
	
	private String uniqueCode;//优惠券唯一码
	private String shopStyleId;//商品id
	private String TicketCode;//扫描返回的优惠券码
	private String intentShopCode;//添加POS商品
	private double payTotalPrice=0.0;//支付总价
	private String orderNo;//单号
	private EditText et_ticket_code;
	private EditText et_vip_mobile;
	
	private String nickName;
	private String mobile;
	private String vipId;
	private String openId;
	private int vipPoint;
	private String MemberId;
	private String vipGrade;
	
	private CreateFromStoreNet createFromStoreNet;
	private GetByBarcodeCodeNet barcodeCodeNet;
	private BarcodeShopAdapter barcodeShopAdapter;
	public List<GetByBarcodeCode> barcodeCodeList=new ArrayList<GetByBarcodeCode>();
	private GetByBarcodeCode bean;
	private GetByBarcodeCode prices=null;;
	
	private JSONArray goodsListJson;
	public JSONArray goodsArray=new JSONArray();
	
	private JSONArray orderJsonArray;
	public JSONArray jsonArray=new JSONArray();
	
	private GetFansInfoByMobileNet byMobileNet;
	private ArrayList<ShopVipBean> vipList=new ArrayList<ShopVipBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cashier_main_layout);
		initView();
		tv_pos_order_shop_name.setText(prefs.getString("shopName"));
	}
	
	/**
	 * 初始化视图控件
	 */
	public void initView(){
		ll_activity=(LinearLayout) findViewById(R.id.ll_activity);
		ll_hang_order=(LinearLayout) findViewById(R.id.ll_hang_order);
		ll_take_order=(LinearLayout) findViewById(R.id.ll_take_order);
		ll_discount_order=(LinearLayout) findViewById(R.id.ll_discount_order);
		ll_coupons_order=(LinearLayout) findViewById(R.id.ll_coupons_order);
		ll_cashier=(LinearLayout) findViewById(R.id.ll_cashier);
		ll_pos_order_back=(LinearLayout) findViewById(R.id.ll_pos_order_back);
		tv_add_vip=(TextView) findViewById(R.id.tv_add_vip);
		tv_add_barcode_shop=(TextView) findViewById(R.id.tv_add_barcode_shop);
		slv_post_hang_order_list=(ShopSilderListView) findViewById(R.id.slv_post_hang_order_list);
		tv_barcode_shop_sum=(TextView) findViewById(R.id.tv_barcode_shop_sum);
		tv_pos_order_shop_name=(TextView) findViewById(R.id.tv_pos_order_shop_name);
		
		tv_barcode_order_no=(TextView) findViewById(R.id.tv_barcode_order_no);
		tv_barcode_order_date=(TextView)findViewById(R.id.tv_pos_date);
		tv_pos_sales=(TextView) findViewById(R.id.tv_pos_sales);
		tv_pos_vips=(TextView)findViewById(R.id.tv_pos_vips);
		tv_pos_points=(TextView) findViewById(R.id.tv_pos_points);
		tv_vip_grade=(TextView) findViewById(R.id.tv_vip_grade);
		
		tv_finally_price=(TextView) findViewById(R.id.tv_finally_price);
		tv_total_price=(TextView) findViewById(R.id.tv_total_price);
		
		ll_activity.setOnClickListener(this);
		ll_hang_order.setOnClickListener(this);
		ll_take_order.setOnClickListener(this);
		ll_discount_order.setOnClickListener(this);
		ll_coupons_order.setOnClickListener(this);
		ll_pos_order_back.setOnClickListener(this);
		ll_cashier.setOnClickListener(this);
		tv_add_vip.setOnClickListener(this);
		tv_add_barcode_shop.setOnClickListener(this);
	}
	
	/**
	 * 接受订单商品消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(GetByBarcodeCode msg) {
		ToastFactory.getLongToast(mContext, ""+msg.position);
	}
	
	/**
	 * 接收活动列表消息
	 * @param msg 消息实体类
	 */
	 public void onEventMainThread(ResultMarketingActivityBean msg) {
     	if(msg.IsSuccess==true){
     		marketingActivityBeen=new ArrayList<MarketingActivityBean>();
     		marketingActivityBeen.addAll(msg.TModel);
            initAdapter();
     		
     	}else{
     		ToastFactory.getLongToast(mContext, msg.Message);
     	}
     }
	 
	 /**
	  * 设置获取优惠券Adapter
	  */
	 public void setGetUseTicketAadapter(){
		 rv_useticket_list.setAdapter(new CommonAdapter<GetUserTicketListBean>(mContext,R.layout.item_getuseticket_activity,getUserTicketInfoBeanList) {

				@Override
				public void convert(ViewHolder holder, GetUserTicketListBean bean,
						int postion) {
					
					holder.setText(R.id.tv_useticket_name,bean.Title);
					holder.setText(R.id.tv_activity_desc,bean.UseRule);
					holder.setText(R.id.tv_activity_time,"活动时间:"+bean.DateActive);

		            if(bean.IsUsed==1){//已使用
		            	holder.getView(R.id.cdv_bg).setBackgroundResource(R.color.line);
		            	holder.setText(R.id.tv_useticket_isused,"已使用");
		            }else{//未使用
		            	holder.getView(R.id.cdv_bg).setBackgroundResource(R.color.refresh_color_2);
		            }
				}
			});
	 }
	 
	 
	 /**
     * 营销活动数据适配器
     */
    public void initAdapter(){
    	mRecyclerView.setAdapter(new CommonAdapter<MarketingActivityBean>(mContext,R.layout.item_marketing_activity,marketingActivityBeen) {

			@Override
			public void convert(ViewHolder holder, MarketingActivityBean bean,
					int postion) {
				
				holder.setText(R.id.tv_activity_name,bean.Description);
				holder.setText(R.id.tv_activity_plan_name,bean.PlanName);
			}
		});
    	
    	initClickListener();
    }
	
    /**
     * 初始化活动ListView点击监听
     */
	 private void initClickListener() {
		 mRecyclerView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				customMarketingActivityDialog.dismiss();
				activityId=marketingActivityBeen.get(position).ActivityId;
				//使用活动
				activityResultNet=new GetActivityResultNet(mContext);
				activityResultNet.setData(activityId, styleArray);
			}
		});
	}
	 
	 /**
	 * 接受使用活动方案消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultActivityResultBean msg) {
		if(msg.IsSuccess==true){//使用活动成功
			ToastFactory.getLongToast(mContext, "恭喜您，使用活动成功!");
			
		}else{//失败
			ToastFactory.getLongToast(mContext, "活动是用失败！"+msg.Message);
		}
	}
	
	/**
	 * 接受获取优惠券消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGetUserTicketListBean msg) {
		if(msg.IsSuccess==true && msg.TModel!=null){//获取优惠券成功
			getUserTicketInfoBeanList=new ArrayList<GetUserTicketListBean>();
            getUserTicketInfoBeanList.addAll(msg.TModel);
            
            setGetUseTicketAadapter();
			
		}else{//失败
			ToastFactory.getLongToast(mContext, "获取优惠券失败！"+msg.Message);
		}
	}
		

	/**
	  * 接收店铺会员VIP信息
	  * @param msg 消息实体类
	  */
	public void onEventMainThread(ShopVipBean msg) {
		if (msg.IsSuccess==true && msg.TModel!=null) {
			
				nickName=msg.TModel.getFANS_NICKNAME();
				mobile=msg.TModel.getFANS_MOBILE();
				vipId=msg.TModel.getFANS_ID()+"";
				openId=msg.TModel.getFANS_OPENID();
				MemberId=msg.TModel.getMEMBER_ID()+"";
				vipPoint=msg.TModel.getFANS_POINT();
				vipGrade=msg.TModel.getFANS_GRADE();
				
				tv_pos_sales.setText(nickName);
				tv_pos_vips.setText(nickName);
				tv_pos_points.setText(vipPoint+"");
				tv_vip_grade.setText(vipGrade);
				
				//订单号生成规则：LS+VipId+MMddHHmm+4位随机数
				orderNo="LS"+vipId+AbDateUtil.getCurrentTimeAsNumber()+RandomUtils.getFourRandom();
				tv_barcode_order_no.setText(orderNo);
				tv_barcode_order_date.setText(AbDateUtil.getCurrentTate());
			}
			else{
				ToastFactory.getLongToast(mContext, "获取会员信息失败！"+msg.Message);
			}
	}
	
	public void onEventMainThread(ResultGetByBarcodeCodeBean msg) {
		if(msg.IsSuccess==true){
			//设置数据
			bean=new GetByBarcodeCode();
			shopStyleId=msg.TModel.getShopStyleId()+"";
			bean.setBarcodeCode(msg.TModel.getBarcodeCode());
			bean.setStyleCode(msg.TModel.getStyleCode());
			bean.setPrice(msg.TModel.getPrice());
			bean.setShopStyleId(msg.TModel.getShopStyleId());
			bean.setBarcodeId(msg.TModel.getBarcodeId());
			bean.setTitle(msg.TModel.getTitle());
			bean.setShopCount(msg.TModel.getShopCount());
			bean.setImageUrl(msg.TModel.getImageUrl());
			//把Object添加到ArrayList集合中
			barcodeCodeList.add(bean);
			//实例化Adapter数据适配器
			barcodeShopAdapter=new BarcodeShopAdapter(mContext,mActivity,barcodeCodeList);
			//将Adapter添加到ListView中
			slv_post_hang_order_list.setAdapter(barcodeShopAdapter);
			
			//获取jsonArray字符串
			for (int i = 0; i < barcodeCodeList.size(); i++) {
				
				GoodsArrayBean goodsBean=new GoodsArrayBean();   
				goodsBean.setId(barcodeCodeList.get(i).getShopStyleId());//商品id
//				goodsBean.setPrice();//商品支付价格
				goodsBean.setCount(barcodeCodeList.get(i).getShopCount());//商品数量
				goodsBean.setPriceOriginal(barcodeCodeList.get(i).getPrice());//商品原价
				
				OrderItemBean itemBean=new OrderItemBean();
				itemBean.setBarcodeId(barcodeCodeList.get(i).getBarcodeId());
				itemBean.setTitle(barcodeCodeList.get(i).getTitle());
				itemBean.setBarcodeNo(barcodeCodeList.get(i).getBarcodeCode());
				itemBean.setPrice(barcodeCodeList.get(i).getPrice());
				itemBean.setShopStyleId(barcodeCodeList.get(i).getShopStyleId());
				itemBean.setImageUrl(barcodeCodeList.get(i).getImageUrl());
				itemBean.setAttr("HJG");
				itemBean.setPayMent(barcodeCodeList.get(i).getPrice());//...
//				itemBean.setPayMent(Double.parseDouble(tv_total_price.getText().toString()));
				itemBean.setQuantity(barcodeCodeList.get(i).getShopCount());
				
				jsonArray.put(itemBean.getOrderItem());
				goodsArray.put(goodsBean.getGoodsArray());
				
				prices=barcodeCodeList.get(i);
				
			}
				payTotalPrice+=prices.getPrice();
				orderJsonArray=jsonArray;
				goodsListJson=goodsArray;
				
				tv_barcode_shop_sum.setText(barcodeCodeList.size()+"件商品");
				
				tv_finally_price.setText(DecimalFormatUtils.decimalFormatRound(payTotalPrice)+"");
				tv_total_price.setText(DecimalFormatUtils.decimalFormatRound(payTotalPrice)+"");
				
		}else if(msg.IsSuccess==false){
			ToastFactory.getLongToast(mContext, "添加失败！"+msg.Message);
		}
	}
	
	/**
	 * POS下单消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultCreateFromStoreBean msg) {
		if(msg.IsSuccess==true){
			//将成功返回的单号生成二维码
			Bundle bundle=new Bundle();
			bundle.putString("orderNo", msg.TModel);
			Skip.mNextFroData(mActivity, CreatePayQRImageViewActivity.class, bundle);
		}else if(msg.IsSuccess==false){
			ToastFactory.getLongToast(mContext, ""+msg.Message);
		}
	}
	
	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_activity://活动
			customMarketingActivityDialog=new CustomMarketingActivityDialog(mContext,R.style.dialog,R.layout.custom_marketing_activity_dialog);
            customMarketingActivityDialog.show();
            customMarketingActivityDialog.setCancelable(false);
            
			break;
			
		case R.id.ll_hang_order://挂单
			new HangOrderDialog(mContext).mInitShow("挂单列表");
			break;
			
		case R.id.ll_take_order://取单
			new GetOrderDialog(mContext).mInitShow("取单列表");
			break;
			
		case R.id.ll_discount_order://折扣
			new DiscountDialog(mContext).mInitShow();
			break;
			
		case R.id.ll_coupons_order://优惠券
			if(vipId!=null){
				//弹出优惠券Dialog
	            customGetUseTickDialog=new CustomGetUseTickDialog(mContext,R.style.dialog,R.layout.custom_getuseticket_activity_dialog);
	            customGetUseTickDialog.show();
	            customGetUseTickDialog.setCancelable(false);
	            
	            //访问接口获取优惠券数据
	            GetUseTicketNet getUseTicketNet=new GetUseTicketNet(mContext);
	            getUseTicketNet.setData(vipId);
	            
			}else{
				ToastFactory.getLongToast(mContext, "请先输入会员！");
			}
			
			
//			new GetTicketInfoDialog(mContext, mActivity).show();
//			TicketIsAvailableNet availableNet=new TicketIsAvailableNet(mContext);
//			availableNet.setData(shopStyleId, goodsListJson, discount);
			break;
			
		case R.id.ll_cashier://收银
			createFromStoreNet=new CreateFromStoreNet(mContext);
			if(vipId!=null){
				//会员
				if(discountPrice==0.0){
					createFromStoreNet.setData(orderNo, new MD5().toMD5("SwApp"+orderNo), 
							payTotalPrice, nickName, payTotalPrice, vipId, openId, mobile,"广东省","罗湖区","深圳市","广东省深圳市罗湖区", 
							orderJsonArray);
				}else{
					createFromStoreNet.setData(orderNo, new MD5().toMD5("SwApp"+orderNo), 
							payTotalPrice, nickName, discountPrice, vipId, openId, mobile,"广东省","罗湖区","深圳市","广东省深圳市罗湖区", 
							orderJsonArray);
				}
				
			}else{
				//非会员
				if(discountPrice==0.0){
					String payOrderNo="LS"+prefs.getString("vipId")+AbDateUtil.getCurrentTimeAsNumber()+RandomUtils.getFourRandom();
					createFromStoreNet.setData(payOrderNo, new MD5().toMD5("SwApp"+orderNo), 2, payTotalPrice, payTotalPrice, orderJsonArray);
				}else{
					String payOrderNo="LS"+prefs.getString("vipId")+AbDateUtil.getCurrentTimeAsNumber()+RandomUtils.getFourRandom();
					createFromStoreNet.setData(payOrderNo, new MD5().toMD5("SwApp"+orderNo), 2, discountPrice, payTotalPrice, orderJsonArray);
				}
			}
			
			break;
		case R.id.tv_add_vip://添加会员
			new AddVipDialog(mContext,mActivity).mInitShow();
			break;
			
		case R.id.tv_add_barcode_shop://添加POS商品
			Skip.mPosShopActivityResult(mActivity, CaptureActivity.class, REQUEST_CONTACT);
			break;
			
		case R.id.ll_pos_order_back://返回上一个页面
			Skip.mBack(mActivity);
			break;

		default:
			break;
		}
	}
	
	
	/**
	 * 处理扫描Activity返回的数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_CODE) {
			if(data.getStringExtra("TicketCode")!=null){
				if(data.getStringExtra("TicketCode").equals("001")){
					//添加优惠券查询唯一码
					scanCode= data.getStringExtra("resultCode");
					et_ticket_code.setText(scanCode);
					
				}
			}
			if(data.getStringExtra("intentShopCode")!=null){
				if(data.getStringExtra("intentShopCode").equals("002")){
					//添加POS 商品
					scanCode= data.getStringExtra("resultCode");
					barcodeCodeNet=new GetByBarcodeCodeNet(mContext);
					barcodeCodeNet.setData(Long.parseLong(scanCode));
				}
			}
			if(data.getStringExtra("intentVipCode")!=null){
				//添加VIP
				if(data.getStringExtra("intentVipCode").equals("004")){
					scanCode= data.getStringExtra("resultCode");
					et_vip_mobile.setText(scanCode);
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * 优惠券查询dialog
	 * 
	 * @author FGB
	 * 
	 */
	public class GetTicketInfoDialog extends Dialog implements OnClickListener {
		
		private ImageView iv_ticket_scan_code;
		private Button btn_confirm_ticket_scan;
		private String ticketCode;
		
		private int REQUEST_CONTACT = 20;
		private Activity activity;
		
		public GetTicketInfoDialog(Context context,Activity activity) {
			this(context, R.style.dialog);
			this.activity=activity;
		}

		public GetTicketInfoDialog(Context context, int dialog) {
			super(context, dialog);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			setContentView(R.layout.ticket_info_dialog);
			
			initView();
		}
		
		public void initView(){
			iv_ticket_scan_code=(ImageView) findViewById(R.id.iv_ticket_scan_code);
			btn_confirm_ticket_scan=(Button) findViewById(R.id.btn_confirm_ticket_scan);
			et_ticket_code=(EditText) findViewById(R.id.et_ticket_code);
			
			iv_ticket_scan_code.setOnClickListener(this);
			btn_confirm_ticket_scan.setOnClickListener(this);
		}
		
		public void mInitShow() {
			show();
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_ticket_scan_code://扫描优惠券二维码
				
				Skip.mActivityResults(activity, CaptureActivity.class, REQUEST_CONTACT);
				break;
			case R.id.btn_confirm_ticket_scan://确定查询优惠券
				if(!TextUtils.isEmpty(et_ticket_code.getText().toString())){
					//获取EditText值
					ticketCode=et_ticket_code.getText().toString();
					if(ticketCode!=null){
						//调用查询优惠券接口
						GetTicketInfoNet getTicketInfoNet=new GetTicketInfoNet(mContext);
						getTicketInfoNet.setData(ticketCode);
						//取消Dialog窗口
						cancel();
					}
					
				}else{
					ToastFactory.getLongToast(activity.getApplicationContext(), "请输入优惠卷码!");
				}
				
				break;

			default:
				break;
			}
		}

	}

	/**
	 * 添加会员dialog
	 * 
	 * @author FGB
	 * 
	 */
	public class AddVipDialog extends Dialog implements OnClickListener {
		
		private ImageView iv_add_scan_vip_mobile;
		private Button btn_confirm_add_vip_mobile;
		public String mobile;
		
		private int REQUEST_CONTACT = 20;
		private Activity activity;
		
		public AddVipDialog(Context context,Activity activity) {
			this(context, R.style.dialog);
			this.activity=activity;
		}

		public AddVipDialog(Context context, int dialog) {
			super(context, dialog);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			setContentView(R.layout.add_vip_dialog);
			
			initView();
		}
		
		public void initView(){
			iv_add_scan_vip_mobile=(ImageView) findViewById(R.id.iv_add_scan_vip_mobile);
			btn_confirm_add_vip_mobile=(Button) findViewById(R.id.btn_confirm_add_vip_mobile);
			et_vip_mobile=(EditText) findViewById(R.id.et_vip_mobile);
			
			iv_add_scan_vip_mobile.setOnClickListener(this);
			btn_confirm_add_vip_mobile.setOnClickListener(this);
		}
		
		public void mInitShow() {
			show();
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_add_scan_vip_mobile://扫描会员手机号
				Skip.mVipActivityResult(activity, CaptureActivity.class, REQUEST_CONTACT);
				break;
			case R.id.btn_confirm_add_vip_mobile://确定添加
				if(!TextUtils.isEmpty(et_vip_mobile.getText().toString())){
					mobile=et_vip_mobile.getText().toString();
					if(mobile!=null){
						byMobileNet=new GetFansInfoByMobileNet(mContext);
						byMobileNet.setData(0, mobile);
						cancel();
					}
					
				}else{
					ToastFactory.getLongToast(activity.getApplicationContext(), "请输入手机号!");
				}
				
				break;

			default:
				break;
			}
		}

	}
	
	
	/**
	 * 折扣dialog
	 * 
	 * @author FGB
	 * 
	 */
	public class DiscountDialog extends Dialog implements OnClickListener{
		
		public DiscountDialog(Context context) {
			this(context, R.style.dialog);
			et_shop_discount=(EditText) findViewById(R.id.et_shop_discount);
			bt_confirm_discount=(Button) findViewById(R.id.bt_confirm_discount);
			bt_confirm_discount.setOnClickListener(this);
		}

		public DiscountDialog(Context context, int dialog) {
			super(context, dialog);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			setContentView(R.layout.discount_dialog);
		}
		
		public void mInitShow() {
			show();
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_confirm_discount:
				discount=Double.parseDouble(et_shop_discount.getText().toString());
				discountPrice=payTotalPrice*discount;
				tv_finally_price.setText(DecimalFormatUtils.decimalFormatRound(discountPrice)+"");
				tv_total_price.setText(DecimalFormatUtils.decimalFormatRound(discountPrice)+"");
				
				this.dismiss();
				break;

			default:
				break;
			}
			
		}
	}
	
	/**
     * 自定义营销活动Dialog
     */
    public class CustomMarketingActivityDialog extends Dialog implements OnClickListener{
        private int layoutRes;//布局文件
        private LinearLayout ll_back_activity;
        Context context;
        public CustomMarketingActivityDialog(Context context, int theme,int resLayout){
            super(context, theme);
            this.context = context;
            this.layoutRes=resLayout;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(layoutRes);
            
            AactivityListNet aactivityListNet=new AactivityListNet(mContext);
            aactivityListNet.setData();
            
            initView();
        }
        
        /**
         * 初始化视图控件
         */
        public void initView(){
            ll_back_activity= (LinearLayout) findViewById(R.id.ll_back_activity);
            ll_back_activity.setOnClickListener(this);
            mRecyclerView= (ListView) findViewById(R.id.rv_marketing_activity_list);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_back_activity://返回
                    customMarketingActivityDialog.dismiss();
                    break;
            }
        }
    }
    
    /**
     * 自定义获取优惠券Dialog
     */
    public class CustomGetUseTickDialog extends Dialog implements OnClickListener{
        private int layoutRes;//布局文件
        private LinearLayout ll_back_useticket_activity;
        Context context;
        public CustomGetUseTickDialog(Context context, int theme,int resLayout){
            super(context, theme);
            this.context = context;
            this.layoutRes=resLayout;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(layoutRes);
            
            AactivityListNet aactivityListNet=new AactivityListNet(mContext);
            aactivityListNet.setData();
            
            initView();
        }
        
        /**
         * 初始化视图控件
         */
        public void initView(){
        	ll_back_useticket_activity= (LinearLayout) findViewById(R.id.ll_back_useticket_activity);
        	ll_back_useticket_activity.setOnClickListener(this);
            rv_useticket_list= (ListView) findViewById(R.id.rv_useticket_list);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_back_useticket_activity://返回
                	customGetUseTickDialog.dismiss();
                    break;
            }
        }
    }
	
}
