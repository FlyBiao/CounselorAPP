package com.cesaas.android.counselor.order.member.adapter.volume;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.service.member.FaceListBean;
import com.cesaas.android.counselor.order.member.bean.service.volume.PushBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description t推送卷列表
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class PushListAdapter extends BaseQuickAdapter<PushBean, BaseViewHolder> {

    private List<PushBean> mData;
    private Activity mActivity;
    private Context mContext;


    public PushListAdapter(List<PushBean> mData, Activity activity, Context context) {
        super( R.layout.item_push_list,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final PushBean item) {
        helper.setText(R.id.tv_name,item.getVipName());
        helper.setText(R.id.tv_mobile,item.getMobile());
        helper.setText(R.id.tv_msg,item.getMessage());
        if(item.getStatus()==1){
            helper.setImageResource(R.id.iv_status,R.mipmap.check);
        }else{
            helper.setImageResource(R.id.iv_status,R.mipmap.close);
        }
    }
}
