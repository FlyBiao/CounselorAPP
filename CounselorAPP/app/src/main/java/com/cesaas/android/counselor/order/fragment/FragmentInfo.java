package com.cesaas.android.counselor.order.fragment;

/**
 * Author FGB
 * Description
 * Created 2017/4/2 zero:58
 * Version 1.zero
 */
public class FragmentInfo {

    private String title;
    private Class fragment;

    public FragmentInfo(String title, Class fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
