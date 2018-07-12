package com.cesaas.android.counselor.order.store;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.CustomTextDialog;
import com.cesaas.android.counselor.order.dialog.CustomTextQueryDialog;
import com.cesaas.android.counselor.order.store.adapter.CompleteDisplayAdapter;
import com.cesaas.android.counselor.order.store.adapter.ReferenceDisplayAdapter;
import com.cesaas.android.counselor.order.store.bean.AapprovalDisplayImagesBean;
import com.cesaas.android.counselor.order.store.bean.CompleteImages;
import com.cesaas.android.counselor.order.store.bean.DisplayRemarkBean;
import com.cesaas.android.counselor.order.store.bean.Images;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayDetailBean;
import com.cesaas.android.counselor.order.store.bean.Shows;
import com.cesaas.android.counselor.order.store.bean.StoreDisplayBean;
import com.cesaas.android.counselor.order.store.net.StoreDisplayDetailNet;
import com.cesaas.android.counselor.order.store.net.StoreDisplayNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import org.json.JSONArray;

/**
 * 门店陈列审核未通过
 * @author FGB
 *
 */
public class DisplayApprovalPassActivity extends BasesActivity implements OnClickListener{

	private RecyclerView rvReferenceDispaly;//参考陈列
	private RecyclerView rvCompleteDispaly;//完成陈列
	private LinearLayout llBack;
	private TextView tvBaseTitle,tvReform,tvReformSubmit;
	private TextView tvDisplayDetailContent;

	private List<Shows> referenceIcon;//参考陈列Icon
	private List<CompleteImages> completeIcon;//完成陈列Icon
	private JSONArray DisplayApprovaImages;//陈列审批图片数据
	private String SheetId;
	private String displayRemark;//陈列备注

	private StoreDisplayDetailNet displayDetailNet;
	private ArrayList<ResultStoreDisplayDetailBean.StoreDisplayDetailBean> displayDetailBeens= new ArrayList<ResultStoreDisplayDetailBean.StoreDisplayDetailBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_approval_pass_activity);

		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			SheetId=bundle.getString("SheetId");
		}

		displayDetailNet=new StoreDisplayDetailNet(mContext);
		displayDetailNet.setData(SheetId);

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
			for (int j=0; j<displayDetailBeens.get(i).Shows.size();j++){
				CompleteImages images=new CompleteImages();
				images.setUrl(displayDetailBeens.get(i).Shows.get(j));
				completeIcon.add(images);
				AapprovalDisplayImagesBean imagesBean=new AapprovalDisplayImagesBean();
				imagesBean.setId("1");
				imagesBean.setComment("审核陈列图片");
				imagesBean.setUrl(displayDetailBeens.get(i).Shows.get(j));
				DisplayApprovaImages.put(imagesBean.getAapprovalDisplayImagesArray());
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
		tvReform= (TextView) findViewById(R.id.tv_back_reform);
		tvReformSubmit= (TextView) findViewById(R.id.tv_reform_submit);
		tvDisplayDetailContent= (TextView) findViewById(R.id.tv_display_detail_content);
		
		//设置视图控件监听
		llBack.setOnClickListener(this);
		tvReform.setOnClickListener(this);
		tvReformSubmit.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_base_title_back://返回
			Skip.mBack(mActivity);
				break;
			case R.id.tv_back_reform://返回重做
				showDialog("返回重做");
				break;
			case R.id.tv_reform_submit://重新提交
				showDialog("提交");
				break;
		}
	}

	public void showDialog(String title){
		new CustomTextDialog(mContext).mInitShow(title,"提交", "返回", new CustomTextDialog.ConfirmListener() {
			@Override
			public void onClick(Dialog dialog) {
				ToastFactory.getLongToast(mContext,"搜索成功！");
			}
		},null);
	}
}
