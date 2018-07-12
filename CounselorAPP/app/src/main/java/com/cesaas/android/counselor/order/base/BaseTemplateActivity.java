package com.cesaas.android.counselor.order.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;

import com.cesaas.android.counselor.order.utils.ACache;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;

import java.util.ArrayList;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description Aactivity公共模板基类
 * Created 2017/4/6 13:45
 * Version 1.zero
 */
public abstract class BaseTemplateActivity extends FragmentActivity implements View.OnClickListener {

    /* Activity集合，便于管理 */
    public static ArrayList<Activity> activityList = new ArrayList<Activity>();
    private SparseArray<View> mViews;

    protected Activity mActivity;
    protected Context mContext;
    protected AbPrefsUtil prefs;
    protected  Bundle bundle;
    protected  static ACache mCache;

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initListener();

    public abstract void initData();

    public abstract void processClick(View v);

    public void onClick(View v) {
        processClick(v);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mViews = new SparseArray<>();
        setContentView(getLayoutId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        bundle=new Bundle();
        mActivity = this;
        mContext=this;
        mCache = ACache.get(this);
        activityList.add(this);
        prefs = AbPrefsUtil.getInstance();

        //通过EventBus订阅事件
        EventBus.getDefault().register(this);

        initViews();
        initListener();
        initData();
    }

    /**
     * 通过ID找到对应View
     * @param viewId
     * @param <E>
     * @return
     */
    public <E extends View> E findView(int viewId) {
        E view = (E) mViews.get(viewId);
        if (view == null) {
            view = (E) findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    /* 退出Activity方法，同时清除列表 */
    protected void onExit() {
        Skip.mBack(mActivity);
        if (activityList != null) {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.equals(this)) {
                    activityList.remove(i);
                }
            }
        }
        this.finish();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);//取消EventBus订阅
        finish();//销毁当前页面
        super.onDestroy();
    }
}