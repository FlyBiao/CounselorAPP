package com.cesaas.android.counselor.order.member.adapter;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.label.bean.ResultGetVipTagBean;
import com.cesaas.android.counselor.order.member.bean.Tags;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 会员标签
 * Created at 2017/11/8 17:25
 * Version 1.0
 */

public class VipTagAdapter extends BaseQuickAdapter<Tags, BaseViewHolder> {

    private List<Tags> mData;

    public VipTagAdapter(List<Tags> mData) {
        super( R.layout.item_vip_tag,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, Tags item) {
        helper.setText(R.id.tv_tag_name,item.getTagName());

    }
}
