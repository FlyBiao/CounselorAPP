package com.cesaas.android.counselor.order.manager.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.alioss.OSSKey;
import com.cesaas.android.counselor.order.compressimage.CompressHelper;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.manager.adapter.TaskAdapter;
import com.cesaas.android.counselor.order.manager.bean.ResultTaskListBean;
import com.cesaas.android.counselor.order.manager.bean.ResultTaskStatisticBean;
import com.cesaas.android.counselor.order.manager.bean.TaskListBean;
import com.cesaas.android.counselor.order.manager.net.CopyTaskListNet;
import com.cesaas.android.counselor.order.manager.net.TaskListNet;
import com.cesaas.android.counselor.order.manager.net.TaskStatisticNet;
import com.cesaas.android.counselor.order.manager.net.TaskWorkListNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.GetFileNameUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 区域经理主页
 */
public class ManagerMainActivity extends BasesActivity implements View.OnClickListener,BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TextView tv_shop_quantity,tv_read_quantity,tv_undo_quantity;
    private TextView tvShop,tvInstitution,tvArea;
    private ArrayList<TextView> tvs=new ArrayList<TextView>();

    private TextView tvTitle,tvLeftTitle;
    private TextView tv_day,tv_month,tv_year;
    private ImageView ivShare;
    private LinearLayout llBack,ll_add_shop_task;

    private static String ossImageUrl;//
    private static OSS oss;
    private File oldFile;
    private File newFile;

    private Typeface font;
    private String leftTitle;

    private WaitDialog dialog;

    private List<TaskListBean> data;
    private TaskAdapter adapter;

    private TaskStatisticNet statisticNet;
    private TaskListNet taskListNet;
    private CopyTaskListNet copyTaskListNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);
        dialog = new WaitDialog(mContext);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            leftTitle=bundle.getString("leftTitle");
        }

        initView();
        initData();
        initNet();
    }

    private void initNet(){
        statisticNet=new TaskStatisticNet(mContext);
        statisticNet.setData();

        taskListNet=new TaskListNet(mContext);
        taskListNet.setData(1,1);

    }

    /**
     * 接收任务统计数据
     * @param msg
     */
    public void onEventMainThread(ResultTaskStatisticBean msg) {
        if(msg.IsSuccess!=false && msg.TModel!=null){
            tv_shop_quantity.setText(msg.TModel.getShopQuantity()+"");
            tv_read_quantity.setText(msg.TModel.getReadTaskQuantity()+"");
            tv_undo_quantity.setText(msg.TModel.getUnDoQuantity()+"");
        }
    }

    /**
     * 接收任务统计数据
     * @param msg
     */
    public void onEventMainThread(ResultTaskListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel.size()!=0){
                data=new ArrayList<>();
                data=msg.TModel;
                adapter=new TaskAdapter(data);
//                adapter.setOnLoadMoreListener(this, mRecyclerView);
                adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                        bundle.putInt("TaskId",data.get(position).getTaskId());
                        bundle.putString("leftTitle",tvTitle.getText().toString());
                        Skip.mNextFroData(mActivity,TaskDetailsActivity.class,bundle);
                    }
                });
            }
        }
    }

    private void initData(){
        tv_year.setText(AbDateUtil.getYear());
         tv_day.setText(AbDateUtil.getDay());
        tv_month.setText(AbDateUtil.getMonth());
    }

    private void initView() {
        font= Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        tv_shop_quantity= (TextView) findViewById(R.id.tv_shop_quantity);
        tv_read_quantity= (TextView) findViewById(R.id.tv_read_quantity);
        tv_undo_quantity= (TextView) findViewById(R.id.tv_undo_quantity);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tvLeftTitle.setText(leftTitle);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("经理主页");
        ivShare= (ImageView) findViewById(R.id.iv_add_module);
        ivShare.setVisibility(View.VISIBLE);
        ivShare.setImageResource(R.mipmap.share);

        tv_day= (TextView) findViewById(R.id.tv_day);
        tv_month= (TextView) findViewById(R.id.tv_month);
        tv_year= (TextView) findViewById(R.id.tv_year);

        ll_add_shop_task= (LinearLayout) findViewById(R.id.ll_add_shop_task);
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);

        tvShop=(TextView) findViewById(R.id.tv_shop);
        tvShop.setOnClickListener(this);
        tvInstitution=(TextView) findViewById(R.id.tv_institution);
        tvInstitution.setOnClickListener(this);
        tvArea=(TextView) findViewById(R.id.tv_area);
        tvArea.setOnClickListener(this);
        tvs.add(tvShop);
        tvs.add(tvInstitution);
        tvs.add(tvArea);
        initClickListener();
    }

    private void initClickListener(){
        ll_add_shop_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle",tvTitle.getText().toString());
                Skip.mNextFroData(mActivity,AddShopTaskActivity.class,bundle);
            }
        });
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int index=-1;
        switch (v.getId()){
            case R.id.tv_shop:
                index=0;
                taskListNet=new TaskListNet(mContext);
                taskListNet.setData(0,1);
                break;
            case R.id.tv_institution:
                index=1;
                copyTaskListNet=new CopyTaskListNet(mContext);
                copyTaskListNet.setData(1,1);
//                bundle.putString("leftTitle",tvTitle.getText().toString());
//                bundle.putString("title",tvInstitution.getText().toString());
//                Skip.mNextFroData(mActivity,TaskMainActivity.class,bundle);
                break;
            case R.id.tv_area:
                index=2;
                bundle.putString("leftTitle",tvTitle.getText().toString());
                bundle.putString("title",tvArea.getText().toString());
                Skip.mNextFroData(mActivity,TaskMainActivity.class,bundle);
                break;
        }
        setColor(index);
    }

    private void setColor(int index) {
        for(int i=0;i<tvs.size();i++){
            tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.darkcyan));
            tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.day_sign_content_text_white_30));
        }
        tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.white));
        tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_green_bg));
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
        File appDir = new File(Environment.getExternalStorageDirectory(),"sales_image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "sales_image" + ".png";
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
                MediaStore.Images.Media.insertImage(this.getContentResolver(), appDir.getAbsolutePath(), fileName, null);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(appDir.getAbsolutePath()!=null){
            // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
            OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSSKey.ACCESS_ID, OSSKey.ACCESS_KEY);
            oss = new OSSClient(mContext, OSSKey.OSS_ENDPOINT, credentialProvider);
            //压缩图片
            oldFile=new File(appDir.getAbsolutePath());
            newFile = CompressHelper.getDefault(mContext).compressToFile(oldFile);
            //调用AliOss上传图片
            setUploadAliOss(GetFileNameUtils.getFileName(newFile.getAbsolutePath(),prefs),newFile.getAbsolutePath());
        }else{
            ToastFactory.getLongToast(mContext,"获取图片失败，请重试！");
        }
    }

    private void showShare(String shareTitle,String imageUrl,String shreUrl) {
        ShareSDK.initSDK(mContext);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(shareTitle);
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(shreUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shreUrl);
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
        oks.show(mContext);
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
                mActivity.runOnUiThread(new Runnable() {
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
                                  com.alibaba.sdk.android.oss.ServiceException serviceException) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
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
                        dialog.dismiss();
                    }
                });
                //获取上传阿里云Image
                ossImageUrl=OSSKey.IMAGE_URL+request.getObjectKey();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //执行分享操作
                        showShare(tvTitle.getText().toString(),ossImageUrl,ossImageUrl);
                    }
                });
            }
        });
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                pullToRefreshAdapter.setNewData(DataServer.getSampleData(PAGE_SIZE));
//                isErr = false;
//                mCurrentCounter = PAGE_SIZE;
                taskListNet=new TaskListNet(mContext);
                taskListNet.setData(1,1);
                mSwipeRefreshLayout.setRefreshing(false);
//                adapter.setEnableLoadMore(true);
            }
        }, 1000);
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
