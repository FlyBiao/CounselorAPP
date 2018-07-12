package com.cesaas.android.counselor.order.store;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.alioss.OSSKey;
import com.cesaas.android.counselor.order.dialog.CustomTextDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.imagepicker.imageloader.GlideImageLoader;
import com.cesaas.android.counselor.order.imagepicker.imagepickeradapter.ImagePickerAdapter;
import com.cesaas.android.counselor.order.store.adapter.CompleteDisplayAdapter;
import com.cesaas.android.counselor.order.store.adapter.ReferenceDisplayAdapter;
import com.cesaas.android.counselor.order.store.bean.CompleteImages;
import com.cesaas.android.counselor.order.store.bean.DisplayImagesBean;
import com.cesaas.android.counselor.order.store.bean.DisplayRemarkBean;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayDetailBean;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplaySubmitBean;
import com.cesaas.android.counselor.order.store.bean.Shows;
import com.cesaas.android.counselor.order.store.net.StoreDisplayDetailNet;
import com.cesaas.android.counselor.order.store.net.StoreDisplaySubmitNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.json.JSONArray;

/**
 * 店铺陈列详情页面
 * @author FGB
 *
 */
public class StoreDisplayDetailActivity extends BasesActivity implements OnClickListener, ImagePickerAdapter.OnRecyclerViewItemClickListener{

	private RecyclerView rvReferenceDispaly;//参考陈列
	private RecyclerView rvCompleteDispaly;//完成陈列
	private RecyclerView recyclerView;
	private LinearLayout llBack,ll_submit_display,ll_reform_display,ll_audit_results;
	private TextView tvBaseTitle,tv_audit_results;
	private TextView tvDisplayDetailContent;

	private List<Shows> referenceIcon;//参考陈列Icon
	private List<CompleteImages> completeIcon;//完成陈列Icon
	private String displayRemark;//陈列备注

	public static final int IMAGE_ITEM_ADD = -1;
	public static final int REQUEST_CODE_SELECT = 100;
	public static final int REQUEST_CODE_PREVIEW = 101;

	private ImagePickerAdapter adapter;
	private ArrayList<ImageItem> selImageList; //当前选择的所有图片
	private int maxImgCount = 8;//允许选择图片最大数

	private String ossImageUrl;//
	private OSS oss;
	private JSONArray displayImages;//陈列图片数据
	private boolean isUploadSuccess=false;//表示图片是否上传成功

	private StoreDisplayDetailNet displayDetailNet;
	private ArrayList<ResultStoreDisplayDetailBean.StoreDisplayDetailBean> displayDetailBeens;
	private String SheetId;//陈列ID
	private String FlowId;
	private String TaskId;

