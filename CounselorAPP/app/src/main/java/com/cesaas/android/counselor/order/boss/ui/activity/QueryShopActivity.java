package com.cesaas.android.counselor.order.boss.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.AreaAdapter;
import com.cesaas.android.counselor.order.adapter.OrganzationAdapter;
import com.cesaas.android.counselor.order.bean.GroupBean;
import com.cesaas.android.counselor.order.bean.GroupTagBean;
import com.cesaas.android.counselor.order.bean.ResultGroupItemBeen;
import com.cesaas.android.counselor.order.boss.adapter.shop.ScreenShopAdapter;
import com.cesaas.android.counselor.order.boss.adapter.shop.ShopTagListAdapter;
import com.cesaas.android.counselor.order.boss.bean.ResultShopListBean;
import com.cesaas.android.counselor.order.boss.bean.SelectShopListBean;
import com.cesaas.android.counselor.order.boss.bean.ShopListBean;
import com.cesaas.android.counselor.order.boss.bean.ShopTagListBean;
import com.cesaas.android.counselor.order.boss.net.ShopListNet;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.utils.format.FormatUtils;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 店铺查询
 */
public class QueryShopActivity extends BasesActivity implements View.OnClickListener,ScreenShopAdapter.OnItemClickListener,ShopTagListAdapter.OnItemClickListenerTag{
    private SwipeMenuRecyclerView rv_organization;
    private SwipeMenuRecyclerView rv_area;
    private RecyclerView rv_tag_view;
    private RecyclerView mRecyclerview;

    private TextView tvTitle,tvSelectAll,tvLeftTitle,tvShopSize,tv_select_title;
    private TextView tvShop,tvInstitution,tvArea,tv_tag;
    private Button btnSure;
    private LinearLayout llBack,ll_select;

    private String leftTitle;
    private int resultCode=201;
    private int shopType;
    private int selectSum;

    private ArrayList<TextView> tvs=new ArrayList<TextView>();
    private List<SelectShopListBean> shopListBeanList;
    private List<ShopTagListBean> shopTagList=new ArrayList<>();
    private List<ShopListBean> shopList=new ArrayList<>();
    private List<ShopListBean> shopLists=new ArrayList<>();
    private ShopListNet shopListNet;
    private OrganzationAdapter organzationAdapter;
    private AreaAdapter areaAdapter;
    private ShopTagListAdapter shopTagListAdapter;

    private boolean isSelectAll = false;
    private boolean editorStatus = true;
    private int index = 0;
    int clickIndex=-1;

