package com.cesaas.android.java.ui.fragment.school;

import android.view.View;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.global.App;

/**
 * Author FGB
 * Description 最近预览
 * Created at 2018/6/29 14:18
 * Version 1.0
 */

public class SchoolPreViewFragment extends BaseFragment {


    /**
     * 单例
     */
    public static SchoolPreViewFragment newInstance() {
        SchoolPreViewFragment fragmentCommon = new SchoolPreViewFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_school_preview;
    }

    @Override
    public void initViews() {
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
