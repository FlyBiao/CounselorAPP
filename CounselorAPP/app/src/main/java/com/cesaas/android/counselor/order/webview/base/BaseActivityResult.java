package com.cesaas.android.counselor.order.webview.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.cesaas.android.counselor.order.alioss.OSSKey;
import com.cesaas.android.counselor.order.boss.bean.ScreenTagBean;
import com.cesaas.android.counselor.order.boss.bean.SelectShopListBean;
import com.cesaas.android.counselor.order.compressimage.CompressHelper;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.imagepicker.imagepickeradapter.ImagePickerAdapter;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.member.bean.SelectVipListBean;
import com.cesaas.android.counselor.order.shopmange.bean.SelectShopBean;
import com.cesaas.android.counselor.order.shopmange.bean.SelectShopMatchBean;
import com.cesaas.android.counselor.order.shoptask.bean.SelectCounselorListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.GetFileNameUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.webview.bean.shop.ResultShopInfoBean;
import com.cesaas.android.counselor.order.webview.bean.shop.ShopInfoBean;
import com.cesaas.android.counselor.order.webview.bean.shop.Shops;
import com.cesaas.android.counselor.order.webview.resultbean.ClerkInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.QueryShop;
import com.cesaas.android.counselor.order.webview.resultbean.ResultQueryShopBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultSelectClerkBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultSelectMemberBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultSelectShopBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultShopMatchBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultTagInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultUploadImageBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultVerbalTrickInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.SelectMemberInfo;
import com.cesaas.android.counselor.order.webview.resultbean.SelectShopInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.ShopMatchInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.TagInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.UploadImageInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.VerbalTrickInfoBean;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import org.json.JSONArray;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 回调方法，从第二个页面回来的时候会执行这个方法
 * Created at 2017/5/21 10:16
 * Version 1.0
 */

public class BaseActivityResult {

    private static String ossImageUrl;//
    private static OSS oss;
    private  ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private  ImagePickerAdapter adapter;

    private WebView webView;
    private Context mContext;
    private Activity activity;

    private File oldFile;
    private File newFile;

    private ResultQueryShopBean resultQueryShopBean;
    private JSONArray year=new JSONArray();
    private JSONArray season=new JSONArray();
    private JSONArray band=new JSONArray();
    private JSONArray category=new JSONArray();
    private JSONArray smallSort=new JSONArray();
    private JSONArray bigSort=new JSONArray();

    public BaseActivityResult(Context mContext, Activity activity,WebView webView,ArrayList<ImageItem> selImageList,ImagePickerAdapter adapter){
        this.mContext=mContext;
        this.activity=activity;
        this.webView=webView;
        this.selImageList=selImageList;
        this.adapter=adapter;
    }
    public BaseActivityResult(Context mContext, Activity activity,WebView webView){
        this.mContext=mContext;
        this.activity=activity;
        this.webView=webView;
    }

