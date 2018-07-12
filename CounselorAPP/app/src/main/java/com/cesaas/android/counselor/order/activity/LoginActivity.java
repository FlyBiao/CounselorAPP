package com.cesaas.android.counselor.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.SuperMainActivity;
import com.cesaas.android.counselor.order.activity.user.ITSupportActivity;
import com.cesaas.android.counselor.order.activity.user.adapter.CompanyAdapter;
import com.cesaas.android.counselor.order.activity.user.bean.CompanyBean;
import com.cesaas.android.counselor.order.activity.user.password.ForgotPasswordActivity;
import com.cesaas.android.counselor.order.activity.user.register.RegisterActivity;
import com.cesaas.android.counselor.order.bean.LoginBean;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.LoginNet;
import com.cesaas.android.counselor.order.utils.AbAppUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.AbStrUtil;
import com.cesaas.android.counselor.order.utils.MaxLengthWatcher;
import com.cesaas.android.counselor.order.utils.OtherUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录
 * @author FGB
 * 
 */
@ContentView(R.layout.activity_login_layout)
public class LoginActivity extends BasesActivity {

	private LoginNet loginNet;
	private WaitDialog dialog;
	private String user ;
	private String pwd ;
	private boolean isSwitch=false;

	@ViewInject(R.id.ll_base_title_back)
	private LinearLayout llBaseBack;
	@ViewInject(R.id.tv_base_title)
	private TextView tvBaseTitle;
	private RecyclerView rv_company_list;

	@ViewInject(R.id.bt_login)
	private Button bt_login;
	@ViewInject(R.id.bt_register)
	private Button bt_register;
	@ViewInject(R.id.et_phone_num)
	private EditText et_phone_num;
	@ViewInject(R.id.et_password)
	private EditText et_password;
	@ViewInject(R.id.tv_user_register)
	private TextView tv_user_register;
	@ViewInject(R.id.tv_forgot_password)
	private TextView tv_forgot_password;
	@ViewInject(R.id.tv_switch)
	private TextView tv_switch;
	@ViewInject(R.id.user_icon)
	private CircleImageView userIcon;
	@ViewInject(R.id.tv_user_name)
	private TextView userNmae;
	@ViewInject(R.id.tv_info)
	private TextView tv_info;
	@ViewInject(R.id.ll_check_pwd)
	private LinearLayout ll_check_pwd;
	@ViewInject(R.id.iv_check_pwd)
	private ImageView iv_check_pwd;
	@ViewInject(R.id.ll_technology)
	LinearLayout ll_technology;

	private int isSwitchLogin=0;
	private boolean isCheckPwd=false;
	private String loginAddress;
	public String token;
	private int type;
	private CompanyAdapter adapter;
	private List<CompanyBean> companyBean=new ArrayList<>();

	// 定义PopupWindow
	private PopupWindow popWindow;
	// 获取手机屏幕分辨率的类
	private DisplayMetrics dm;
	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog = new WaitDialog(mContext);
		
