package com.cesaas.android.counselor.order.workbench.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseActivity;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.member.SendMessageActivity;
import com.cesaas.android.counselor.order.net.GetFansInfoByMobileNet;
import com.cesaas.android.counselor.order.pos.CashierMainActivity;
import com.cesaas.android.counselor.order.report.net.DeleteCustomersNet;
import com.cesaas.android.counselor.order.salestalk.activity.SelectSalesTalkActivity;
import com.cesaas.android.counselor.order.shopmange.bean.RecentPurchaseBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultRecentPurchaseBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.CallUtils;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.workbench.adapter.ReturnVisitGalleryRecyclerAdapter;
import com.cesaas.android.counselor.order.workbench.bean.ResultReturnVisitBean;
import com.cesaas.android.counselor.order.workbench.bean.ResultReturnVisitDetailBean;
import com.cesaas.android.counselor.order.workbench.bean.ResultSureReturnVisiterBean;
import com.cesaas.android.counselor.order.workbench.net.ReturnVisitDetailNet;
import com.cesaas.android.counselor.order.workbench.net.SureReturnVisiterNet;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.flybiao.materialdialog.MaterialDialog;
import com.lidroid.xutils.exception.HttpException;

import java.util.ArrayList;
import java.util.List;

import cn.hugo.android.scanner.CaptureActivity;
import io.rong.imkit.RongIM;

/**
 * 回访详情
 */
