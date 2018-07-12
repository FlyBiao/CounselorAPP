package com.cesaas.android.counselor.order.activity;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

import java.util.ArrayList;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultFansDetailBean;
import com.cesaas.android.counselor.order.bean.ResultFansDetailBean.FansDetailBean;
import com.cesaas.android.counselor.order.bean.ResultFansDetailBean.TagBean;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.label.VipLabelListAactivity;
import com.cesaas.android.counselor.order.net.FansDetailNet;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.FlowLayoutWidget;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.get_shopfans_detail_layout)
public class GetShopFansDetailActivity extends BasesActivity{

	public static final String TAG="GetShopFansDetailActivity";
	
	@ViewInject(R.id.tv_shopfans_name)
	private TextView tv_shopfans_name;
	@ViewInject(R.id.tv_shop_counselor_name)
	private TextView tv_shop_counselor_name;
	@ViewInject(R.id.iv_shopfans_img)
	private MyImageViewWidget iv_shopfans_img;
	@ViewInject(R.id.tv_shopfans_mobile)
	private TextView tv_shopfans_mobile;
	@ViewInject(R.id.tv_shopfans_nick)
	private TextView tv_shopfans_nick;
	@ViewInject(R.id.tv_shopfans_sex)
	private TextView tv_shopfans_sex;
	@ViewInject(R.id.tv_shopfans_birthday)
	private TextView tv_shopfans_birthday;
	@ViewInject(R.id.tv_shopfans_point)
	private TextView tv_shopfans_point;
	@ViewInject(R.id.tv_shopfans_grade)
	private TextView tv_shopfans_grade;
	@ViewInject(R.id.btn_fans_conversation)
	private Button btn_fans_conversation;
	
	@ViewInject(R.id.ll_shop_fans)
	private LinearLayout ll_shop_fans;
	@ViewInject(R.id.ll_my_label)
	private LinearLayout ll_my_label;
	@ViewInject(R.id.tv_vip_labels)
	private FlowLayoutWidget mFlowLayout;
	@ViewInject(R.id.tv_label_null)
	private TextView tv_label_null;
	
	private String fansId;
	
	private FansDetailBean bean;
	private FansDetailNet detailNet;
	
	private UserInfoNet userInfoNet;
	private UserBean userBean;
	private static GetShopFansDetailActivity fragment;
	private ArrayList<TagBean> tagLis= new ArrayList<TagBean>();
	
	
	public static Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
//			fragment.loadData();
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			fansId=bundle.getString("fansId");
		}
		super.onCreate(savedInstanceState);
		
		fragment=this;
		
		bitmapUtils = BitmapHelp.getBitmapUtils(mContext.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(mContext).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
		loadData();
		
		labelClick();
		initBackShopFans();
		FansConverSation();
		
	}
	
	public void loadData(){
		
		detailNet=new FansDetailNet(mActivity);
		detailNet.setData(fansId);
		
		userInfoNet=new UserInfoNet(mContext);
		userInfoNet.setData();
	}
	
	public void onEventMainThread(ResultFansDetailBean msg) {
		
		if(tagLis.size()>0){
			tagLis.clear();
		}
		
		if(msg!=null){
			bean=msg.TModel;
			tv_shopfans_name.setText(bean.FANS_NICKNAME);
			tv_shopfans_nick.setText(bean.FANS_NICKNAME);
			tv_shopfans_grade.setText("会员等级:"+bean.FANS_GRADE);
			tv_shop_counselor_name.setText("所属顾问:"+bean.COUNSELOR_NAME);
			tv_shopfans_mobile.setText(bean.FANS_MOBILE);
			tv_shopfans_point.setText(bean.FANS_POINT);
			bitmapUtils.display(iv_shopfans_img, bean.FANS_ICON, App.mInstance().bitmapConfig);
			
			if (bean.FANS_SEX.equals("zero")) {
				tv_shopfans_sex.setText("男");
			}else{
				tv_shopfans_sex.setText("女");
			}
			
			if(bean.FANS_BIRTHDAY!=null){
				tv_shopfans_birthday.setText(bean.FANS_BIRTHDAY.split(" ")[0]);
				
			}else{
				tv_shopfans_birthday.setText("暂未填写");
				tv_shopfans_birthday.setTextColor(getResources().getColor(R.color.lightgray));
			}
			
			//添加标签到集合中
			tagLis.addAll(msg.TModel.TAGS);
			initChildViews();
		}
		
	}
	
	public void onEventMainThread(ResultUserBean msg) {
		userBean=msg.TModel;
	}
	
	public void initChildViews(){
		
		if(tagLis.size()!=0){
			mFlowLayout.removeAllViews();
		}
		
		if(tagLis.size()==0){
        	tv_label_null.setVisibility(View.VISIBLE);
			mFlowLayout.setVisibility(View.GONE);
			
        }else{
        	tv_label_null.setVisibility(View.GONE);
			MarginLayoutParams lp = new MarginLayoutParams(
	                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	        		lp.leftMargin = 17;
	        		
	        for(int i = 0; i < tagLis.size(); i ++){
	             TextView view = new TextView(this);
            	 view.setText(tagLis.get(i).Name);
            	 view.setTextColor(Color.WHITE);
 	             view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
 	             mFlowLayout.addView(view,lp);
	        }
        }
	}
	
	
	/**
	 * 聊天
	 */
	public void FansConverSation(){
		btn_fans_conversation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//启动单聊会话界面
            	if (RongIM.getInstance() != null)
            	   RongIM.getInstance().startPrivateChat(mActivity, 
            			   bean.FANS_ID, bean.FANS_NICKNAME);
            	
            	RongIM.getInstance().refreshUserInfoCache(new UserInfo(bean.FANS_ID,"",Uri.parse(bean.FANS_ICON)));
            	RongIM.getInstance().refreshUserInfoCache(new UserInfo(userBean.VipId+"","",Uri.parse(userBean.Icon)));
            	
			    }
		});
	}
	
	/**
	 * 标签点击事件
	 */
	public void labelClick(){
		ll_my_label.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("fansId", fansId);
				Skip.mNextFroData(mActivity, VipLabelListAactivity.class,bundle);
				
			}
		});
	}
	
	/**
	 * 返回上一个页面
	 */
	public void initBackShopFans(){
		ll_shop_fans.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SerachVipActivity.activity.finish();
				finish();
			}
		});
	}
}
