package com.cesaas.android.counselor.order.training;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.GetMenuPowerBean;
import com.cesaas.android.counselor.order.bean.ResultGetMenuPowerBean;
import com.cesaas.android.counselor.order.net.GetMenuPowerNet;
import com.cesaas.android.counselor.order.stafftest.StaffTestListFragment;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.view.NoPayOrderStateView;

/**
 * 培训相关
 * @author FGB
 *
 */
public class TrainingRelatedActivity extends BasesActivity implements OnClickListener{
	
	private LinearLayout ll_training_back,ll_training;
	
	private TextView tv_training_test,tv_training_ziliao;
	private ArrayList<TextView> tvs=new ArrayList<TextView>();
	
	//获取菜单权限
	private GetMenuPowerNet menuPowerNet;
	private List<GetMenuPowerBean> menuPowerBeans;
	
	//子菜单
	private String aPPCollege;
	
	private FragmentManager fm;
	private FragmentTransaction transaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training_related);
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			aPPCollege=bundle.getString("APP_College");
		}
		
		menuPowerNet=new GetMenuPowerNet(mContext);
		menuPowerNet.setData();
		
		initView();
		mBack();
	}
	
	/**
	 * 接收菜单权限POST消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGetMenuPowerBean msg) {
		if(msg.IsSuccess==true && msg.TModel!=null){
			menuPowerBeans=new ArrayList<GetMenuPowerBean>();
			menuPowerBeans.addAll(msg.TModel);
			
			for (int i = 0; i < menuPowerBeans.size(); i++) {
				if(aPPCollege.equals(menuPowerBeans.get(i).getMENU_PARENTNO())){
					if(menuPowerBeans.get(i).getMENU_NAME().equals("培训考试")){
						ToastFactory.getLongToast(mContext, "培训考试");
					}else if(menuPowerBeans.get(i).getMENU_NAME().equals("培训资料")){
//						ToastFactory.getLongToast(mContext, "培训资料");
					}else{
//						ToastFactory.getLongToast(mContext, "2");
					}
					Log.i("Menus", menuPowerBeans.get(i).getMENU_NAME());
				}
			}
		}
	}
	
	/**
	 * 初始化控件视图
	 */
	private void initView(){
		ll_training=(LinearLayout) findViewById(R.id.ll_training);
		ll_training_back=(LinearLayout) findViewById(R.id.ll_training_back);
		tv_training_test=(TextView) findViewById(R.id.tv_training_test);
		tv_training_ziliao=(TextView) findViewById(R.id.tv_training_ziliao);
		
		tv_training_ziliao.setOnClickListener(this);
		tv_training_test.setOnClickListener(this);
		
		tvs.add(tv_training_ziliao);
		tvs.add(tv_training_test);
		
		if(!NoPayOrderStateView.getInstance().isAdded()){
			fm = getSupportFragmentManager();
			transaction = fm.beginTransaction();
			transaction.add(R.id.school_realtabcontent, new TrainingRelatedFragment()).commit();
		}
	}
	
	public void mBack(){
		ll_training_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		
		int index=-1;
		fm = getSupportFragmentManager();
		transaction = fm.beginTransaction();
		
		switch (v.getId()) {
		case R.id.tv_training_ziliao://
			index=0;
			
			if(tv_training_ziliao.isEnabled()==true){
				transaction.replace(R.id.school_realtabcontent, new TrainingRelatedFragment());
			}else{
				tv_training_ziliao.setEnabled(false);
			}
			break;
			
		case R.id.tv_training_test://
			index=1;
			transaction.replace(R.id.school_realtabcontent, new StaffTestListFragment());
			break;
		}
		
		setColor(index);
		transaction.commit();
		
	}
	private void setColor(int index) {
		// TODO Auto-generated method stub
		for(int i=0;i<tvs.size();i++){
			tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.c1));
			tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.day_sign_content_text_white_30));
		}
		tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.white));
		tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_ellipse_blue_bg));
	}
}
