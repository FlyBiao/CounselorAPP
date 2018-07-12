package com.cesaas.android.counselor.order.boss.adapter.member;

import android.app.Activity;
import android.content.Context;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.bean.member.TaskTypeListBean;
import com.cesaas.android.counselor.order.boss.bean.member.TaskTypeShopListBean;
import com.cesaas.android.counselor.order.global.App;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 会员服务PACD统计(服务类别列表)
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class TaskTypeListAdapter extends BaseQuickAdapter<TaskTypeListBean, BaseViewHolder> {

    private List<TaskTypeListBean> mData;
    private Activity mActivity;
    private Context mContext;

    public TaskTypeListAdapter(List<TaskTypeListBean> mData, Activity activity, Context context) {
        super( R.layout.item_task_type_list,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final TaskTypeListBean item) {
        switch (item.getServiceType()){
            case 1:
                helper.setText(R.id.tv_task_type_name,"销售回访");
                break;
            case 2:
                helper.setText(R.id.tv_task_type_name,"生日祝福");
                break;
            case 3:
                helper.setText(R.id.tv_task_type_name,"节日安排");
                break;
            case 4:
                helper.setText(R.id.tv_task_type_name,"休眠激活");
                break;
            case 5:
                helper.setText(R.id.tv_task_type_name,"返修退换货");
                break;
            case 99:
                helper.setText(R.id.tv_task_type_name,"定制会员");
                break;
            default:
                helper.setText(R.id.tv_task_type_name,"全部");
                break;
        }
        helper.setText(R.id.tv_shop_sum,String.valueOf(item.getShopNums()));
        helper.setText(R.id.tv_finish_server_sum,String.valueOf(item.getServerFinishNums()));
        helper.setText(R.id.tv_server_sum,String.valueOf(item.getServerNums()));
        helper.setText(R.id.tv_type,R.string.fa_chrome);
        helper.setTypeface(R.id.tv_type, App.font);
    }
}
