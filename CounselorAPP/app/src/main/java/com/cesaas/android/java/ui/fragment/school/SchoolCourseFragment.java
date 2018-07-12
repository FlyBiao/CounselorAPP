package com.cesaas.android.java.ui.fragment.school;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.java.ui.activity.school.SchoolCourseDetailsActivity;

/**
 * Author FGB
 * Description 课程表
 * Created at 2018/6/29 14:18
 * Version 1.0
 */

public class SchoolCourseFragment extends BaseFragment {

    private TextView tv_sort_icon,tv_level_icon,tv_filter_icon;
    private LinearLayout ll_course_details;

    /**
     * 单例
     */
    public static SchoolCourseFragment newInstance() {
        SchoolCourseFragment fragmentCommon = new SchoolCourseFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_school_course;
    }

    @Override
    public void initViews() {
        tv_sort_icon=findView(R.id.tv_sort_icon);
        tv_sort_icon.setText(R.string.fa_caret_down);
        tv_sort_icon.setTypeface(App.font);
        tv_level_icon=findView(R.id.tv_level_icon);
        tv_level_icon.setText(R.string.fa_caret_down);
        tv_level_icon.setTypeface(App.font);
        tv_filter_icon=findView(R.id.tv_filter_icon);
        tv_filter_icon.setText(R.string.fa_caret_down);
        tv_filter_icon.setTypeface(App.font);
        ll_course_details=findView(R.id.ll_course_details);
    }

    @Override
    public void initListener() {
        ll_course_details.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.ll_course_details:
                Skip.mNext(getActivity(),SchoolCourseDetailsActivity.class);
                break;
        }
    }

    @Override
    public void fetchData() {

    }
}
