package com.cesaas.android.counselor.order.member.service;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.bean.ResultSetRemarkBean;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.adapter.TabLayoutViewPagerReturnVistAdapter;
import com.cesaas.android.counselor.order.member.bean.ResultVipGetOneBean;
import com.cesaas.android.counselor.order.member.bean.ResultVipOrderStatisticBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.net.VipGetOneNet;
import com.cesaas.android.counselor.order.member.net.VipOrderStatisticNet;
import com.cesaas.android.counselor.order.member.net.service.SetMemberRemarkNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.CalculateDateUtil;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员服务
 */
public class MemberReturnVisitDetailsActivity extends BaseTemplateActivity {

    private TextView tvTitle,tv_shop_name,tv_member_consume_amount,tv_last_buy,tv_ym,tv_quantity,tv_payment,tv_order_price,tv_member_remark,tv_edit_remark;
    private TextView tv_birthday,tv_database,tv_mobile,tv_cart,tv_certificate,tv_glass,tv_city,tv_grade,tv_member_name,tv_member_birthday,tv_birthday_day,tv_member_city,tv_member_province,tv_member_counselor,tv_member_mobile;
    private CircleImageView ivw_user_icon;
    private LinearLayout llBack,ll_edit_remark;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private int id;
    private int VipId;
    public static int VipIds;
    private String Name;
    private String Phone;
    private String Date;
    private String Desc;
    private String Remark;
    private String Title;
    private int Status;
    private String sex;

    private int month;
    private int year;

    private VipGetOneNet net;
    private VipOrderStatisticNet statisticNet;

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_return_visit_detailss;
    }

    public void onEventMainThread(ResultVipGetOneBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null){
                sex=msg.TModel.getSex();
                Phone=msg.TModel.getMobile();
                VipIds=msg.TModel.getVipId();
                tv_grade.setText(msg.TModel.getCardName());
                try{
                    tv_shop_name.setText(msg.TModel.getShopName());
                    tv_member_city.setText(msg.TModel.getCity());
                    tv_member_consume_amount.setText(msg.TModel.getPoint()+"");
                    tv_member_mobile.setText(msg.TModel.getMobile());
                    if(msg.TModel.getLastBuy()!=null && !"".equals(msg.TModel.getLastBuy())){
                        tv_last_buy.setText(AbDateUtil.formats(msg.TModel.getLastBuy()));
                    }else{
                        tv_last_buy.setText("暂无购买");
                    }
                    try {
                        month=CalculateDateUtil.monthsBetween(msg.TModel.getCardCreateTime(),AbDateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                        year=CalculateDateUtil.yearsBetween(msg.TModel.getCardCreateTime(),AbDateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                        tv_ym.setText("会员"+year+"年"+month+"月");
                    } catch (ParseException e) {
                        e.printStackTrace();
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
                    if(msg.TModel.getRemark()!=null){
                        tv_member_remark.setText(msg.TModel.getRemark());
                    }else{
                        tv_member_remark.setText("暂无备注");
                    }

                    PagerAdapter adapter= new TabLayoutViewPagerReturnVistAdapter(getSupportFragmentManager());
                    mViewPager.setAdapter(adapter);
                    mTabLayout.setupWithViewPager(mViewPager);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取会员数据失败："+msg.Message);
      }
    }

    public void onEventMainThread(ResultVipOrderStatisticBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null){
                tv_quantity.setText(msg.TModel.getOrderQuantity()+"");
                tv_payment.setText("￥"+msg.TModel.getPayment());
                double orderPrice=msg.TModel.getPayment()/msg.TModel.getOrderQuantity();
                tv_order_price.setText("￥"+ DecimalFormatUtils.decimalFormatRound(orderPrice));
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取会员数据失败："+msg.Message);
        }
    }

    public void onEventMainThread(ResultSetRemarkBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"添加备注成功!");
            net=new VipGetOneNet(mContext,1);
            net.setData(VipId+"");
        }else{
            ToastFactory.getLongToast(mContext,"添加备注失败"+msg.Message);
        }
    }

    @Override
    public void initViews() {
        ll_edit_remark=findView(R.id.ll_edit_remark);
        tv_edit_remark=findView(R.id.tv_edit_remark);
        tv_edit_remark.setText(R.string.fa_pencil);
        tv_edit_remark.setTypeface(App.font);
        tv_member_remark=findView(R.id.tv_member_remark);
        tv_birthday_day=findView(R.id.tv_birthday_day);
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
        tvTitle.setText("会员详情");
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
    }

    @Override
    public void initListener() {
        try{
            Bundle bundle=getIntent().getExtras();
            id=bundle.getInt("Id");
            VipId=bundle.getInt("VipId");
//            VipIds=VipId;
            Name=bundle.getString("Name");
            Phone=bundle.getString("Phone");
            Date=bundle.getString("Date");
            Desc=bundle.getString("Desc");
            Remark=bundle.getString("Remark");
            Title=bundle.getString("Title");
            Status=bundle.getInt("Status");

            llBack.setOnClickListener(this);
            ll_edit_remark.setOnClickListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int getVipId(){
        int id=VipIds;
        return id;
    }

    @Override
    public void initData() {
        net=new VipGetOneNet(mContext,1);
        net.setData(VipId+"");
        statisticNet=new VipOrderStatisticNet(mContext);
        statisticNet.setData(VipId+"");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
            break;
            case R.id.ll_edit_remark:
                setMemberRemarkDialog(mContext,VipId);
                break;

            default:
                break;
        }
    }

    private  Dialog bottomDialog;
    public  View dialogContentView;
    public  EditText et_service_remark;
    private LinearLayout ll_service_remark;
    public  TextView tv_remark;
    public  void setMemberRemarkDialog(final Context mContext, final int vipId){
        bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_remark, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        tv_remark= (TextView) bottomDialog.findViewById(R.id.tv_remark);
        tv_remark.setText("添加会员备注");
        et_service_remark= (EditText) bottomDialog.findViewById(R.id.et_service_remark);
        ll_service_remark= (LinearLayout) bottomDialog.findViewById(R.id.ll_service_remark);
        ll_service_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(et_service_remark.getText().toString())){
                    bottomDialog.dismiss();
                    SetMemberRemarkNet remarkNet=new SetMemberRemarkNet(mContext);
                    remarkNet.setData(et_service_remark.getText().toString(),vipId);
                }
                else{
                    ToastFactory.getLongToast(mContext,"请输入备注内容！");
                }
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }
}
