package com.cesaas.android.counselor.order.boss.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.adapter.AreaListAdapter;
import com.cesaas.android.counselor.order.boss.adapter.OrganizationAdapter;
import com.cesaas.android.counselor.order.boss.adapter.ShopListAdapter;
import com.cesaas.android.counselor.order.boss.bean.ResultShopListBean;
import com.cesaas.android.counselor.order.boss.bean.SelectShopListBean;
import com.cesaas.android.counselor.order.boss.bean.ShopListBean;
import com.cesaas.android.counselor.order.boss.net.ShopListNet;
import com.cesaas.android.counselor.order.boss.ui.fragment.SearchFragment;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.yokeyword.indexablerv.EntityWrapper;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

public class SelectShopActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle,tvSelectAll,tvLeftTitle,tvShopSize;
    private TextView tvShop,tvInstitution,tvArea;
    private Button btnSure;
    private LinearLayout llBack;

    private SearchFragment mSearchFragment;
    private SearchView mSearchView;

    private Dialog areaDialog;
    private View areaDialogContentView;
    private Dialog organizationDialog;
    private View organizationDialogView;

    private IndexableLayout indexableLayout;
    private ShopListAdapter mAdapter;

    private List<ShopListBean> lists;
    private String leftTitle;
    private int resultCode=201;

    private ArrayList<TextView> tvs=new ArrayList<TextView>();
    private List<SelectShopListBean> shopListBeanList;
    private List<ShopListBean> shopList;
    private List<ShopListBean> shopAreaList;
    private List<ShopListBean> shopOrganizationList;

    private ShopListNet shopListNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shop);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            leftTitle=bundle.getString("LeftTitle");
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
        if(msg.IsSuccess!=false && msg.TModel.size()!=0){
            shopList=new ArrayList<>();
            shopList=msg.TModel;
            // set Datas
            mAdapter.setDatas(initDatas(shopList));
            // set Material Design OverlayView
            indexableLayout.setOverlayStyle_MaterialDesign(Color.RED);
            // 全字母排序。  排序规则设置为：每个字母都会进行比较排序
            indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);

            lists=initDatas(shopList);
            mSearchFragment.bindDatas(lists);
        }
    }

    private  void initData(){
        shopListNet=new ShopListNet(mContext);
        shopListNet.setData();
    }

    /**
     * 接收EventBus
     * @param listBeen 列表集合
     */
	public void onEventMainThread(List<SelectShopListBean> listBeen) {
        if(listBeen.size()!=0){
            shopListBeanList=new ArrayList<>();
            shopListBeanList=listBeen;
            tvShopSize.setText(listBeen.size()+"");
        }
    }

    private void initView() {
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("选择店铺");
        tvSelectAll= (TextView) findViewById(R.id.tv_message);
        tvSelectAll.setVisibility(View.VISIBLE);
        tvSelectAll.setText("全选");
        tvShopSize= (TextView) findViewById(R.id.tv_shop_size);
        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tvLeftTitle.setText(leftTitle);

        btnSure= (Button) findViewById(R.id.btn_sure);

        tvShop=(TextView) findViewById(R.id.tv_shop);
        tvShop.setOnClickListener(this);
        tvInstitution=(TextView) findViewById(R.id.tv_institution);
        tvInstitution.setOnClickListener(this);
        tvArea=(TextView) findViewById(R.id.tv_area);
        tvArea.setOnClickListener(this);
        tvs.add(tvShop);
        tvs.add(tvInstitution);
        tvs.add(tvArea);

        mSearchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment);
        mSearchView = (SearchView) findViewById(R.id.searchview);

        indexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);
        indexableLayout.setLayoutManager(new LinearLayoutManager(this));
        // 多音字处理
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this)));

        // setAdapter
        mAdapter = new ShopListAdapter(this);
        indexableLayout.setAdapter(mAdapter);

        mAdapter.setDatas(lists, new IndexableAdapter.IndexCallback<ShopListBean>() {
            @Override
            public void onFinished(List<EntityWrapper<ShopListBean>> datas) {
                // 数据处理完成后回调
                mSearchFragment.bindDatas(lists);
            }
        });

