package com.cesaas.android.counselor.order.staffmange;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.InitSetPasswordBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.net.InitSetPasswordNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;

/**
 * 店员详情
 * @author FGB
 *
 */
public class ShopStaffDetailActivity extends BasesActivity{
	
	private TextView tv_shopstaff_name,tv_shopstaff_grade,
	tv_shopstaff_mobile,tv_shopstaff_nick,tv_shopstaff_sex,
	tv_shopstaff_shopname,tv_shop_id,tv_edit_staff;
	private MyImageViewWidget iv_shopstaff_img;
	private LinearLayout ll_staff_detail_back;
	private Button btn_init_setPassword;
	
	private String name;
	private String icon;
	private int type;
	private int shopId;
	private int counselorId;
	private String nick;
	private String shopName;
	private String mobile;
	private String sex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_staff_detail);
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			name=bundle.getString("Name");
			icon=bundle.getString("Icon");
			nick=bundle.getString("Nick");
			type=bundle.getInt("Type");
			shopId=bundle.getInt("ShopId");
			shopName=bundle.getString("ShopName");
			mobile=bundle.getString("Mobile");
			sex=bundle.getString("Sex");
			counselorId=bundle.getInt("counselorId");
		}
		
		initView();
		initData();
		mClick();
	}
	
	/**
	 * 初始化视图控件
	 */
	public void initView(){
		btn_init_setPassword=(Button) findViewById(R.id.btn_init_setPassword);
		ll_staff_detail_back=(LinearLayout) findViewById(R.id.ll_staff_detail_back);
		tv_edit_staff=(TextView) findViewById(R.id.tv_edit_staff);
		tv_shopstaff_name=(TextView) findViewById(R.id.tv_shopstaff_name);
		tv_shopstaff_grade=(TextView) findViewById(R.id.tv_shopstaff_grade);
		iv_shopstaff_img=(MyImageViewWidget) findViewById(R.id.iv_shopstaff_img);
		tv_shopstaff_mobile=(TextView) findViewById(R.id.tv_shopstaff_mobile);
		tv_shopstaff_nick=(TextView) findViewById(R.id.tv_shopstaff_nick);
		tv_shopstaff_sex=(TextView) findViewById(R.id.tv_shopstaff_sex);
		tv_shopstaff_shopname=(TextView) findViewById(R.id.tv_shopstaff_shopname);
		tv_shop_id=(TextView) findViewById(R.id.tv_shop_id);
	}
	
	/**
	 * 初始化数据
	 */
	public void initData(){
		if(type==1){
			tv_shopstaff_grade.setText("店长");
		}else{
			tv_shopstaff_grade.setText("店员");
		}
		tv_shopstaff_name.setText(name);
		bitmapUtils.display(iv_shopstaff_img, icon, App.mInstance().bitmapConfig);
		tv_shop_id.setText(""+shopId);
		tv_shopstaff_shopname.setText(shopName);
		tv_shopstaff_sex.setText(sex);
		tv_shopstaff_nick.setText(nick);
		tv_shopstaff_mobile.setText(mobile);
		
	}
	
	public void mClick(){
		//编辑
		tv_edit_staff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("Name", name);
				bundle.putString("Nick", nick);
				bundle.putString("ShopName", shopName);
				bundle.putString("Mobile", mobile);
				bundle.putString("Sex", sex);
				bundle.putInt("ShopId", shopId);
				bundle.putString("Icon", icon);
				bundle.putInt("Type", type);
				bundle.putInt("counselorId",counselorId);
				Skip.mNextFroData(mActivity, UpdateShopStaffActivity.class,bundle);
			}
		});
		
		//重置密码
		btn_init_setPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InitSetPasswordNet initSetPasswordNet=new InitSetPasswordNet(mContext);
				initSetPasswordNet.setData(123456, counselorId);
			}
		});
		
		//返回
		ll_staff_detail_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
	}
	
	public void onEventMainThread(InitSetPasswordBean msg) {
		if(msg.IsSuccess==true){
			ToastFactory.getLongToast(mContext, "密码重置成功！");
			Skip.mNext(mActivity, ShopStaffListActivity.class);
			
		}else{
			ToastFactory.getLongToast(mContext, "密码重置失败!"+msg.Message);
		}
	}

}
