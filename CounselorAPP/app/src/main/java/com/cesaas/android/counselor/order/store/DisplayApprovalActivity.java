package com.cesaas.android.counselor.order.store;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.CustomTextDialog;
import com.cesaas.android.counselor.order.dialog.CustomTextQueryDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.store.adapter.CompleteDisplayAdapter;
import com.cesaas.android.counselor.order.store.adapter.ReferenceDisplayAdapter;
import com.cesaas.android.counselor.order.store.bean.AapprovalDisplayImagesBean;
import com.cesaas.android.counselor.order.store.bean.CompleteImages;
import com.cesaas.android.counselor.order.store.bean.DisplayImagesBean;
import com.cesaas.android.counselor.order.store.bean.DisplayRemarkBean;
import com.cesaas.android.counselor.order.store.bean.GraffitiImagePathBean;
import com.cesaas.android.counselor.order.store.bean.Images;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayApprovaBean;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayApprovaPassBean;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayDetailBean;
import com.cesaas.android.counselor.order.store.bean.Shows;
import com.cesaas.android.counselor.order.store.net.StoreDisplayApprovalNet;
import com.cesaas.android.counselor.order.store.net.StoreDisplayApprovalPassNet;
import com.cesaas.android.counselor.order.store.net.StoreDisplayDetailNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;

import cn.forward.androids.utils.LogUtil;
import cn.hzw.graffiti.GraffitiActivity;

/**
 * 陈列审批页面
 * @author FGB
 *
 */
public class DisplayApprovalActivity extends BasesActivity implements OnClickListener{

	public static final int REQ_CODE_SELECT_IMAGE = 100;
	public static final int REQ_CODE_GRAFFITI = 101;
	
	private RecyclerView rvReferenceDispaly;//参考陈列
	private RecyclerView rvCompleteDispaly;//完成陈列
	private LinearLayout llBack;
	private TextView tvBaseTitle,tvApprovalPass,tvApprovalThrough;
	private TextView tvDisplayDetailContent;
	
	private List<Shows> referenceIcon;//参考陈列Icon
	private List<CompleteImages> completeIcon;//完成陈列Icon

	private GraffitiImagePathBean imagePathBean;//涂鸦图片路径Bean

	private JSONArray DisplayApprovaImages=new JSONArray();//陈列审批图片数据
	private String SheetId;
	private String FlowId;
	private String TaskId;
	private String displayRemark;//陈列备注
	private int approvalType=1;//审批类型 zero：通过，1：不通过（默认0：不通过）

	private StoreDisplayDetailNet displayDetailNet;
	private ArrayList<ResultStoreDisplayDetailBean.StoreDisplayDetailBean> displayDetailBeens= new ArrayList<ResultStoreDisplayDetailBean.StoreDisplayDetailBean>();

