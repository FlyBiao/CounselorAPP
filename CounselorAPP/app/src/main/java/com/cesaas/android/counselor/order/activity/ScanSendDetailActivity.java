package com.cesaas.android.counselor.order.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.OrderActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetCodeOrderBean;
import com.cesaas.android.counselor.order.bean.ResultSendOrderBean;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.net.GetCodeOrderNet;
import com.cesaas.android.counselor.order.net.SendOrderNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 扫描发货订单详情
 * @author FGB
 *
 */
@ContentView(R.layout.activity_scan_send_detail)
public class ScanSendDetailActivity extends BasesActivity{

	@ViewInject(R.id.ll_detail_back)
	private LinearLayout ll_detail_back;
	private TextView tv_code_order_reserve_name;
    private TextView tv_code_order_express;
    private TextView tv_code_order_reserve_date;
    private TextView tv_code_order_address;
    private TextView tv_code_order_mobile;
    private TextView tv_code_order_remark;
    private TextView tv_code_order_id;
    private TextView tv_code_create_date;
    private TextView tv_code_order_pay_price;
    private TextView tv_code_order_total_price;
    private TextView tv_code_order_status;
    private LinearLayout ll_mobile;
    
    private TextView btn_scan_send;

    private LinearLayout ll_code_order_address;
    private LinearLayout ll_code_order_reserve_date;

    private String PushscanOrder;
	private String CodeId;
	private String orderId;
	private GetCodeOrderNet detailNet;
	private SendOrderNet orderNet;
	
	private boolean isSuccess;
	private String message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			CodeId=bundle.getString("CodeId");
			PushscanOrder=bundle.getString("PushscanOrder");
		}
		super.onCreate(savedInstanceState);
		
		initView();
