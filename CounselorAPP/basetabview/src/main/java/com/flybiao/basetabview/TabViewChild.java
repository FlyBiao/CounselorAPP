package com.flybiao.basetabview;

import android.support.v4.app.Fragment;

/**
 *
 */

public class TabViewChild {

    private Integer selectIcon;
    private Integer noSelectIcon;
    private String text;
    private Fragment fragment;

    public TabViewChild() {
    }

    public TabViewChild(Integer selectIcon, Integer noSelectIcon, String text, Fragment fragment) {
        this.selectIcon = selectIcon;
        this.noSelectIcon = noSelectIcon;
        this.text = text;
        this.fragment = fragment;
    }

    public TabViewChild(Integer selectIcon, Integer noSelectIcon, String text) {
        this.selectIcon = selectIcon;
        this.noSelectIcon = noSelectIcon;
        this.text = text;
    }

    public int getSelectIcon() {
        return selectIcon;
    }

    public int getNoSelectIcon() {
        return noSelectIcon;
    }

    public String getText() {
        return text;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setSelectIcon(int selectIcon) {
        this.selectIcon = selectIcon;
    }

    public void setNoSelectIcon(int noSelectIcon) {
        this.noSelectIcon = noSelectIcon;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
