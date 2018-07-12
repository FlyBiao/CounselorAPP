package com.cesaas.android.counselor.order.stafftest;

import io.rong.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserInfo;
import com.cesaas.android.counselor.order.dialog.CameraDialog;
import com.cesaas.android.counselor.order.dialog.CameraDialog.CameraInterface;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.group.fragment.GroupImageMsgBean;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.AbPhotoUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;

/**
 * 店员考试页面
 * @author fgb
 *
 */
public class StaffTestActivity extends BasesActivity implements OnClickListener{
	
	private LinearLayout ll_test_back;
	private TextView tv_exit_test;
	private WebView wv_test_webview;
	
	public String mobile;//手机号
    public String icon;//用户头像
    public String name;//用户名称
    public String nickName;//用户昵称
    public String sex;//性别
    public String shopId;//店铺ID
    public String shopName;//店铺名称
    public String shopMobile;//店铺电话
    public String typeName;//用户身份：店员，店长
    public String typeId;//1：店长，2：店员
    public String vipId;
    public String ticket;//生成拉粉二维码用票
    public String imToken;
    public String counselorId;//顾问ID
    public String gzNo;//公众号GzNo
    public int tId;
    
    public String shopPostCode;//商品提交码
    public String shopProvince;//商品所在省
    public String shopAddress;//商品所在地址
    public String shopArea;//商品所在区域
    public String shopCity;//商品所在城市
    
    private List<String> listBean=new ArrayList<String>();
    private WebViewCallbackBean callbackBean;
    private String strJson;
    
  //试卷ID
  	private int paperId;
	
	private UserInfoNet userInfoNet;
	private UserInfo userInfo;
	
	private WaitDialog dialog;
	private JavascriptInterface javascriptInterface;
	private SwipeRefreshLayout swipeRefreshLayout;
	
	private AbPhotoUtils photoUtil;
	private String thePath ;
	private String type ;
	
