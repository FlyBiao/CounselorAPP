package com.cesaas.android.counselor.order.store;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.store.adapter.CompleteDisplayAdapter;
import com.cesaas.android.counselor.order.store.adapter.ReferenceDisplayAdapter;
import com.cesaas.android.counselor.order.store.bean.CompleteImages;
import com.cesaas.android.counselor.order.store.bean.Images;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayDetailBean;
import com.cesaas.android.counselor.order.store.bean.Shows;
import com.cesaas.android.counselor.order.store.net.StoreDisplayDetailNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 门店陈列审核通过
 * @author FGB
 *
 */
public class DisplayApprovalCompleteActivity extends BasesActivity implements OnClickListener{

	private RecyclerView rvReferenceDispaly;//参考陈列
	private RecyclerView rvCompleteDispaly;//完成陈列
	private LinearLayout llBack;
	private TextView tvBaseTitle,tvDisplayDetailContent;

	private List<Shows> referenceIcon;//参考陈列Icon
	private List<CompleteImages> completeIcon;//完成陈列Icon

	private String SheetId;//陈列ID
	private StoreDisplayDetailNet displayDetailNet;
	private ArrayList<ResultStoreDisplayDetailBean.StoreDisplayDetailBean> displayDetailBeens= new ArrayList<ResultStoreDisplayDetailBean.StoreDisplayDetailBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_approval_complete_activity);

		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			SheetId=bundle.getString("SheetId");
		}
		initView();
		displayDetailNet=new StoreDisplayDetailNet(mContext);
		displayDetailNet.setData(SheetId);
//		setData();
	}

	/**
	 * 接受EventBus发送消息
	 * @param msg 陈列详情消息实体类
	 */
	public void onEventMainThread(ResultStoreDisplayDetailBean msg) {
		if(msg.IsSuccess==true){
			displayDetailBeens.add(msg.TModel);
			tvDisplayDetailContent.setText(msg.TModel.Content);
			Log.i(Constant.TAG,"陈列详情消息实体类=="+msg.TModel.Content);
			setData();

		}else{
			ToastFactory.getLongToast(mContext,msg.Message);
		}
	}

	private void setData() {
		tvBaseTitle.setText("陈列详情");

		//加载参考陈列图片数据
		referenceIcon=new ArrayList<Shows>();
        for (int i = 0; i < 2; i++) {
			Shows show=new Shows();
			show.setUrl("http://shenzhentesting.oss-cn-shenzhen.aliyuncs.com/image1.jpg");
			referenceIcon.add(show);
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
       for (int i = 0; i < 2; i++) {
		   CompleteImages images=new CompleteImages();
		   images.setUrl("http://shenzhentesting.oss-cn-shenzhen.aliyuncs.com/image1.jpg");
		   completeIcon.add(images);
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
		tvDisplayDetailContent= (TextView) findViewById(R.id.tv_display_detail_content);
		
		//设置视图控件监听
		llBack.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_base_title_back://返回
			Skip.mBack(mActivity);
			break;
		}
		
	}
}
