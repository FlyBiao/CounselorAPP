package com.cesaas.android.counselor.order.stores;

import io.rong.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.HomeActivity;
import com.cesaas.android.counselor.order.bean.CompetingBean;
import com.cesaas.android.counselor.order.bean.DayBeforeMsg;
import com.cesaas.android.counselor.order.bean.ResultAddSaleGoalBean;
import com.cesaas.android.counselor.order.bean.ResultSalesInfoBean;
import com.cesaas.android.counselor.order.net.AddSaleGoalNet;
import com.cesaas.android.counselor.order.net.GetOneMouthSaleNet;
import com.cesaas.android.counselor.order.net.GetOneSaleNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.cesaas.android.counselor.order.widget.ProgressbarWidget;

/**
 * 添加门店报数
 * @author FGB
 *
 */
public class AddStoresCountOffActivity extends BasesActivity{
	
	private LinearLayout ll_add_store_back;
	private TextView tv_day_before,btn_submit,mcet_sales_date,tv_sela_progress,tv_sela_target;
	private TextView btn_add_competing_goods,tv_current_day;
	private MClearEditText mcet_sales_amount,mcet_sales_quantity,mcet_sales_order_count;
	private ProgressbarWidget mProgress;
	
	private ListView lv_competing_list;
	public List<CompetingBean> competingList=new ArrayList<CompetingBean>();
	private CompetingBean competingBean;
	private CompetingAdapter adapter;
	
	private addCompetingDialog dialog;
	
	private int max = 100;
	private int current = 0;
	
	private String companyName;
	private String salesAmount;
	
	private int currYear;//当前年
	private int currMonth;//当月
	private String yesTerDay;//前一天
	private int isDayBefore;
	private String lastDayOfMonth;//当月最后一天s
	private int currDay;
	private double monthProgress;//月进度
	
	private int orderCount;
	private int productCount;
	private double saleValue;
	private String currSaledate;
	private int getCheckBoxItmeActionId=1;
	
	private JSONArray companyListJson;
	public JSONArray companyArray=new JSONArray();
	
	//获取当天报数
	private GetOneSaleNet getOneSaleNet;
	//提交店铺每日销售数据
	private AddSaleGoalNet addSaleGoalNet;
	//根据年月获取月份完成目标以及报数数据
	private GetOneMouthSaleNet getOneMouthSaleNet;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_add_stores_count_off);
	        
	        currSaledate=AbDateUtil.getCurrentDate("yyyy-MM-dd");
	        currYear=Integer.parseInt(AbDateUtil.getCurrentDate("yyyy"));
	        currMonth=Integer.parseInt(AbDateUtil.getCurrentDate("MM"));
	        currDay=Integer.parseInt(AbDateUtil.getCurrentDate("dd"));
	        yesTerDay=AbDateUtil.YesTerDay("yyyy-MM-dd");
	        lastDayOfMonth=AbDateUtil.getLastDayOfMonth("dd");
	        monthProgress=Double.parseDouble(currDay+"")/Double.parseDouble(lastDayOfMonth)*100;
	        
//	        getOneSaleNet=new GetOneSaleNet(mContext);
//	        getOneSaleNet.setData(currSaledate);
	        
	        getOneMouthSaleNet=new GetOneMouthSaleNet(mContext);
	        getOneMouthSaleNet.setData(currYear,currMonth);
	        
	        initView();
	        mClick();
	        initListItemOnClick();
	 }
	 
	 
	 
	 /**
		 * 接收门店报数消息订阅事件
		 * @param msg 消息实体类
		 */
		public void onEventMainThread(ResultSalesInfoBean msg) {
	    	if(msg.IsSuccess==true){
	    		tv_sela_target.setText(msg.TModel.Target+"");
	    		
	    	}else{
	    		ToastFactory.getLongToast(mContext, "获取数据失败!"+msg.Message);
	    	}
	    }
		
		/**
		 * 接收前一天销售日期消息
		 * @param msg 消息实体类
		 */
		public void onEventMainThread(DayBeforeMsg msg) {
			if(msg.IsSuccess==true){//前一天报数
				isDayBefore=msg.isDayBefore;
				mcet_sales_date.setText(yesTerDay);
				
			}else{//当天报数
				mcet_sales_date.setText(currSaledate);
			}
		}
		
	 /**
	  * 获取EventBus订阅事件
	  * @param msg 当天报数信息Bean
	  */
