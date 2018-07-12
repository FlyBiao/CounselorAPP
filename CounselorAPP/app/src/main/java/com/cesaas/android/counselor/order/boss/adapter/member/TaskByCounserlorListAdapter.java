package com.cesaas.android.counselor.order.boss.adapter.member;

import android.app.Activity;
import android.content.Context;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.bean.member.ShopSalesListBean;
import com.cesaas.android.counselor.order.boss.bean.member.TaskByCounserlorListBean;
import com.cesaas.android.counselor.order.boss.bean.member.TaskByCounserlorListsBean;
import com.cesaas.android.counselor.order.boss.bean.member.TaskTypeShopListBean;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 会员服务PACD统计(按顾问服务统计分析报表)
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class TaskByCounserlorListAdapter extends BaseQuickAdapter<TaskByCounserlorListsBean, BaseViewHolder> {

    private List<TaskByCounserlorListsBean> mData;
    private Activity mActivity;
    private Context mContext;

    public TaskByCounserlorListAdapter(List<TaskByCounserlorListsBean> mData,Activity activity, Context context) {
        super( R.layout.item_task_by_counserlor,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final TaskByCounserlorListsBean item) {
        helper.setText(R.id.tv_task_sum,String.valueOf(item.getServerNums()));
        helper.setText(R.id.tv_finish_service_sum,String.valueOf(item.getServerFinishNums()));
        helper.setText(R.id.tv_go_shop_sum,String.valueOf(item.getGoShopNums()));
        helper.setText(R.id.tv_buill_sum,String.valueOf(item.getBillNums()));
        helper.setText(R.id.tv_TaskSums,String.valueOf(item.getTaskSums()));
        helper.setText(R.id.tv_member_name,item.getName());
        helper.setText(R.id.tv_member_sum,"会员数"+item.getFansCount());

        if(item.getServerNums()!=0){
            double finish=Double.parseDouble(item.getServerFinishNums()+"")/Double.parseDouble(item.getServerNums()+"")*100;
            helper.setText(R.id.tv_finish_service_progress, DecimalFormatUtils.decimalToFormats(finish)+"%");

            double goShop=Double.parseDouble(item.getGoShopNums()+"")/Double.parseDouble(item.getServerNums()+"")*100;
            helper.setText(R.id.tv_go_shop_progress,DecimalFormatUtils.decimalToFormats(goShop)+"%");

            double buill=Double.parseDouble(item.getBillNums()+"")/Double.parseDouble(item.getServerNums()+"")*100;
            helper.setText(R.id.tv_buill_progress,DecimalFormatUtils.decimalToFormats(buill)+"%");
        }
    }
}
