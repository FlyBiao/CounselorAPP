package com.cesaas.android.counselor.order.member.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.cesaas.android.counselor.order.member.adapter.service.tag.MemberTagListAdapter;
import com.cesaas.android.counselor.order.member.bean.ResultVipGetOneLabelBean;
import com.cesaas.android.counselor.order.member.bean.Tags;
import com.cesaas.android.counselor.order.member.net.VipGetOneNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员标签列表
 */
public class MemberTagListActivity extends BasesActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private LinearLayout mBack;
    private TextView mTvTitle,mTvRightTitle;
    private int resultCode = 901;

    private String vipId;

    private List<CategoryTagBean> mGetCategoryTagListBeen=new ArrayList<>();//父类和子类标签列表
    private List<GetTagListBean> mGetTagListBeen=new ArrayList<>();//标签列表
    private List<ResultGetCategoryListBean.GetCategoryListBean> categoryListBeen=new ArrayList<>();
    private List<GetTagListBean> selectTagList=new ArrayList<>();
    private List<Integer> exisTags=new ArrayList<>();
    private GetTagListNet net;
    private GetCategoryListNet categoryListNet;
    private VipGetOneNet getOneNet;
    private MemberTagListAdapter adapter;

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
       if(bean.IsSuccess!=false && bean.TModel!=null){
           exisTags=new ArrayList<>();
           for(int i=0;i<bean.TModel.getTags().size();i++){
               exisTags.add(bean.TModel.getTags().get(i).getTagId());
           }
           categoryListNet=new GetCategoryListNet(mContext);
           categoryListNet.setData();
       }
    }

    /**
     * 接收已选标签
     * @param selectedTagList
     */
    public void onEventMainThread(List<GetTagListBean> selectedTagList) {
        selectTagList=new ArrayList<>();
        selectTagList.addAll(selectedTagList);
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
                            //设置分类标签下所属标签
                            listBean=new GetTagListBean();
                            listBean.setTagName(mGetTagListBeen.get(j).getTagName());
                            listBean.setTagId(mGetTagListBeen.get(j).getTagId());
                            listBean.setCategoryId(mGetTagListBeen.get(j).CategoryId);
                            listBean.setCategoryName(mGetTagListBeen.get(j).getCategoryName());
                            bean.getTagList.add(listBean);
                        }
                    }
                    mGetCategoryTagListBeen.add(bean);
                }

                adapter=new MemberTagListAdapter(mGetCategoryTagListBeen,exisTags,mActivity,mContext);
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
                if(selectTagList.size()!=0){
                    Intent mIntent = new Intent();
                    mIntent.putExtra("selectList",(Serializable)selectTagList);
                    setResult(resultCode, mIntent);// 设置结果，并进行传送
                    finish();
                }else{
                    ToastFactory.getLongToast(mContext,"请选择标签！");
                }
                break;
        }
    }
}
