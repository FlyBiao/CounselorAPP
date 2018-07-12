package com.cesaas.android.counselor.order.custom.tablayout.bean;

import android.os.Bundle;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * Author FGB
 * Description
 * Created 2017/4/6 10:42
 * Version 1.zero
 */
public abstract class Fragment extends android.support.v4.app.Fragment {

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    protected BitmapUtils bitmapUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bitmapUtils = BitmapHelp.getBitmapUtils(getContext().getApplicationContext());
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(getContext()).scaleDown(3));
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }


}
