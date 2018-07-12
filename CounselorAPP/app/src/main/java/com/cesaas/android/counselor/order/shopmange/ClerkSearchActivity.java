package com.cesaas.android.counselor.order.shopmange;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.shopmange.adapter.ClerkSearchRecordAdapter;
import com.cesaas.android.counselor.order.shopmange.adapter.SearchRecordTagAdapter;
import com.cesaas.android.counselor.order.shopmange.bean.ClerkSearchRecordBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.db.DBConstant;
import com.cesaas.android.counselor.order.utils.db.DBHelp;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 店员查询页面
 */
public class ClerkSearchActivity extends BasesActivity implements View.OnClickListener{

    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private LinearLayout mBack;
    private TextView mTvTitle;
    private TextView tvDeleteRecord,tvSearchName;
    private MClearEditText etSearchValue;

    private FlowTagLayout tag_flow_layout;

    private int resultCode=1000;
    private Typeface font;
    private DBHelp help;

    private List<ClerkSearchRecordBean> clerkSearchRecordBeen;
    private ClerkSearchRecordAdapter searchRecordAdapter;
    private SearchRecordTagAdapter<ClerkSearchRecordBean> mBaseTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_search_aactivity);
        initView();
        initData();
    }

    private void initData() {
        help=new DBHelp(mContext,null,null, DBConstant.VERSION);
        help.createDB(mContext,DBConstant.DB,DBConstant.VERSION);
        help.selectData(mContext);
    }

    public void onEventMainThread(List<ClerkSearchRecordBean> bean) {
        clerkSearchRecordBeen=new ArrayList<>();
        clerkSearchRecordBeen=bean;

//        searchRecordAdapter=new ClerkSearchRecordAdapter(clerkSearchRecordBeen);
//        searchRecordAdapter.setOnItemClickListener(onItemClickListener);
//        mSwipeMenuRecyclerView.setAdapter(searchRecordAdapter);

        mBaseTagAdapter=new SearchRecordTagAdapter<>(mContext);
        tag_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tag_flow_layout.setAdapter(mBaseTagAdapter);
        mBaseTagAdapter.onlyAddAll(clerkSearchRecordBeen);

        tag_flow_layout.setOnTagSelectListener(new com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    for (final int i : selectedList) {
                        Intent mIntent = new Intent();
                        mIntent.putExtra("searchValue",clerkSearchRecordBeen.get(i).getName());
                        setResult(resultCode, mIntent);// 设置结果，并进行传送
                        finish();
                    }
                } else {
                    Toast.makeText(mContext,"没有选择标签！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * OnItemClickListener
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Intent mIntent = new Intent();
            mIntent.putExtra("searchValue",clerkSearchRecordBeen.get(position).getName());
            setResult(resultCode, mIntent);// 设置结果，并进行传送
            finish();
        }
    };

    private void initView() {
        font= Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");//记得加上这句
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
        mTvTitle.setText("店员搜索");
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
                    help.insterData(mContext,etSearchValue.getText().toString());
                }
                Intent mIntent = new Intent();
                mIntent.putExtra("searchValue",etSearchValue.getText().toString());
                etSearchValue.setText("");
                setResult(resultCode, mIntent);// 设置结果，并进行传送
                finish();
                break;
            case R.id.tv_del_record:
                help.delete(mContext);
                help.selectData(mContext);
                break;
        }
    }
}
