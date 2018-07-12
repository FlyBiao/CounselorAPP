package com.cesaas.android.counselor.order.member.adapter;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.MemberServiceBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jauker.widget.BadgeView;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberServiceAdapter extends BaseQuickAdapter<MemberServiceBean, BaseViewHolder> {

    private List<MemberServiceBean> mData;

    public MemberServiceAdapter(List<MemberServiceBean> mData) {
        super( R.layout.item_member_service,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberServiceBean item) {
        helper.setText(R.id.tv_service_title,item.getTitle());
        helper.setText(R.id.tv_service_desc,item.getContext());
        helper.setText(R.id.tv_service_count,item.getSum()+"");
    }
}