    public void getOnActivityResult(int requestCode, int resultCode, Intent data,AbPrefsUtil pres){
        switch (requestCode){
            case 111://图片上传
                    if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                        //添加图片返回
                        if (data != null && requestCode == 111) {
                            // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
                            OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSSKey.ACCESS_ID, OSSKey.ACCESS_KEY);
                            oss = new OSSClient(mContext, OSSKey.OSS_ENDPOINT, credentialProvider);

                            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                            selImageList.addAll(images);
                            adapter.setImages(selImageList);
                            for (int i=0;i<selImageList.size();i++){
                                //压缩图片
                                oldFile=new File(selImageList.get(i).path);
                                newFile = CompressHelper.getDefault(mContext).compressToFile(oldFile);
                                //调用AliOss上传图片
                                setUploadAliOss(GetFileNameUtils.getFileName(newFile.getAbsolutePath(),pres),newFile.getAbsolutePath());
                            }
                        }
                }
                break;
            case 112://图片预览
                break;
            case 301://商品选择
                if(data!=null) {
                    List<SelectShopBean> selectShopBeen=(ArrayList<SelectShopBean>)data.getSerializableExtra("selectList");
                    SelectShopInfoBean shopInfoBean=null;
                    JSONArray jsonArray = new JSONArray();
                    for (int i=0;i<selectShopBeen.size();i++){
                        shopInfoBean= new SelectShopInfoBean();
                        shopInfoBean.setId(selectShopBeen.get(i).getId());
                        shopInfoBean.setTitle(selectShopBeen.get(i).getTitle());
                        shopInfoBean.setPrice(selectShopBeen.get(i).getSalesPrice()+"");
                        shopInfoBean.setImage_url(selectShopBeen.get(i).getImageUrl());
                        jsonArray.put(shopInfoBean.getSelectShopInfo());
                    }

                    ResultSelectShopBean resultSelectShopBean = new ResultSelectShopBean();
                    resultSelectShopBean.setType(301);
                    resultSelectShopBean.setParam(jsonArray);
                    webView.loadUrl("javascript:appCallback('" + resultSelectShopBean.getSelectShopResult() + "')");
                }
                break;
            case 302://选择搭配
                if(data!=null){
                    List<SelectShopMatchBean> selectShopMatchBeen=(ArrayList<SelectShopMatchBean>)data.getSerializableExtra("selectList");
                    ShopMatchInfoBean matchInfoBean=null;
                    JSONArray arr = new JSONArray();
                    for (int i=0;i<selectShopMatchBeen.size();i++){
                        matchInfoBean=new ShopMatchInfoBean();
                        matchInfoBean.setId(selectShopMatchBeen.get(i).getId());
                        matchInfoBean.setTitle(selectShopMatchBeen.get(i).getTitle());
                        matchInfoBean.setImage_url(selectShopMatchBeen.get(i).getImageUrl());
                        matchInfoBean.setSell_point(selectShopMatchBeen.get(i).getEvaluate()+"");
                        arr.put(matchInfoBean.getShopMatchInfo());
                    }
                    ResultShopMatchBean resultShopMatchBean=new ResultShopMatchBean();
                    resultShopMatchBean.setType(302);
                    resultShopMatchBean.setParam(arr);
                    webView.loadUrl("javascript:appCallback('" + resultShopMatchBean.getShopMatchResult() + "')");
                }
                break;
            case 304://商品筛选条件
                if(data!=null){
                    List<ScreenTagBean> screenTagBeanList=(ArrayList<ScreenTagBean>)data.getSerializableExtra("selectList");
                    resultQueryShopBean =new ResultQueryShopBean();
                    QueryShop shop=new QueryShop();
                    for (int i=0;i<screenTagBeanList.size();i++){
                        switch (screenTagBeanList.get(i).getType()){
                            case 1:
                                year.put(screenTagBeanList.get(i).getName());
                                shop.setYear(year);
                                break;
                            case 2:
                                season.put(screenTagBeanList.get(i).getName());
                                shop.setSeason(season);
                                break;
                            case 3:
                                smallSort.put(screenTagBeanList.get(i).getId());
                                shop.setSmallSort(smallSort);
                                break;
                            case 4:
                                band.put(screenTagBeanList.get(i).getName());
                                shop.setBand(band);
                                break;
                            case 5:
                                category.put(screenTagBeanList.get(i).getName());
                                shop.setCategory(category);
                                break;
                        }
                    }
//                smallSort.put(1);
//                smallSort.put(2);
//                shop.setSmallSort(smallSort);
                    resultQueryShopBean.setType(304);
                    resultQueryShopBean.setParam(shop.getQueryInfo());
                    webView.loadUrl("javascript:appCallback('" + resultQueryShopBean.getQueryInfo() + "')");
                }
                break;
            case 401://选择会员
                if(data!=null){
                    List<SelectVipListBean> selectVipListBeen=(ArrayList<SelectVipListBean>)data.getSerializableExtra("selectList");
                    SelectMemberInfo memberInfo=null;
                    JSONArray jsonArray=new JSONArray();
                    for (int i=0;i<selectVipListBeen.size();i++){
                        memberInfo=new SelectMemberInfo();
                        memberInfo.setId(selectVipListBeen.get(i).getVipId());
                        memberInfo.setName(selectVipListBeen.get(i).getNickName());
                        memberInfo.setLogo(selectVipListBeen.get(i).getImage());
                        memberInfo.setGrade(selectVipListBeen.get(i).getCardName());
                        memberInfo.setMobile(selectVipListBeen.get(i).getMobile());
                        memberInfo.setCounselor_id(selectVipListBeen.get(i).getCounselorId());
                        memberInfo.setBirthday(selectVipListBeen.get(i).getBirthDay());
                        memberInfo.setPoints(selectVipListBeen.get(i).getPoint());
                        jsonArray.put(memberInfo.getMemberInfo());
                    }

                    ResultSelectMemberBean resultSelectMemberBean=new ResultSelectMemberBean();
                    resultSelectMemberBean.setType(401);
                    resultSelectMemberBean.setParam(jsonArray);
                    webView.loadUrl("javascript:appCallback('"+resultSelectMemberBean.getSelectMemberResult()+"')");
                }

