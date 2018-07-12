package com.cesaas.android.counselor.order.power.adapter;

import android.content.Context;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.power.bean.MenuDataBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/1/31 10:47
 * Version 1.0
 */

public class InventoryMenuPowerAdapter extends BaseQuickAdapter<MenuDataBean, BaseViewHolder> {

    private List<MenuDataBean> mData;
    private Context ct;
    private TextView iv_menu;

    public InventoryMenuPowerAdapter(List<MenuDataBean> mData,Context c) {
        super( R.layout.item_menu,mData);
        this.mData=mData;
        this.ct=c;
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuDataBean item) {
        helper.setText(R.id.tv_menu_name,item.getMenuName());
        helper.setText(R.id.iv_menu,item.getMenuImage());
        helper.setTypeface(R.id.iv_menu,App.font);
        iv_menu=helper.getView(R.id.iv_menu);
        switch (item.getStatus()){
            case 0:
                iv_menu.setBackgroundResource(R.drawable.shape_menushop_cheng_bg);
                iv_menu.setPadding(45,45,45,45);
            break;
            case 1:
                iv_menu.setBackgroundResource(R.drawable.shape_menushop_qianzhise_bg);
                iv_menu.setPadding(45,45,45,45);
                break;
            case 2:
                iv_menu.setBackgroundResource(R.drawable.shape_menushop_chengse_bg);
                iv_menu.setPadding(45,45,45,45);
                break;
            case 3:
                iv_menu.setBackgroundResource(R.drawable.shape_menushop_chengse_bg);
                iv_menu.setPadding(45,45,45,45);
                break;
            case 4:
                iv_menu.setBackgroundResource(R.drawable.shape_menushop_gese_bg);
                iv_menu.setPadding(45,45,45,45);
                break;
            case 5:
                iv_menu.setBackgroundResource(R.drawable.shape_menushop_zhise_bg);
                iv_menu.setPadding(45,45,45,45);
                break;
            case 6:
                iv_menu.setBackgroundResource(R.drawable.shape_menushop_gese_bg);
                iv_menu.setPadding(45,45,45,45);
                break;
            case 7:
                iv_menu.setBackgroundResource(R.drawable.shape_menushop_chengse_bg);
                iv_menu.setPadding(45,45,45,45);
                break;
            case 8:
                iv_menu.setBackgroundResource(R.drawable.shape_menushop_fense_bg);
                iv_menu.setPadding(45,45,45,45);
                break;
        }
    }
}
