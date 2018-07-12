package com.cesaas.android.counselor.order.fans.activity;

import io.rong.imkit.RongIM;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultFansDetailBean;
import com.cesaas.android.counselor.order.custom.flowlayout.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.flowlayout.OnTagClickListener;
import com.cesaas.android.counselor.order.custom.flowlayout.TagAdapter;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.label.EditTagActivity;
import com.cesaas.android.counselor.order.label.bean.ResultGetVipTagBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetVipTagBean.GetVipTagBean;
import com.cesaas.android.counselor.order.label.net.GetVipTagNet;
import com.cesaas.android.counselor.order.net.FansDetailNet;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * 会员情页
 * @author FGB
 *
 */
public class VipDetailActivity extends BasesActivity implements OnClickListener{
	
	private FlowTagLayout mColorFlowTagLayout;
	private List<String> dataSource;
	private TagAdapter<String> mColorTagAdapter = new TagAdapter<String>(this);
	
	private LinearLayout ll_vip_info_back,ll_set_fans_remark;
	private RelativeLayout rl_vip_tags;
	private MyImageViewWidget iv_vip_img;
	private Button btn_vip_conversation;
	private TextView tv_vip_name,tv_vip_grade,tv_vip_counselor_name,tv_vip_mobile,
			tv_vip_nick,tv_vip_sex,tv_vip_birthday,tv_vip_point;
	
	private FansDetailNet detailNet;
	private GetVipTagNet getVipTagNet;
	private ArrayList<GetVipTagBean> getVipTagBeans= new ArrayList<GetVipTagBean>();
	
	private String fansId;
	private String fansNickName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vip_detail_layout);
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			fansId=bundle.getString("fansId");
			fansNickName=bundle.getString("fansNickName");
		}		
		
		detailNet=new FansDetailNet(mActivity);
		detailNet.setData(fansId);
		
		getVipTagNet=new GetVipTagNet(mContext);
		getVipTagNet.setData(fansId);
		
		bitmapUtils = BitmapHelp.getBitmapUtils(mContext.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(mContext).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
		
		initView();
		flowTagClick();
	}
	
	public void onEventMainThread(ResultFansDetailBean msg) {
		
		if(msg.IsSuccess==true){
			tv_vip_name.setText(msg.TModel.FANS_NICKNAME);
			tv_vip_nick.setText(msg.TModel.FANS_NICKNAME);
			tv_vip_grade.setText("会员等级:"+msg.TModel.FANS_GRADE);
			tv_vip_counselor_name.setText("所属顾问:"+msg.TModel.COUNSELOR_NAME);
			tv_vip_mobile.setText(msg.TModel.FANS_MOBILE);
			tv_vip_point.setText(msg.TModel.FANS_POINT);
			bitmapUtils.display(iv_vip_img, msg.TModel.FANS_ICON, App.mInstance().bitmapConfig);
			
			if (msg.TModel.FANS_SEX.contains("1")) {
				tv_vip_sex.setText("男");
			}else{
				tv_vip_sex.setText("女");
			}
			
			if(msg.TModel.FANS_BIRTHDAY!=null){
				tv_vip_birthday.setText(msg.TModel.FANS_BIRTHDAY.split(" ")[0]);
				
			}else{
				tv_vip_birthday.setText("暂未填写");
				tv_vip_birthday.setTextColor(getResources().getColor(R.color.lightgray));
			}
			
		}else{
			ToastFactory.getLongToast(mContext, msg.Message);
		}
		
	}
	
	public void onEventMainThread(ResultGetVipTagBean msg) {
		
		if(msg.IsSuccess==true){
			
			getVipTagBeans.addAll(msg.TModel);
			//设置标签
			dataSource= new ArrayList<String>();
			mColorFlowTagLayout.setAdapter(mColorTagAdapter);
	        for (int i = 0; i < getVipTagBeans.size(); i++) {
	        	 dataSource.add(getVipTagBeans.get(i).TagName);
			}
	        mColorTagAdapter.onlyAddAll(dataSource);
			
		}else{
			ToastFactory.getLongToast(mContext, msg.Message);
		}
	}
	
	public void flowTagClick(){
        mColorFlowTagLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
            	ToastFactory.getLongToast(mContext, "颜色:" + parent.getAdapter().getItem(position)+"="+position);
            }
        });
	}
	

	/**
	 * 初始化视图控件
	 */
	private void initView() {
		mColorFlowTagLayout = (FlowTagLayout) findViewById(R.id.color_flow_layout);
        
		rl_vip_tags=(RelativeLayout) findViewById(R.id.rl_vip_tags);
		tv_vip_name=(TextView) findViewById(R.id.tv_vip_name);
		tv_vip_grade=(TextView) findViewById(R.id.tv_vip_grade);
		tv_vip_counselor_name=(TextView) findViewById(R.id.tv_vip_counselor_name);
		tv_vip_mobile=(TextView) findViewById(R.id.tv_vip_mobile);
		tv_vip_nick=(TextView) findViewById(R.id.tv_vip_nick);
		tv_vip_sex=(TextView) findViewById(R.id.tv_vip_sex);
		tv_vip_birthday=(TextView) findViewById(R.id.tv_vip_birthday);
		tv_vip_point=(TextView) findViewById(R.id.tv_vip_point);
		btn_vip_conversation=(Button) findViewById(R.id.btn_vip_conversation);
		iv_vip_img=(MyImageViewWidget) findViewById(R.id.iv_vip_img);
		ll_vip_info_back=(LinearLayout) findViewById(R.id.ll_vip_info_back);
		ll_set_fans_remark=(LinearLayout) findViewById(R.id.ll_set_fans_remark);
		
		ll_set_fans_remark.setOnClickListener(this);
		ll_vip_info_back.setOnClickListener(this);
		btn_vip_conversation.setOnClickListener(this);
		rl_vip_tags.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_set_fans_remark://设置备注
			Bundle bundle=new Bundle();
			bundle.putString("fansId", fansId);
			bundle.putString("fansNickName", fansNickName);
			Skip.mNextFroData(mActivity, SetFansRemarkActivity.class, bundle);
			break;
		
		case R.id.ll_vip_info_back://返回
			Skip.mBack(mActivity);
			break;
			
		case R.id.btn_vip_conversation://会话
			
			//启动单聊会话界面
        	if (RongIM.getInstance() != null)
        	   RongIM.getInstance().startPrivateChat(mContext, fansId,fansNickName);
			
			break;
			
		case R.id.rl_vip_tags://标签
			Skip.mNext(mActivity, EditTagActivity.class);
			break;

		default:
			break;
		}
		
	}
		
}
