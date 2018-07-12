package com.cesaas.android.counselor.order.fans.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.fans.fragment.MyFansActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.index.decoration.DividerItemDecoration;
import com.cesaas.android.counselor.order.widget.index.decoration.TitleItemDecoration;
import com.cesaas.android.counselor.order.widget.indexbar.CityAdapter;
import com.cesaas.android.counselor.order.widget.indexbar.CityBean;
import com.cesaas.android.counselor.order.widget.indexbar.IndexBar;

/**
 * 粉丝标签详情页
 * @author FGB
 *
 */
public class FansLabelDetailAactivity extends BasesActivity implements OnClickListener{
	
	private LinearLayout ll_fans_label_detail_back,ll_add_label_fans,ll_fans_group_message;
	private TextView tv_label_title;
	
	private RecyclerView mRv;
    private CityAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas;

    private TitleItemDecoration mDecoration;

    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fans_label_detail_layout);
		
		initView();
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			tv_label_title.setText(bundle.getString("label"));
		}
		
		mRv.setLayoutManager(mManager = new LinearLayoutManager(this));
        //initDatas();
//        initDatas(getResources().getStringArray(R.array.provinces));
        //mDatas = new ArrayList<>();//测试为空或者null的情况 已经通过 2016 09 08

        mRv.setAdapter(mAdapter = new CityAdapter(this, mDatas));
        mRv.addItemDecoration(mDecoration = new TitleItemDecoration(this, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        //mRv.addItemDecoration(new TitleItemDecoration2(this,mDatas));
        mRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setmSourceDatas(mDatas);//设置数据源
	}
	
	/**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(String[] data) {
        mDatas = new ArrayList<CityBean>();
        for (int i = 0; i < data.length; i++) {
            CityBean cityBean = new CityBean();
            cityBean.setCity(data[i]);//设置城市名称
            mDatas.add(cityBean);
        }
    }

	private void initView() {
		
		mRv = (RecyclerView) findViewById(R.id.rv);
		//使用indexBar
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar
		
		tv_label_title=(TextView) findViewById(R.id.tv_label_title);
		ll_fans_label_detail_back=(LinearLayout) findViewById(R.id.ll_fans_label_detail_back);
		ll_add_label_fans=(LinearLayout) findViewById(R.id.ll_add_label_fans);
		ll_fans_group_message=(LinearLayout) findViewById(R.id.ll_fans_group_message);
		ll_add_label_fans.setOnClickListener(this);
		ll_fans_group_message.setOnClickListener(this);
		ll_fans_label_detail_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_fans_label_detail_back://返回
			Skip.mBack(mActivity);
			break;
			
		case R.id.ll_add_label_fans://添加粉丝
			Skip.mNext(mActivity, MyFansActivity.class);
			break;
			
		case R.id.ll_fans_group_message://群发消息
			Skip.mNext(mActivity, GroupSendAactivity.class);;
		break;

		default:
			break;
		}
		
	}
}