    private ScreenShopAdapter mRadioAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_shop);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            leftTitle=bundle.getString("LeftTitle");
            shopType=bundle.getInt("shopType");
        }
        initView();
        initClick();
        initData();
    }

    /**
     * 接收店铺列表数据
     * @param msg
     */
    public void onEventMainThread(ResultShopListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                shopList=new ArrayList<>();
                shopLists=new ArrayList<>();
                shopList.addAll(msg.TModel);
                if(shopType!=0){
                    for (int i=0;i<shopList.size();i++){
                        if(shopList.get(i).getShopType()==shopType){
                            ShopListBean bean=new ShopListBean();
                            bean.setShopId(shopList.get(i).getShopId());
                            bean.setShopName(shopList.get(i).getShopName());
                            bean.setShopType(shopList.get(i).getShopType());
                            bean.setOrganizationName(shopList.get(i).getOrganizationName());
                            bean.setAreaId(shopList.get(i).getAreaId());
                            bean.setAreaName(shopList.get(i).getAreaName());
                            bean.setOrganizationId(shopList.get(i).getOrganizationId());
                            bean.setSelect(true);
                            shopLists.add(bean);
                        }
                    }
                    mRadioAdapter = new ScreenShopAdapter(this,mActivity,shopType);
                    mRecyclerview.setAdapter(mRadioAdapter);
                    mRadioAdapter.notifyAdapter(shopLists, false);
                    mRadioAdapter.setOnItemClickListener(this);

                }else{
                    mRadioAdapter = new ScreenShopAdapter(this,mActivity,shopType);
                    mRecyclerview.setAdapter(mRadioAdapter);
                    mRadioAdapter.notifyAdapter(shopList, false);
                    mRadioAdapter.setOnItemClickListener(this);
                }

                for (int i = 0; i< mRadioAdapter.getMyLiveList().size();i++) {
                    if(mRadioAdapter.getMyLiveList().get(i).getShopType()==shopType){
                        index+=1;
                        mRadioAdapter.getMyLiveList().get(i).setSelect(true);
                    }
                }
//                index = mRadioAdapter.getMyLiveList().size();
                tvSelectAll.setText("取消全选");
                isSelectAll = true;
                tvShopSize.setText(String.valueOf(index));
                mRadioAdapter.notifyDataSetChanged();

            }else{
                ToastFactory.getLongToast(mContext,"暂无店铺数据!");
            }
        }else{
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }

    private  void initData(){
        shopListNet=new ShopListNet(mContext);
        shopListNet.setData();
    }

    /**
     * 接收EventBus
     * @param groupItem 机构列表集合
     */
    public void onEventMainThread(ResultGroupItemBeen groupItem) {
        if(groupItem.getShopListBeen().size()!=0){
            shopListBeanList=new ArrayList<>();
            for (int i=0;i<groupItem.getShopListBeen().size();i++){
                SelectShopListBean bean=new SelectShopListBean();
                bean.setShopId(groupItem.getShopListBeen().get(i).getShopId());
                bean.setShopName(groupItem.getShopListBeen().get(i).getShopName());
                bean.setShopType(groupItem.getShopListBeen().get(i).getShopType());
                shopListBeanList.add(bean);
            }
            Intent mIntent = new Intent();
            mIntent.putExtra("selectList",(Serializable)shopListBeanList);
            setResult(resultCode, mIntent);// 设置结果，并进行传送
            finish();
        }else{
            ToastFactory.getLongToast(mContext,"未获取数据！");
        }
    }

    private void initView() {
        ll_select= (LinearLayout) findViewById(R.id.ll_select);
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("选择店铺");
        tvSelectAll= (TextView) findViewById(R.id.tv_message);
        tvSelectAll.setVisibility(View.VISIBLE);
        tvSelectAll.setText("全选");
        tvShopSize= (TextView) findViewById(R.id.tv_shop_size);
        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tvLeftTitle.setText(leftTitle);
        tv_select_title= (TextView) findViewById(R.id.tv_select_title);

        btnSure= (Button) findViewById(R.id.btn_sure);

        tvShop=(TextView) findViewById(R.id.tv_shop);
        tvShop.setOnClickListener(this);
        tvInstitution=(TextView) findViewById(R.id.tv_institution);
        tvInstitution.setOnClickListener(this);
        tvArea=(TextView) findViewById(R.id.tv_area);
        tvArea.setOnClickListener(this);
        tv_tag= (TextView) findViewById(R.id.tv_tag);
        tv_tag.setOnClickListener(this);
        tvs.add(tvShop);
        tvs.add(tvInstitution);
        tvs.add(tvArea);
        tvs.add(tv_tag);


        mRecyclerview= (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        rv_tag_view= (RecyclerView) findViewById(R.id.rv_tag_view);
        rv_tag_view.setLayoutManager(new LinearLayoutManager(this));


        rv_organization= (SwipeMenuRecyclerView) findViewById(R.id.rv_organization);
        rv_area= (SwipeMenuRecyclerView) findViewById(R.id.rv_area);

        rv_organization.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        rv_organization.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        rv_organization.addItemDecoration(new ListViewDecoration());// 添加分割线。

        rv_area.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        rv_area.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        rv_area.addItemDecoration(new ListViewDecoration());// 添加分割线。
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_shop:
                clickIndex=0;
                ll_select.setVisibility(View.VISIBLE);
                mRecyclerview.setVisibility(View.VISIBLE);
                tvSelectAll.setVisibility(View.VISIBLE);
                rv_organization.setVisibility(View.GONE);
                rv_area.setVisibility(View.GONE);
                rv_tag_view.setVisibility(View.GONE);
                tv_select_title.setText("个店铺");
                break;
            case R.id.tv_institution:
                clickIndex=1;
                rv_organization.setVisibility(View.VISIBLE);
                ll_select.setVisibility(View.GONE);
                mRecyclerview.setVisibility(View.GONE);
                rv_area.setVisibility(View.GONE);
                tvSelectAll.setVisibility(View.GONE);
                rv_tag_view.setVisibility(View.GONE);
                ArrayList<GroupBean> resultList = new ArrayList<>();
                HashMap<String,GroupBean> hashMap;
                if(shopType!=0){
                    hashMap= FormatUtils.organizationGroup(shopLists);
                }else{
                    hashMap= FormatUtils.organizationGroup(shopList);
                }

                Set<String> keys = hashMap.keySet();
                for(String key:keys){
                    GroupBean beans = hashMap.get(key);
                    if(beans!=null){
                        resultList.add(beans);
                    }
                }
                organzationAdapter=new OrganzationAdapter(resultList,mContext);
                organzationAdapter.setOnItemClickListener(organizationItemClickListener);
                rv_organization.setAdapter(organzationAdapter);
                break;
            case R.id.tv_area:
                clickIndex=2;
                ll_select.setVisibility(View.GONE);
                mRecyclerview.setVisibility(View.GONE);
                rv_organization.setVisibility(View.GONE);
                tvSelectAll.setVisibility(View.GONE);
                rv_tag_view.setVisibility(View.GONE);
                rv_area.setVisibility(View.VISIBLE);
                ArrayList<GroupBean> resultLists = new ArrayList<>();
                HashMap<String,GroupBean> hashMaps;
                if(shopType!=0){
                    hashMaps = FormatUtils.areaGroup(shopLists);
                }else{
                    hashMaps = FormatUtils.areaGroup(shopList);
                }

                Set<String> keyss = hashMaps.keySet();
                for(String key:keyss){
                    GroupBean beans = hashMaps.get(key);
                    if(beans!=null){
                        resultLists.add(beans);
                    }
                }
                areaAdapter=new AreaAdapter(resultLists,mContext);
                rv_area.setAdapter(areaAdapter);
                break;
            case R.id.tv_tag:
                clickIndex=3;
                mRecyclerview.setVisibility(View.GONE);
                rv_organization.setVisibility(View.GONE);
                rv_area.setVisibility(View.GONE);
                ll_select.setVisibility(View.VISIBLE);
                tvSelectAll.setVisibility(View.VISIBLE);
                rv_tag_view.setVisibility(View.VISIBLE);
                tvShopSize.setText(String.valueOf(index));
                tv_select_title.setText("个标签");
                for (int i=0;i<shopList.size();i++){
                    if(shopList.get(i).getTags()!=null && shopList.get(i).getTags().size()!=0){
                        for (int j=0;j<shopList.get(i).getTags().size();j++){
                            ShopTagListBean tagListBean=new ShopTagListBean();
                            tagListBean.setAreaId(shopList.get(i).getAreaId());
                            tagListBean.setAreaName(shopList.get(i).getAreaName());
                            tagListBean.setOrganizationId(shopList.get(i).getOrganizationId());
                            tagListBean.setOrganizationName(shopList.get(i).getOrganizationName());
                            tagListBean.setShopType(shopList.get(i).getShopType());
                            tagListBean.setShopId(shopList.get(i).getShopId());
                            tagListBean.setShopName(shopList.get(i).getShopName());
                            tagListBean.setTagId(shopList.get(i).getTags().get(j).getTagId());
                            tagListBean.setTagName(shopList.get(i).getTags().get(j).getTagName());
                            tagListBean.setCategoryId(shopList.get(i).getTags().get(j).getCategoryId());
                            tagListBean.setCategoryName(shopList.get(i).getTags().get(j).getCategoryName());
                            shopTagList.add(tagListBean);
                        }
                    }else{
                        ShopTagListBean tagListBean=new ShopTagListBean();
                        tagListBean.setAreaId(shopList.get(i).getAreaId());
                        tagListBean.setAreaName(shopList.get(i).getAreaName());
                        tagListBean.setOrganizationId(shopList.get(i).getOrganizationId());
                        tagListBean.setOrganizationName(shopList.get(i).getOrganizationName());
                        tagListBean.setShopType(shopList.get(i).getShopType());
                        tagListBean.setShopId(shopList.get(i).getShopId());
                        tagListBean.setShopName(shopList.get(i).getShopName());
                        shopTagList.add(tagListBean);
                    }
                }
                ArrayList<GroupTagBean> resultTagList = new ArrayList<>();
                HashMap<String,GroupTagBean> hashMapTags;
                hashMapTags = FormatUtils.shopTagGroup(shopTagList);
                Set<String> tagKeys = hashMapTags.keySet();
                for(String key:tagKeys){
                    GroupTagBean beans = hashMapTags.get(key);
                    if(beans!=null){
                        resultTagList.add(beans);
                    }
                }

                shopTagListAdapter=new ShopTagListAdapter(mContext,mActivity,shopType);
                rv_tag_view.setAdapter(shopTagListAdapter);
                shopTagListAdapter.notifyAdapter(resultTagList, false);
                shopTagListAdapter.setOnItemClickListener(this);
                break;
        }
        setColor(clickIndex);
    }

    private OnItemClickListener organizationItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            shopListBeanList=new ArrayList<>();
        }
    };

    private void setColor(int index) {
        for(int i=0;i<tvs.size();i++){
            tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.tv_green));
            tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.day_sign_content_text_white_30));
        }
        tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.white));
        tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_green_bg));
    }

    public void initClick(){
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
        tvSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAllMain();
            }
        });

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopListBeanList=new ArrayList<>();
                if(clickIndex==3){
                    for (int i=0;i<shopTagListAdapter.getMyLiveList().size();i++){
                        if(shopTagListAdapter.getMyLiveList().get(i).isSelect()==true){
                            for (int j=0;j<shopTagListAdapter.getMyLiveList().get(i).getGroupItemBeen().size();j++){
                                SelectShopListBean bean=new SelectShopListBean();
                                bean.setShopId(shopTagListAdapter.getMyLiveList().get(i).getGroupItemBeen().get(j).getShopId());
                                bean.setShopName(shopTagListAdapter.getMyLiveList().get(i).getGroupItemBeen().get(j).getShopName());
                                bean.setShopType(shopTagListAdapter.getMyLiveList().get(i).getGroupItemBeen().get(j).getShopType());
                                shopListBeanList.add(bean);
                            }
                        }
                    }
                }else{
                    for (int i=0;i<mRadioAdapter.getMyLiveList().size();i++){
                        if(mRadioAdapter.getMyLiveList().get(i).isSelect()==true){
                            SelectShopListBean bean=new SelectShopListBean();
                            bean.setShopId(mRadioAdapter.getMyLiveList().get(i).getShopId());
                            bean.setShopName(mRadioAdapter.getMyLiveList().get(i).getShopName());
                            bean.setShopType(mRadioAdapter.getMyLiveList().get(i).getShopType());
                            shopListBeanList.add(bean);
                        }
                    }
                }

                if(shopListBeanList!=null && shopListBeanList.size()!=0){
                    Intent mIntent = new Intent();
                    mIntent.putExtra("selectList",(Serializable)shopListBeanList);
                    setResult(resultCode, mIntent);
                    finish();
                }else{
                    ToastFactory.getLongToast(mContext,"请选择需要查询的店铺！");
                }
            }
        });
    }

    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if(clickIndex==3){
            if (shopTagListAdapter == null) return;
            if (!isSelectAll) {
                for (int i = 0, j = shopTagListAdapter.getMyLiveList().size(); i < j; i++) {
                    shopTagListAdapter.getMyLiveList().get(i).setSelect(true);
                }
                index = shopTagListAdapter.getMyLiveList().size();
                tvSelectAll.setText("取消全选");
                isSelectAll = true;
            } else {
                for (int i = 0, j = shopTagListAdapter.getMyLiveList().size(); i < j; i++) {
                    shopTagListAdapter.getMyLiveList().get(i).setSelect(false);
                }
                index = 0;
                tvSelectAll.setText("全选");
                isSelectAll = false;
            }
            shopTagListAdapter.notifyDataSetChanged();
            tvShopSize.setText(String.valueOf(index));
            tv_select_title.setText("个标签");
        }else{
            if (mRadioAdapter == null) return;
            if (!isSelectAll) {
                for (int i = 0, j = mRadioAdapter.getMyLiveList().size(); i < j; i++) {
                    mRadioAdapter.getMyLiveList().get(i).setSelect(true);
                }
                index = mRadioAdapter.getMyLiveList().size();
                tvSelectAll.setText("取消全选");
                isSelectAll = true;
            } else {
                for (int i = 0, j = mRadioAdapter.getMyLiveList().size(); i < j; i++) {
                    mRadioAdapter.getMyLiveList().get(i).setSelect(false);
                }
                index = 0;
                tvSelectAll.setText("全选");
                isSelectAll = false;
            }
            mRadioAdapter.notifyDataSetChanged();
            tvShopSize.setText(String.valueOf(index));
            tv_select_title.setText("个店铺");
        }
    }


    @Override
    public void onItemClickListener(int pos, List<ShopListBean> myLiveList) {
        if (editorStatus) {
            ShopListBean myLive = myLiveList.get(pos);
            boolean isSelect = myLive.isSelect();
            if (!isSelect) {
                index++;
                myLive.setSelect(true);
                if (index == myLiveList.size()) {
                    isSelectAll = true;
                    tvSelectAll.setText("取消全选");
                }
            } else {
                myLive.setSelect(false);
                index--;
                isSelectAll = false;
                tvSelectAll.setText("全选");
            }
            tvShopSize.setText(String.valueOf(index));
            tv_select_title.setText("个店铺");
            mRadioAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnItemClickListenerTag(int pos, List<GroupTagBean> myLiveList) {
        if (editorStatus) {
            GroupTagBean myLive = myLiveList.get(pos);
            boolean isSelect = myLive.isSelect();
            if (!isSelect) {
                index++;
                myLive.setSelect(true);
                if (index == myLiveList.size()) {
                    isSelectAll = true;
                    tvSelectAll.setText("取消全选");
                }
            } else {
                myLive.setSelect(false);
                index--;
                isSelectAll = false;
                tvSelectAll.setText("全选");
            }
            tvShopSize.setText(String.valueOf(index));
            tv_select_title.setText("个标签");
            shopTagListAdapter.notifyDataSetChanged();
        }
    }
}
