package com.cesaas.android.counselor.order.shopmange;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.label.adapter.GetCategoryAdapter;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetCategoryListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetTagListBean;
import com.cesaas.android.counselor.order.label.bean.CategoryTagBean;
import com.cesaas.android.counselor.order.label.net.GetCategoryListNet;
import com.cesaas.android.counselor.order.label.net.GetTagListNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品标签列表
 */
public class ShopTagListActivity extends BasesActivity implements View.OnClickListener{

    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private LinearLayout mBack;
    private TextView mTvTitle,mTvRightTitle;
    private int resultCode = 901;

    private GetCategoryListNet categoryListNet;
    private GetTagListNet getTagListNet;

    private List<CategoryTagBean> mGetCategoryTagListBeen=new ArrayList<>();//父类和子类标签列表
    private List<GetTagListBean> mGetTagListBeen=new ArrayList<>();//标签列表

    private List<ResultGetCategoryListBean.GetCategoryListBean> categoryListBeen;
    private GetCategoryAdapter mGetCategoryAdapter;

    private List<GetTagListBean> selectTagList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_tag_list);

        initView();
        initData();
    }

    /**
     * 接收已选标签
     * @param selectedTagList
     */
    public void onEventMainThread(List<GetTagListBean> selectedTagList) {
        selectTagList=new ArrayList<>();
        selectTagList=selectedTagList;
    }

    /**
     * 获取标签分类
     * @param msg
     */
    public void onEventMainThread(ResultGetCategoryListBean msg) {
        if(msg.IsSuccess==true){
            if(msg.TModel!=null){
                categoryListBeen=new ArrayList<>();
                categoryListBeen.addAll(msg.TModel);

                for (int i=0;i<categoryListBeen.size();i++){
                    getTagListNet=new GetTagListNet(mContext);
                    getTagListNet.setData(categoryListBeen.get(i).CategoryId);
                }
            }
        }else{
            ToastFactory.getLongToast(mContext,"null:"+msg.Message);
        }
    }

    /**
     * 获取标签列表
     * @param msg
     */
    public void onEventMainThread(ResultGetTagListBean msg) {
        if(msg.IsSuccess==true){
            if(msg.TModel!=null){
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

                Log.i(Constant.TAG,"标签："+ JsonUtils.toJson(mGetCategoryTagListBeen));
                mGetCategoryAdapter=new GetCategoryAdapter(mGetCategoryTagListBeen,mContext);
                mSwipeMenuRecyclerView.setAdapter(mGetCategoryAdapter);
            }
        }else{
            ToastFactory.getLongToast(mContext,"null:"+msg.Message);
        }
    }

    //初始化数据
    private void initData(){
        categoryListNet=new GetCategoryListNet(mContext);
        categoryListNet.setData();
    }

    //初始化试图控件
    public void initView(){
        mSwipeMenuRecyclerView= (SwipeMenuRecyclerView) findViewById(R.id.recycler_tag_category_view);

        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。

        mBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mTvTitle= (TextView) findViewById(R.id.tv_base_title);
        mTvRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTvRightTitle.setVisibility(View.VISIBLE);
        mTvRightTitle.setText("确定");
        mTvTitle.setText("选择标签");

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
