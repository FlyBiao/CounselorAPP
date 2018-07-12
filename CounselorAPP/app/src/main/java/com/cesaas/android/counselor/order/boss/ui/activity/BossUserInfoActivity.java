package com.cesaas.android.counselor.order.boss.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.DepositActivity;
import com.cesaas.android.counselor.order.activity.LoginActivity;
import com.cesaas.android.counselor.order.activity.ModifyNickActivity;
import com.cesaas.android.counselor.order.activity.ModifyPwdActivity;
import com.cesaas.android.counselor.order.activity.user.SetUserSignatureActivity;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.dialog.CameraDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.net.UserModifyPicNet;
import com.cesaas.android.counselor.order.utils.AbImageUtil;
import com.cesaas.android.counselor.order.utils.AbPhotoUtils;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;

@ContentView(R.layout.activity_boss_user_info)
public class BossUserInfoActivity extends BasesActivity {

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

    private static String nickName = "";// 用户昵称
    private static String icon="";// 用户头像
    private String account;// 用户账号
    private String mobile;
    private String siganature;

    private String aPPPersonalCenter;

    //用户信息
    private UserInfoNet userInfoNet;

    public static BitmapUtils bitmapUtils;
    private AbPhotoUtils photoUtil;
    private Bitmap bitmap;
    private UserModifyPicNet modifyPicNet;
    private static BossUserInfoActivity activity;

    static boolean nickModify = false;//昵称是否修改了，默认false
    static boolean iconModify=false;//头像是否修改了，默认false

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

        photoUtil = AbPhotoUtils.getInstance();
        modifyPicNet = new UserModifyPicNet(mContext);
        activity = this;

        initDate();
        initBack();
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        tv_user_nick.setText(nickName);
        bitmapUtils.display(iv_user_head_icon, icon, App.mInstance().bitmapConfig);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        nickModify=false;
        iconModify=false;
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
//                new CameraDialog(mActivity, true, new CameraDialog.CameraInterface() {
//
//                    @Override
//                    public void onCamera() {//相机
//                        photoUtil.clearCachedCropFile();
//                        Intent intent = new Intent();
//                        intent.setAction(Intent.ACTION_PICK);
//                        intent.setType("image/*");
//                        if (Build.VERSION.SDK_INT < 19) {
//                            startActivityForResult(intent, AbPhotoUtils.KITKAT_LESS);
//                        } else {
//                            startActivityForResult(intent,AbPhotoUtils.KITKAT_ABOVE);
//                        }
//                    }
//
//                    @Override
//                    public void onPhoto() {//相册
//                        photoUtil.clearCachedCropFile();
//                        startActivityForResult(photoUtil.selectCamera(),AbPhotoUtils.PHOTO_CAMEAR);
//                    }
//
//                }).mShow();
                break;

            case R.id.btn_exit_login:
                exit();
                break;
        }

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
            } else if (requestCode == AbPhotoUtils.KITKAT_LESS) {
                if (data != null) {
                    uri = data.getData();
                    // 调用裁剪方法
                    cropImageUri(uri, 300, AbPhotoUtils.INTENT_CROP);
                }
            } else if (requestCode == AbPhotoUtils.KITKAT_ABOVE) {
                if (data != null) {
                    uri = data.getData();
                    // 先将这个uri转换为path，然后再转换为uri
                    String thePath = photoUtil.getPath(mActivity, uri);
                    cropImageUri(Uri.fromFile(new File(thePath)), 300,
                            AbPhotoUtils.INTENT_CROP);
                }
            }
        }
        if (requestCode == AbPhotoUtils.INTENT_CROP) {
            onPhotoCropped(photoUtil.buildUri());
        }
    }

    Handler handlPic = new Handler();

    public void onPhotoCropped(final Uri uri) {
        handlPic.post(new Runnable() {
            @Override
            public void run() {
                bitmap = photoUtil.decodeUriAsBitmap(mActivity, uri);
                if (bitmap != null) {
                    modifyPicNet.setData(Base64.encodeToString(AbImageUtil
                                    .bitmap2Bytes(bitmap, Bitmap.CompressFormat.PNG, false),
                            Base64.DEFAULT));
                }
            }
        });
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
//		intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUtil.buildUri());
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        if (photoUtil != null) {
            photoUtil.clearCachedCropFile(photoUtil.mCameraUri());
            photoUtil.clearCachedCropFile();
        }
        super.onDestroy();
        BossUserInfoActivity.this.finish();
    }

    /**
     * 返回上一个页面
     */
    private void initBack() {
        iv_userCenter_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    /**
     * 退出当前用户
     */
    public void exit() {
        new MyAlertDialog(mContext).mInitShow("退出登录", "是否退出登录，退出后将不能接收最新消息",
                "我知道", "点错了", new MyAlertDialog.ConfirmListener() {
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
                                BossUserInfoActivity.this.finish();
                                setResult(0xe); // 用于退出时关闭本页
                                onExit();
                                Skip.mNextFroData(mActivity, LoginActivity.class, bundle,true);
                            }
                        });
                    }
                }, null);
    }

}
