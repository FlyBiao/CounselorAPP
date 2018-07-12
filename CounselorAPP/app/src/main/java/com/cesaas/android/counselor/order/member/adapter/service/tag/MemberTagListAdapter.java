package com.cesaas.android.counselor.order.member.adapter.service.tag;

import android.app.Activity;
import android.content.Context;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.tag.adapter.TagAdapter;
import com.cesaas.android.counselor.order.label.bean.CategoryTagBean;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.member.bean.Tags;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员标签
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberTagListAdapter extends BaseQuickAdapter<CategoryTagBean, BaseViewHolder> {

    private FlowTagLayout tag_flow_layout;
    public static TagAdapter<GetTagListBean> mBaseTagAdapter;
    private List<CategoryTagBean> mData;
    private List<GetTagListBean> selectedTagList=new ArrayList<>();
    private Set<GetTagListBean> sets=new HashSet<>();
    private List<Integer> exisTags;
    private Activity mActivity;
    private Context mContext;

    public MemberTagListAdapter(List<CategoryTagBean> mData,List<Integer> exisTags, Activity activity, Context context) {
        super( R.layout.item_tag,mData);
        this.mData=mData;
        this.exisTags=exisTags;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CategoryTagBean item) {
        helper.setText(R.id.tv_tag_category_name,item.getCategoryName());
        tag_flow_layout=helper.getView(R.id.tag_flow_layout);

        mBaseTagAdapter = new TagAdapter<>(mContext);
        tag_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        tag_flow_layout.setAdapter(mBaseTagAdapter);
        mBaseTagAdapter.onlyAddAll(item.getTagList,exisTags);
        tag_flow_layout.setOnTagSelectListener(new com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {

                if (selectedList != null && selectedList.size() > 0) {
                    for (int i : selectedList) {
                        GetTagListBean tagBean=new GetTagListBean();
                        tagBean.setCategoryId(item.getCategoryId());
                        tagBean.setCategoryName(item.getCategoryName());
                        tagBean.setTagId(item.getTagList.get(i).getTagId());
                        tagBean.setTagName(item.getTagList.get(i).getTagName());
                        sets.add(tagBean);
                    }
                    selectedTagList=new ArrayList<>();
                   for (GetTagListBean tag:sets){
                       selectedTagList.add(tag);
                   }

                    EventBus.getDefault().post(selectedTagList);
                } else {
                    //没有选标签
                }
            }
        });
    }
}
