package com.cesaas.android.counselor.order.base;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.counselor.order.utils.ACache;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 公共Fragment基类
 * Created 2017/4/6 16:44
 * Version 1.zero
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private boolean isVisible = false;
    private boolean isInitView = false;
    private boolean isFirstLoad = true;

    protected Bundle bundle;
    protected AbPrefsUtil prefs;
    protected  static ACache mCache;

    private View convertView;
    private SparseArray<View> mViews;

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initListener();

    public abstract void initData();

    public abstract void processClick(View v);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViews = new SparseArray<>();
        convertView = inflater.inflate(getLayoutId(), container, false);

        mCache = ACache.get(getContext());
        bundle=new Bundle();
        prefs = AbPrefsUtil.getInstance();


        initViews();

        isInitView = true;
        lazyLoad();
        return convertView;
    }


    @Override
    public void onClick(View v) {
        processClick(v);
    }

    public <E extends View> E findView(int viewId) {
        if (convertView != null) {
            E view = (E) mViews.get(viewId);
            if (view == null) {
                view = (E) convertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }
        return null;
    }

    private void lazyLoad() {
//        if (!isFirstLoad || !isVisible || !isInitView) {
//            //不加载数据
//            return;
//        }
        //加载数据
        initListener();
        initData();

        isFirstLoad = false;

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
