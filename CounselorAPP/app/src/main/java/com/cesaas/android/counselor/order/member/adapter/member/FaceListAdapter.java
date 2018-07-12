package com.cesaas.android.counselor.order.member.adapter.member;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.service.member.FaceListBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 会员-all
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class FaceListAdapter extends BaseQuickAdapter<FaceListBean, BaseViewHolder> {

    private List<FaceListBean> mData;
    private Activity mActivity;
    private Context mContext;


    public FaceListAdapter(List<FaceListBean> mData, Activity activity, Context context) {
        super( R.layout.item_face,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final FaceListBean item) {

        if(item.getImageUrl()!=null && !"".equals(item.getImageUrl()) && !"NULL".equals(item.getImageUrl())){
            // 加载网络图片
            Glide.with(mContext).load(item.getImageUrl()).crossFade().into((ImageView) helper.getView(R.id.img));
        }else{
            helper.setImageResource(R.id.img,R.mipmap.ic_launcher);
        }
        if(item.getImageTime()!=null && !"".equals(item.getImageTime())){
            helper.setText(R.id.date, AbDateUtil.formats(item.getImageTime()));
        }else{
            helper.setText(R.id.date,"暂无时间");
        }
    }
}
