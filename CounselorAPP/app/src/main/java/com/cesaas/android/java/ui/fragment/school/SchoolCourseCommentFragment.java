package com.cesaas.android.java.ui.fragment.school;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.counselor.order.global.App;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/6/29 15:29
 * Version 1.0
 */

public class SchoolCourseCommentFragment extends Fragment {

    private View view;
    private TextView tv_score1,tv_score2,tv_score3,tv_score4,tv_score5,tv_points_icon;
    private TextView tv_review_score1,tv_review_score2,tv_review_score3,tv_review_score4,tv_review_score5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        EventBus.getDefault().register(this);
        view = inflater.inflate(R.layout.fragment_school_course_comment, container, false);

        tv_points_icon= (TextView) view.findViewById(R.id.tv_points_icon);
        tv_points_icon.setText(R.string.fa_file_text_o);
        tv_points_icon.setTypeface(App.font);

        tv_score1= (TextView) view.findViewById(R.id.tv_score1);
        tv_score1.setText(R.string.fa_star_o);
        tv_score1.setTypeface(App.font);
        tv_score2= (TextView) view.findViewById(R.id.tv_score2);
        tv_score2.setText(R.string.fa_star_o);
        tv_score2.setTypeface(App.font);
        tv_score3= (TextView) view.findViewById(R.id.tv_score3);
        tv_score3.setText(R.string.fa_star_o);
        tv_score3.setTypeface(App.font);
        tv_score4= (TextView) view.findViewById(R.id.tv_score4);
        tv_score4.setText(R.string.fa_star_o);
        tv_score4.setTypeface(App.font);
        tv_score5= (TextView) view.findViewById(R.id.tv_score5);
        tv_score5.setText(R.string.fa_star_o);
        tv_score5.setTypeface(App.font);

        tv_review_score1= (TextView) view.findViewById(R.id.tv_review_score1);
        tv_review_score1.setText(R.string.fa_star);
        tv_review_score1.setTypeface(App.font);
        tv_review_score2= (TextView) view.findViewById(R.id.tv_review_score2);
        tv_review_score2.setText(R.string.fa_star);
        tv_review_score2.setTypeface(App.font);
        tv_review_score3= (TextView) view.findViewById(R.id.tv_review_score3);
        tv_review_score3.setText(R.string.fa_star);
        tv_review_score3.setTypeface(App.font);
        tv_review_score4= (TextView) view.findViewById(R.id.tv_review_score4);
        tv_review_score4.setText(R.string.fa_star);
        tv_review_score4.setTypeface(App.font);
        tv_review_score5= (TextView) view.findViewById(R.id.tv_review_score5);
        tv_review_score5.setText(R.string.fa_star);
        tv_review_score5.setTypeface(App.font);

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