	private StoreDisplaySubmitNet displaySubmitNet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_display_detail_activity);

		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			SheetId=bundle.getString("SheetId");
			FlowId=bundle.getString("FlowId");
			TaskId=bundle.getString("TaskId");
		}

		initView();
		displayDetailNet=new StoreDisplayDetailNet(mContext);
		displayDetailNet.setData(FlowId);

		//最好放到 Application oncreate执行
		initImagePicker();
		initWidget();
	}

	/**
	 * 接受EventBus发送消息
	 * @param msg 陈列详情消息实体类
	 */
	public void onEventMainThread(ResultStoreDisplayDetailBean msg) {
		displayDetailBeens= new ArrayList<ResultStoreDisplayDetailBean.StoreDisplayDetailBean>();
		if(msg.IsSuccess==true){
			displayDetailBeens.add(msg.TModel);
			tvDisplayDetailContent.setText(msg.TModel.Content);

			setData();

			if (msg.TModel.Images.size()!=0){
				rvCompleteDispaly.setVisibility(View.VISIBLE);
				recyclerView.setVisibility(View.GONE);
				ll_reform_display.setVisibility(View.VISIBLE);
				ll_submit_display.setVisibility(View.GONE);
				ll_audit_results.setVisibility(View.VISIBLE);
				if(msg.TModel.Comment!=null){
					tv_audit_results.setText(msg.TModel.Comment);
				}

			}else if(msg.TModel.Images.size()==0){
				recyclerView.setVisibility(View.VISIBLE);
				rvCompleteDispaly.setVisibility(View.GONE);
				ll_submit_display.setVisibility(View.VISIBLE);
				ll_reform_display.setVisibility(View.GONE);
				ll_audit_results.setVisibility(View.GONE);
			}
		}else{
			ToastFactory.getLongToast(mContext,"获取数据失败！"+msg.Message);
		}
	}

	/**
	 * 接受EventBus发送消息
	 * @param msg 提交陈列任务消息实体类
	 */
	public void onEventMainThread(ResultStoreDisplaySubmitBean msg) {
		if(msg.IsSuccess==true){//提交成功，跳转到陈列审批列表页面
			Skip.mNext(mActivity,StoreDisplayActivity.class);
		}else{
			ToastFactory.getLongToast(mContext,"提交陈列任务失败！"+msg.Message);
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
//				DisplayApprovaImages.put(images.getAapprovalDisplayImagesArray());
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
		ll_reform_display= (LinearLayout) findViewById(R.id.ll_reform_display);
		ll_submit_display= (LinearLayout) findViewById(R.id.ll_submit_display);
		ll_audit_results= (LinearLayout) findViewById(R.id.ll_audit_results);
		rvReferenceDispaly=(RecyclerView) findViewById(R.id.rv_reference_dispaly);
		rvCompleteDispaly=(RecyclerView) findViewById(R.id.rv_complete_dispaly);
		recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
		llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
		tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
		tv_audit_results= (TextView) findViewById(R.id.tv_audit_results);
		tvDisplayDetailContent= (TextView) findViewById(R.id.tv_display_detail_content);

		//设置视图控件监听
		llBack.setOnClickListener(this);
		ll_submit_display.setOnClickListener(this);
		ll_reform_display.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_base_title_back://返回
			Skip.mBack(mActivity);
			break;
			case R.id.ll_submit_display://提交陈列任务
				showDialog();
				break;
			case R.id.ll_reform_display://重做
				ll_submit_display.setVisibility(View.VISIBLE);
				ll_reform_display.setVisibility(View.GONE);
				recyclerView.setVisibility(View.VISIBLE);
				rvCompleteDispaly.setVisibility(View.GONE);
				break;
		}
	}

	public void showDialog(){
		new CustomTextDialog(mContext).mInitShow("备注","确认提交", "返回", new CustomTextDialog.ConfirmListener() {
			@Override
			public void onClick(Dialog dialog) {
				displaySubmitNet=new StoreDisplaySubmitNet(mContext);
				displaySubmitNet.setData(SheetId,FlowId,TaskId,displayRemark,displayImages);
			}
		},null);
	}

	/**
	 * 初始化图片选项
	 */
	private void initImagePicker() {
		ImagePicker imagePicker = ImagePicker.getInstance();
		imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
		imagePicker.setShowCamera(true);                      //显示拍照按钮
		imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
		imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
		imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
		imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
		imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
		imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
		imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
		imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
	}

	/**
	 * 初始化图片加载挂件
	 */
	private void initWidget() {
		selImageList = new ArrayList<>();
		adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
		adapter.setOnItemClickListener(this);

		recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(adapter);
	}

	/**
	 * 图片点击预览
	 * @param view
	 * @param position
     */
	@Override
	public void onItemClick(View view, int position) {
		switch (position) {
			case IMAGE_ITEM_ADD:
				//打开选择,本次允许选择的数量
				ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
				Intent intent = new Intent(this, ImageGridActivity.class);
				startActivityForResult(intent, REQUEST_CODE_SELECT);
				break;
			default:
				//打开预览
				Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
				intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
				intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
				startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
				break;
		}
	}

	/**
	 * 获取添加图片返回结果
	 * @param requestCode
	 * @param resultCode
	 * @param data
     */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
		OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSSKey.ACCESS_ID, OSSKey.ACCESS_KEY);
		oss = new OSSClient(mContext, OSSKey.OSS_ENDPOINT, credentialProvider);

		if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
			//添加图片返回
			if (data != null && requestCode == REQUEST_CODE_SELECT) {
				ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
				selImageList.addAll(images);
				adapter.setImages(selImageList);
				displayImages=new JSONArray();
				for (int i=0;i<selImageList.size();i++){
					//调用AliOss上传图片
					setUploadAliOss(selImageList.get(i).name,selImageList.get(i).path);
					Log.i(Constant.TAG,"本地图片："+selImageList.get(i).path);
				}
			}
		} else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
			//预览图片返回
			if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
				ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
				selImageList.clear();
				selImageList.addAll(images);
				adapter.setImages(selImageList);
				displayImages=new JSONArray();
				for (int i=0;i<selImageList.size();i++){
					//调用AliOss上传图片
					setUploadAliOss(selImageList.get(i).name,selImageList.get(i).path);
				}
			}
		}
	}

	public void setUploadAliOss(String imageName,String imageUrl){
		/**
		 * 构造上传请求
		 * bucketName - 上传到Bucket的名字
		 * objectKey - 上传到OSS后的ObjectKey
		 * uploadFilePath - 上传文件的本地路径
		 */
		PutObjectRequest put = new PutObjectRequest(OSSKey.BUCKET_NAME, imageName, imageUrl);
		// 异步上传时可以设置进度回调
		put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
			@Override
			public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//				Log.i("PutObject", "Uploading..."+"=getUploadFilePath=="+request.getUploadFilePath());
			}
		});
		oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
			/**
			 * 上传失败
			 * @param arg0
			 * @param arg0
			 *
			 */
			@Override
			public void onFailure(PutObjectRequest arg0,
								  com.alibaba.sdk.android.oss.ClientException clientExcepion,
								  com.alibaba.sdk.android.oss.ServiceException serviceException) {
//				Log.i("PutObject", "onFailure==");
				// 请求异常
				if (clientExcepion != null) {
					// 本地异常如网络异常等
					clientExcepion.printStackTrace();
				}
				if (serviceException != null) {
					// 服务异常
//					Log.e("ErrorCode", serviceException.getErrorCode());
//					Log.e("RequestId", serviceException.getRequestId());
//					Log.e("HostId", serviceException.getHostId());
//					Log.e("RawMessage", serviceException.getRawMessage());
				}
			}

			/**
			 * 上传成功
			 * @param request
			 * @param result
			 */
			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
				//获取上传阿里云Image
				ossImageUrl=OSSKey.IMAGE_URL+request.getObjectKey();
				DisplayImagesBean imagesBean=new DisplayImagesBean();
				imagesBean.setDescribe("陈列图片");
				imagesBean.setImageUrl(ossImageUrl);
				displayImages.put(imagesBean.getDisplayImageArray());
				Log.i(Constant.TAG,"阿里OSS图片路劲："+displayImages+"=="+ossImageUrl);
			}
		});
		//Log.i("RequestId", "==="+strJson);
		// task.cancel(); // 可以取消任务
		//task.waitUntilFinished(); // 可以等待直到任务完成
	}
}
