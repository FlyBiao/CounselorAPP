package com.cesaas.android.counselor.order.member.service;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseTabBean;
import com.cesaas.android.counselor.order.base.BaseTabsBean;
import com.cesaas.android.counselor.order.member.bean.service.SearchVipEventBean;
import com.cesaas.android.counselor.order.member.fragment.MemberInfoUpdateFragment;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.lidroid.xutils.view.annotation.ContentView;

import io.rong.eventbus.EventBus;

/**
 * 会员服务信息
 * @author FGB
 *
 */

@ContentView(R.layout.activity_member_info_update_list_layout)
public class MemberInfoUpdateListActivity extends BasesActivity implements OnClickListener{

	private TextView tvTitle;
	private LinearLayout llBack;
	private LinearLayout ll_search;
	private MClearEditText et_search;
	private int TabType=10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initClickListener();
		initData();
	}

	private void initData(){
		getSupportFragmentManager().beginTransaction().add(R.id.member_info_update_list_frame_layout, new MemberInfoUpdateFragment()).commit();
	}
	private void initClickListener(){
		llBack.setOnClickListener(this);
	}
	private void initView() {
		tvTitle= (TextView) findViewById(R.id.tv_base_title);
		tvTitle.setText("会员资料审核");
		llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
		ll_search= (LinearLayout) findViewById(R.id.ll_search);
		ll_search.setOnClickListener(this);
		et_search= (MClearEditText) findViewById(R.id.et_search_content);
		et_search.setHint("按手机号，会员名称搜索");
		et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					SearchVipEventBean bean=new SearchVipEventBean();
					bean.setType(TabType);
					bean.setContent(et_search.getText().toString());
					EventBus.getDefault().post(bean);
				}
				return false;
			}
		});
	}

	public void onEventMainThread(BaseTabsBean msg) {
		TabType=msg.getTabType();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.ll_base_title_back:
				Skip.mBack(mActivity);
				break;
			case R.id.ll_search:
				SearchVipEventBean bean=new SearchVipEventBean();
				bean.setType(TabType);
				bean.setContent(et_search.getText().toString());
				EventBus.getDefault().post(bean);
				break;
		}
	}
}
