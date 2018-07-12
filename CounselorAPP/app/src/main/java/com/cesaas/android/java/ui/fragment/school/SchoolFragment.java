package com.cesaas.android.java.ui.fragment.school;

import android.view.View;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.global.App;

/**
 * Author FGB
 * Description 商学院
 * Created at 2018/6/29 14:18
 * Version 1.0
 */

public class SchoolFragment extends BaseFragment {

    private TextView tv_sort_icon,tv_level_icon,tv_filter_icon;

    /**
     * 单例
     */
    public static SchoolFragment newInstance() {
        SchoolFragment fragmentCommon = new SchoolFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_school_list;
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
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void fetchData() {

    }
}
