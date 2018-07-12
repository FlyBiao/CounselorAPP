package com.cesaas.android.counselor.order.member.adapter.service.tag;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.tag.adapter.TagAdapter;
import com.cesaas.android.counselor.order.label.bean.CategoryTagBean;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员标签[NEW]
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberNewTagListAdapter extends BaseQuickAdapter<CategoryTagBean, BaseViewHolder> implements TagSelectAdapter.OnItemClickListener{

    private TagSelectAdapter tagSelectAdapter;
    private RecyclerView recyclerview;
    private List<CategoryTagBean> mData;
    private List<Integer> exisTags;
    private Activity mActivity;
    private Context mContext;

    private List<GetTagListBean> mSelectTag=new ArrayList<>();
    private List<GetTagListBean> mExisSelectTags;

    private boolean isSelectAll = false;
    private boolean editorStatus = true;
    private int index = 0;

    public MemberNewTagListAdapter(List<CategoryTagBean> mData, List<Integer> exisTags, List<GetTagListBean> mExisSelectTags,Activity activity, Context context) {
        super( R.layout.item_new_tag,mData);
        this.mData=mData;
        this.exisTags=exisTags;
        this.mExisSelectTags=mExisSelectTags;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CategoryTagBean item) {
        helper.setText(R.id.tv_tag_category_name,item.getCategoryName());
        recyclerview=helper.getView(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(mContext,4));

        tagSelectAdapter=new TagSelectAdapter(mContext,mActivity);
        tagSelectAdapter.setOnItemClickListener(this);
        recyclerview.setAdapter(tagSelectAdapter);
        tagSelectAdapter.notifyAdapter(item.getTagList, exisTags,false);
    }

    @Override
    public void onItemClickListener(int pos, List<GetTagListBean> myLiveList) {

        if(myLiveList.get(pos).isSelect()==true && myLiveList.get(pos).isR()==true){
            for (int i=0;i<mExisSelectTags.size();i++){
                if(myLiveList.get(pos).getTagId()==mExisSelectTags.get(i).getTagId()){
                    mExisSelectTags.remove(i);
                }
            }
        }

        if(editorStatus){
            GetTagListBean myLive = myLiveList.get(pos);
            boolean isSelect = myLive.isSelect();
            if (!isSelect) {
                index++;
                myLive.setSelect(true);
                if (index == myLiveList.size()) {
                    isSelectAll = true;
                }
            } else {
                myLive.setSelect(false);
                index--;
                isSelectAll = false;
            }
            tagSelectAdapter.notifyDataSetChanged();
        }

        if(myLiveList.get(pos).isSelect()==false){
            for(int i=0;i<mSelectTag.size();i++){
                if(mSelectTag.get(i).getTagId()==myLiveList.get(pos).getTagId()){
                    mSelectTag.remove(i);
                }
            }
        }else{
            GetTagListBean t=new GetTagListBean();
            t.setPos(pos);
            t.setSelect(myLiveList.get(pos).isSelect());
            t.setTagName(myLiveList.get(pos).getTagName());
            t.setTagId(myLiveList.get(pos).getTagId());
            t.setCategoryId(myLiveList.get(pos).getCategoryId());
            t.setCategoryName(myLiveList.get(pos).getCategoryName());
            mSelectTag.add(t);
        }
    }

    public List<GetTagListBean> getSelectTag(){
        for (int i=0;i<mExisSelectTags.size();i++){
            GetTagListBean t=new GetTagListBean();
            t.setPos(i);
            t.setSelect(mExisSelectTags.get(i).isSelect());
            t.setTagName(mExisSelectTags.get(i).getTagName());
            t.setTagId(mExisSelectTags.get(i).getTagId());
            t.setCategoryId(mExisSelectTags.get(i).getCategoryId());
            t.setCategoryName(mExisSelectTags.get(i).getCategoryName());
            mSelectTag.add(t);
        }

        return mSelectTag;
    }
}
