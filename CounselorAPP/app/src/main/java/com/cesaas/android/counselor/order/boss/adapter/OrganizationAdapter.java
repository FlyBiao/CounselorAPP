package com.cesaas.android.counselor.order.boss.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.bean.ShopListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/8/20 17:48
 * Version 1.0
 */

public class OrganizationAdapter extends BaseQuickAdapter<ShopListBean, BaseViewHolder> {

    private List<ShopListBean> mData;

    public OrganizationAdapter(List<ShopListBean> mData){
        super( R.layout.item_diaolg,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShopListBean item) {
        for (int i=0;i<mData.size();i++){
            Log.i("test",mData.get(i).getOrganizationName());
        }
        if(item.getOrganizationName()!=null && !"".equals(item.getOrganizationName())){
            helper.setText(R.id.tv_dialog_title, item.getOrganizationName());
        }
    }
}
