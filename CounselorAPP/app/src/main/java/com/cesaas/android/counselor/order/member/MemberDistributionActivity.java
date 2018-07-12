package com.cesaas.android.counselor.order.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;

import com.cesaas.android.counselor.order.member.adapter.TabLayoutViewPagerAdapter;
import com.cesaas.android.counselor.order.member.bean.BindVipIdBean;

import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员分配
 */
public class MemberDistributionActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle,tvBaseRight;

    TabLayout mTabLayout;
    ViewPager mViewPager;

    private int resultCode=200;
    List<BindVipIdBean> vips=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_distribution);
        initViews();
        initData();
    }

    public void initViews() {
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("会员分配");
        tvBaseRight= (TextView) findViewById(R.id.tv_base_title_right);
        tvBaseRight.setText("分配");
        tvBaseRight.setVisibility(View.VISIBLE);
        tvBaseRight.setOnClickListener(this);

        mTabLayout= (TabLayout) findViewById(R.id.tab_layout);
        mViewPager= (ViewPager) findViewById(R.id.view_pager);

    }



    public void onEventMainThread(List<BindVipIdBean> selectBindVipIdBeen){
        vips=new ArrayList<>();
        vips=selectBindVipIdBeen;
    }


    public void initData() {
        try{
            PagerAdapter adapter= new TabLayoutViewPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(adapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:
                if(vips.size()!=0){
                    Intent mIntent = new Intent(mContext, CounselorListActivity.class);
                    mIntent.putExtra("selectList",(Serializable)vips);
                    Skip.mSelActivityResult(mActivity,resultCode,mIntent);
                }else{
                    ToastFactory.getLongToast(mContext,"请选择公共池会员进行分配！");
                }
                break;
        }
    }
}