public class ReturnVisitDetailActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack,ll_send_message;
    private TextView tvBaseTitle,tv_return_title,tv_return_content,tv_return_data;
    private MyImageViewWidget ivw_return_visit_detail_icon;
    private EditText etContent,et_return_visit_content;
    private Button btn_confirm_add_return_visit;
    private ImageView iv_tel_return,iv_sms_return,iv_weixin_return,iv_edit_talk_template;
    private RecyclerView rv_shop_product;

    private int ruleId;
    private int vipId;
    private int requestCode=200;

    private ReturnVisitGalleryRecyclerAdapter mRecyclerAdapter;
    private List<RecentPurchaseBean> urls;
    private RecentPurchaseNet mRecentPurchaseNet;

    private ReturnVisitDetailNet mReturnVisitDetailNet;
    private SureReturnVisiterNet mSureReturnVisiterNet;

    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_visit_detail);

        Bundle bundle=getIntent().getExtras();
        ruleId=bundle.getInt("RuleId");
        vipId=bundle.getInt("VipId");

        mRecentPurchaseNet=new RecentPurchaseNet(mContext);
        mRecentPurchaseNet.setData(1,vipId);

        mReturnVisitDetailNet=new ReturnVisitDetailNet(mContext);
        mReturnVisitDetailNet.setData(ruleId,vipId);

        initViews();
        initListener();
        initData();
    }

    public void initViews() {
        iv_tel_return= (ImageView) findViewById(R.id.iv_tel_return);
        iv_sms_return= (ImageView) findViewById(R.id.iv_sms_return);
        iv_weixin_return= (ImageView) findViewById(R.id.iv_weixin_return);
        iv_edit_talk_template= (ImageView) findViewById(R.id.iv_edit_talk_template);

        ivw_return_visit_detail_icon= (MyImageViewWidget) findViewById(R.id.ivw_return_visit_detail_icon);

        rv_shop_product= (RecyclerView) findViewById(R.id.rv_shop_product);//固定高度
        rv_shop_product.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);//创建布局管理器
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);//设置横向
        rv_shop_product.setLayoutManager(linearLayoutManager);//设置布局管理器

        ll_send_message= (LinearLayout) findViewById(R.id.ll_send_message);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tv_return_title= (TextView) findViewById(R.id.tv_return_title);
        tv_return_content= (TextView) findViewById(R.id.tv_return_content);
        tv_return_data= (TextView) findViewById(R.id.tv_return_data);
        etContent= (EditText) findViewById(R.id.etContent);
        tvBaseTitle.setText("回访详情");

        llBaseBack.setOnClickListener(this);
        ll_send_message.setOnClickListener(this);

    }

    public void initListener() {
        iv_tel_return.setOnClickListener(this);
        iv_sms_return.setOnClickListener(this);
        iv_weixin_return.setOnClickListener(this);
        iv_edit_talk_template.setOnClickListener(this);
    }

    public void initData() {
//        urls=new ArrayList<>();
//        for (int i=0;i<3;i++){
//            RecentPurchaseBean bean=new RecentPurchaseBean();
//            bean.setImageUrl("http://shenzhentesting.oss-cn-shenzhen.aliyuncs.com/images/3/2017/2/16/79eac3d0-f3f3-11e6-9a43-e36f6b841fe3.png");
//            urls.add(bean);
//        };
//        mRecyclerAdapter=new ReturnVisitGalleryRecyclerAdapter(mContext,urls);
//        rv_shop_product.setAdapter(mRecyclerAdapter);

        //点击
        mRecyclerAdapter.setOnRecyclerViewItemClickListener(new ReturnVisitGalleryRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id. iv_tel_return://电话回访
                CallUtils.call("13430706607",mActivity);
                break;
            case R.id. iv_sms_return://短信回访
                Bundle bundle=new Bundle();
                bundle.putString("Tel","13430706607");
                Skip.mNextFroData(mActivity,SendMessageActivity.class,bundle);
                break;
            case R.id. iv_weixin_return://微信回访
                //启动单聊会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(mContext, vipId+"","微信会话");
                break;
            case R.id.iv_edit_talk_template://编辑话术模板
                Skip.mActivityResult(mActivity,SelectSalesTalkActivity.class,requestCode);
                break;
            case R.id.ll_send_message://确定回访
//                new AddReturnVisitRemarksDialog(mContext,mActivity).mInitShow();

                mMaterialDialog=new MaterialDialog(mContext);
                if (mMaterialDialog != null) {
                    mMaterialDialog.setTitle("温馨提示！")
                            .setMessage(
                                    "请确定是否回访？")
                            //mMaterialDialog.setBackgroundResource(R.drawable.background);
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    mMaterialDialog.dismiss();
                                    mSureReturnVisiterNet=new SureReturnVisiterNet(mContext);
                                    mSureReturnVisiterNet.setData(vipId);
                                }
                            })
                            .setNegativeButton("取消",
                                    new View.OnClickListener() {
                                        @Override public void onClick(View v) {
                                            mMaterialDialog.dismiss();
                                            ToastFactory.getLongToast(mContext,"已取消删除！");
                                        }
                                    })
                            .setCanceledOnTouchOutside(true).show();
                }
                break;
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;
        }
    }

    /**
     *
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultReturnVisitDetailBean msg) {

        if(msg.IsSuccess==true){
            etContent.setText(msg.TModel.getTalkMessage());
            tv_return_data.setText(msg.TModel.getTime());
            tv_return_title.setText(msg.TModel.getNickName());
            Glide.with(mContext).load(msg.TModel.getImage()).into(this.ivw_return_visit_detail_icon);
            if(msg.TModel.getGrade()!=null){
                tv_return_content.setText(msg.TModel.getGrade());
            }else{
                tv_return_content.setText("暂无会员等级");
            }

        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败！"+msg.Message);
        }
    }


    public void onEventMainThread(ResultSureReturnVisiterBean msg) {
        if(msg.IsSuccess==true){
            ToastFactory.getLongToast(mContext,"确认回访成功！");
            Skip.mNext(mActivity,ReturnVisitActivity.class);
        }else{
            ToastFactory.getLongToast(mContext,"确认回访失败！"+msg.Message);
        }
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getStringExtra("talkContent");
        if(requestCode==200){
            etContent.setText(result);
        }
    }


    /**
     * 添加回访备注dialog
     *
     * @author FGB
     *
     */
    public class AddReturnVisitRemarksDialog extends Dialog implements View.OnClickListener {

        private int REQUEST_CONTACT = 20;
        private Activity activity;

        public AddReturnVisitRemarksDialog(Context context, Activity activity) {
            this(context, R.style.dialog);
            this.activity=activity;
        }

        public AddReturnVisitRemarksDialog(Context context, int dialog) {
            super(context, dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(R.layout.add_return_visit_dialog);

            initView();
        }

        public void initView(){
            btn_confirm_add_return_visit=(Button) findViewById(R.id.btn_confirm_add_return_visit);
            et_return_visit_content=(EditText) findViewById(R.id.et_return_visit_content);
//
            btn_confirm_add_return_visit.setOnClickListener(this);
        }

        public void mInitShow() {
            show();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_confirm_add_return_visit://确定添加
                    if(!et_return_visit_content.getText().toString().equals("")){
                        mSureReturnVisiterNet=new SureReturnVisiterNet(mContext);
                        mSureReturnVisiterNet.setData(vipId);
                        cancel();

                    }else{
                        ToastFactory.getLongToast(activity.getApplicationContext(), "请输入回访备注!");
                    }

                    break;

                default:
                    break;
            }
        }

    }

    /**
     * Author FGB
     * Description 最近购买商品Net
     * Created 2017/4/28 9:46
     * Version 1.0
     */
    public class RecentPurchaseNet extends BaseNet {
        public RecentPurchaseNet(Context context) {
            super(context, true);
            this.uri="Order/Sw/Order/RecentPurchase";
        }

        public void setData(int PageIndex,int VipId){
            try{
                data.put("VipId",VipId);
                data.put("PageIndex",PageIndex);
                data.put("PageSize",30);
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultRecentPurchaseBean bean= JsonUtils.fromJson(rJson,ResultRecentPurchaseBean.class);
            if(bean.IsSuccess){
                if(bean.TModel!=null){
                    urls=new ArrayList<>();
                    urls.addAll(bean.TModel);
//                    tv_recent_purchase_count.setText(urls.size()+"");
                    mRecyclerAdapter=new ReturnVisitGalleryRecyclerAdapter(mContext,urls);
                    rv_shop_product.setAdapter(mRecyclerAdapter);
                }else{
                    ToastFactory.getLongToast(mContext,"没有最近购买商品！");
                }
            }else{
                ToastFactory.getLongToast(mContext,"获取最近购买数据失败！");
            }
        }
        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }

}