//		
		if(CodeId!=""){
			detailNet=new GetCodeOrderNet(mContext);
			detailNet.setData(CodeId);
			
		}else{
			ToastFactory.show(mContext, "订单号不存在或为空", ToastFactory.CENTER);
		}
		
		send();
		initBack();
		
	}
	
	/**
     * 初始化视图控件
     */
    public void initView(){
        tv_code_order_reserve_name= (TextView) findViewById(R.id.tv_code_order_reserve_name);
        tv_code_order_express= (TextView) findViewById(R.id.tv_code_order_express);
        tv_code_order_reserve_date= (TextView) findViewById(R.id.tv_code_order_reserve_date);
        tv_code_order_address= (TextView) findViewById(R.id.tv_code_order_address);
        tv_code_order_mobile= (TextView) findViewById(R.id.tv_code_order_mobile);
        tv_code_order_remark= (TextView) findViewById(R.id.tv_code_order_remark);
        tv_code_order_id= (TextView) findViewById(R.id.tv_code_order_id);
        tv_code_create_date= (TextView) findViewById(R.id.tv_code_create_date);
        tv_code_order_pay_price= (TextView) findViewById(R.id.tv_code_order_pay_price);
        tv_code_order_total_price= (TextView) findViewById(R.id.tv_code_order_total_price);
        tv_code_order_status= (TextView) findViewById(R.id.tv_code_order_status);
        ll_mobile=(LinearLayout) findViewById(R.id.ll_mobile);
        
        btn_scan_send=(TextView)findViewById(R.id.btn_scan_send);

        ll_code_order_address= (LinearLayout) findViewById(R.id.ll_code_order_address);
        ll_code_order_reserve_date= (LinearLayout) findViewById(R.id.ll_code_order_reserve_date);
    }
	
	/**
	 * 确认发货
	 */
	private void send() {
		
		//扫描发货
		btn_scan_send.setVisibility(View.VISIBLE);
		btn_scan_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				orderNet=new SendOrderNet(mContext);
		    	orderNet.setData(CodeId);
			}
		});
	}
	
	public void onEventMainThread(ResultSendOrderBean msg) {
    	
		if(msg.IsSuccess==true){
			new MyAlertDialog(mContext).mSingleShow("发货成功", "订单:"+orderId+"已经完成发货","确定", new ConfirmListener() {
				
				@Override
				public void onClick(Dialog dialog) {
					if(PushscanOrder.equals("1001")){
						Skip.mNext(mActivity, OrderActivity.class);
					}
					ScanSendDetailActivity.this.finish();
				}
			});
		}else if(msg.IsSuccess==false){
			new MyAlertDialog(mContext).mSingleShow("发货失败", "订单:"+orderId+"有误！"+msg.Message,"返回", new ConfirmListener() {
				
				@Override
				public void onClick(Dialog dialog) {
					if(PushscanOrder.equals("1001")){
						Skip.mNext(mActivity, OrderActivity.class);
					}
					ScanSendDetailActivity.this.finish();
				}
			});
		}
    }
	
	public void onEventMainThread(ResultGetCodeOrderBean bean) {
		
		for (int i=0;i<bean.TModel.size();i++){
            if(bean.TModel.get(i).ExpressType==0){//快递方式取货
                tv_code_order_express.setText("快递");
                tv_code_order_mobile.setText(bean.TModel.get(i).Mobile);
                ll_code_order_address.setVisibility(View.VISIBLE);
                tv_code_order_reserve_name.setText(bean.TModel.get(i).ConsigneeName);
                tv_code_order_address.setText(bean.TModel.get(i).Province+bean.TModel.get(i).City+bean.TModel.get(i).District+bean.TModel.get(i).Address);

            }else if(bean.TModel.get(i).ExpressType==1){//到店自提取货
                tv_code_order_express.setText("到店自提");
                ll_mobile.setVisibility(View.GONE);
                tv_code_order_reserve_name.setText(bean.TModel.get(i).NickName);
                ll_code_order_reserve_date.setVisibility(View.VISIBLE);
                tv_code_order_reserve_date.setText(bean.TModel.get(i).ReserveDate);
            }
            	orderId=bean.TModel.get(i).OrderId;
//	            tv_code_order_mobile.setText(bean.TModel.get(i).Mobile);
	            tv_code_order_remark.setText(bean.TModel.get(i).OrderRemark);
	            tv_code_order_id.setText(orderId);
	            tv_code_create_date.setText(bean.TModel.get(i).CreateDate);
	            tv_code_order_pay_price.setText(Double.toString(bean.TModel.get(i).PayPrice));
	            tv_code_order_total_price.setText(Double.toString(bean.TModel.get(i).TotalPrice));

            //订单状态判断
            if(bean.TModel.get(i).OrderStatus==10){
                tv_code_order_status.setText("未支付");
                tv_code_order_status.setTextColor(getResources().getColor(R.color.red));
                btn_scan_send.setVisibility(View.GONE);

            }else if(bean.TModel.get(i).OrderStatus==11){
                tv_code_order_status.setText("待审核");
                tv_code_order_status.setTextColor(getResources().getColor(R.color.red));
                btn_scan_send.setVisibility(View.GONE);

            }else if(bean.TModel.get(i).OrderStatus==20){
                tv_code_order_status.setText("待支付");
                tv_code_order_status.setTextColor(getResources().getColor(R.color.red));
                btn_scan_send.setVisibility(View.GONE);

            }else if(bean.TModel.get(i).OrderStatus==30){
                tv_code_order_status.setText("已支付");

            }else if(bean.TModel.get(i).OrderStatus==40){
                tv_code_order_status.setText("已发货");
                tv_code_order_status.setTextColor(getResources().getColor(R.color.red));
                btn_scan_send.setVisibility(View.GONE);

            }else if(bean.TModel.get(i).OrderStatus==80){
                tv_code_order_status.setText("已退款");
                tv_code_order_status.setTextColor(getResources().getColor(R.color.red));
                btn_scan_send.setVisibility(View.GONE);

            }else if(bean.TModel.get(i).OrderStatus==81){
                tv_code_order_status.setText("已取消");
                tv_code_order_status.setTextColor(getResources().getColor(R.color.red));
                btn_scan_send.setVisibility(View.GONE);

            }else if(bean.TModel.get(i).OrderStatus==100){
                tv_code_order_status.setText("已完成");
                tv_code_order_status.setTextColor(getResources().getColor(R.color.red));
                btn_scan_send.setVisibility(View.GONE);
            }
        }
	}
	
	/**
	 * 返回上一个页面
	 */
	public void initBack(){
		ll_detail_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
	
}
