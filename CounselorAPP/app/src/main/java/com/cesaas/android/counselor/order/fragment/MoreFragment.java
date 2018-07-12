package com.cesaas.android.counselor.order.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.LoginActivity;
import com.cesaas.android.counselor.order.activity.SettingActivity;
import com.cesaas.android.counselor.order.activity.ShopManageActvity;
import com.cesaas.android.counselor.order.activity.UserCentreActivity;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserBean;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
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
 * 我的页面
 * Created by FGB on 2016/3/7.
 */
public class MoreFragment extends Fragment implements OnClickListener{
	
	public static final String TAG="MoreFragment";

	private LinearLayout ll_earnings;
	private LinearLayout ll_ranking;
	private LinearLayout ll_shop_manage;
    private LinearLayout ll_my_setting;
	private LinearLayout ll_help;
	private LinearLayout ll_labes;
	private LinearLayout ll_sales_talk;
	private RelativeLayout rl_set_user_info;
	private Button bt_logout;
	private View view_line_h;
	
	private MyImageViewWidget iv_user_icon;
	private TextView tv_my_name;
	private TextView tv_my_grade;
	private TextView tv_my_shop;
	
	
	private AbPrefsUtil prefs;
	private View view;
	private App myapp;
	private UserBean userBean;
	private UserInfoNet userInfoNet;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	view=inflater.inflate(R.layout.activity_more_layout, container, false);
        initView();
        return view;
    }
    
    public void initView(){
    	
    	ll_earnings=(LinearLayout)view.findViewById(R.id.ll_earnings);
    	ll_ranking=(LinearLayout)view.findViewById(R.id.ll_ranking);
    	ll_shop_manage=(LinearLayout)view.findViewById(R.id.ll_shop_manage);
    	ll_my_setting=(LinearLayout) view.findViewById(R.id.ll_my_setting);
    	ll_help=(LinearLayout) view.findViewById(R.id.ll_help);
    	ll_labes=(LinearLayout) view.findViewById(R.id.ll_labes);
    	ll_sales_talk=(LinearLayout) view.findViewById(R.id.ll_sales_talk);
    	bt_logout=(Button) view.findViewById(R.id.bt_logout);
    	rl_set_user_info=(RelativeLayout) view.findViewById(R.id.rl_set_user_info);
    	view_line_h=view.findViewById(R.id.view_line_h);
    	
    	iv_user_icon=(MyImageViewWidget)view.findViewById(R.id.iv_user_icon);
    	tv_my_name=(TextView)view.findViewById(R.id.tv_my_name);
    	tv_my_shop=(TextView)view.findViewById(R.id.tv_my_shop);
    	tv_my_grade=(TextView)view.findViewById(R.id.tv_my_grade);
    	
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        userInfoNet=new UserInfoNet(getContext());
        userInfoNet.setData();
        
        prefs= AbPrefsUtil.getInstance();
        myapp = App.mInstance();
        
    }

	@Override
	public void fetchData() {

	}

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_set_user_info:
    			Bundle bundle=new Bundle();
        		bundle.putString("icon", userBean.Icon);
        		bundle.putString("nickName", userBean.Name);
        		bundle.putString("mobile", userBean.Mobile);
        		Skip.mNextFroData(getActivity(), UserCentreActivity.class,bundle);
			break;
			
		case R.id.ll_my_setting:
			Bundle bundle1 =new Bundle();
			if(userBean.Mobile!=null){
				bundle1.putString("Mobile", userBean.Mobile);
				Skip.mNextFroData(this.getActivity(), SettingActivity.class,bundle1);
				
			}else{
				Skip.mNext(this.getActivity(), SettingActivity.class);
			}
			
			break;
			
		case R.id.ll_sales_talk:
			Skip.mNext(getActivity(), SalesTalkProviderActivity.class);
			
			break;
			
		case R.id.ll_labes:
			Bundle bundle2=new Bundle();
			bundle2.putString("VipId", userBean.VipId+"");
			Skip.mNextFroData(getActivity(), LabelListAactivity.class,bundle2);
			break;
			
		case R.id.ll_help:
			Skip.mNext(getActivity(), O2OOrderHelpActivity.class);
			break;
			
		case R.id.ll_earnings:
			Skip.mNext(getActivity(), EarningsActivity.class);
			break;
			
		case R.id.ll_ranking:
			Skip.mNext(getActivity(), RanKingActivity.class);
			break;
			
		case R.id.ll_shop_manage:
			Skip.mNext(getActivity(), ShopManageActvity.class);
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
		new MyAlertDialog(getContext()).mInitShow("退出登录", "是否退出登录，退出后将不能接收最新消息",
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
							
							Skip.mNext(getActivity(), LoginActivity.class, true,bundle);
						}
					});
				}
			}, null);
	}

}
