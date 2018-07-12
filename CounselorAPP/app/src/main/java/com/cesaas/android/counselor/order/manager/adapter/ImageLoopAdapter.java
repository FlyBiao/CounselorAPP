package com.cesaas.android.counselor.order.manager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/9/15 14:39
 * Version 1.0
 */

public class ImageLoopAdapter extends LoopPagerAdapter {

    private List<String> list;
    private Context ct;

    public ImageLoopAdapter(RollPagerView viewPager,List<String> list,Context ct) {
        super(viewPager);
        this.list=list;
        this.ct=ct;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Glide.with(ct).load(list.get(position)).into(view);
        return view;
    }

    @Override
    public int getRealCount() {
        return list.size();
    }
}
