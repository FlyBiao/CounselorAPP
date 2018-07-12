package com.cesaas.android.counselor.order.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserBean;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.earnings.activity.EarningsActivity;
import com.cesaas.android.counselor.order.earnings.activity.RanKingActivity;
import com.cesaas.android.counselor.order.express.view.O2OOrderHelpActivity;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.label.LabelListAactivity;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.rong.activity.SalesTalkProviderActivity;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;

/**
 * 用户中心页面
 * @author fgb
 *
 */
public class UserInfoActivity extends BasesActivity implements OnClickListener{

	private LinearLayout ll_earnings;
	private LinearLayout ll_ranking;
	private LinearLayout ll_shop_manage;
    private LinearLayout ll_my_setting;
	private LinearLayout ll_help;
	private LinearLayout ll_labes;
	private LinearLayout ll_sales_talk;
	private LinearLayout ll_user_info_back;
	private RelativeLayout rl_set_user_info;
	private Button bt_logout;
	private View view_line_h;
	
	private MyImageViewWidget iv_user_icon;
	private TextView tv_my_name;
	private TextView tv_my_grade;
	private TextView tv_my_shop;
	
	private AbPrefsUtil prefs;
	private App myapp;
	private UserBean userBean;
	private UserInfoNet userInfoNet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_layout);
		userInfoNet=new UserInfoNet(mContext);
        userInfoNet.setData();
        
        prefs= AbPrefsUtil.getInstance();
        myapp = App.mInstance();
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		 {
		  // 透明状态栏
		  getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
        
		initView();
		
	}
	
	/**
	 * 接受用户POST信息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultUserBean msg) {
		userBean=msg.TModel;
		tv_my_name.setText(userBean.Name);
		tv_my_grade.setText(userBean.TypeName);
		tv_my_shop.setText(userBean.ShopName);
		bitmapUtils.display(iv_user_icon, userBean.Icon, App.mInstance().bitmapConfig);
		
		if(userBean.TypeId.equals("1")){//店长
			view_line_h.setVisibility(View.VISIBLE);
			ll_shop_manage.setVisibility(View.VISIBLE);
		}else{//员工
			view_line_h.setVisibility(View.GONE);
			ll_shop_manage.setVisibility(View.GONE);
		}
}
	
	
	public void initView(){
		ll_user_info_back=(LinearLayout) findViewById(R.id.ll_user_info_back);
    	ll_earnings=(LinearLayout)findViewById(R.id.ll_earnings);
    	ll_ranking=(LinearLayout)findViewById(R.id.ll_ranking);
    	ll_shop_manage=(LinearLayout)findViewById(R.id.ll_shop_manage);
    	ll_my_setting=(LinearLayout) findViewById(R.id.ll_my_setting);
    	ll_help=(LinearLayout)findViewById(R.id.ll_help);
    	ll_labes=(LinearLayout)findViewById(R.id.ll_labes);
    	ll_sales_talk=(LinearLayout) findViewById(R.id.ll_sales_talk);
    	bt_logout=(Button) findViewById(R.id.bt_logout);
    	rl_set_user_info=(RelativeLayout)findViewById(R.id.rl_set_user_info);
    	view_line_h=findViewById(R.id.view_line_h);
    	
    	iv_user_icon=(MyImageViewWidget)findViewById(R.id.iv_user_icon);
    	tv_my_name=(TextView)findViewById(R.id.tv_my_name);
    	tv_my_shop=(TextView)findViewById(R.id.tv_my_shop);
    	tv_my_grade=(TextView)findViewById(R.id.tv_my_grade);
    	
    	ll_user_info_back.setOnClickListener(this);
    	rl_set_user_info.setOnClickListener(this);
    	ll_earnings.setOnClickListener(this);
    	ll_ranking.setOnClickListener(this);
    	ll_shop_manage.setOnClickListener(this);
    	ll_my_setting.setOnClickListener(this);
    	ll_help.setOnClickListener(this);
    	bt_logout.setOnClickListener(this);
    	ll_sales_talk.setOnClickListener(this);
    	ll_labes.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.ll_user_info_back:
			Skip.mBack(mActivity);
			break;
		
		case R.id.rl_set_user_info:
				Bundle bundle=new Bundle();
	    		bundle.putString("icon", userBean.Icon);
	    		bundle.putString("nickName", userBean.Name);
	    		bundle.putString("mobile", userBean.Mobile);
	    		Skip.mNextFroData(mActivity, UserCentreActivity.class,bundle);
			break;
			
		case R.id.ll_my_setting:
			Bundle bundle1 =new Bundle();
			if(userBean.Mobile!=null){
				bundle1.putString("Mobile", userBean.Mobile);
				Skip.mNextFroData(mActivity, SettingActivity.class,bundle1);
				
			}else{
				Skip.mNext(mActivity, SettingActivity.class);
			}
			
			break;
			
		case R.id.ll_sales_talk:
			Skip.mNext(mActivity, SalesTalkProviderActivity.class);
			
			break;
			
		case R.id.ll_labes:
			Bundle bundle2=new Bundle();
			bundle2.putString("VipId", userBean.VipId+"");
			Skip.mNextFroData(mActivity, LabelListAactivity.class,bundle2);
			break;
			
		case R.id.ll_help:
			Skip.mNext(mActivity, O2OOrderHelpActivity.class);
			break;
			
		case R.id.ll_earnings:
			Skip.mNext(mActivity, EarningsActivity.class);
			break;
			
		case R.id.ll_ranking:
			Skip.mNext(mActivity, RanKingActivity.class);
			break;
			
		case R.id.ll_shop_manage:
			Skip.mNext(mActivity, ShopManageActvity.class);
			break;
			
		case R.id.bt_logout:
			exit();
			break;
	
		default:
			break;
		}
	}

	/**
	 * 退出当前用户
	 */
	public void exit() {
		new MyAlertDialog(mContext).mInitShow("退出登录", "是否退出登录，退出后将不能接收最新消息",
			"我知道", "点错了", new ConfirmListener() {
				@Override
				public void onClick(Dialog dialog) {
					myapp.mExecutorService.execute(new Runnable() {
	
						@Override
						public void run() {
							prefs.cleanAll();
							Bundle bundle =new Bundle();
							if(userBean.Mobile!=null){
								bundle.putString("Mobile", userBean.Mobile);
							}
							
							Skip.mNext(mActivity, LoginActivity.class, true,bundle);
						}
					});
				}
			}, null);
	}
}
