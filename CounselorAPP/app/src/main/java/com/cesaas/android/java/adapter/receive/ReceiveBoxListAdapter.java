package com.cesaas.android.java.adapter.receive;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.util.BottonDialogUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.bean.move.MoveDeliveryBoxListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 获取箱列表 /收货
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class ReceiveBoxListAdapter extends BaseQuickAdapter<MoveDeliveryBoxListBean, BaseViewHolder> {

    private String no;
    private long id;
    private int status;
    private List<MoveDeliveryBoxListBean> mData;

    private Context mContext;
    private  Activity mActivity;

    public ReceiveBoxListAdapter(List<MoveDeliveryBoxListBean> mData, long id,String no, Activity activity, Context ct,int status) {
        super( R.layout.item_receive_box_list,mData);
        this.mData=mData;
        this.no=no;
        this.id=id;
        this.mActivity=activity;
        this.mContext=ct;
        this.status=status;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MoveDeliveryBoxListBean item) {
        helper.setText(R.id.tv_sort_desc,R.string.fa_caret_down);
        helper.setTypeface(R.id.tv_sort_desc, App.font);
        helper.setText(R.id.tv_fly,R.string.fa_plane);
        helper.setTypeface(R.id.tv_fly, App.font);

        helper.setText(R.id.tv_boxNo,String.valueOf(item.getBoxNo()));
        helper.setText(R.id.tv_num,String.valueOf(item.getNum()));
        if(item.getExpressNo()!=null && !"".equals(item.getExpressNo())){
            helper.setText(R.id.tv_expressName,item.getExpressName()+":");
            helper.setText(R.id.tv_expressNo,item.getExpressNo());
        }

        if(item.getExpressRemark()!=null && !"".equals(item.getExpressRemark())){
            helper.setText(R.id.tv_remark,item.getExpressRemark());
        }

        switch (item.getStatus()){
            case 0:
                helper.setText(R.id.tv_status,"制单");
                helper.setBackgroundRes(R.id.tv_status,R.drawable.button_ellipse_orange_bg);
                break;
            case 10:
                helper.setText(R.id.tv_status,"通知捡货");
                helper.setBackgroundRes(R.id.tv_status,R.drawable.button_ellipse_orange_bg);
                break;
            case 15:
                helper.setText(R.id.tv_status,"开始捡货");
                helper.setBackgroundRes(R.id.tv_status,R.drawable.button_ellipse_orange_bg);
                break;
            case 20:
                helper.setText(R.id.tv_status,"捡货完成");
                helper.setBackgroundRes(R.id.tv_status,R.drawable.button_ellipse_orange_bg);
                break;
            case 30:
                helper.setText(R.id.tv_status,"已提交");
                helper.setBackgroundRes(R.id.tv_status,R.drawable.button_ellipse_orange_bg);
                break;
            case 40:
                helper.setText(R.id.tv_status,"已确认");
                helper.setBackgroundRes(R.id.tv_status,R.drawable.button_ellipse_huise_bg);
                break;
            case 50:
                helper.setText(R.id.tv_status,"已驳回");
                helper.setBackgroundRes(R.id.tv_status,R.drawable.button_ellipse_orange_bg);
                break;
            case 60:
                helper.setText(R.id.tv_status,"已停用");
                helper.setBackgroundRes(R.id.tv_status,R.drawable.button_ellipse_huise_bg);
                break;
        }

        helper.setOnClickListener(R.id.ll_sort_desc, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status==40){
                    ToastFactory.getLongToast(mContext,"该订单已确认");
                }
                else{
                    BottonDialogUtils.getReceiveBoxDialog(mContext,mActivity,item,id);
                }
            }
        });

    }
}
