package com.cesaas.android.counselor.order.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.utils.NotificationUpdate;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 设置
 * Created by FGB on 2016/3/10.
 */
@ContentView(R.layout.activity_my_setting_layout)
public class SettingActivity extends BasesActivity implements OnClickListener{
	
	@ViewInject(R.id.ll_setting_back)
	private LinearLayout ll_setting_back;
	@ViewInject(R.id.checkversion)
	private LinearLayout checkversion;
	@ViewInject(R.id.bt_logout)
	private Button bt_logout;
	@ViewInject(R.id.ll_my_about_app)
	private LinearLayout ll_my_about_app;
	@ViewInject(R.id.layout_feedback)
	private LinearLayout layout_feedback;
	@ViewInject(R.id.ll_clean_cache)
	private LinearLayout ll_clean_cache;
	@ViewInject(R.id.tv_cache_size)
	private TextView tv_cache_size;
	
	private String mobile;
	private String aPPSettings;
	//子菜单
//	private GetSubMenuPowerNet getSubMenuPowerNet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundles=getIntent().getExtras();
		if(bundles!=null){
			aPPSettings=bundles.getString("APP_Set up");
		}
		
//		getSubMenuPowerNet=new GetSubMenuPowerNet(mContext);
//		getSubMenuPowerNet.setData();
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			mobile=bundle.getString("Mobile");
		}
		
		ll_clean_cache.setOnClickListener(this);
		layout_feedback.setOnClickListener(this);
		checkversion.setOnClickListener(this);
		ll_setting_back.setOnClickListener(this);
		ll_my_about_app.setOnClickListener(this);
		bt_logout.setOnClickListener(this);
		
	}
	
	/**
	 * 接收子菜单POS消息
	 * @param msg 消息实体类
	 */
//	public void onEventMainThread(ResultGetSubMenuPowerBean msg) {
//		
//		if(msg.TModel!=null){
//			for (int i = zero; i < msg.TModel.size(); i++) {
//				if(aPPSettings.equals(msg.TModel.get(i).getMENU_PARENTNO())){
//					Log.i("Menus", msg.TModel.get(i).getMENU_NAME());
//				}
//			}
//		}
//		
//	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
		case R.id.ll_clean_cache://清理缓存
			
			new MyAlertDialog(mContext).mInitShow("温馨提示", "是否需要清理缓存，清理成功需要重新登陆",
					"确定", "点错了", new ConfirmListener() {
						@Override
						public void onClick(Dialog dialog) {
							myapp.mExecutorService.execute(new Runnable() {

								@Override
								public void run() {
									prefs.cleanAll();
									Bundle bundle =new Bundle();
									if(mobile!=null){
										bundle.putString("Mobile", mobile);
									}
									Skip.mNext(mActivity, LoginActivity.class, true,bundle);
								}
							});
						}
					}, null);
			break;
			
		case R.id.checkversion://版本检查更新
			NotificationUpdate.mInstance(mContext).mCheckVersions(true);
			
		break;
		
		case R.id.bt_logout://退出当前用户
					
			exit();
		break;
			
		case R.id.ll_setting_back://返回上一个页面
			
			initBack();
		break;
			
		case R.id.ll_my_about_app://关于
			
			Skip.mNext(mActivity, AboutActivity.class);
					
		break;
			
		case R.id.layout_feedback://意见反馈
			
			Skip.mNext(mActivity, FeedBackActivity.class);
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
							Bundle bundle1 =new Bundle();
							if(mobile!=null){
								bundle1.putString("Mobile", mobile);
								Skip.mNext(mActivity, LoginActivity.class, true,bundle1);
							}
							else{
								Skip.mNext(mActivity, LoginActivity.class, true);
							}
							
						}
					});
				}
			}, null);
	}
	
	/**
	 * 返回上一个页面
	 */
	private void initBack() {
		ll_setting_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
}
