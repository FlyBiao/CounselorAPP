package com.cesaas.android.counselor.order.member.service;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.NewMainActivity;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.adapter.TabLayoutViewPagerReturnVistsAdapter;
import com.cesaas.android.counselor.order.member.adapter.service.multipleservice.MultipleServiceAdapter;
import com.cesaas.android.counselor.order.member.bean.ResultVipGetOneBean;
import com.cesaas.android.counselor.order.member.bean.ResultVipOrderStatisticBean;
import com.cesaas.android.counselor.order.member.bean.VipGetOneBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultCloseBean;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultSendMsgBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.multipleservice.MultipleServiceBean;
import com.cesaas.android.counselor.order.member.bean.service.multipleservice.ResultGetVipTaskBean;
import com.cesaas.android.counselor.order.member.net.VipGetOneNet;
import com.cesaas.android.counselor.order.member.net.VipOrderStatisticNet;
import com.cesaas.android.counselor.order.member.net.service.ColseServiceNet;
import com.cesaas.android.counselor.order.member.net.service.GetVipTaskNet;
import com.cesaas.android.counselor.order.member.net.service.SendMsgNet;
import com.cesaas.android.counselor.order.member.util.MemberPhoneListenUtils;
import com.cesaas.android.counselor.order.member.volume.SendVolumeActivity;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.CalculateDateUtil;
import com.cesaas.android.counselor.order.utils.CallUtils;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员服务
 */
public class MemberReturnVisitDetailActivity extends BaseTemplateActivity {

    private RecyclerView mRecyclerView;
    private TextView tvTitle,tv_shop_name,tv_member_consume_amount,tv_last_buy,tv_ym,tv_quantity,tv_payment,tv_order_price,tv_title,tv_time,tv_day,tv_desc,tv_remark,tv_status;
    private TextView tv_birthday,tv_database,tv_mobile,tv_cart,tv_certificate,tv_glass,tv_city,tv_grade,tv_member_name,tv_member_birthday,tv_birthday_day,tv_member_city,tv_member_province,tv_member_counselor,tv_member_mobile;
    private CircleImageView ivw_user_icon;
    private LinearLayout ll_start_service,ll_hid_service,ll_service_content;
    private LinearLayout llBack;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private int id;
    private static int VipId;
    public static int VipIds;
    private String Name;
    private String Phone;
    private String Date;
    private String Desc;
    private String Remark;
    private String Title;
    private int Status;
    private String sex;
    private boolean isShow=false;

