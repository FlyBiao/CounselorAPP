package com.cesaas.android.counselor.order.boss.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultBandBean;
import com.cesaas.android.counselor.order.bean.ResultGoodsCategoryBean;
import com.cesaas.android.counselor.order.bean.ResultSortBean;
import com.cesaas.android.counselor.order.boss.bean.ScreenTagBean;
import com.cesaas.android.counselor.order.boss.bean.tag.ResultGetSeasonAllBean;
import com.cesaas.android.counselor.order.boss.bean.tag.TagDataUtils;
import com.cesaas.android.counselor.order.boss.net.GetSeasonAllNet;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.tag.adapter.TagBandAdapter;
import com.cesaas.android.counselor.order.custom.tag.adapter.TagBigCAdapter;
import com.cesaas.android.counselor.order.custom.tag.adapter.TagGoodsCategoryAdapter;
import com.cesaas.android.counselor.order.custom.tag.adapter.TagScreenAdapter;
import com.cesaas.android.counselor.order.custom.tag.adapter.TagSeasonAdapter;
import com.cesaas.android.counselor.order.custom.tag.adapter.TagYearAdapter;
import com.cesaas.android.counselor.order.custom.tree.Node;
import com.cesaas.android.counselor.order.custom.tree.TreeRecyclerAdapter;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.net.BandNet;
import com.cesaas.android.counselor.order.net.GoodsCategoryNet;
import com.cesaas.android.counselor.order.net.SortAllNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 店铺报表
 */
public class QueryReportActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle,tvLeftTitle,tvRightTitle;
    private TextView tvShop,tvInstitution,tvArea,tvGoodsCategory;
    private LinearLayout llBack,ll_year_season,ll_category,ll_band,ll_screen,ll_goods_category;
    private RecyclerView rv_tree;
    public static FlowTagLayout tag_year_flow_layout;
    public static FlowTagLayout tag_season_flow_layout;
    public static FlowTagLayout tag_band_flow_layout;
    public static FlowTagLayout tag_category_flow_layout;
    public static FlowTagLayout tag_screen_flow_layout;
    public static FlowTagLayout tag_goods_category_flow_layout;

    private ArrayList<TextView> tvs=new ArrayList<TextView>();
    public static TagYearAdapter<GetTagListBean> yearTagAdapter;
    public static TagSeasonAdapter<GetTagListBean> seasonTagAdapter;
    public static TagBandAdapter<GetTagListBean> bandTagAdapter;
    public static TagBigCAdapter<GetTagListBean> categoryTagAdapter;
    public static TagScreenAdapter <ScreenTagBean> screenTagScreenAdapter;
    public static TagGoodsCategoryAdapter goodsCategoryAdapter;
    private TreeRecyclerAdapter mAdapter;
    protected List<Node> mDatas = new ArrayList<Node>();
    private List<GetTagListBean> bandList;
    private List<GetTagListBean> goodsCategoryList;
    private List<GetTagListBean> categoryList;

    private List<ScreenTagBean> screenTagBeanList=new ArrayList<>();
    private List<ScreenTagBean> screenYearTagList=new ArrayList<>();
    private List<ScreenTagBean> screenSeasonTagList=new ArrayList<>();
    private List<ScreenTagBean> screenCategoryTagList=new ArrayList<>();
    private List<ScreenTagBean> screenBandTagList=new ArrayList<>();
    private List<ScreenTagBean> screenGoodsCategoryTagList=new ArrayList<>();

    private BandNet bandNet;
    private SortAllNet sortAllNet;
    private GoodsCategoryNet categoryNet;
    private GetSeasonAllNet seasonAllNet;