//	 public void onEventMainThread(ResultGetOneSaleBean msg) {
//			if(msg.IsSuccess==true && msg.TModel!=null){
//				mcet_sales_amount.setText(msg.TModel.SaleValue+"");
//				mcet_sales_quantity.setText(msg.TModel.ProductCount+"");
//				mcet_sales_order_count.setText(msg.TModel.OrderCount+"");
//				
//				CompetingBean bean=new CompetingBean();
//				for(int i=zero;i<msg.TModel.Competitor.size();i++){
//					bean.setName(msg.TModel.Competitor.get(i).Name);
//					bean.setSaleValue(msg.TModel.Competitor.get(i).SaleValue+"");
//					competingList.add(competingBean);
//					Log.i(Constant.TAG, "---00---=="+bean.getName()+bean.getSaleValue()+"="+competingList.size());
//				}
//				adapter=new CompetingAdapter(mContext,competingList);
////				lv_competing_list.setAdapter(adapter);
//			}
//	 }

	private void mClick() {
		//返回
		ll_add_store_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
		
		//前一天报数
		tv_day_before.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv_current_day.setVisibility(View.VISIBLE);
				tv_day_before.setVisibility(View.GONE);
				DayBeforeMsg msg=new DayBeforeMsg();
				msg.IsSuccess=true;
				msg.isDayBefore=1;
				EventBus.getDefault().post(msg);
			}
		});
		
		//当天报数
		tv_current_day.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv_day_before.setVisibility(View.VISIBLE);
				tv_current_day.setVisibility(View.GONE);
				DayBeforeMsg msg=new DayBeforeMsg();
				msg.IsSuccess=false;
				msg.isDayBefore=0;
				EventBus.getDefault().post(msg);
			}
		});
		
		//添加竞品
		btn_add_competing_goods.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog=new addCompetingDialog(mContext, R.style.dialog, R.layout.add_competing_dialog);
				dialog.show();
				dialog.setCancelable(false);
			}
		});
		
		//提交
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCheck();
			}
		});
	}
	
	/**
	 * 接收添加门店消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultAddSaleGoalBean msg) {
		if(msg.IsSuccess==true){
			ToastFactory.getLongToast(mContext, "门店报数提交成功！");
			Skip.mNext(mActivity, HomeActivity.class);
			
		}else{
			ToastFactory.getLongToast(mContext, "提交失败！"+msg.Message);
		}
	}

	/**
	 * 初始化视图控件
	 */
	private void initView() {
		
		tv_sela_target=(TextView) findViewById(R.id.tv_sela_target);
		tv_sela_progress=(TextView) findViewById(R.id.tv_sela_progress);
		
		mProgress=(ProgressbarWidget) findViewById(R.id.pgb_m_progress);
		initProgress();
		
		mcet_sales_amount=(MClearEditText) findViewById(R.id.mcet_sales_amount);
		mcet_sales_quantity=(MClearEditText) findViewById(R.id.mcet_sales_quantity);
		mcet_sales_order_count=(MClearEditText) findViewById(R.id.mcet_sales_order_count);
		mcet_sales_date=(TextView) findViewById(R.id.mcet_sales_date);
		mcet_sales_date.setText(currSaledate);
		
		lv_competing_list=(ListView) findViewById(R.id.lv_competing_list);
		btn_add_competing_goods=(TextView) findViewById(R.id.btn_add_competing_goods);
		tv_current_day=(TextView) findViewById(R.id.tv_current_day);
		tv_day_before=(TextView) findViewById(R.id.tv_day_before);
		btn_submit=(TextView) findViewById(R.id.btn_submit);
		ll_add_store_back=(LinearLayout) findViewById(R.id.ll_add_store_back);
	}
	
	/**
	 * 初始化月进度 进度条
	 */
	public void initProgress(){
		if (current <= max) {
			mProgress.setMaxValue(max);
			mProgress.setCurrentValue(Float.parseFloat(DecimalFormatUtils.decimalToFormat(monthProgress)));
		}
		//进度
		tv_sela_progress.setText(Float.parseFloat(DecimalFormatUtils.decimalToFormat(monthProgress))+"%");
	}
	
	/**
	 * 校验门店报数
	 */
	public void mCheck(){
		
		//判断所输入的报数是否为空
		if(!TextUtils.isEmpty(mcet_sales_amount.getText().toString())){
			if(!TextUtils.isEmpty(mcet_sales_quantity.getText().toString())){
				if(!TextUtils.isEmpty(mcet_sales_order_count.getText().toString())){
					
					//为门店各个报数赋值
					orderCount=Integer.parseInt(mcet_sales_order_count.getText().toString());
					productCount=Integer.parseInt(mcet_sales_quantity.getText().toString());
					saleValue=Double.parseDouble(mcet_sales_amount.getText().toString());
					
					//访问添加门店报数接口
					addSaleGoalNet=new AddSaleGoalNet(mContext);
					if(isDayBefore==0){//添加当前天
						addSaleGoalNet.setData(orderCount, productCount, saleValue, companyArray,0);
						
					}else{//添加前一天
						addSaleGoalNet.setData(orderCount, productCount, saleValue, companyArray,1);
					}
					
				}else{
					ToastFactory.getLongToast(mContext, "请输入销售单数！！");
				}
			}else{
				ToastFactory.getLongToast(mContext, "请输入销售量！");
			}
		}else{
			ToastFactory.getLongToast(mContext, "请输入销售额！");
		}
	}
	
	public void initListItemOnClick(){
		lv_competing_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
	}
	
	/**
	 * 添加标签Dialog
	 * @author FGB
	 *
	 */
	public class addCompetingDialog extends Dialog implements OnClickListener{
		
		int layoutRes;//布局文件
	    Context context;
	    
	    private Button btn_cancel_add_competing,btn_sure_add_competing;
	    private MClearEditText mcet_sales_amount,mcet_company_name;

	    public addCompetingDialog(Context context, int theme,int resLayout){
            super(context, theme);
            this.context = context;
            this.layoutRes=resLayout;
        }
	    
	    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(layoutRes);
            
            mcet_company_name=(MClearEditText) findViewById(R.id.mcet_company_name);
            mcet_sales_amount=(MClearEditText) findViewById(R.id.mcet_sales_amount);
            
            btn_cancel_add_competing=(Button) findViewById(R.id.btn_cancel_add_competing);
            btn_sure_add_competing=(Button) findViewById(R.id.btn_sure_add_competing);
            btn_cancel_add_competing.setOnClickListener(this);
            btn_sure_add_competing.setOnClickListener(this);
        }
	    

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				
			case R.id.btn_cancel_add_competing://取消添加
				dialog.dismiss();
				break;
				
			case R.id.btn_sure_add_competing://确定添加
				mCheck();
				break;
			default:
				break;
			}
		}
		/**
		 * 校验文本编辑框
		 */
		public void mCheck(){
			if(!TextUtils.isEmpty(mcet_company_name.getText().toString())){
				companyName=mcet_company_name.getText().toString();
				
				if(!TextUtils.isEmpty(mcet_sales_amount.getText().toString())){
					salesAmount=mcet_sales_amount.getText().toString();
					
					competingBean=new CompetingBean();
					competingBean.setName(companyName);
					competingBean.setSaleValue(salesAmount);
					
					competingList.add(competingBean);
					adapter=new CompetingAdapter(mContext,competingList);
					lv_competing_list.setAdapter(adapter);
					
					companyArray.put(competingBean.getCompetingBean());
					dialog.dismiss();
					
				}else{
					ToastFactory.getLongToast(mContext, "请输销售金额!");
				}
				
			}else{
				ToastFactory.getLongToast(mContext, "请输入企业名称!");
			}
		}
	}
}
