package com.cesaas.android.counselor.order.staffmange;

import java.util.ArrayList;

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
import com.cesaas.android.counselor.order.adapter.CustomCunselorLevelAdapter;
import com.cesaas.android.counselor.order.bean.ResultAddShopStaffBean;
import com.cesaas.android.counselor.order.bean.ResultCounselorLevelBean;
import com.cesaas.android.counselor.order.bean.ResultCounselorLevelBean.CounselorLevelBean;
import com.cesaas.android.counselor.order.net.AddShopStaffNet;
import com.cesaas.android.counselor.order.net.CounselorLevelNet;
import com.cesaas.android.counselor.order.utils.OtherUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.GroupButtonView;
import com.cesaas.android.counselor.order.widget.MClearEditText;

/**
 * 新增店员
 * @author FGB
 *
 */
public class AddShopStaffActivity extends BasesActivity implements OnClickListener{
	
	private TextView tv_counselor_level;
	private Button btn_create_staff_code;
	private LinearLayout ll_shop_back,ll_cunselor_level_list;
	private MClearEditText mcet_shopstaff_mobile,mcet_shopstaff_nickname,mcet_shopstaff_name;
	private GroupButtonView gbv_shopstaff_sex,gbv_shopstaff_level ;
	private ListView lv_cunselor_level;
	
	private int levelId;//等级ID
	private CustomCunselorLevelDialog dialog;
	private CustomCunselorLevelAdapter adapter;
	private CounselorLevelNet counselorLevelNet;//店员等级List
	private ArrayList<CounselorLevelBean> levelBeans=new ArrayList<CounselorLevelBean>();
	
	private AddShopStaffNet addShopStaffNet;//添加店员
	
	private int sex=0;
	private int type=0;
	private String name;
	private String nameNick;
	private String mobile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_shop_staff);
		initView ();
	}
	
	/**
	 * 接受店员等级消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultCounselorLevelBean msg) {
		if(msg.IsSuccess==true && msg.TModel!=null){
			levelBeans.clear();
			levelBeans.addAll(msg.TModel);
			
			//显示店员级别列表
			adapter=new CustomCunselorLevelAdapter(levelBeans, mContext);
			lv_cunselor_level.setAdapter(adapter);
			
			initItemClickListener();
		}
	}
	
	/**
	 * ListView Item Click
	 */
	private void initItemClickListener() {
		lv_cunselor_level.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				tv_counselor_level.setText(levelBeans.get(position).getTitle());
				levelId=levelBeans.get(position).getId();
				// 切换选中的内容
				adapter.setSelectedItem(position);
				// 更新数据
				adapter.notifyDataSetChanged();
				
				dialog.dismiss();
			}
		});
	}
	
	/**
	 * 初始化视图控件
	 */
	public void initView (){
		tv_counselor_level=(TextView) findViewById(R.id.tv_counselor_level);
		mcet_shopstaff_mobile=(MClearEditText) findViewById(R.id.mcet_shopstaff_mobile);
		mcet_shopstaff_nickname=(MClearEditText) findViewById(R.id.mcet_shopstaff_nickname);
		mcet_shopstaff_name=(MClearEditText) findViewById(R.id.mcet_shopstaff_name);
		ll_cunselor_level_list=(LinearLayout) findViewById(R.id.ll_cunselor_level_list);
		ll_shop_back=(LinearLayout) findViewById(R.id.ll_shop_back);
		ll_cunselor_level_list.setOnClickListener(this);
		ll_shop_back.setOnClickListener(this);
		
		gbv_shopstaff_sex= (GroupButtonView) findViewById(R.id.gbv_shopstaff_sex);
		gbv_shopstaff_sex.setOnGroupBtnClickListener(new GroupButtonView.OnGroupBtnClickListener() {
	            @Override
	            public void groupBtnClick(String code) {
	            	sex=Integer.parseInt(code);
	            }
	        });
	        
		gbv_shopstaff_level= (GroupButtonView) findViewById(R.id.gbv_shopstaff_level);

		gbv_shopstaff_level.setOnGroupBtnClickListener(new GroupButtonView.OnGroupBtnClickListener() {
	            @Override
	            public void groupBtnClick(String code) {
	                type=Integer.parseInt(code);
	            }
	        });
		
		//添加店员并且生成二维码
		btn_create_staff_code=(Button) findViewById(R.id.btn_create_staff_code);
		btn_create_staff_code.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCheck();
			}
		});
	}
	
	public void mCheck(){
		if(!TextUtils.isEmpty(mcet_shopstaff_name.getText().toString())){
			name=mcet_shopstaff_nickname.getText().toString();
			
			if(!TextUtils.isEmpty(mcet_shopstaff_nickname.getText().toString())){
				nameNick=mcet_shopstaff_name.getText().toString();
				
				if(OtherUtil.phoneVerify(mContext,mcet_shopstaff_mobile.getText().toString())){
					
					mobile=mcet_shopstaff_mobile.getText().toString();
					addShopStaffNet=new AddShopStaffNet(mContext);
					if(sex==0){
						addShopStaffNet.setData("男", type, name,nameNick, mobile,levelId);
					}else{
						addShopStaffNet.setData("女", type, name,nameNick, mobile,levelId);
					}
				}
			}else{
				ToastFactory.getLongToast(mContext, "请输入店员昵称！");
			}
		}else{
			ToastFactory.getLongToast(mContext, "请输入店员名称！");
		}
	}
	
	public void onEventMainThread(ResultAddShopStaffBean msg) {
		if(msg.IsSuccess==true){
			//返回店员列表页面
			ToastFactory.getLongToast(mContext, "新增店员成功！");
			finish();
			Skip.mNext(mActivity, ShopStaffMangerActivity.class);
			
		}else{
			ToastFactory.getLongToast(mContext, msg.Message);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_cunselor_level_list://等级列表
			counselorLevelNet=new CounselorLevelNet(mContext);
			counselorLevelNet.setData();
			dialog=new CustomCunselorLevelDialog(mContext, R.style.dialog, R.layout.custom_cunselor_level_dialog_layout);
			dialog.show();
			break;
			
		case R.id.ll_shop_back://返回
			Skip.mBack(mActivity);
		default:
			break;
		}
	}
	
	/**
	 * 自定义店员级别Dialog
	 * @author FGB
	 *
	 */
	public class CustomCunselorLevelDialog extends Dialog{

		int layoutRes;//布局文件
	    Context context;
		
		public CustomCunselorLevelDialog(Context context,int theme,int resLayout) {
			super(context, theme);
            this.context = context;
            this.layoutRes=resLayout;
		}
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
            this.setContentView(layoutRes);
            lv_cunselor_level=(ListView) findViewById(R.id.lv_cunselor_level);
		}
		
	}
	
}