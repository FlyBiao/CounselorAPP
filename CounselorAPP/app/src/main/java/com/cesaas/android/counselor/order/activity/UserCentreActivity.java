package com.cesaas.android.counselor.order.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.user.SetUserSignatureActivity;
import com.cesaas.android.counselor.order.alioss.OSSKey;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.ResultUserPicBean;
import com.cesaas.android.counselor.order.compressimage.CompressHelper;
import com.cesaas.android.counselor.order.dialog.CameraDialog;
import com.cesaas.android.counselor.order.dialog.CameraDialog.CameraInterface;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.net.UserModifyPicNet;
import com.cesaas.android.counselor.order.utils.AbImageUtil;
import com.cesaas.android.counselor.order.utils.AbPhotoUtils;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.GetFileNameUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 账号个人信息设置
 * 
 * @author FGB
 * 
 */
@ContentView(R.layout.activity_my_account_setting)
public class UserCentreActivity extends BasesActivity {

	@ViewInject(R.id.iv_user_head_icon)
	private MyImageViewWidget iv_user_head_icon;
	@ViewInject(R.id.tv_user_nick)
	private TextView tv_user_nick;
	@ViewInject(R.id.tv_user_account)
	private TextView tv_user_account;
	@ViewInject(R.id.tv_user_signature)
	private TextView tv_user_signature;
	@ViewInject(R.id.iv_userCenter_back)
	private LinearLayout iv_userCenter_back;
	@ViewInject(R.id.btn_exit_login)
	private Button btn_exit_login;

	@ViewInject(R.id.ll_head)
	private LinearLayout ll_head;
	@ViewInject(R.id.ll_set_nick)
	private LinearLayout ll_set_nick;
	@ViewInject(R.id.ll_set_pwd)
	private LinearLayout ll_set_pwd;
	@ViewInject(R.id.ll_set_account)
	private LinearLayout ll_set_account;
	@ViewInject(R.id.ll_set_signature)
	private LinearLayout ll_set_signature;
	@ViewInject(R.id.my_earnings)
	private LinearLayout my_earnings;

	//相册请求码
	private static final int ALBUM_REQUEST_CODE = 1;
	//相机请求码
	private static final int CAMERA_REQUEST_CODE = 2;
	//剪裁请求码
	private static final int CROP_REQUEST_CODE = 3;

	//调用照相机返回图片文件
	private File tempFile;

	private static String nickName = "";// 用户昵称
	private static String icon="";// 用户头像
	private String account;// 用户账号
	private String mobile;
	private String siganature;

	//用户信息
	private UserInfoNet userInfoNet;

	public static BitmapUtils bitmapUtils;
	private Bitmap bitmap;
	private UserModifyPicNet modifyPicNet;

	static boolean nickModify = false;//昵称是否修改了，默认false
	static boolean iconModify=false;//头像是否修改了，默认false

	private static String ossImageUrl;//
	private static OSS oss;
	private File oldFile;
	private File newFile;
	private String thePath;

