package com.cesaas.android.counselor.order.member.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.label.bean.CategoryTagBean;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetCategoryListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetTagListBean;
import com.cesaas.android.counselor.order.label.net.GetCategoryListNet;
import com.cesaas.android.counselor.order.label.net.GetTagListNet;
import com.cesaas.android.counselor.order.member.adapter.service.tag.MemberNewTagListAdapter;
import com.cesaas.android.counselor.order.member.adapter.service.tag.MemberTagListAdapter;
import com.cesaas.android.counselor.order.member.adapter.service.tag.TagSelectAdapter;
import com.cesaas.android.counselor.order.member.bean.ResultVipGetOneLabelBean;
import com.cesaas.android.counselor.order.member.net.VipGetOneNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员标签列表[New]
 */
public class MemberNewTagListActivity extends BasesActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private LinearLayout mBack;
    private TextView mTvTitle,mTvRightTitle;
    private int resultCode = 901;

    private String vipId;

    private List<GetTagListBean> mExisSelectTags=new ArrayList<>();

    private List<CategoryTagBean> mGetCategoryTagListBeen=new ArrayList<>();//父类和子类标签列表
    private List<GetTagListBean> mGetTagListBeen=new ArrayList<>();//标签列表
    private List<ResultGetCategoryListBean.GetCategoryListBean> categoryListBeen=new ArrayList<>();
    private List<Integer> exisTags=new ArrayList<>();
    private GetTagListNet net;
    private GetCategoryListNet categoryListNet;
    private VipGetOneNet getOneNet;
    private MemberNewTagListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_tag_list);
        initView();
        initData();
    }

    /**
     * 接收已打标签
     * @param bean
     */
    public void onEventMainThread(ResultVipGetOneLabelBean bean) {
       if(bean.IsSuccess!=false){
           if(bean.TModel.getTags()!=null && bean.TModel.getTags().size()!=0){
               exisTags=new ArrayList<>();
               mExisSelectTags=new ArrayList<>();
               for(int i=0;i<bean.TModel.getTags().size();i++){
                   exisTags.add(bean.TModel.getTags().get(i).getTagId());

                   GetTagListBean t=new GetTagListBean();
                   t.setPos(i);
                   t.setSelect(true);
                   t.setTagName(bean.TModel.getTags().get(i).getTagName());
                   t.setTagId(bean.TModel.getTags().get(i).getTagId());
                   t.setCategoryId(bean.TModel.getTags().get(i).getCategoryId());
                   t.setCategoryName(bean.TModel.getTags().get(i).getCategoryName());
                   mExisSelectTags.add(t);
               }
           }
           categoryListNet=new GetCategoryListNet(mContext);
           categoryListNet.setData();
       }else{
           categoryListNet=new GetCategoryListNet(mContext);
           categoryListNet.setData();
       }
    }

    /**
     * 获取标签分类
     * @param msg
     */
    public void onEventMainThread(ResultGetCategoryListBean msg) {
        if(msg.IsSuccess==true){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                categoryListBeen=new ArrayList<>();
                categoryListBeen.addAll(msg.TModel);
                for (int i=0;i<categoryListBeen.size();i++){
                    net=new GetTagListNet(mContext);
                    net.setData(categoryListBeen.get(i).CategoryId);
                }
            }
        }
    }

    /**
     * 获取标签列表
     * @param msg
     */
    public void onEventMainThread(ResultGetTagListBean msg) {
        if(msg.IsSuccess==true){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                for (int i=0;i<msg.TModel.size();i++){
                    if(msg.TModel.get(i).Type==0){
                        GetTagListBean bean=new GetTagListBean();
                        bean.setCategoryId(msg.TModel.get(i).CategoryId);
                        bean.setControllerType(msg.TModel.get(i).ControllerType);
                        bean.setTagId(msg.TModel.get(i).TagId);
                        bean.setTagName(msg.TModel.get(i).TagName);
                        mGetTagListBeen.add(bean);
                    }
                }
                mGetCategoryTagListBeen=new ArrayList<>();
                for(int i=0;i<categoryListBeen.size();i++){//父类标签列表
                    CategoryTagBean bean=null;
                    GetTagListBean listBean=null;
                    //设置分类标签信息
                    bean=new CategoryTagBean();
                    bean.setCategoryName(categoryListBeen.get(i).CategoryName);
                    bean.setCategoryId(categoryListBeen.get(i).CategoryId);

                    for (int j=0;j<mGetTagListBeen.size();j++){//父类下的所有标签列表
                        if(mGetTagListBeen.get(j).getCategoryId()==categoryListBeen.get(i).CategoryId){
                            if(exisTags.indexOf(mGetTagListBeen.get(j).getTagId())!=-1){
                                //设置分类标签下所属标签
                                listBean=new GetTagListBean();
                                listBean.setSelect(true);
                                listBean.setR(true);
                                listBean.setTagName(mGetTagListBeen.get(j).getTagName());
                                listBean.setTagId(mGetTagListBeen.get(j).getTagId());
                                listBean.setCategoryId(mGetTagListBeen.get(j).CategoryId);
                                listBean.setCategoryName(mGetTagListBeen.get(j).getCategoryName());
                            }else{
                                //设置分类标签下所属标签
                                listBean=new GetTagListBean();
                                listBean.setSelect(false);
                                listBean.setR(false);
                                listBean.setTagName(mGetTagListBeen.get(j).getTagName());
                                listBean.setTagId(mGetTagListBeen.get(j).getTagId());
                                listBean.setCategoryId(mGetTagListBeen.get(j).CategoryId);
                                listBean.setCategoryName(mGetTagListBeen.get(j).getCategoryName());
                            }
                            bean.getTagList.add(listBean);
                        }
                    }
                    mGetCategoryTagListBeen.add(bean);
                }

                adapter=new MemberNewTagListAdapter(mGetCategoryTagListBeen,exisTags,mExisSelectTags,mActivity,mContext);
                mRecyclerView.setAdapter(adapter);
            }
        }else{
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }

    //初始化数据
    private void initData(){

        Intent intent=getIntent();
        vipId=intent.getStringExtra("vipId");

        if(!vipId.equals("0")){
            getOneNet=new VipGetOneNet(mContext,2);
            getOneNet.setData(vipId);
        }else{
            categoryListNet=new GetCategoryListNet(mContext);
            categoryListNet.setData();
        }
    }

    //初始化试图控件
    public void initView(){
        mBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mTvTitle= (TextView) findViewById(R.id.tv_base_title);
        mTvRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTvRightTitle.setVisibility(View.VISIBLE);
        mTvRightTitle.setText("确定");
        mTvTitle.setText("选择标签");
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mBack.setOnClickListener(this);
        mTvRightTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:
                if(adapter.getSelectTag().size()!=0){
                    Intent mIntent = new Intent();
                    mIntent.putExtra("selectList",(Serializable)adapter.getSelectTag());
                    setResult(resultCode, mIntent);// 设置结果，并进行传送
                    finish();
                }else{
                    ToastFactory.getLongToast(mContext,"请选择标签！");
                }
                break;
        }
    }

}
