package com.cesaas.android.counselor.order.boss.ui.fragment.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.TaskNewActivity;
import com.cesaas.android.counselor.order.alioss.OSSKey;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.boss.ui.activity.QueryShopActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.SelectShopActivity;
import com.cesaas.android.counselor.order.compressimage.CompressHelper;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.member.adapter.MemberServiceAdapter;
import com.cesaas.android.counselor.order.member.bean.service.ResultProcessedServiceSumBean;
import com.cesaas.android.counselor.order.member.net.service.ServiceSumDataNet;
import com.cesaas.android.counselor.order.member.service.MemberReturnServiceInfoActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceBirthInfoActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceCustomInfoActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceDormancyInfoActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceFestInfoActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceInfoActivity;
import com.cesaas.android.counselor.order.shopmange.AddInvitationOrderActivity;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.GetFileNameUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.utils.WebViewUtils;
import com.cesaas.android.counselor.order.webview.base.BaseActivityResult;
import com.cesaas.android.counselor.order.webview.base.BaseInitJavascriptInterface;
import com.cesaas.android.counselor.order.webview.base.BaseJavascriptInterface;
import com.cesaas.android.counselor.order.webview.bean.shop.QueryShopEventBean;
import com.flybiao.materialdialog.MaterialDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/1/23 14:56
 * Version 1.0
 */

public class BossMemberFragment extends BaseFragment {

    private TextView tvTitle,tvLeftTitle,mTextViewRightTitle;
    private ImageView ivShare,ivSelect;
    private LinearLayout llBack;
    private WebView wvOnLineShop;
    private ScrollView sv_shop_img;

    protected WaitDialog mDialog;
    private MaterialDialog mMaterialDialog;

    private static String ossImageUrl;//
    private static OSS oss;
    private File oldFile;
    private File newFile;
    private WaitDialog dialog;

    private String leftTitle;

    /**
     * 单例
     */
    public static BossMemberFragment newInstance() {
        BossMemberFragment fragmentCommon = new BossMemberFragment();
        return fragmentCommon;
    }

    public void onEventMainThread(QueryShopEventBean msg) {
        if(msg.getQueryType()==3){
            Intent intentShop= new Intent(getContext(), QueryShopActivity.class);
            intentShop.putExtra("LeftTitle","会员");
            startActivityForResult(intentShop,201);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_boss_member;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        llBack=findView(R.id.ll_base_title_back);
        llBack.setVisibility(View.GONE);
        tvTitle=findView(R.id.tv_base_title);
        tvTitle.setText("会员");
        tvLeftTitle=findView(R.id.tv_base_title_left);
        tvLeftTitle.setText(leftTitle);
        ivShare=findView(R.id.iv_add_module);
        ivShare.setVisibility(View.VISIBLE);
        ivShare.setImageResource(R.mipmap.share);
        ivSelect=findView(R.id.iv_scan_s);
        ivSelect.setVisibility(View.GONE);
        ivSelect.setImageResource(R.mipmap.select);
        mTextViewRightTitle=findView(R.id.tv_base_title_right);
        wvOnLineShop=findView(R.id.wv_shop_sell_number);
        sv_shop_img=findView(R.id.sv_shop_img);
    }

    @Override
    public void initListener() {
        ivShare.setOnClickListener(this);
        mTextViewRightTitle.setOnClickListener(this);
        ivSelect.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mDialog = new WaitDialog(getContext());
        mMaterialDialog=new MaterialDialog(getContext());
        dialog=new WaitDialog(getContext());
        WebViewUtils.initWebSettings(wvOnLineShop,mDialog, Urls.RESULTS_VIP_TARGET);
        BaseInitJavascriptInterface.initJavascriptInterface(getContext(),getActivity(),mMaterialDialog,wvOnLineShop,bundle,prefs,tvTitle,mTextViewRightTitle,0,3,0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BaseActivityResult result=new BaseActivityResult(getContext(),getActivity(),wvOnLineShop);
        result.getOnActivityResult(requestCode,resultCode,data,prefs);
    }


    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.iv_add_module:
                getBitmapByView(sv_shop_img);
                break;
        }
    }

    @Override
    public void fetchData() {

    }

    private Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(
                    Color.parseColor("#ffffff"));
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        saveBitmap(bitmap);
        return bitmap;
    }

    public void saveBitmap(Bitmap bitmap) {
        // 保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),"image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = AbDateUtil.getStringDateShort();
        appDir = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(appDir);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 把文件插入到系统图库
        try {
            if(appDir.getAbsolutePath()!=null){
                MediaStore.Images.Media.insertImage(getContext().getContentResolver(), appDir.getAbsolutePath(), fileName, null);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(appDir.getAbsolutePath()!=null){
            // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
            OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSSKey.ACCESS_ID, OSSKey.ACCESS_KEY);
            oss = new OSSClient(getContext(), OSSKey.OSS_ENDPOINT, credentialProvider);
            //压缩图片
            oldFile=new File(appDir.getAbsolutePath());
            newFile = CompressHelper.getDefault(getContext()).compressToFile(oldFile);
            //调用AliOss上传图片
            setUploadAliOss(GetFileNameUtils.getFileName(newFile.getName(),prefs),newFile.getAbsolutePath());
        }else{
            ToastFactory.getLongToast(getContext(),"获取图片失败，请重试！");
        }
    }

    private void showShare(String shareTitle,String imageUrl,String shreUrl,String content) {
        ShareSDK.initSDK(getContext());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(shareTitle);
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(shreUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("点击查看");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath(imageUrl);//确保SDcard下面存在此张图片
        oks.setImageUrl(imageUrl);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shreUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(shreUrl);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(R.string.app_name+"");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shreUrl);

        // 启动分享GUI
        oks.show(getContext());
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
//				Log.i(Constant.TAG, "Uploading..."+"=totalSize=="+totalSize+"==currentSize:"+currentSize);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.show();
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
                                  final com.alibaba.sdk.android.oss.ServiceException serviceException) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastFactory.getLongToast(getContext(),"本地异常如网络异常等");
                        }
                    });
                    Log.i(Constant.TAG,"onFailure：本地异常如网络异常等:"+clientExcepion);
                }
                if (serviceException != null) {
                    // 服务异常
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastFactory.getLongToast(getContext(),"ErrorCode:"+serviceException.getErrorCode());
                        }
                    });
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                //获取上传阿里云Image
                ossImageUrl=OSSKey.IMAGE_URL+request.getObjectKey();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //执行分享操作
                        //执行分享操作
                        showShare(tvTitle.getText().toString(),ossImageUrl,ossImageUrl,"店员数据");
                    }
                });
            }
        });
    }
}
