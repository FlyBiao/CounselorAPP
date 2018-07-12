package com.cesaas.android.counselor.order.member;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.adapter.SearchRecordAdapter;
import com.cesaas.android.counselor.order.member.adapter.VipSearchRecordTagAdapter;
import com.cesaas.android.counselor.order.member.bean.SearchRecordBean;
import com.cesaas.android.counselor.order.shopmange.adapter.SearchRecordTagAdapter;
import com.cesaas.android.counselor.order.shopmange.bean.ClerkSearchRecordBean;
import com.cesaas.android.counselor.order.utils.db.DBConstant;
import com.cesaas.android.counselor.order.utils.db.VipSQLiteDatabaseUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员搜索页面
 */
public class VipSearchActivity extends BasesActivity implements View.OnClickListener{
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private LinearLayout mBack;
    private TextView mTvTitle;
    private TextView tvDeleteRecord,tvSearchName;
    private MClearEditText etSearchValue;

    private int resultCode=1000;
    private Typeface font;
    private VipSQLiteDatabaseUtils databaseUtils;

    private SearchRecordAdapter mSearchRecordAdapter;
    private List<SearchRecordBean> searchRecordBeen;
    private VipSearchRecordTagAdapter<SearchRecordBean> mBaseTagAdapter;

    private FlowTagLayout tag_flow_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_search);
        font= Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");//记得加上这句

        initView();
        createSqliteDB();
    }

    public void onEventMainThread(List<SearchRecordBean> bean) {
        searchRecordBeen=new ArrayList<>();
        searchRecordBeen=bean;

//        mSearchRecordAdapter=new SearchRecordAdapter(searchRecordBeen);
//        mSearchRecordAdapter.setOnItemClickListener(onItemClickListener);
//        mSwipeMenuRecyclerView.setAdapter(mSearchRecordAdapter);

        mBaseTagAdapter=new VipSearchRecordTagAdapter<>(mContext);
        tag_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tag_flow_layout.setAdapter(mBaseTagAdapter);
        mBaseTagAdapter.onlyAddAll(searchRecordBeen);

        tag_flow_layout.setOnTagSelectListener(new com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    for (final int i : selectedList) {
                        Intent mIntent = new Intent();
                        mIntent.putExtra("searchValue",searchRecordBeen.get(i).getName());
                        setResult(resultCode, mIntent);// 设置结果，并进行传送
                        finish();
                    }
                } else {
                    Toast.makeText(mContext,"没有选择标签！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //创建sqlite数据库
    private void createSqliteDB(){
        databaseUtils=new VipSQLiteDatabaseUtils(mContext,null,null, DBConstant.VERSION);
        databaseUtils.createDB(mContext, DBConstant.DB, DBConstant.VERSION);
        databaseUtils.selectData(mContext);
    }

    /**
     * OnItemClickListener
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Intent mIntent = new Intent();
            mIntent.putExtra("searchValue",searchRecordBeen.get(position).getName());
            setResult(resultCode, mIntent);// 设置结果，并进行传送
            finish();
        }
    };

    private void initView() {
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_vip_record_list_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
//        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。

        tag_flow_layout= (FlowTagLayout) findViewById(R.id.tag_flow_layout);
        etSearchValue= (MClearEditText) findViewById(R.id.et_search_value);
        etSearchValue.setOnClickListener(this);
        tvSearchName= (TextView) findViewById(R.id.tv_search_name);
        tvSearchName.setOnClickListener(this);

        mBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mTvTitle= (TextView) findViewById(R.id.tv_base_title);
        mTvTitle.setText("会员搜索");
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
                databaseUtils.insterData(mContext,1,etSearchValue.getText().toString());
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