		//获取已登录的账号
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			type=bundle.getInt("type");
			et_phone_num.setText(bundle.getString("Mobile"));
			userNmae.setText(bundle.getString("nickName"));
			if(bundle.getString("userIcon")!=null && !"".equals(bundle.getString("userIcon"))){
				Glide.with(mContext).load(bundle.getString("userIcon")).into(userIcon);
			}
			else{
				userIcon.setImageResource(R.mipmap.not_user_icon);
			}
		}

		if(!TextUtils.isEmpty(et_phone_num.getText().toString())){
			et_phone_num.setVisibility(View.GONE);
		}else{
			et_phone_num.setVisibility(View.VISIBLE);
		}

		if(!TextUtils.isEmpty(et_phone_num.getText().toString())){
			tv_switch.setVisibility(View.VISIBLE);
		}else{
			tv_switch.setVisibility(View.GONE);
		}

		initData();
		if(type!=100){
			if (AbPrefsUtil.getInstance().getBoolean(Constant.SPF_ISLOGIN)) {
				Skip.mNext(mActivity,SuperMainActivity.class,true);
			}
		}
	}

	// 数据初始化
	public void initData() {
//		loginAddress=AMapLocationUtils.initAMapLocation(mContext);

		llBaseBack.setVisibility(View.GONE);
		tvBaseTitle.setText("登录");
		
		// 控制editText输入的手机号长度【注：13是因为把空格也算进去了】
		et_phone_num.addTextChangedListener(new MaxLengthWatcher(13, et_phone_num));
		AbStrUtil.editTextFilterChinese(et_password);
		et_phone_num.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				OtherUtil.formatPhoneNum(et_phone_num, s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				et_password.setText("");
			}
		});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (OtherUtil.isShouldHideInput(v, ev)) {
				AbAppUtil.hideSoftInput(mContext);
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	// 点击事件
	@OnClick({ R.id.bt_login,R.id.bt_register,R.id.tv_user_register,R.id.tv_forgot_password,R.id.tv_switch,R.id.ll_check_pwd,R.id.ll_technology})
	public void onClickt(View v) {
		switch (v.getId()) {
		case R.id.bt_login:// 登录

			user= et_phone_num.getText().toString().trim();
			pwd= et_password.getText().toString().trim();
			if (OtherUtil.phoneVerify(mContext, user)) {
				if (OtherUtil.passwordVerify(mContext, pwd)) {
					if(et_password.getText().toString().equals("test")){
						mCache.put(Constant.IS_SWITCH_SERVER,"false");
						isSwitchLogin=1;
					}else if(et_password.getText().toString().equals("pos811")){
						mCache.put(Constant.IS_SWITCH_SERVER,"pos");
						isSwitchLogin=2;
					} else if(et_password.getText().toString().equals("config")){
						mCache.put(Constant.IS_SWITCH_SERVER,"true");
						isSwitchLogin=3;
					}

					if(isSwitchLogin==1){
						isSwitchLogin=0;
						ToastFactory.getLongToast(mContext,"已切换到测试服务器！");
						et_password.setText("");
						et_password.setHint("请输入密码");
					}else if(isSwitchLogin==2){
						isSwitchLogin=0;
						ToastFactory.getLongToast(mContext,"已切换到811服务器！");
						et_password.setText("");
						et_password.setHint("请输入密码");
					} else if(isSwitchLogin==3){
						isSwitchLogin=0;
						ToastFactory.getLongToast(mContext,"已切换到正式服务器！");
						et_password.setText("");
						et_password.setHint("请输入密码");
					}
					else{
//						loginNet = new LoginNet(mContext, user, pwd,GetPhoneInfoUtils.getPhoneInfo(mContext),loginAddress);
						loginNet = new LoginNet(mContext, user, pwd);
						loginNet.mPostNet();
						dialog.mStart();
					}
				}
			}
			break;
			case R.id.ll_technology:
				Skip.mNext(mActivity,ITSupportActivity.class);
				break;

			case R.id.tv_switch:
				if(isSwitch==true){
					et_phone_num.setVisibility(View.VISIBLE);
					isSwitch=false;
				}else{
					et_phone_num.setVisibility(View.GONE);
					isSwitch=true;
				}
				break;

			case R.id.tv_user_register:
				Skip.mNext(mActivity, RegisterActivity.class);
				break;

			case R.id.tv_forgot_password:
				Skip.mNext(mActivity,ForgotPasswordActivity.class);
				break;
		case R.id.bt_register:
			Skip.mNext(mActivity, RegisterAactivity.class);
			break;
		case R.id.ll_check_pwd:
			if(isCheckPwd==false){
				isCheckPwd=true;
				iv_check_pwd.setImageResource(R.mipmap.check_pwd_b);
				et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			}else{
				isCheckPwd=false;
				iv_check_pwd.setImageResource(R.mipmap.check_pwd);
				et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}
			break;
		}
	}

	public void onEventMainThread(final LoginBean msg) { // 登录成功回调
		dialog.mStop();
		if (msg.IsSuccess==true) {
			if(msg.TModel.UserTicket!=null && !"".equals(msg.TModel.UserTicket!=null)){//直接登录
				myapp.mExecutorService.execute(new Runnable() {
					@Override
					public void run() {
						String tel = et_phone_num.getText().toString().replace(" ", "");
						prefs.putBoolean(Constant.SPF_ISLOGIN, msg.IsSuccess);
						prefs.putString(Constant.SPF_TOKEN,msg.TModel.UserTicket);
						prefs.putString(Constant.SPF_AUTHKEY,msg.TModel.AuthKey);
						prefs.putString(Constant.SPF_ACCOUNT, tel);
						prefs.putString(Constant.SPF_TIME,String.valueOf(System.currentTimeMillis()));

						Intent intent = new Intent(LoginActivity.this, SuperMainActivity.class);
						intent.putExtra("ResultNo",msg.ResultNo);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						LoginActivity.this.finish();
					}
				});
			}else{//选择企业登录
				companyBean=new ArrayList<>();
				companyBean.addAll(msg.TModel.Company);
				showPopupWindow(tv_info);
			}

		}else{
			ToastFactory.getLongToast(mContext,"登录失败！"+msg.Message);
		}
	}

	/**
	 * 显示PopupWindow弹出菜单
	 */
	private void showPopupWindow(View parent) {
		if (popWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.popwindow_company_layout, null);
			dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			// 创建一个PopuWidow对象
			popWindow = new PopupWindow(view, dm.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);
		}
		// 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
		popWindow.setFocusable(false);
		// 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_blue_bg));
		// 设置允许在外点击消失
		popWindow.setOutsideTouchable(true);
		// PopupWindow的显示及位置设置
		popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

		rv_company_list = (RecyclerView)view.findViewById(R.id.rv_company_list);
		rv_company_list.setLayoutManager(new LinearLayoutManager(this));
		adapter=new CompanyAdapter(companyBean);
		rv_company_list.setAdapter(adapter);
		rv_company_list.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
			@Override
			public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
				if(et_password.getText().toString().equals("test")){
					mCache.put(Constant.IS_SWITCH_SERVER,"false");
					isSwitchLogin=1;
				}else if(et_password.getText().toString().equals("pos")){
					mCache.put(Constant.IS_SWITCH_SERVER,"pos");
					isSwitchLogin=2;
				} else if(et_password.getText().toString().equals("config")){
					mCache.put(Constant.IS_SWITCH_SERVER,"true");
					isSwitchLogin=3;
				}
				if(isSwitchLogin==1){
					isSwitchLogin=0;
					ToastFactory.getLongToast(mContext,"已切换到测试服务器！");
					et_password.setText("");
					et_password.setHint("请输入密码");
				}else if(isSwitchLogin==2){
					isSwitchLogin=0;
					ToastFactory.getLongToast(mContext,"已切换到811服务器！");
					et_password.setText("");
					et_password.setHint("请输入密码");
				} else if(isSwitchLogin==3){
					isSwitchLogin=0;
					ToastFactory.getLongToast(mContext,"已切换到正式服务器！");
					et_password.setText("");
					et_password.setHint("请输入密码");
				}
				else{
//					LoginNet net=new LoginNet(mContext, user, pwd,companyBean.get(position).getTId(), GetPhoneInfoUtils.getPhoneInfo(mContext),loginAddress);
					LoginNet net=new LoginNet(mContext, user, pwd,companyBean.get(position).getTId());
					net.mPostNet();
					popWindow.dismiss();
				}
			}
		});
	}

}