                break;
            case 402://选择话术
                if(data!=null){
                    String idss = data.getStringExtra("id");
                    String question = data.getStringExtra("question");
                    String content = data.getStringExtra("content");
                    String group_id = data.getStringExtra("group_id");
                    String group_name=data.getStringExtra("group_name");

                    VerbalTrickInfoBean trickInfoBean=new VerbalTrickInfoBean();
                    trickInfoBean.setId(Integer.parseInt(idss));
                    trickInfoBean.setQuestion(question);
                    trickInfoBean.setContent(content);
                    trickInfoBean.setGroup_id(Integer.parseInt(group_id));
                    trickInfoBean.setGroup_name(group_name);

                    ResultVerbalTrickInfoBean resultVerbalTrickInfoBean=new ResultVerbalTrickInfoBean();
                    resultVerbalTrickInfoBean.setType(402);
                    resultVerbalTrickInfoBean.setParam(trickInfoBean.getVerbalTrickInfo());

                    webView.loadUrl("javascript:appCallback('" + resultVerbalTrickInfoBean.getVerbalTrickInfoResult() + "')");
                }
                break;
            case 701://选择店员
                if(data!=null){
                    List<SelectCounselorListBean> selectCounselorListBeen=(ArrayList<SelectCounselorListBean>)data.getSerializableExtra("selectList");
                    JSONArray arr=new JSONArray();
                    ClerkInfoBean clerkInfoBean=null;

                    for(int i=0;i<selectCounselorListBeen.size();i++){
                        clerkInfoBean=new ClerkInfoBean();
                        clerkInfoBean.setId(selectCounselorListBeen.get(i).getCOUNSELOR_ID());
                        clerkInfoBean.setImage_url(selectCounselorListBeen.get(i).getCOUNSELOR_ICON());
                        clerkInfoBean.setName(selectCounselorListBeen.get(i).getCOUNSELOR_NICKNAME());
                        arr.put(clerkInfoBean.getClerkInfo());
                    }
                    ResultSelectClerkBean resultSelectClerkBean=new ResultSelectClerkBean();
                    resultSelectClerkBean.setType(701);
                    resultSelectClerkBean.setParam(arr);
                    //调用js中的appCallback方法
                    webView.loadUrl("javascript:appCallback('"+resultSelectClerkBean.getClerkInfoResult()+"')");
                }
                break;

            case 901://标签筛选
                if(data!=null){
                    List<GetTagListBean> selectList=(ArrayList<GetTagListBean>)data.getSerializableExtra("selectList");
                    JSONArray jsonArray=new JSONArray();
                    TagInfoBean tagInfoBean=null;
                    for (int i=0;i<selectList.size();i++){
                        tagInfoBean=new TagInfoBean();
                        tagInfoBean.setCategory_id(selectList.get(i).getCategoryId());
                        tagInfoBean.setCategory_name(selectList.get(i).getCategoryName());//
                        tagInfoBean.setId(selectList.get(i).getTagId());
                        tagInfoBean.setTitle(selectList.get(i).getTagName());
                        jsonArray.put(tagInfoBean.getTagInfo());
                    }
                    ResultTagInfoBean resultTagInfoBean=new ResultTagInfoBean();
                    resultTagInfoBean.setType(901);
                    resultTagInfoBean.setParam(jsonArray);
                    //调用js中的appCallback方法
                    webView.loadUrl("javascript:appCallback('"+resultTagInfoBean.getTagInfoResult()+"')");
                }
            break;
            case 201:
                if(data!=null){
                    List<SelectShopListBean> shopListBeanList=(ArrayList<SelectShopListBean>)data.getSerializableExtra("selectList");
                    JSONArray jsonArray=new JSONArray();
                    JSONArray resultJsonArray=new JSONArray();
                    ShopInfoBean shopInfoBean=null;
                    for (int i=0;i<shopListBeanList.size();i++){
                        shopInfoBean=new ShopInfoBean();
                        shopInfoBean.setId(shopListBeanList.get(i).getShopId());
                        shopInfoBean.setName(shopListBeanList.get(i).getShopName());
                        shopInfoBean.setShop_type(shopListBeanList.get(i).getShopType());
                        jsonArray.put(shopInfoBean.getShopInfo());
                    }
//                    Shops shops=new Shops();
//                    shops.setShops(jsonArray);
//                    resultJsonArray.put(shops.getResultShops());
                    final ResultShopInfoBean resultShopInfoBean=new ResultShopInfoBean();
                    resultShopInfoBean.setType(201);
                    resultShopInfoBean.setParam(jsonArray);
                    //调用js中的appCallback方法
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl("javascript:appCallback('" + resultShopInfoBean.getShopInfResult() + "')");
                        }
                    });

                }
                break;
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
//				Log.i(Constant.TAG, "Uploading..."+"=totalSize=="+totalSize+"==currentSize:"+currentSize);
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
                //获取上传阿里云Image
                ossImageUrl=OSSKey.IMAGE_URL+request.getObjectKey();
                UploadImageInfoBean imageInfoBean=new UploadImageInfoBean();
                imageInfoBean.setImage_url(ossImageUrl);
                final ResultUploadImageBean uploadImageBean=new ResultUploadImageBean();
                uploadImageBean.setType(111);
                uploadImageBean.setParam(imageInfoBean.getImageUrl());

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //调用js中的appCallback方法
                        webView.loadUrl("javascript:appCallback('"+uploadImageBean.getUploadImageResult()+"')");
                    }
                });
            }
        });
        //Log.i("RequestId", "==="+strJson);
        // task.cancel(); // 可以取消任务
//        task.waitUntilFinished(); // 可以等待直到任务完成
    }
}