    private GetVipTaskNet getVipTaskNet;
    private VipGetOneNet net;
    private VipOrderStatisticNet statisticNet;
    private MultipleServiceAdapter mAdapter;
    private List<MultipleServiceBean> mData;
    private VipGetOneBean vipGetOneBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_return_visit_details;
    }

    public void onEventMainThread(ResultVipGetOneBean msg) {
        if(msg.IsSuccess!=false && msg.TModel!=null){
            try {
                vipGetOneBean=new VipGetOneBean();
                vipGetOneBean=msg.TModel;
                Phone=msg.TModel.getMobile();
                sex=msg.TModel.getSex();
                VipIds=msg.TModel.getVipId();
                tv_grade.setText(msg.TModel.getCardName());
                tv_shop_name.setText(msg.TModel.getShopName());
                tv_member_city.setText(msg.TModel.getCity());
                tv_member_consume_amount.setText(msg.TModel.getConsumeAmount()+"");
                tv_member_mobile.setText(msg.TModel.getMobile());
                if(msg.TModel.getLastBuy()!=null && !"".equals(msg.TModel.getLastBuy())){
                    tv_last_buy.setText(AbDateUtil.formats(msg.TModel.getLastBuy()));
                }else{
                    tv_last_buy.setText("暂无购买");
                }
                if(msg.TModel.getCardCreateTime()!=null && !"".equals(msg.TModel.getCardCreateTime())){
                    tv_ym.setText(CalculateDateUtil.yearsBetween(msg.TModel.getCardCreateTime(),AbDateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"))+"年"+CalculateDateUtil.monthsBetween(msg.TModel.getCardCreateTime(),AbDateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"))+"月");
                }else{
                    tv_ym.setText("");
                }
                if(msg.TModel.getBirthDay()!=null && !"".equals(msg.TModel.getBirthDay())){
                    tv_member_birthday.setText(AbDateUtil.toDateMDs(msg.TModel.getBirthDay()));
                    tv_birthday_day.setText(AbDateUtil.formats(msg.TModel.getBirthDay()));
                }else{
                    tv_member_birthday.setText("");
                }

                if(msg.TModel.getFansName()!=null && !"".equals(msg.TModel.getFansName())){
                    tv_member_name.setText(msg.TModel.getFansName());
                }else{
                    tv_member_name.setText(msg.TModel.getNickName());
                }

                if(msg.TModel.getImage()!=null && !"".equals(msg.TModel.getImage())){
                    Glide.with(mContext).load(msg.TModel.getImage()).into(ivw_user_icon);
                }else{
                    ivw_user_icon.setImageResource(R.mipmap.not_user_icon);
                }
                if(msg.TModel.getCounselorName()!=null && !"".equals(msg.TModel.getCounselorName())){
                    tv_member_counselor.setText(msg.TModel.getCounselorName());
                }else{
                    tv_member_counselor.setText("暂无顾问");
                }


                if(msg.TModel.getContent()!=null && !"".equals(msg.TModel.getContent())){
                    tv_remark.setText(msg.TModel.getContent());
                }else if(msg.TModel.getDesc()!=null && !"".equals(msg.TModel.getDesc())){
                    tv_remark.setText(msg.TModel.getDesc());
                }else{
                    tv_remark.setText(msg.TModel.getRemark());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取会员数据失败："+msg.Message);
      }
    }

    public void onEventMainThread(ResultVipOrderStatisticBean msg) {
        if(msg.IsSuccess!=false){
            tv_quantity.setText(msg.TModel.getOrderQuantity()+"");
            tv_payment.setText("￥"+msg.TModel.getPayment());
            double orderPrice=msg.TModel.getPayment()/msg.TModel.getOrderQuantity();
            tv_order_price.setText("￥"+ DecimalFormatUtils.decimalFormatRound(orderPrice));

        }else{
            ToastFactory.getLongToast(mContext,"获取会员数据失败："+msg.Message);
        }
    }

    public void onEventMainThread(ResultSendMsgBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"发送短信成功!");
            bundle.putInt("Id",id);
            bundle.putString("Name",Name);
            bundle.putString("Phone",Phone);
            bundle.putString("sex",sex);
            bundle.putSerializable("MultipleServiceList", (Serializable)mData);
            Skip.mNextFroData(mActivity, MemberServiceResultActivity.class,bundle,true);
        }else{
            ToastFactory.getLongToast(mContext,"发送短信失败："+ msg.Message);
        }
    }

    public void onEventMainThread(ResultCloseBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"关闭任务成功！");
            Skip.mNext(mActivity, NewMainActivity.class);
        }else{
            ToastFactory.getLongToast(mContext,"关闭任务失败："+ msg.Message);
        }
    }

    public void onEventMainThread(ResultGetVipTaskBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);
                mAdapter=new MultipleServiceAdapter(mData,mActivity);
                mRecyclerView.setAdapter(mAdapter);
            }
        }else{
            ToastFactory.getLongToast(mContext,"关闭任务失败："+ msg.Message);
        }
    }

    @Override
    public void initViews() {
        ll_service_content=findView(R.id.ll_service_content);
        ll_hid_service=findView(R.id.ll_hid_service);
        ll_hid_service.setOnClickListener(this);
        tv_birthday_day=findView(R.id.tv_birthday_day);
        tv_status=findView(R.id.tv_status);
        tv_remark=findView(R.id.tv_remark);
        tv_desc=findView(R.id.tv_desc);
        tv_day=findView(R.id.tv_day);
        tv_time=findView(R.id.tv_time);
        tv_title=findView(R.id.tv_title);
        tv_order_price=findView(R.id.tv_order_price);
        tv_payment=findView(R.id.tv_payment);
        tv_quantity=findView(R.id.tv_quantity);
        tv_ym=findView(R.id.tv_ym);
        tv_last_buy=findView(R.id.tv_last_buy);
        tv_member_consume_amount=findView(R.id.tv_member_consume_amount);
        tv_member_mobile=findView(R.id.tv_member_mobile);
        tv_shop_name=findView(R.id.tv_shop_name);
        tv_member_counselor=findView(R.id.tv_member_counselor);
        tv_member_province=findView(R.id.tv_member_province);
        tv_member_city=findView(R.id.tv_member_city);
        tv_member_birthday=findView(R.id.tv_member_birthday);
        tv_member_name=findView(R.id.tv_member_name);
        tv_grade=findView(R.id.tv_grade);
        ivw_user_icon=findView(R.id.ivw_user_icon);
        tvTitle=findView(R.id.tv_base_title);
        tvTitle.setText("会员服务-回访详情");
        llBack=findView(R.id.ll_base_title_back);
        tv_birthday=findView(R.id.tv_birthday);
        tv_birthday.setText(R.string.fa_birthday);
        tv_birthday.setTypeface(App.font);
        tv_database=findView(R.id.tv_database);
        tv_database.setText(R.string.fa_database);
        tv_database.setTypeface(App.font);
        tv_mobile=findView(R.id.tv_mobile);
        tv_mobile.setText(R.string.mobile);
        tv_mobile.setTypeface(App.font);
        tv_cart=findView(R.id.tv_cart);
        tv_cart.setText(R.string.fa_cart);
        tv_cart.setTypeface(App.font);
        tv_certificate=findView(R.id.tv_certificate);
        tv_certificate.setText(R.string.fa_certificate);
        tv_certificate.setTypeface(App.font);
        tv_glass=findView(R.id.tv_glass);
        tv_glass.setText(R.string.fa_glass);
        tv_glass.setTypeface(App.font);
        tv_city=findView(R.id.tv_city);
        tv_city.setText(R.string.fa_glass);
        tv_city.setTypeface(App.font);
        mTabLayout= (TabLayout) findViewById(R.id.tab_layout);
        mViewPager= (ViewPager) findViewById(R.id.view_pager);
        ll_start_service=findView(R.id.ll_start_service);
        mRecyclerView=findView(R.id.rv_service_list);
        LinearLayoutManager ms= new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(ms);
    }

    @Override
    public void initListener() {
        llBack.setOnClickListener(this);
        ll_start_service.setOnClickListener(this);
    }

    public static int getVipId(){
        int id=VipId;
        return id;
    }

    @Override
    public void initData() {
        Bundle bundle=getIntent().getExtras();
        id=bundle.getInt("Id");
        VipId=bundle.getInt("VipId");
        Name=bundle.getString("Name");
        Date=bundle.getString("Date");
        Desc=bundle.getString("Desc");
        Remark=bundle.getString("Remark");
        Title=bundle.getString("Title");
        Status=bundle.getInt("Status");

        tv_title.setText(Title);
        if(Date!=null){
            String str=Date;
            String dateType=str.substring(4,5);
            if(dateType.equals("/")){
                tv_day.setText(AbDateUtil.formatDateStr2Desc(Date,"yyyy/MM/dd HH:mm:ss"));
            }else{
                tv_day.setText(AbDateUtil.formatDateStr2Desc(Date,"yyyy-MM-dd HH:mm:ss"));
            }
            tv_time.setText(AbDateUtil.getDateYMDs(Date));
        }else{
            tv_time.setText("暂无日期");
            tv_day.setText("");
        }
        tv_desc.setText(Desc);
        switch (Status){
            case 10:
                tv_status.setText("待处理");
                break;
            case 20:
                tv_status.setText("已完成");
                break;
            case 30:
                tv_status.setText("已关闭");
                break;
        }

        net=new VipGetOneNet(mContext,1);
        net.setData(VipId+"");
        statisticNet=new VipOrderStatisticNet(mContext);
        statisticNet.setData(VipId+"");

        getVipTaskNet=new GetVipTaskNet(mContext);
        getVipTaskNet.setData(VipId);

        PagerAdapter adapter= new TabLayoutViewPagerReturnVistsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
            break;
            case R.id.ll_start_service:
                showBottonDialog();
                break;
            case R.id.ll_hid_service:
                if(isShow==false){
                    isShow=true;
                    ll_service_content.setVisibility(View.GONE);
                }else{
                    isShow=false;
                    ll_service_content.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    private Dialog bottomDialog;
    private Dialog sendDialog;
    private View dialogContentView;
    private TextView tv_name,tv_phone;
    private EditText et_service_content;
    private LinearLayout ll_add_service_result,ll_send_msg,ll_service_send,ll_call_phone,ll_close_service,ll_send_volume;
    private List<MemberServiceListBean> selectMember=new ArrayList<>();

    public void showBottonDialog(){
        bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_detail_service_content_normal, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        ll_call_phone= (LinearLayout) bottomDialog.findViewById(R.id.ll_call_phone);
        ll_close_service= (LinearLayout) bottomDialog.findViewById(R.id.ll_close_service);
        ll_add_service_result= (LinearLayout) bottomDialog.findViewById(R.id.ll_add_service_result);
        ll_send_msg= (LinearLayout) bottomDialog.findViewById(R.id.ll_send_msg);
        ll_send_volume= (LinearLayout) bottomDialog.findViewById(R.id.ll_send_volume);
        ll_send_volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberServiceListBean bean=new MemberServiceListBean();
                bean.setMobile(vipGetOneBean.getMobile());
                bean.setVipId(vipGetOneBean.getVipId());
                bean.setGrade(vipGetOneBean.getCardName());
                bean.setName(vipGetOneBean.getFansName());
                bean.setGradeId(vipGetOneBean.getVipGradeId());
                bean.setBirthday(vipGetOneBean.getBirthday());
                bean.setMemberId(vipGetOneBean.getMemberId());
                selectMember.add(bean);
                bundle.putSerializable("MemberList", (Serializable)selectMember);
                Skip.mNextFroData(mActivity,SendVolumeActivity.class,bundle);
                bottomDialog.dismiss();
            }
        });
        ll_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {setSendDialog(Name,Phone);
                }
        });
        ll_add_service_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                bundle.putInt("Id",id);
                bundle.putInt("VipId",VipId);
                bundle.putString("Name",Name);
                bundle.putString("Phone",Phone);
                bundle.putString("sex",sex);
                bundle.putSerializable("MultipleServiceList", (Serializable)mData);
                Skip.mNextFroData(mActivity, MemberServiceResultActivity.class,bundle);
            }
        });
        ll_call_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                    CallUtils.call(Phone,mActivity);
                    //调用通话监听
                    TelephonyManager phoneManager = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
                    MemberPhoneListenUtils phoneListenUtils=new MemberPhoneListenUtils(mContext,mActivity,VipId,Name,Phone,sex,id,mData);
                    phoneManager.listen(phoneListenUtils, PhoneStateListener.LISTEN_CALL_STATE);
