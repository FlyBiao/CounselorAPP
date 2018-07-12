package com.cesaas.android.java.ui.fragment.school;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/6/29 15:29
 * Version 1.0
 */

public class SchoolCourseDetailsFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        EventBus.getDefault().register(this);
        view = inflater.inflate(R.layout.fragment_school_course_details, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void fetchData() {

    }
}
