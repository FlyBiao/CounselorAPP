package com.cesaas.android.counselor.order.boss.adapter.member;

import android.app.Activity;
import android.content.Context;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.bean.member.MemberServiceCategory;
import com.cesaas.android.counselor.order.boss.bean.member.ShopSalesListBean;
import com.cesaas.android.counselor.order.boss.bean.member.TaskTypeShopDataBean;
import com.cesaas.android.counselor.order.boss.bean.member.TaskTypeShopListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 会员-all
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class TaskTypeShopListAdapter extends BaseQuickAdapter<TaskTypeShopDataBean, BaseViewHolder> {

    private List<TaskTypeShopDataBean> mData;
    private List<TaskTypeShopDataBean> shopSalesListBeen;
    private Activity mActivity;
    private Context mContext;

    public TaskTypeShopListAdapter(List<TaskTypeShopDataBean> mData,List<TaskTypeShopDataBean> shopSalesListBeen, Activity activity, Context context) {
        super( R.layout.item_task_type_shop,mData);
        this.mData=mData;
        this.shopSalesListBeen=shopSalesListBeen;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final TaskTypeShopDataBean item) {
        helper.setText(R.id.tv_org_name,item.getOrgNames());
        helper.setText(R.id.tv_shop_name,item.getShopNames());
        helper.setText(R.id.tv_service_finish_sum,String.valueOf(item.getServerFinishNumss()));
        helper.setText(R.id.tv_service_sum,String.valueOf(item.getServerNumss()));
        helper.setText(R.id.tv_go_shop,String.valueOf(item.getGoShopNumss()));
        helper.setText(R.id.tv_shop_sums,"￥"+item.getShopSumss());

        if(item.getServerNumss()!=0){
            double prs=Double.parseDouble(item.getServerFinishNumss()+"")/Double.parseDouble(item.getServerNumss()+"")*100;
            helper.setProgress(R.id.pb_service,(int)prs);
        }
    }
}