//                }
            }
        });
        ll_close_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            new MyAlertDialog(mContext).mInitShow("关闭任务", "确认关闭任务？",
                    "我知道", "点错了", new MyAlertDialog.ConfirmListener() {
                        @Override
                        public void onClick(Dialog dialog) {
                            bottomDialog.dismiss();
                            ColseServiceNet net=new ColseServiceNet(mContext);
                            net.setData(id);
                        }
                    }, null);
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }

    public void setSendDialog(final String name, final String phone){
        sendDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_msg, null);
        sendDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);
        tv_name= (TextView) sendDialog.findViewById(R.id.tv_name);
        tv_name.setText(name);
        tv_phone= (TextView) sendDialog.findViewById(R.id.tv_phone);
        tv_phone.setText(phone);
        ll_close_service= (LinearLayout) sendDialog.findViewById(R.id.ll_close_service);
        et_service_content= (EditText) sendDialog.findViewById(R.id.et_service_content);
        ll_service_send= (LinearLayout) sendDialog.findViewById(R.id.ll_service_send);
        ll_service_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAlertDialog(mContext).mInitShow("短信发送", "确认发送短信吗？",
                        "确认", "点错了", new MyAlertDialog.ConfirmListener() {
                            @Override
                            public void onClick(Dialog dialog) {
                                sendDialog.dismiss();
                                SendMsgNet net=new SendMsgNet(mContext);
                                net.setData(VipId,phone,et_service_content.getText().toString());
                            }
                        }, null);
            }
        });

        sendDialog.getWindow().setGravity(Gravity.BOTTOM);
        sendDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        sendDialog.setCanceledOnTouchOutside(true);
        sendDialog.show();
    }
}
