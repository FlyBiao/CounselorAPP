package com.cesaas.android.counselor.order.group.fragment;

import io.rong.eventbus.EventBus;

import org.json.JSONArray;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.alioss.OSSKey;
import com.cesaas.android.counselor.order.bean.ResultSendImageWxMsBean;
import com.cesaas.android.counselor.order.dialog.CameraDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.CameraDialog.CameraInterface;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.fans.activity.GroupSendAactivity;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.SendImageWxMsgNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.AbPhotoUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 群发消息-图片
 * @author FGB
 *
 */
public class GroupImageFragment extends BaseFragment implements OnClickListener{
	
	private View view;
	private RelativeLayout rl_group_image_message;
	private TextView tv_add_group_image;
	private ImageView iv_group_image;

	private AbPhotoUtils photoUtil;
	public JSONArray fansArray;
	private String thePath ;
	
	private SendImageWxMsgNet imageWxMsgNet;
	
	private OSS oss;
	private String imageUrl;
	
	/**
	 * 单例
	 */
	public static GroupImageFragment instance=null;
	public static GroupImageFragment getInstance(){
		if(instance==null){
			instance=new GroupImageFragment();
		}
		return instance;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		photoUtil = AbPhotoUtils.getInstance();
		fansArray=GroupSendAactivity.fansArray;
		
	}
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        view=inflater.inflate(R.layout.fragment_group_shop, container, false);
	        initView();
	        return view;
	    }
	
	private void initView() {
		tv_add_group_image=(TextView) view.findViewById(R.id.tv_add_group_image);
		iv_group_image=(ImageView) view.findViewById(R.id.iv_group_image);
		rl_group_image_message=(RelativeLayout) view.findViewById(R.id.rl_group_image_message);
		rl_group_image_message.setOnClickListener(this);
		tv_add_group_image.setOnClickListener(this);
	}

	@Override
	protected void lazyLoad() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.tv_add_group_image://添加图片
			setAddImage();
			break;
		
		case R.id.rl_group_image_message://发送图片
			if(imageUrl!=null && imageUrl!=""){
				imageWxMsgNet=new SendImageWxMsgNet(getContext());
				imageWxMsgNet.setData(imageUrl,fansArray);
				
			}else{
				ToastFactory.getLongToast(getContext(), "请先添加图片！");
			}
			
			break;
		}
	}
	
	/**
	 * 接收群发图片消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(GroupImageMsgBean msg) {
		if(msg.isSuccess()==true){
			
			// 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
			OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSSKey.ACCESS_ID, OSSKey.ACCESS_KEY);
			oss = new OSSClient(getContext(), OSSKey.OSS_ENDPOINT, credentialProvider);
			
			/**
			 * 构造上传请求
			 * bucketName - 上传到Bucket的名字
			 * objectKey - 上传到OSS后的ObjectKey
			 * uploadFilePath - 上传文件的本地路径
			 */
			PutObjectRequest put = new PutObjectRequest(OSSKey.BUCKET_NAME, "image"+AbDateUtil.getCurrentDate("yyyyMMddHHmmss"), thePath);

			// 异步上传时可以设置进度回调
			put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
			    @Override
			    public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
			    	Log.i("PutObject", "Uploading..."+"=getUploadFilePath=="+request.getUploadFilePath());
			    
			    }
			});
			
			oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {

				/**
				 * 上传失败
				 * @param arg0
				 * @param arg1
				 * @param arg2
				 */
				@Override
				public void onFailure(PutObjectRequest arg0,
						com.alibaba.sdk.android.oss.ClientException clientExcepion,
						com.alibaba.sdk.android.oss.ServiceException serviceException) {
					Log.i("PutObject", "onFailure==");
					// 请求异常
			        if (clientExcepion != null) {
			            // 本地异常如网络异常等
			            clientExcepion.printStackTrace();
			        }
			        if (serviceException != null) {
			            // 服务异常
			            Log.e("ErrorCode", serviceException.getErrorCode());
			            Log.e("RequestId", serviceException.getRequestId());
			            Log.e("HostId", serviceException.getHostId());
			            Log.e("RawMessage", serviceException.getRawMessage());
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
			        imageUrl=OSSKey.IMAGE_URL+request.getObjectKey();
			        Log.i("getObject", "UploadSuccess_imageUrl=="+imageUrl);
				}
			});
			
		}
	}
	
	/**
	 * 接受发送图片消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultSendImageWxMsBean msg) {
		if (msg.IsSuccess==true) {//成功
			ToastFactory.getLongToast(getContext(), "图片消息发送成功!");
			sendMsgSuccess(msg.TModel.Total,msg.TModel.SuccessCount,msg.TModel.ErrorCount);
			
		}else{//失败
			ToastFactory.getLongToast(getContext(), "图片消息发送失败!"+msg.Message);
		}
	}
	
	/**
	 * 发送消息成功
	 * @param Total 消息总数
	 * @param SuccessCount 成功数
	 * @param ErrorCount 失败数
	 */
	public void sendMsgSuccess(int Total,int SuccessCount,int ErrorCount) {
		new MyAlertDialog(getContext()).mInitShow("发送成功", "总共"+Total+"条 \n"+"成功："+SuccessCount+"条，失败："+ErrorCount+"条",
			"返回上一页", "再发一次", new ConfirmListener() {
				@Override
				public void onClick(Dialog dialog) {
					Skip.mBack(getActivity());
				}
			}, null);
	}
	
	/**
	 * 设置添加图片
	 */
	public void setAddImage(){
		new CameraDialog(getActivity(), true, new CameraInterface() {

			@Override
			public void onCamera() {//相机
				photoUtil.clearCachedCropFile();
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_PICK);
				intent.setType("image/*");
				if (Build.VERSION.SDK_INT < 19) {
					startActivityForResult(intent, AbPhotoUtils.KITKAT_LESS);
				} else {
					startActivityForResult(intent,AbPhotoUtils.KITKAT_ABOVE);
				}
			}

			@Override
			public void onPhoto() {//相册
				photoUtil.clearCachedCropFile();
				startActivityForResult(photoUtil.selectCamera(),AbPhotoUtils.PHOTO_CAMEAR);
			}

		}).mShow();
	}

	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// 获取头像
		if (resultCode == Activity.RESULT_OK) {
			Uri uri = null;
			if (requestCode == AbPhotoUtils.PHOTO_CAMEAR) {
				cropImageUri(photoUtil.mCameraUri(), 300,
						AbPhotoUtils.INTENT_CROP);
			}else if (requestCode == AbPhotoUtils.KITKAT_ABOVE) {
				if (data != null) {
					uri = data.getData();
					// 先将这个uri转换为path，然后再转换为uri
					thePath= photoUtil.getPath(getActivity(), uri);
					
					iv_group_image.setVisibility(View.VISIBLE);
					bitmapUtils.display(iv_group_image, thePath, App.mInstance().bitmapConfig);
					
					if(thePath!=null && thePath!=""){
						//发送上传消息
						GroupImageMsgBean msgBean=new GroupImageMsgBean();
						msgBean.setSuccess(true);
						EventBus.getDefault().post(msgBean);
					}
				}
			}
		}
		if (requestCode == AbPhotoUtils.INTENT_CROP) {
			iv_group_image.setVisibility(View.VISIBLE);
			bitmapUtils.display(iv_group_image, ""+photoUtil.buildUri(), App.mInstance().bitmapConfig);
			
		}
	}
	
	private void cropImageUri(Uri uri, int output, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");// 才能出剪辑的小方框，不然没有剪辑功能，只能选取图片
		intent.putExtra("aspectX", 1); // 放大缩小比例的X
		intent.putExtra("aspectY", 1);// 放大缩小比例的X 这里的比例为： 1:1
		intent.putExtra("outputX", output); // 这个是限制输出图片大小
		intent.putExtra("outputY", output);
		intent.putExtra("return-data", false);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true); // 去掉黑边
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUtil.buildUri());
		startActivityForResult(intent, requestCode);
	}
	
}
