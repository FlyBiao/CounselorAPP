package com.cesaas.android.java.ui.activity.school;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.adapter.TabLayoutViewPagerReturnVistAdapter;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.java.adapter.school.TabLayoutViewPagerSchoolAdapter;

/**
 * Author FGB
 * Description 课程详情
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class SchoolCourseDetailsActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle;
    private LinearLayout llBack;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_course_details);

        initView();
        initData();
    }

    private void initView() {
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("课程详情");
        mTabLayout= (TabLayout) findViewById(R.id.tab_layout);
        mViewPager= (ViewPager) findViewById(R.id.view_pager);
    }

    public void initData() {
        PagerAdapter adapter= new TabLayoutViewPagerSchoolAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;

        }
    }

}
