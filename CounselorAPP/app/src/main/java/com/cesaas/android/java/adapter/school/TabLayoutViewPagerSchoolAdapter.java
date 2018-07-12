package com.cesaas.android.java.adapter.school;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cesaas.android.counselor.order.fragment.FragmentInfo;
import com.cesaas.android.counselor.order.member.fragment.MemberLabelFragment;
import com.cesaas.android.counselor.order.member.fragment.MemberOrderView;
import com.cesaas.android.counselor.order.member.fragment.MemberServiceRecordFragment;
import com.cesaas.android.java.ui.fragment.school.SchoolCourseCommentFragment;
import com.cesaas.android.java.ui.fragment.school.SchoolCourseDetailsFragment;
import com.cesaas.android.java.ui.fragment.school.SchoolCourseDirectoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description TabLayoutViewPagerAdapter
 * Created 2017/4/2 zero:41
 * Version 1.zero
 */
public class TabLayoutViewPagerSchoolAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mFragments=new ArrayList<>();

    public TabLayoutViewPagerSchoolAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments(){
        mFragments.add(new FragmentInfo("详情",SchoolCourseDetailsFragment.class));
        mFragments.add(new FragmentInfo("目录",SchoolCourseDirectoryFragment.class));
        mFragments.add(new FragmentInfo("评价",SchoolCourseCommentFragment.class));
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return (Fragment) mFragments.get(position).getFragment().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }
}
