package com.cesaas.android.counselor.order.member.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.tag.adapter.TagMemberLabelAdapter;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.member.bean.ResultVipGetOneLabelBean;
import com.cesaas.android.counselor.order.member.net.VipGetOneNet;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailActivity;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailsActivity;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员标签
 * Created at 2018/2/27 17:33
 * Version 1.record
 */

public class MemberLabelsFragment extends Fragment {

    private TextView tv_member_lable,tv_dynamic_lable;
    private FlowTagLayout  member_lable_flow_layout,member_dynamic_lable_flow_layout;
    private View view;

    public static TagMemberLabelAdapter<GetTagListBean> yearTagAdapter;
    private List<GetTagListBean> mData=new ArrayList<>();
    public static TagMemberLabelAdapter<GetTagListBean> yearTagAdapters;
    private List<GetTagListBean> mDatas=new ArrayList<>();

    private VipGetOneNet net;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        view = inflater.inflate(R.layout.fragment_member_label, container, false);
        member_lable_flow_layout= (FlowTagLayout) view.findViewById(R.id.member_lable_flow_layout);
        member_lable_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        member_dynamic_lable_flow_layout= (FlowTagLayout) view.findViewById(R.id.member_dynamic_lable_flow_layout);
        member_dynamic_lable_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        tv_member_lable= (TextView) view.findViewById(R.id.tv_member_lable);
        tv_member_lable.setText(R.string.fa_tag);
        tv_member_lable.setTypeface(App.font);
        tv_dynamic_lable= (TextView) view.findViewById(R.id.tv_dynamic_lable);
        tv_dynamic_lable.setText(R.string.fa_tags);
        tv_dynamic_lable.setTypeface(App.font);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        net=new VipGetOneNet(getContext(),2);
        net.setData(MemberReturnVisitDetailActivity.getVipId()+"");
    }

    public void onEventMainThread(ResultVipGetOneLabelBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.getTags().size()!=0){
                try{
                    mData=new ArrayList<>();
                    mDatas=new ArrayList<>();
                    for (int i=0;i<msg.TModel.getTags().size();i++){
                        if(msg.TModel.getTags().get(i).getTagType()==0){//手动标签
                            GetTagListBean t=new GetTagListBean();
                            t.setTagName(msg.TModel.getTags().get(i).getCategoryName()+":"+msg.TModel.getTags().get(i).getTagName());
                            t.setTagId(msg.TModel.getTags().get(i).getTagId());
                            t.setCategoryId(msg.TModel.getTags().get(i).getCategoryId());
                            mData.add(t);
                        }else if(msg.TModel.getTags().get(i).getTagType()==1){//动态标签
                            GetTagListBean t=new GetTagListBean();
                            t.setTagName(msg.TModel.getTags().get(i).getCategoryName()+":"+msg.TModel.getTags().get(i).getTagName());
                            t.setTagId(msg.TModel.getTags().get(i).getTagId());
                            t.setCategoryId(msg.TModel.getTags().get(i).getCategoryId());
                            mDatas.add(t);
                        }
                    }

                    yearTagAdapters = new TagMemberLabelAdapter<>(getContext());
                    member_dynamic_lable_flow_layout.setAdapter(yearTagAdapters);
                    yearTagAdapters.onlyAddAll(mDatas);

                    yearTagAdapter = new TagMemberLabelAdapter<>(getContext());
                    member_lable_flow_layout.setAdapter(yearTagAdapter);
                    yearTagAdapter.onlyAddAll(mData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
            }
        }else{
            ToastFactory.getLongToast(getContext(),"Msg:"+msg.Message);
        }
    }


    @Override
    public void fetchData() {

    }
}
