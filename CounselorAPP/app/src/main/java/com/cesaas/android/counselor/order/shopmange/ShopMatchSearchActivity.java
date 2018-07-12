package com.cesaas.android.counselor.order.shopmange;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.shopmange.adapter.ShopMacthSearchRecordTagAdapter;
import com.cesaas.android.counselor.order.shopmange.adapter.ShopSeasonTagAdapter;
import com.cesaas.android.counselor.order.shopmange.bean.ShopSearchRecordBean;
import com.cesaas.android.counselor.order.shopmange.bean.ShopSeasonBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.db.DBConstant;
import com.cesaas.android.counselor.order.utils.db.ShopMatchSQLiteDatabaseUtils;
import com.cesaas.android.counselor.order.widget.MClearEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品搭配查询
 */
public class ShopMatchSearchActivity extends BasesActivity implements View.OnClickListener{
    private LinearLayout mBack;
    private TextView mTvTitle;
    private TextView tvDeleteRecord,tvSearchName;
    private MClearEditText etSearchValue;
    private FlowTagLayout tag_flow_layout,tag_flow_season_layout;

    private int resultCode=1000;
    private Typeface font;

    private ShopMatchSQLiteDatabaseUtils databaseUtils;
    private static List<ShopSearchRecordBean> searchRecordBeanList;
    private ShopMacthSearchRecordTagAdapter<ShopSearchRecordBean> mBaseTagAdapter;
    private static List<ShopSeasonBean> shopSeasonBeen;
    private ShopSeasonTagAdapter<ShopSeasonBean> seasonTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_match_search);
        font= Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");//记得加上这句

        initView();
        createSqliteDB();
    }

    private void initSeasoData() {
        shopSeasonBeen=new ArrayList<>();
        ShopSeasonBean bean=new ShopSeasonBean();
        bean.setId(1);
        bean.setName("春节");
        ShopSeasonBean bean1=new ShopSeasonBean();
        bean1.setId(2);
        bean1.setName("夏季");
        ShopSeasonBean bean2=new ShopSeasonBean();
        bean2.setId(3);
        bean2.setName("秋季");
        ShopSeasonBean bean3=new ShopSeasonBean();
        bean3.setId(4);
        bean3.setName("冬季");
        shopSeasonBeen.add(bean);
        shopSeasonBeen.add(bean1);
        shopSeasonBeen.add(bean2);
        shopSeasonBeen.add(bean3);

        seasonTagAdapter=new ShopSeasonTagAdapter<>(mContext);
        tag_flow_season_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tag_flow_season_layout.setAdapter(seasonTagAdapter);
        seasonTagAdapter.onlyAddAll(shopSeasonBeen);

        tag_flow_season_layout.setOnTagSelectListener(new com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    for (final int i : selectedList) {
                        Intent mIntent = new Intent();
                        mIntent.putExtra("seasonSel","true");
                        mIntent.putExtra("searchValue",shopSeasonBeen.get(i).getName());
                        setResult(resultCode, mIntent);// 设置结果，并进行传送
                        finish();
                    }
                } else {
                    Toast.makeText(mContext,"没有选择标签！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onEventMainThread(List<ShopSearchRecordBean> bean) {
        searchRecordBeanList=new ArrayList<>();
        searchRecordBeanList=bean;

        mBaseTagAdapter=new ShopMacthSearchRecordTagAdapter<>(mContext);
        tag_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tag_flow_layout.setAdapter(mBaseTagAdapter);
        mBaseTagAdapter.onlyAddAll(searchRecordBeanList);

        tag_flow_layout.setOnTagSelectListener(new com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    for (final int i : selectedList) {
                        Intent mIntent = new Intent();
                        mIntent.putExtra("searchValue",searchRecordBeanList.get(i).getName());
                        setResult(resultCode, mIntent);// 设置结果，并进行传送
                        finish();
                    }
                } else {
                    Toast.makeText(mContext,"没有选择标签！", Toast.LENGTH_LONG).show();
                }
            }
        });

        initSeasoData();

    }

    private void createSqliteDB() {
        databaseUtils=new ShopMatchSQLiteDatabaseUtils(mContext,null,null, DBConstant.VERSION);
        databaseUtils.createDB(mContext, DBConstant.DB, DBConstant.VERSION);
        databaseUtils.selectData(mContext);
    }

    private void initView() {
        tag_flow_season_layout= (FlowTagLayout) findViewById(R.id.tag_flow_season_layout);
        tag_flow_layout= (FlowTagLayout) findViewById(R.id.tag_flow_layout);
        etSearchValue= (MClearEditText) findViewById(R.id.et_search_value);
        etSearchValue.setOnClickListener(this);
        tvSearchName= (TextView) findViewById(R.id.tv_search_name);
        tvSearchName.setOnClickListener(this);

        mBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mTvTitle= (TextView) findViewById(R.id.tv_base_title);
        mTvTitle.setText("商品搜索");
        tvDeleteRecord= (TextView) findViewById(R.id.tv_del_record);
        tvDeleteRecord.setTypeface(font);
        tvDeleteRecord.setText(R.string.del);

        mBack.setOnClickListener(this);
        tvDeleteRecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_search_name:
                if(!TextUtils.isEmpty(etSearchValue.getText().toString())){
                    databaseUtils.insterData(mContext,1,etSearchValue.getText().toString());
                }
                Intent mIntent = new Intent();
                mIntent.putExtra("searchValue",etSearchValue.getText().toString());
                etSearchValue.setText("");
                setResult(resultCode, mIntent);// 设置结果，并进行传送
                finish();
                break;
            case R.id.tv_del_record:
                databaseUtils.delete(mContext);
                databaseUtils.selectData(mContext);
                break;
        }
    }
}