//    private SortListNet sortListNet;

    private int type;
    private int resultCode=304;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_report);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            type=bundle.getInt("type");
        }
        initView();
        initClick();
        initData();
    }

    /**
     * 接收商品品类
     * @param msg
     */
    public void onEventMainThread(ResultGoodsCategoryBean msg){
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                goodsCategoryList=new ArrayList<>();
                for (int i=0;i<msg.TModel.size();i++){
                    GetTagListBean band=new GetTagListBean();
                    band.setTagId(i);
                    band.setTagName(msg.TModel.get(i).getSTYLE_CATEGORY());
                    goodsCategoryList.add(band);
                }
                goodsCategoryAdapter = new TagGoodsCategoryAdapter(mContext);
                tag_goods_category_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
                tag_goods_category_flow_layout.setAdapter(goodsCategoryAdapter);
                goodsCategoryAdapter.onlyAddAll(goodsCategoryList);
                //商品品类
                tag_goods_category_flow_layout.setOnTagSelectListener(new com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener() {
                    @Override
                    public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
                        if (selectedList != null && selectedList.size() > 0) {
                            screenGoodsCategoryTagList=new ArrayList<ScreenTagBean>();
                            for (int i : selectedList) {
                                ScreenTagBean tag=new ScreenTagBean();
                                tag.setType(5);
                                tag.setId(goodsCategoryList.get(i).getTagId());
                                tag.setName(goodsCategoryList.get(i).getTagName());
                                screenGoodsCategoryTagList.add(tag);
                            }
                            showData();
                        } else {
                            screenGoodsCategoryTagList=new ArrayList<ScreenTagBean>();
                            showData();
                            Toast.makeText(mContext,"没有选择商品品类标签！",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else{
//                ToastFactory.getLongToast(mContext,"暂无商品品类信息！");
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取商品品类！"+msg.Message);
        }
    }

    /**
     * 接收大类小类数据
     * @param msg
     */
    public void onEventMainThread(ResultSortBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.getTModel()!=null && msg.getTModel().size()!=0){
                categoryList=new ArrayList<>();
                for (int i=0;i<msg.getTModel().size();i++){
                    GetTagListBean band=new GetTagListBean();
                    band.setTagId(msg.getTModel().get(i).getId());
                    band.setTagName(msg.getTModel().get(i).getTitle());
                    categoryList.add(band);
                }
                categoryTagAdapter = new TagBigCAdapter<>(mContext);
                tag_category_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
                tag_category_flow_layout.setAdapter(categoryTagAdapter);
                categoryTagAdapter.onlyAddAll(categoryList);
                //大类小类
                tag_category_flow_layout.setOnTagSelectListener(new com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener() {
                    @Override
                    public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
                        if (selectedList != null && selectedList.size() > 0) {
                            screenCategoryTagList=new ArrayList<ScreenTagBean>();
                            for (int i : selectedList) {
                                ScreenTagBean tag=new ScreenTagBean();
                                tag.setType(3);
                                tag.setId(categoryList.get(i).getTagId());
                                tag.setName(categoryList.get(i).getTagName());
                                screenCategoryTagList.add(tag);
                            }
                            showData();
                        } else {
                            screenCategoryTagList=new ArrayList<ScreenTagBean>();
                            showData();
                            Toast.makeText(mContext,"没有选择类型标签！",Toast.LENGTH_LONG).show();
                        }
                    }
                });
//                mDatas=new ArrayList<>();
//                for (int i=0;i<msg.getTModel().size();i++){
//                    mDatas.add(new Node(msg.getTModel().get(i).getId(),msg.getTModel().get(i).getParentId(),msg.getTModel().get(i).getTitle(),msg.getTModel().get(i).getType()));
//                }
//                //==大类小类
//                //第一个参数  RecyclerView
//                //第二个参数  上下文
//                //第三个参数  数据集
//                //第四个参数  默认展开层级数 0为不展开
//                //第五个参数  展开的图标
//                //第六个参数  闭合的图标
//                mAdapter = new SimpleTreeRecyclerAdapter(rv_tree, mContext, mDatas, 0,R.mipmap.hide,R.mipmap.show);
//                rv_tree.setAdapter(mAdapter);
            }else{
//                ToastFactory.getLongToast(mContext,"暂无大类小类信息！");
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取大类小类失败！"+msg.Message);
        }
    }

    /**
     * 接收波段数据
     * @param msg
     */
    public void onEventMainThread(ResultBandBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.getTModel()!=null && msg.getTModel().size()!=0){
                bandList=new ArrayList<>();
                for (int i=0;i<msg.getTModel().size();i++){
                    GetTagListBean band=new GetTagListBean();
                    band.setTagId(i);
                    band.setTagName(msg.getTModel().get(i).getSTYLE_BAND());
                    bandList.add(band);
                }
                bandTagAdapter = new TagBandAdapter<>(mContext);
                tag_band_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
                tag_band_flow_layout.setAdapter(bandTagAdapter);
                bandTagAdapter.onlyAddAll(bandList);
                //波段
                tag_band_flow_layout.setOnTagSelectListener(new com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener() {
                    @Override
                    public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
                        if (selectedList != null && selectedList.size() > 0) {
                            screenBandTagList=new ArrayList<ScreenTagBean>();
                            for (int i : selectedList) {
                                ScreenTagBean tag=new ScreenTagBean();
                                tag.setType(4);
                                tag.setId(bandList.get(i).getTagId());
                                tag.setName(bandList.get(i).getTagName());
                                screenBandTagList.add(tag);
                            }
                            showData();
                        } else {
                            screenBandTagList=new ArrayList<ScreenTagBean>();
                            showData();
                            Toast.makeText(mContext,"没有选择波段标签！",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else{
//                ToastFactory.getLongToast(mContext,"暂无波段信息！");
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取波段失败！"+msg.Message);
        }
    }

    /**
     * 接收季节数据
     * @param msg
     */
    public void onEventMainThread(final ResultGetSeasonAllBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                seasonTagAdapter = new TagSeasonAdapter<>(mContext);
                tag_season_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
                tag_season_flow_layout.setAdapter(seasonTagAdapter);
                seasonTagAdapter.onlyAddAll(msg.TModel);
                tag_season_flow_layout.setOnTagSelectListener(new com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener() {
                    @Override
                    public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
                        if (selectedList != null && selectedList.size() > 0) {
                            screenSeasonTagList=new ArrayList<ScreenTagBean>();
                            for (int i : selectedList) {
                                ScreenTagBean tag=new ScreenTagBean();
                                tag.setType(2);
                                tag.setId(msg.TModel.get(positoin).getId());
                                tag.setName(msg.TModel.get(positoin).getTitle());
                                screenSeasonTagList.add(tag);
                            }
                            showData();
                        } else {
                            screenSeasonTagList=new ArrayList<ScreenTagBean>();
                            showData();
                            Toast.makeText(mContext,"没有选择季节标签！",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else{
//                ToastFactory.getLongToast(mContext,"暂无季节信息！");
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取季节信息失败！"+msg.Message);
        }
    }

    private void initData() {
        bandNet=new BandNet(mContext);
        bandNet.setData();
        sortAllNet=new SortAllNet(mContext);
        sortAllNet.setData(1);
        seasonAllNet=new GetSeasonAllNet(mContext);
        seasonAllNet.setData();
        categoryNet=new GoodsCategoryNet(mContext);
        categoryNet.setData();
        //年份
        yearTagAdapter = new TagYearAdapter<>(mContext);
        tag_year_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        tag_year_flow_layout.setAdapter(yearTagAdapter);
        yearTagAdapter.onlyAddAll(TagDataUtils.getYearTagList());
        tag_year_flow_layout.setOnTagSelectListener(new com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    screenYearTagList=new ArrayList<ScreenTagBean>();
                    for (int i : selectedList) {
                        ScreenTagBean tag=new ScreenTagBean();
                        tag.setType(1);
                        tag.setId(TagDataUtils.getYearTagList().get(i).getTagId());
                        tag.setName(TagDataUtils.getYearTagList().get(i).getTagName());
                        screenYearTagList.add(tag);
                    }
                    showData();
                } else {
                    screenYearTagList=new ArrayList<ScreenTagBean>();
                    showData();
                    Toast.makeText(mContext,"没有选择年份标签！",Toast.LENGTH_LONG).show();
                }
            }
        });
        //季节
//        seasonTagAdapter = new TagSeasonAdapter<>(mContext);
//        tag_season_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
//        tag_season_flow_layout.setAdapter(seasonTagAdapter);
//        seasonTagAdapter.onlyAddAll(TagDataUtils.getSeasonTagList());
//        tag_season_flow_layout.setOnTagSelectListener(new com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener() {
//            @Override
//            public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
//                if (selectedList != null && selectedList.size() > 0) {
//                    screenSeasonTagList=new ArrayList<ScreenTagBean>();
//                    for (int i : selectedList) {
//                        ScreenTagBean tag=new ScreenTagBean();
//                        tag.setType(2);
//                        tag.setId(TagDataUtils.getSeasonTagList().get(i).getTagId());
//                        tag.setName(TagDataUtils.getSeasonTagList().get(i).getTagName());
//                        screenSeasonTagList.add(tag);
//                    }
//                    showData();
//                } else {
//                    screenSeasonTagList=new ArrayList<ScreenTagBean>();
//                    showData();
//                    Toast.makeText(mContext,"没有选择季节标签！",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }

    public void showData() {
        screenTagBeanList=new ArrayList<>();
        for (int i=0;i<screenYearTagList.size();i++){
            ScreenTagBean tag=new ScreenTagBean();
            tag.setType(screenYearTagList.get(i).getType());
            tag.setId(screenYearTagList.get(i).getId());
            tag.setName(screenYearTagList.get(i).getName());
            screenTagBeanList.add(tag);
        }
        for (int i=0;i<screenSeasonTagList.size();i++){
            ScreenTagBean tag=new ScreenTagBean();
            tag.setType(screenSeasonTagList.get(i).getType());
            tag.setId(screenSeasonTagList.get(i).getId());
            tag.setName(screenSeasonTagList.get(i).getName());
            screenTagBeanList.add(tag);
        }
        for (int i=0;i<screenCategoryTagList.size();i++){
            ScreenTagBean tag=new ScreenTagBean();
            tag.setType(screenCategoryTagList.get(i).getType());
            tag.setId(screenCategoryTagList.get(i).getId());
            tag.setName(screenCategoryTagList.get(i).getName());
            screenTagBeanList.add(tag);
        }
        for (int i=0;i<screenBandTagList.size();i++){
            ScreenTagBean tag=new ScreenTagBean();
            tag.setType(screenBandTagList.get(i).getType());
            tag.setId(screenBandTagList.get(i).getId());
            tag.setName(screenBandTagList.get(i).getName());
            screenTagBeanList.add(tag);
        }
        for (int i=0;i<screenGoodsCategoryTagList.size();i++){
            ScreenTagBean tag=new ScreenTagBean();
            tag.setType(screenGoodsCategoryTagList.get(i).getType());
            tag.setId(screenGoodsCategoryTagList.get(i).getId());
            tag.setName(screenGoodsCategoryTagList.get(i).getName());
            screenTagBeanList.add(tag);
        }
        if(screenTagBeanList.size()!=0){
            ll_screen.setVisibility(View.VISIBLE);
        }else{
            ll_screen.setVisibility(View.GONE);
        }
        screenTagScreenAdapter = new TagScreenAdapter<>(mContext);
        tag_screen_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);
        tag_screen_flow_layout.setAdapter(screenTagScreenAdapter);
        screenTagScreenAdapter.onlyAddAll(screenTagBeanList);
    }

    /**
     * 获取选中数据
     */
    public void clickShow(){
        StringBuilder sb = new StringBuilder();
        final List<Node> allNodes = mAdapter.getAllNodes();
        for (int i = 0; i < allNodes.size(); i++) {
            if (allNodes.get(i).isChecked()){
                sb.append(allNodes.get(i).getType()+"   ");
            }
        }
        String strNodesName = sb.toString();
        if (!TextUtils.isEmpty(strNodesName))
            Toast.makeText(this, strNodesName.substring(0, strNodesName.length()-1),Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("商品过滤");
        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tvLeftTitle.setText("首页");
        tvRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvRightTitle.setText("确定");
        tvRightTitle.setVisibility(View.VISIBLE);

        tvShop=(TextView) findViewById(R.id.tv_shop);
        tvShop.setOnClickListener(this);
        tvInstitution=(TextView) findViewById(R.id.tv_institution);
        tvInstitution.setOnClickListener(this);
        tvArea=(TextView) findViewById(R.id.tv_area);
        tvArea.setOnClickListener(this);
        tvGoodsCategory=(TextView) findViewById(R.id.tv_goods_category);
        tvGoodsCategory.setOnClickListener(this);
        tvs.add(tvShop);
        tvs.add(tvInstitution);
        tvs.add(tvArea);
        tvs.add(tvGoodsCategory);

        rv_tree= (RecyclerView) findViewById(R.id.rv_tree);
        rv_tree.setLayoutManager(new LinearLayoutManager(this));
        ll_band= (LinearLayout) findViewById(R.id.ll_band);
        ll_category= (LinearLayout) findViewById(R.id.ll_category);
        ll_year_season= (LinearLayout) findViewById(R.id.ll_year_season);
        ll_screen= (LinearLayout) findViewById(R.id.ll_screen);
        ll_goods_category= (LinearLayout) findViewById(R.id.ll_goods_category);
        tag_year_flow_layout= (FlowTagLayout) findViewById(R.id.tag_year_flow_layout);
        tag_season_flow_layout= (FlowTagLayout) findViewById(R.id.tag_season_flow_layout);
        tag_band_flow_layout= (FlowTagLayout) findViewById(R.id.tag_band_flow_layout);
        tag_category_flow_layout= (FlowTagLayout) findViewById(R.id.tag_category_flow_layout);
        tag_screen_flow_layout= (FlowTagLayout) findViewById(R.id.tag_screen_flow_layout);
        tag_goods_category_flow_layout= (FlowTagLayout) findViewById(R.id.tag_goods_category_flow_layout);

        tvRightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.putExtra("selectList",(Serializable)screenTagBeanList);
                setResult(resultCode, mIntent);// 设置结果，并进行传送
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int index=-1;
        switch (v.getId()){
            case R.id.tv_shop:
                index=0;
                ll_year_season.setVisibility(View.VISIBLE);
                ll_category.setVisibility(View.GONE);
                ll_band.setVisibility(View.GONE);
                ll_goods_category.setVisibility(View.GONE);
                break;
            case R.id.tv_institution:
                index=1;
                ll_category.setVisibility(View.VISIBLE);
                ll_year_season.setVisibility(View.GONE);
                ll_band.setVisibility(View.GONE);
                ll_goods_category.setVisibility(View.GONE);
                break;
            case R.id.tv_area:
                index=2;
                ll_band.setVisibility(View.VISIBLE);
                ll_category.setVisibility(View.GONE);
                ll_year_season.setVisibility(View.GONE);
                ll_goods_category.setVisibility(View.GONE);
                break;
            case R.id.tv_goods_category:
                index=3;
                ll_goods_category.setVisibility(View.VISIBLE);
                ll_band.setVisibility(View.GONE);
                ll_category.setVisibility(View.GONE);
                ll_year_season.setVisibility(View.GONE);
                break;
        }
        setColor(index);
    }

    private void setColor(int index) {
        for(int i=0;i<tvs.size();i++){
            tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.darkcyan));
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
    }
}