	//接收Message发送的消息
	public static Handler handler = new Handler() {
		public void handleMessage(final android.os.Message msg) {
			switch (msg.what) {
			case 1:
				nickName = String.valueOf(msg.obj);
				break;
			case 2:
				icon=String.valueOf(msg.obj);
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bitmapUtils = BitmapHelp.getBitmapUtils(getApplicationContext()
				.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(
				getApplicationContext()).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);

		userInfoNet=new UserInfoNet(mContext);
		userInfoNet.setData();

		modifyPicNet = new UserModifyPicNet(mContext);
		initDate();
		initBack();
	}


	@Override
	protected void onResume() {
		super.onResume();
		tv_user_nick.setText(nickName);
		bitmapUtils.display(iv_user_head_icon, icon,App.mInstance().bitmapConfig);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		nickModify=false;
		iconModify=false;
	}

	/**
	 * 接收用户POST数据
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultUserPicBean msg) {
		if(msg.IsSuccess!=false){
			ToastFactory.getLongToast(mContext,"上次头像成功！");
			userInfoNet=new UserInfoNet(mContext);
			userInfoNet.setData();
		}else{
			ToastFactory.getLongToast(mContext,"上次头像失败！"+msg.Message);
		}
	}
	
	/**
	 * 接收用户POST数据
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultUserBean msg) {
    	if(msg.IsSuccess==true && msg.TModel!=null){
			if(msg.TModel.Siganature!=null){
				tv_user_signature.setText(msg.TModel.Siganature);
			}else{
				tv_user_signature.setText("暂无签名！");
			}
			siganature=msg.TModel.Siganature;
			mobile=msg.TModel.Mobile;
			icon=msg.TModel.Icon;
			nickName=msg.TModel.NickName;
    		tv_user_nick.setText(msg.TModel.NickName);
    		tv_user_account.setText(msg.TModel.Mobile);
			Glide.with(mContext).load(msg.TModel.Icon).into(iv_user_head_icon);
    		}else{
			ToastFactory.getLongToast(mContext,"获取用户信息失败！");
		}
    	}
	
	/**
	 * 初始化数据
	 */
	public void initDate() {
		tv_user_nick.setText(nickName);
		tv_user_account.setText(account);
		bitmapUtils.display(iv_user_head_icon, icon,
				App.mInstance().bitmapConfig);
	}

	@OnClick({ R.id.ll_set_account, R.id.ll_set_pwd,
			R.id.ll_set_nick, R.id.ll_head ,R.id.btn_exit_login,R.id.ll_set_signature,R.id.my_earnings})
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.my_earnings://我的收益
				Skip.mNext(mActivity,DepositActivity.class);
				break;
		case R.id.ll_set_account:
			ToastFactory.show(mContext, "不能修改账号喔！", ToastFactory.CENTER);
			break;
		case R.id.ll_set_pwd:// 修改密码
			Skip.mNext(mActivity, ModifyPwdActivity.class);
			break;
		case R.id.ll_set_nick:// 修改昵称
			Bundle bundle = new Bundle();
			bundle.putString("nickName", nickName);
			Skip.mNextFroData(mActivity, ModifyNickActivity.class, bundle);
			break;
			case R.id.ll_set_signature://个性签名
				Bundle bundles = new Bundle();
				bundles.putString("nickSignature", siganature);
				Skip.mNextFroData(mActivity, SetUserSignatureActivity.class, bundles);
				break;
		case R.id.ll_head:// 修改头像
			new CameraDialog(mActivity, true, new CameraInterface() {
				@Override
				public void onCamera() {//相册
					getPicFromAlbm();
				}

				@Override
				public void onPhoto() {//相机
					getPicFromCamera();
				}

			}).mShow();
			break;
			
		case R.id.btn_exit_login:
			exit();
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		switch (requestCode) {
			case CAMERA_REQUEST_CODE:   //调用相机后返回
				if (resultCode == RESULT_OK) {
					//用相机返回的照片去调用剪裁也需要对Uri进行处理
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
						Uri contentUri = FileProvider.getUriForFile(UserCentreActivity.this, "com.cesaas.android.counselor.order", tempFile);
						cropPhoto(contentUri);
					} else {
						cropPhoto(Uri.fromFile(tempFile));
					}
				}
				break;
			case ALBUM_REQUEST_CODE:    //调用相册后返回
				if (resultCode == RESULT_OK) {
					Uri uri = intent.getData();
					cropPhoto(uri);
				}
				break;
			case CROP_REQUEST_CODE://调用剪裁后返回
				Bundle bundle = intent.getExtras();
				if (bundle != null) {
					//在这里获得了剪裁后的Bitmap对象，可以用于上传
					Bitmap image = bundle.getParcelable("data");
					//也可以进行一些保存、压缩等操作后上传
					String path = saveImage(System.currentTimeMillis()+"", image);
					if (path != null) {
						OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSSKey.ACCESS_ID, OSSKey.ACCESS_KEY);
						oss = new OSSClient(mContext, OSSKey.OSS_ENDPOINT, credentialProvider);
						//压缩图片
						oldFile=new File(path);
						newFile = CompressHelper.getDefault(mContext).compressToFile(oldFile);
						setUploadAliOss(GetFileNameUtils.getFileName(newFile.getAbsolutePath(),prefs),newFile.getAbsolutePath());
					}
				}
				break;
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	/**
	 * 返回上一个页面
	 */
	private void initBack() {
		iv_userCenter_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
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
				mActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
					}
				});

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
				mActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
					}
				});
				// 请求异常
				if (clientExcepion != null) {
					// 本地异常如网络异常等
					clientExcepion.printStackTrace();
					Log.i(Constant.TAG,"onFailure：本地异常如网络异常等:"+clientExcepion);
				}
				if (serviceException != null) {
					// 服务异常
					Log.i(Constant.TAG,"ErrorCode:"+serviceException.getErrorCode());
					Log.i(Constant.TAG,"RequestId:"+serviceException.getRequestId());
					Log.i(Constant.TAG,"HostId:"+serviceException.getHostId());
					Log.i(Constant.TAG,"RawMessage:"+serviceException.getRawMessage());
				}
			}

			/**
			 * 上传成功
			 * @param request
			 * @param result
			 */
			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
				mActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
					}
				});
				//获取上传阿里云Image
				ossImageUrl=OSSKey.IMAGE_URL+request.getObjectKey();
				mActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//执行上传操作
						modifyPicNet.setData(ossImageUrl);
					}
				});
			}
		});
	}


	/**
	 * 退出当前用户
	 */
	public void exit() {
		new MyAlertDialog(mContext).mInitShow("退出登录", "是否退出登录，退出后将不能接收最新消息",
				"我知道", "点错了", new ConfirmListener() {
					@Override
					public void onClick(Dialog dialog) {
						myapp.mExecutorService.execute(new Runnable() {

							@Override
							public void run() {
								prefs.cleanAll();
								prefs.removeKey(Constant.SPF_TOKEN);
								bundle.putString("Mobile",mobile);
								bundle.putString("userIcon",icon);
								bundle.putString("nickName",nickName);
								mCache.clear();
                                UserCentreActivity.this.finish();
                                setResult(0xe); // 用于退出时关闭本页
                                onExit();
								Skip.mNextFroData(mActivity, LoginActivity.class, bundle,true);
							}
						});
					}
				}, null);
	}


	/**
	 * 从相机获取图片
	 */
	private void getPicFromCamera() {
		//用于保存调用相机拍照后所生成的文件
		tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
		//跳转到调用系统相机
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//判断版本
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果在Android7.0以上,使用FileProvider获取Uri
			intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(UserCentreActivity.this, "com.cesaas.android.counselor.order", tempFile);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
		} else {//否则使用Uri.fromFile(file)方法获取Uri
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		}
		startActivityForResult(intent, CAMERA_REQUEST_CODE);
	}

	/**
	 * 从相册获取图片
	 */
	private void getPicFromAlbm() {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
	}


	/**
	 * 裁剪图片
	 */
	private void cropPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
		intent.setDataAndType(uri, "image/*");
		intent.putExtra(System.currentTimeMillis()+"", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, CROP_REQUEST_CODE);
	}


	public String saveImage(String name, Bitmap bmp) {
		File appDir = new File(Environment.getExternalStorageDirectory().getPath());
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = name + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
			return file.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