	private OSS oss;
	private String imageUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_staff_test);
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			paperId=bundle.getInt("PaperId");
		}
		
		photoUtil = AbPhotoUtils.getInstance();
		dialog = new WaitDialog(mContext);
		
		userInfoNet=new UserInfoNet(mContext);
		userInfoNet.setData();
		
		initView();
		loadWebData();
		initSwipeRefreshLayout();
	}

	/**
	 * 加载Web页面数据
	 */
	private void loadWebData() {
		WebSettings settings = wv_test_webview.getSettings();
		settings.setJavaScriptEnabled(true);// 代表设置支持JS
		settings.setBuiltInZoomControls(true);// 在网页中显示放大缩小按钮
		settings.setUseWideViewPort(true);// 开启支持双击网页进行缩放
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		settings.setAllowFileAccess(true);// 设置允许访问文件数据
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		
		wv_test_webview.setWebViewClient(new WebViewClient() {

			/**
			 * 所有跳转的链接都会在此方法中调用
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);

				return true;
			}
			
			/**
			 * 网页开始加载
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				dialog.mStart();
			}
			
			/**
			 * 网页加载结束
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				dialog.mStop();
			}
		});
		
		wv_test_webview.loadUrl(Urls.STAFF_TEST+paperId);// 加载网页
		wv_test_webview.setWebChromeClient(new WebChromeClient());
		javascriptInterface = new JavascriptInterface(mContext);
		wv_test_webview.addJavascriptInterface(javascriptInterface, "appHome");		
	}
	
	public void onEventMainThread(ResultUserBean msg) {

		if (msg != null) {
			mobile=msg.TModel.Mobile;
		    icon=msg.TModel.Icon;//用户头像
		    name=msg.TModel.Name;//用户名称
		    nickName=msg.TModel.NickName;//用户昵称
		    sex=msg.TModel.Sex;//性别
		    shopId=msg.TModel.ShopId;//店铺ID
		    shopName=msg.TModel.ShopName;//店铺名称
		    shopMobile=msg.TModel.ShopMobile;//店铺电话
		    typeName=msg.TModel.TypeName;//用户身份：店员，店长
		    typeId=msg.TModel.TypeId;//1：店长，2：店员
		    vipId=msg.TModel.VipId+"";
		    ticket=msg.TModel.Ticket;//生成拉粉二维码用票
		    imToken=msg.TModel.ImToken;
		    counselorId=msg.TModel.CounselorId;//顾问ID
		    gzNo=msg.TModel.GzNo;//公众号GzNo
		    tId=msg.TModel.tId;
		    
		    shopPostCode=msg.TModel.ShopPostCode;//商品提交码
		    shopProvince=msg.TModel.ShopProvince;//商品所在省
		    shopAddress=msg.TModel.ShopAddress;//商品所在地址
		    shopArea=msg.TModel.ShopArea;//商品所在区域
		    shopCity=msg.TModel.ShopCity;//商品所在城市
		    
		}
	}
	
	/**
	 * 下拉刷新
	 */
	public void initSwipeRefreshLayout(){
		 //设置刷新时动画的颜色，可以设置4个
      swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, 
      		android.R.color.holo_red_light, 
      		android.R.color.holo_orange_light, 
      		android.R.color.holo_green_light);
      
      //设置下拉刷新
      swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
      	             
            @Override
            public void onRefresh() {
//          	  dialog.mStop();
          	//下拉重新加载
            	wv_test_webview.reload();
	            	
               new Handler().postDelayed(new Runnable() {
              	 
                   @Override
                   public void run() {
                       swipeRefreshLayout.setRefreshing(false);
                   }
               }, 3000);
           }
       });
	}

	private void initView() {
		tv_exit_test=(TextView) findViewById(R.id.tv_exit_test);
		wv_test_webview=(WebView) findViewById(R.id.wv_test_webview);
		swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshShopLayout);
		//这一句是设置js 对话框
		wv_test_webview.setWebChromeClient(new WebChromeClient());
		//不加上，会显示白边  
		wv_test_webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		ll_test_back=(LinearLayout) findViewById(R.id.ll_test_back);
		ll_test_back.setOnClickListener(this);
		tv_exit_test.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_test_back://返回
			Skip.mBack(mActivity);
			break;
			
		case R.id.tv_exit_test://推出考试
			Skip.mBack(mActivity);
			break;
		}
	}
	
	/** 
	 * 定义JS交互接口
	 * @author fgb
	 * 
	 */
	public class JavascriptInterface {
		Context mContext;

		JavascriptInterface(Context c) {
			mContext = c;
		}
		
		/**
		 * 返回UserToken
		 * @return UserToken
		 */
		@android.webkit.JavascriptInterface
		public String getUserInfo() {

			return prefs.getString("token");
		}
		
		/**
		 * WebView posMessag
		 * @param postData
		 */
		@android.webkit.JavascriptInterface
		public void postMessage(String postData) {
			try {
				JSONObject mJson = new JSONObject(postData);
				type= mJson.optString("type");
				
				if (type != null) {
					 if ("29".equals(type)) {//上传文件
						 setAddImage();
						
					}else{
						ToastFactory.getLongToast(mContext, "没有该类型！");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * WebView回调
		 */
		@android.webkit.JavascriptInterface
		public void appCallback(String callBackData){
			
			ToastFactory.getLongToast(mContext, "WebView回调"+callBackData);
		}
		
		/**
		 * 返回用户信息
		 * @return
		 */
		@android.webkit.JavascriptInterface
		public String appUserInfo(){
			
			userInfo=new UserInfo();
			userInfo.setCounselorId(counselorId);
			userInfo.setGzNo(gzNo);
			userInfo.setIcon(icon);
			userInfo.setImToken(imToken);
			userInfo.setName(name);
			userInfo.setMobile(mobile);
			userInfo.setNickName(nickName);
			userInfo.setSex(sex);
			userInfo.setShopAddress(shopAddress);
			userInfo.setShopArea(shopArea);
			userInfo.setShopCity(shopCity);
			userInfo.setShopId(shopId);
			userInfo.setShopMobile(shopMobile);
			userInfo.setShopName(shopName);
			userInfo.setShopPostCode(shopPostCode);
			userInfo.setShopProvince(shopProvince);
			userInfo.setTicket(ticket);
			userInfo.setTypeId(typeId);
			userInfo.setTypeName(typeName);
			userInfo.setVipId(vipId);
			userInfo.settId(tId);
			
			Gson gson=new Gson();
			String obj=gson.toJson(userInfo);
			
			return obj;
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
			oss = new OSSClient(mContext, OSSKey.OSS_ENDPOINT, credentialProvider);
			
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
					Log.i("PutObject", "UploadSuccess=="+request.getObjectKey());
			        Log.i("ETag", result.getETag());
			        Log.i("RequestId", result.getRequestId());
			        
			        //获取上传阿里云Image
			        imageUrl=OSSKey.IMAGE_URL+request.getObjectKey();
			        
			        callbackBean=new WebViewCallbackBean();
					listBean.add(imageUrl);
					
					//添加参数
					callbackBean.setType(Integer.parseInt(type));
					callbackBean.setParam(listBean);
					
					//转换json字符串
					strJson=gson.toJson(callbackBean);
					Log.i("RequestId", "==="+strJson);
					javascriptInterface.appCallback(strJson);
				}
			});
			
//			Log.i("RequestId", "==="+strJson);
			// task.cancel(); // 可以取消任务
//			 task.waitUntilFinished(); // 可以等待直到任务完成
		}
	}
	
	/**
	 * 设置添加图片
	 */
	public void setAddImage(){
		new CameraDialog(mActivity, true, new CameraInterface() {

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

	/**
	 * 接收相册返回数据结果
	 */
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
					thePath= photoUtil.getPath(mActivity, uri);
//					iv_group_image.setVisibility(View.VISIBLE);
//					bitmapUtils.display(iv_group_image, thePath, App.mInstance().bitmapConfig);
					
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
//			iv_group_image.setVisibility(View.VISIBLE);
//			bitmapUtils.display(iv_group_image, ""+photoUtil.buildUri(), App.mInstance().bitmapConfig);
			
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
