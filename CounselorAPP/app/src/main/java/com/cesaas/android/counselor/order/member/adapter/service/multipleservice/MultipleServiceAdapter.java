package com.cesaas.android.counselor.order.member.adapter.service.multipleservice;

import android.app.Activity;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.bean.service.apply.MemberApplyListBean;
import com.cesaas.android.counselor.order.member.bean.service.multipleservice.MultipleServiceBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/4/25 10:11
 * Version 1.0
 */

public class MultipleServiceAdapter extends BaseQuickAdapter<MultipleServiceBean, BaseViewHolder> {

    private Activity mActivity;
    private List<MultipleServiceBean> mData;

    public MultipleServiceAdapter(List<MultipleServiceBean> mData,Activity activity) {
        super( R.layout.item_multip_srvice,mData);
        this.mData=mData;
        this.mActivity=activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MultipleServiceBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_desc,item.getTaskContent());
        if(item.getRemark()!=null && !"".equals(item.getRemark())){
            helper.setText(R.id.tv_remark,item.getRemark());
        }else{
            helper.setText(R.id.tv_remark,"暂无备注");
        }
        if(item.getCounselorName()!=null && !"".equals(item.getCounselorName())){
            helper.setText(R.id.tv_counselor_name,item.getCounselorName());
        }else{
            helper.setText(R.id.tv_counselor_name,"未分配导购");
        }

        helper.setText(R.id.tv_remark_txt,R.string.fa_file_text_o);
        helper.setTypeface(R.id.tv_remark_txt, App.font);
        helper.setText(R.id.tv_circle_down,R.string.fa_chevron_circle_down);
        helper.setTypeface(R.id.tv_circle_down, App.font);

        if(item.getTaskDate()!=null){
            String str=item.getTaskDate();
            String dateType=str.substring(4,5);
            if(dateType.equals("/")){
                helper.setText(R.id.tv_day,AbDateUtil.formatDateStr2Desc(item.getTaskDate(),"yyyy/MM/dd HH:mm:ss"));
            }else{
                helper.setText(R.id.tv_day,AbDateUtil.formatDateStr2Desc(item.getTaskDate(),"yyyy-MM-dd HH:mm:ss"));
            }
            helper.setText(R.id.tv_time,AbDateUtil.getDateYMDs(item.getTaskDate()));
        }else{
            helper.setText(R.id.tv_time,"暂无日期");
            helper.setText(R.id.tv_day,"");
        }

        switch (item.getType()){
            case 10:
                helper.setText(R.id.tv_status,"待处理");
                break;
            case 20:
                helper.setText(R.id.tv_status,"已完成");
                break;
            case 30:
                helper.setText(R.id.tv_status,"已关闭");
                break;
        }
    }
}