//        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<ShopListBean>() {
//            @Override
//            public void onItemClick(View v, int originalPosition, int currentPosition, ShopListBean entity) {
//                if (originalPosition >= 0) {
////                    ToastFactory.getLongToast(SelectShopActivity.this, "选中:" + entity.getNick() + "  当前位置:" + currentPosition + "  原始所在数组位置:" + originalPosition);
//                } else {
////                    ToastFactory.getLongToast(SelectShopActivity.this, "选中Header/Footer:" + entity.getNick() + "  当前位置:" + currentPosition);
//                }
//            }
//        });

        // 搜索
        initSearch();
    }

    private List<ShopListBean> initDatas(List<ShopListBean> lists) {
        List<ShopListBean> list = new ArrayList<>();
        // 初始化数据
//        List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.city_array));
//        List<String> mobileStrings =Arrays.asList(getResources().getStringArray(R.array.city_join));

        for (int i = 0; i < lists.size(); i++) {
            ShopListBean contactEntity = new ShopListBean(lists.get(i).getShopName(), lists.get(i).getOrganizationName(),lists.get(i).getShopId(),false);
            list.add(contactEntity);
        }
        return list;
    }

    private void initSearch() {
        getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0) {
                    if (mSearchFragment.isHidden()) {
                        getSupportFragmentManager().beginTransaction().show(mSearchFragment).commit();
                    }
                } else {
                    if (!mSearchFragment.isHidden()) {
                        getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();
                    }
                }
                mSearchFragment.bindQueryText(newText);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!mSearchFragment.isHidden()) {
            // 隐藏 搜索
            mSearchView.setQuery(null, false);
            getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int index=-1;
        switch (v.getId()){
            case R.id.tv_shop:
                index=0;
                initData();
                break;
            case R.id.tv_institution:
                index=1;
                if(shopList.size()!=0){
                    showOrganizationDialog();
                }else{
                    ToastFactory.getLongToast(mContext,"当前数据可获取");
                }
                break;
            case R.id.tv_area:
                index=2;
                if(shopList.size()!=0){
                    showAreaDialog();
                }else{
                    ToastFactory.getLongToast(mContext,"当前数据可获取");
                }
                break;
        }
        setColor(index);
    }

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
                for (int i=0;i<shopList.size();i++){
                    shopList.get(i).setBo(true);
                }
            }
        });

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopListBeanList.size()!=0){
                    Intent mIntent = new Intent();
                    mIntent.putExtra("selectList",(Serializable)shopListBeanList);
                    setResult(resultCode, mIntent);// 设置结果，并进行传送
                    finish();
                }else{
                    ToastFactory.getLongToast(mContext,"请选择需要查询的店铺！");
                }
            }
        });
    }

    private ListView lv_area;
    private AreaListAdapter areaListAdapter;
    public void showAreaDialog(){
        areaDialog=new Dialog(this, R.style.BottomDialog);
        areaDialogContentView = LayoutInflater.from(this).inflate(R.layout.dialog_area_content, null);
        areaDialog.setContentView(areaDialogContentView);
        ViewGroup.LayoutParams layoutParams = areaDialogContentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        areaDialogContentView.setLayoutParams(layoutParams);

        lv_area= (ListView) areaDialogContentView.findViewById(R.id.lv_area);

        areaDialog.getWindow().setGravity(Gravity.BOTTOM);
        areaDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        areaDialog.setCanceledOnTouchOutside(true);
        areaDialog.show();

        areaListAdapter=new AreaListAdapter(shopList,mContext);
        lv_area.setAdapter(areaListAdapter);
        lv_area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shopAreaList=new ArrayList<>();
                for (int i=0;i<shopList.size();i++){
                    if(shopList.get(i).getAreaId()==shopList.get(position).getAreaId()){
                        ShopListBean shopListBean=new ShopListBean();
                        shopListBean.setAreaName(shopList.get(i).getAreaName());
                        shopListBean.setShopName(shopList.get(i).getShopName());
                        shopAreaList.add(shopListBean);
                    }
                }

                // set Datas
                mAdapter.setDatas(initDatas(shopAreaList));
                // set Material Design OverlayView
                indexableLayout.setOverlayStyle_MaterialDesign(Color.RED);
                // 全字母排序。  排序规则设置为：每个字母都会进行比较排序
                indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);

                lists=initDatas(shopAreaList);
                mSearchFragment.bindDatas(lists);

                areaDialog.dismiss();
            }
        });
    }

    private RecyclerView lv_organization;
    private OrganizationAdapter organizationAdapter;
    public void showOrganizationDialog(){
        organizationDialog=new Dialog(this, R.style.BottomDialog);
        organizationDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_organization_content, null);
        organizationDialog.setContentView(organizationDialogView);
        ViewGroup.LayoutParams layoutParams = organizationDialogView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        organizationDialogView.setLayoutParams(layoutParams);

        lv_organization= (RecyclerView) organizationDialogView.findViewById(R.id.lv_organization);
        lv_organization.setLayoutManager(new LinearLayoutManager(mContext));

        organizationDialog.getWindow().setGravity(Gravity.BOTTOM);
        organizationDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        organizationDialog.setCanceledOnTouchOutside(true);
        organizationDialog.show();

        organizationAdapter=new OrganizationAdapter(shopList);
        lv_organization.setAdapter(organizationAdapter);
        lv_organization.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                shopOrganizationList=new ArrayList<>();
                for (int i=0;i<shopList.size();i++){
                    if(shopList.get(i).getAreaId()==shopList.get(position).getAreaId()){
                        ShopListBean shopListBean=new ShopListBean();
                        shopListBean.setAreaName(shopList.get(i).getAreaName());
                        shopListBean.setShopName(shopList.get(i).getShopName());
                        shopOrganizationList.add(shopListBean);
                    }
                }
                // set Datas
                mAdapter.setDatas(initDatas(shopOrganizationList));
                // set Material Design OverlayView
                indexableLayout.setOverlayStyle_MaterialDesign(Color.RED);
                // 全字母排序。  排序规则设置为：每个字母都会进行比较排序
                indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
                lists=initDatas(shopOrganizationList);
                mSearchFragment.bindDatas(lists);

                organizationDialog.dismiss();
            }
        });
    }
}