	private StoreDisplayApprovalNet displayApprovalNet;
	private StoreDisplayApprovalPassNet displayApprovalPassNet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_approval_activity);

		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			SheetId=bundle.getString("SheetId");
			TaskId=bundle.getString("TaskId");
			FlowId=bundle.getString("FlowId");
		}

		displayDetailNet=new StoreDisplayDetailNet(mContext);
		displayDetailNet.setData(FlowId);
		
		initView();

	}

	/**
	 * 接受EventBus发送消息
	 * @param msg 陈列详情消息实体类
	 */
	public void onEventMainThread(ResultStoreDisplayDetailBean msg) {
		if(msg.IsSuccess==true){
			displayDetailBeens.add(msg.TModel);
			tvDisplayDetailContent.setText(msg.TModel.Content);
			setData();

		}else{
			ToastFactory.getLongToast(mContext,msg.Message);
		}
	}

	/**
	 * 接受EventBus发送消息
	 * @param msg 陈列审批结果消息实体类
	 */
	public void onEventMainThread(ResultStoreDisplayApprovaBean msg) {
		if (msg.IsSuccess==true){
			Skip.mNext(mActivity,StoreDisplayActivity.class);
		}else{
			ToastFactory.getLongToast(mContext,"审批失败！"+msg.Message);
		}
	}

	/**
	 * 接受EventBus发送消息
	 * @param msg 陈列审批不通过结果消息实体类
	 */
	public void onEventMainThread(ResultStoreDisplayApprovaPassBean msg) {
		if (msg.IsSuccess==true){
			Skip.mNext(mActivity,StoreDisplayActivity.class);
		}else{
			ToastFactory.getLongToast(mContext,"审批失败！"+msg.Message);
		}
	}


	/**
	 * 接受EventBus发送消息
	 * @param msg 提交陈列任务备注消息实体类
	 */
	public void onEventMainThread(DisplayRemarkBean msg) {
		displayRemark=msg.getDisplayRemark();
	}
	
	private void setData() {
		tvBaseTitle.setText("陈列详情");

		//加载参考陈列图片数据
		referenceIcon=new ArrayList<Shows>();
		for (int i = 0; i < displayDetailBeens.size(); i++) {
			for (int j=0; j<displayDetailBeens.get(i).Shows.size();j++){
				Shows show=new Shows();
				show.setUrl(displayDetailBeens.get(i).Shows.get(j));
				referenceIcon.add(show);
			}
		}
		GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvReferenceDispaly.setLayoutManager(layoutManager);
        rvReferenceDispaly.setAdapter(new ReferenceDisplayAdapter(this,referenceIcon));
        rvReferenceDispaly.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {

            }
        });

		//加载完成陈列图片数据
		completeIcon=new ArrayList<CompleteImages>();
		for (int i = 0; i < displayDetailBeens.size(); i++) {
			for (int j=0; j<displayDetailBeens.get(i).Images.size();j++){
				CompleteImages images=new CompleteImages();
				images.setUrl(displayDetailBeens.get(i).Images.get(j).getUrl());
				images.setId(displayDetailBeens.get(i).Images.get(j).getId());
				images.setDescribe(displayDetailBeens.get(i).Images.get(j).getDescribe());
				completeIcon.add(images);
				DisplayApprovaImages.put(images.getAapprovalDisplayImagesArray());
			}
		}
		GridLayoutManager layoutManager1 = new GridLayoutManager(this,1);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		rvCompleteDispaly.setLayoutManager(layoutManager1);
		rvCompleteDispaly.setAdapter(new CompleteDisplayAdapter(mContext,mActivity,completeIcon));
		rvCompleteDispaly.setRecyclerListener(new RecyclerView.RecyclerListener() {
			@Override
			public void onViewRecycled(RecyclerView.ViewHolder holder) {

			}
		});
	}

	/**
	 * 初始化视图控件
	 */
	private void initView() {
		rvReferenceDispaly=(RecyclerView) findViewById(R.id.rv_reference_dispaly);
		rvCompleteDispaly=(RecyclerView) findViewById(R.id.rv_complete_dispaly);
		llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
		tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
		tvApprovalPass= (TextView) findViewById(R.id.tv_approval_through);
		tvApprovalThrough= (TextView) findViewById(R.id.tv_approval_pass);
		tvDisplayDetailContent= (TextView) findViewById(R.id.tv_display_detail_content);
		
		//设置视图控件监听
		llBack.setOnClickListener(this);
		tvApprovalPass.setOnClickListener(this);
		tvApprovalThrough.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_base_title_back://返回
			Skip.mBack(mActivity);
			break;
			case R.id.tv_approval_through://审批通过
				approvalType=0;
				showDialog();
				break;
			case R.id.tv_approval_pass://审批不通过
				approvalType=1;
				showDialog();
				break;
		}
	}

	public void showDialog(){
		new CustomTextDialog(mContext).mInitShow("审批结果","提交", "返回", new CustomTextDialog.ConfirmListener() {
			@Override
			public void onClick(Dialog dialog) {
				if(approvalType==0){
					//通过
					displayApprovalNet=new StoreDisplayApprovalNet(mContext);
					displayApprovalNet.setData(SheetId,displayRemark,FlowId,TaskId,DisplayApprovaImages);
				}else{
					//不通过
					displayApprovalPassNet=new StoreDisplayApprovalPassNet(mContext);
					displayApprovalPassNet.setData(SheetId,displayRemark,FlowId,TaskId,DisplayApprovaImages);
				}
			}
		},null);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_SELECT_IMAGE) {
			if (data == null) {
				return;
			}

		} else if (requestCode == REQ_CODE_GRAFFITI) {
			if (data == null) {
				return;
			}
			if (resultCode == GraffitiActivity.RESULT_OK) {
				String path = data.getStringExtra(GraffitiActivity.KEY_IMAGE_PATH);
				if (TextUtils.isEmpty(path)) {
					return;
				}
				//显示修改保存后的图片
//				ImageLoader.getInstance(this).display(findViewById(R.id.img), path);
				Log.i(Constant.TAG,"获取保存图片："+path);
			} else if (resultCode == GraffitiActivity.RESULT_ERROR) {
				Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
