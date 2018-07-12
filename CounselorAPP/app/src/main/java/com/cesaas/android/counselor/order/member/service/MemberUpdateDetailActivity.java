package com.cesaas.android.counselor.order.member.service;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.adapter.TabLayoutViewPagerReturnVistAdapter;
import com.cesaas.android.counselor.order.member.adapter.TabLayoutViewPagerUpdateDetailAdapter;
import com.cesaas.android.counselor.order.member.bean.ResultVipGetOneBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultChangeMemberBirthDayBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultChangeMemberMobileBean;
import com.cesaas.android.counselor.order.member.net.VipGetOneNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 会员信息更改详情
 */
public class MemberUpdateDetailActivity extends BaseTemplateActivity {

    private TextView tvTitle;
    private TextView tv_date,tv_counselor,tv_dot_circle,tv_point,tv_member_counselor,tv_member_name,tv_grade,tv_member_point,tv_create_date,tv_member_city,tv_member_shop,tv_shops;
    private LinearLayout llBack;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CircleImageView ivw_user_icon;

    private static int VipId;
    private VipGetOneNet net;

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_udpate_detail;
    }

    public void onEventMainThread(ResultChangeMemberMobileBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"提交成功，等待审核！");
            finish();
        }else{
            ToastFactory.getLongToast(mContext,"提交失败！"+msg.Message);
        }
    }

    public void onEventMainThread(ResultChangeMemberBirthDayBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"提交成功，等待审核！");
            finish();
        }else{
            ToastFactory.getLongToast(mContext,"提交失败！"+msg.Message);
        }
    }

    public void onEventMainThread(ResultVipGetOneBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && !"".equals(msg.TModel)){
                tv_member_city.setText(msg.TModel.getCity());
                tv_shops.setText(msg.TModel.getCity());
                tv_member_shop.setText(msg.TModel.getShopName());
                tv_member_name.setText(msg.TModel.getNickName());
                tv_grade.setText(msg.TModel.getCardName());
                tv_member_point.setText(msg.TModel.getPoint()+"");
                if(msg.TModel.getCardCreateTime()!=null && !"".equals(msg.TModel.getCardCreateTime())){
                    tv_create_date.setText(AbDateUtil.getDateYMDs(msg.TModel.getCardCreateTime()));
                }else{
                    tv_create_date.setText("暂无开卡日期");
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
            }

        }else{
            ToastFactory.getLongToast(mContext,"获取会员信息失败："+msg.Message);
        }
    }


    @Override
    public void initViews() {
        tv_shops=findView(R.id.tv_shops);
        tv_member_city=findView(R.id.tv_member_city);
        tv_member_shop=findView(R.id.tv_member_shop);
        tv_create_date=findView(R.id.tv_create_date);
        tv_member_point=findView(R.id.tv_member_point);
        tv_grade=findView(R.id.tv_grade);
        tv_member_name=findView(R.id.tv_member_name);
        tv_member_counselor=findView(R.id.tv_member_counselor);
        ivw_user_icon=findView(R.id.ivw_user_icon);
        tvTitle=findView(R.id.tv_base_title);
        tvTitle.setText("会员信息更改详情");
        llBack=findView(R.id.ll_base_title_back);
        tv_date=findView(R.id.tv_date);
        tv_date.setText(R.string.fa_calendar);
        tv_date.setTypeface(App.font);
        tv_counselor=findView(R.id.tv_counselor);
        tv_counselor.setText(R.string.fa_glass);
        tv_counselor.setTypeface(App.font);
        tv_dot_circle=findView(R.id.tv_dot_circle);
        tv_dot_circle.setText(R.string.fa_dot_circle);
        tv_dot_circle.setTypeface(App.font);
        tv_point=findView(R.id.tv_point);
        tv_point.setText(R.string.fa_dollar);
        tv_point.setTypeface(App.font);
        mTabLayout= (TabLayout) findViewById(R.id.tab_layout);
        mViewPager= (ViewPager) findViewById(R.id.view_pager);
    }

    @Override
    public void initListener() {
        llBack.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            VipId=bundle.getInt("VipId");
            net=new VipGetOneNet(mContext,1);
            net.setData(VipId+"");
        }
        PagerAdapter adapter= new TabLayoutViewPagerUpdateDetailAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void processClick(View v) {

        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
            break;
            default:
                break;
        }
    }

}
