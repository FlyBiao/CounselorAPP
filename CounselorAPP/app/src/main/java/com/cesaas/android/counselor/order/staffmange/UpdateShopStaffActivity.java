package com.cesaas.android.counselor.order.staffmange;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultUpdateShopStaffBean;
import com.cesaas.android.counselor.order.net.UpdateShopStaffNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 修改店员信息
 * @author FGB
 *
 */
public class UpdateShopStaffActivity extends BasesActivity{
	
	private TextView mcet_update_shopstaff_name,
	mcet_update_shopstaff_nickname,mcet_update_shopstaff_mobile,
	gbv_update_shopstaff_sex,gbv_update_shopstaff_level;
	private Button btn_update;
	private LinearLayout ll_shopstaff_update_back;

	private String name;
	private String icon;
	private int type;
	private int shopId;
	private String nick;
	private String shopName;
	private String mobile;
	private String sex;
	private int counselorId;
	
	private UpdateShopStaffNet updateShopStaffNet;
	
	private selectSexDailog sexDailog;
	private selectShopLaeveDailog laeveDailog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_shoptaff);
		
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

	private void initData() {
		mcet_update_shopstaff_name.setText(name);
		mcet_update_shopstaff_nickname.setText(nick);
		mcet_update_shopstaff_mobile.setText(mobile);
		gbv_update_shopstaff_sex.setText(sex);
		if(type==1){
			gbv_update_shopstaff_level.setText("店长");
		}else{
			gbv_update_shopstaff_level.setText("店员");
		}
	}

	private void initView() {
		btn_update=(Button) findViewById(R.id.btn_update);
		ll_shopstaff_update_back=(LinearLayout) findViewById(R.id.ll_shopstaff_update_back);
		mcet_update_shopstaff_name=(TextView) findViewById(R.id.mcet_update_shopstaff_name);
		mcet_update_shopstaff_nickname=(TextView) findViewById(R.id.mcet_update_shopstaff_nickname);
		mcet_update_shopstaff_mobile=(TextView) findViewById(R.id.mcet_update_shopstaff_mobile);
		gbv_update_shopstaff_sex=(TextView) findViewById(R.id.gbv_update_shopstaff_sex);
		gbv_update_shopstaff_level=(TextView) findViewById(R.id.gbv_update_shopstaff_level);
	}
	
	public void mClick(){
		//性别
		gbv_update_shopstaff_sex.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sexDailog=new selectSexDailog(mContext, R.style.dialog, R.layout.select_sex_dialog);
				sexDailog.show();
			}
		});
		//店铺等级
		gbv_update_shopstaff_level.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				laeveDailog=new selectShopLaeveDailog(mContext, R.style.dialog, R.layout.select_shop_laeve_dialog);
				laeveDailog.show();
			}
		});
		
		//修改
		btn_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(gbv_update_shopstaff_level.getText().toString().equals("店员")){
					type=0;
				}else{
					type=1;
				}
				updateShopStaffNet=new UpdateShopStaffNet(mContext);
				updateShopStaffNet.setData(
						mcet_update_shopstaff_name.getText().toString(), 
						mcet_update_shopstaff_nickname.getText().toString(),
						mcet_update_shopstaff_mobile.getText().toString(), 
						gbv_update_shopstaff_sex.getText().toString(), 
						type,counselorId);
			}
		});
		
		//返回
		ll_shopstaff_update_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
	
	public void onEventMainThread(ResultUpdateShopStaffBean msg) {
		if(msg.IsSuccess==true){
			ToastFactory.getLongToast(mContext, "修改成功");
			Skip.mNext(mActivity, ShopStaffListActivity.class);
			
		}else{
			ToastFactory.getLongToast(mContext, "修改失败!"+msg.Message);
		}
	}
	
	/**
	 * 性别选择Dialog
	 * @author fgb
	 *
	 */
	public class selectSexDailog extends Dialog implements OnClickListener{
		int layoutRes;//布局文件
	    Context context;
	    private LinearLayout ll_sex_man,ll_sex_woman;

	    public selectSexDailog(Context context, int theme,int resLayout){
            super(context, theme);
            this.context = context;
            this.layoutRes=resLayout;
        }
	    
	    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(layoutRes);
            
            ll_sex_man=(LinearLayout) findViewById(R.id.ll_sex_man);
            ll_sex_woman=(LinearLayout) findViewById(R.id.ll_sex_woman);
            ll_sex_man.setOnClickListener(this);
            ll_sex_woman.setOnClickListener(this);
        }

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ll_sex_man:
				gbv_update_shopstaff_sex.setText("男");
				sexDailog.dismiss();
				break;
				
			case R.id.ll_sex_woman:
				gbv_update_shopstaff_sex.setText("女");
				sexDailog.dismiss();
				break;

			default:
				break;
			}
			
		}
	}
	
	/**
	 * 店铺职位选择Dialog
	 * @author fgb
	 *
	 */
	public class selectShopLaeveDailog extends Dialog implements OnClickListener{
		int layoutRes;//布局文件
	    Context context;
	    private LinearLayout ll_shop_manager,ll_shop_staff;

	    public selectShopLaeveDailog(Context context, int theme,int resLayout){
            super(context, theme);
            this.context = context;
            this.layoutRes=resLayout;
        }
	    
	    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(layoutRes);
            
            ll_shop_manager=(LinearLayout) findViewById(R.id.ll_shop_manager);
            ll_shop_staff=(LinearLayout) findViewById(R.id.ll_shop_staff);
            ll_shop_manager.setOnClickListener(this);
            ll_shop_staff.setOnClickListener(this);
        }

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ll_shop_manager:
				gbv_update_shopstaff_level.setText("店长");
				laeveDailog.dismiss();
				break;
				
			case R.id.ll_shop_staff:
				gbv_update_shopstaff_level.setText("店员");
				laeveDailog.dismiss();
				break;

			default:
				break;
			}
			
		}
	}
}
