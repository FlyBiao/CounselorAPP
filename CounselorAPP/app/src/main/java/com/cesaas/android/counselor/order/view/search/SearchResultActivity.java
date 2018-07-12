package com.cesaas.android.counselor.order.view.search;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.BasePagerAdapter;
import com.cesaas.android.counselor.order.base.BaseSearchView;
import com.cesaas.android.counselor.order.bean.ResultGetListByShopIdBean;
import com.cesaas.android.counselor.order.utils.AbAppUtil;
import com.cesaas.android.counselor.order.utils.AbOtherUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.AutoWrapLinearLayout;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


/**
 * 查询结果页面
 * @author fgb
 *
 */
@ContentView(R.layout.search_layout)
public class SearchResultActivity extends BasesActivity{

	@ViewInject(R.id.il_shopfans_back)
	private LinearLayout il_shopfans_back;
	@ViewInject(R.id.hl_editext_shopfans)
	private EditText hl_editext_shopfans;
	@ViewInject(R.id.sl_go_btn)
	private TextView sl_go_btn;
	@ViewInject(R.id.sl_viewpage)
	private ViewPager sl_viewpage;
	@ViewInject(R.id.sl_cursor)
	private ImageView sl_cursor;
	@ViewInject(R.id.sl_hitory)
	private AutoWrapLinearLayout sl_hitory;
	@ViewInject(R.id.search_shopfans)
	private TextView search_shopfans;
	@ViewInject(R.id.sl_clean)
	private TextView sl_clean;
	@ViewInject(R.id.sl_index)
	private LinearLayout sl_index;
	@ViewInject(R.id.sl_content)
	private LinearLayout sl_content;
	
	
	private boolean isgo = false;//是否搜索
	private boolean click = false;//是否点击
	
	private SearchShopFansView fansView;
	private ArrayList<View> pageView; // 页面集合
	private int pWidth;// 图片宽度
	private int currentIndex; // 当前标签位置
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		shopFansBack();
//		initData();
	}
	
	/**
	 * 初始化数据
	 */
	public void initData(){
		/**
		 * 输入框内容变化监听
		 */
		hl_editext_shopfans.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					isgo = true;
					sl_go_btn.setText(getRstring(R.string.search_btn));
				} else {
					isgo = false;
					sl_go_btn.setText(getRstring(R.string.search_cancel));
				}				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/**
		 * 键盘按钮事件
		 */
		hl_editext_shopfans.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				onSearch();
				return true;
			}
		});
		
		fansView = new SearchShopFansView(mContext);
		// 页面添加
		pageView = new ArrayList<View>();
		pageView.add(fansView);
		sl_viewpage.setAdapter(new BasePagerAdapter(pageView));
		sl_viewpage.addOnPageChangeListener(myChangeListen);
		initCursor();
	}
	
	/**
	 * 初始化光标
	 */
	public void initCursor() {
		// step5>>初始化横向划线的位置，需要计算偏移量并移动ImageView，有两个方法
		// 通过BitmapFactory获得横向划线图片宽度
		pWidth = AbAppUtil.getDeviceWidth(mContext) / 2;
		sl_cursor.getLayoutParams().width = pWidth;
	}

	@Override
	protected void onResume() {
		super.onResume();
		String pre = prefs.getString("search_history", "");
		sl_hitory.removeAllViews();
		if (!"".equals(pre)) {
			if (pre.contains(",")) {
				String[] historys = pre.split(",");
				int d = 0;
				for (int x = (historys.length - 1); x >= 0; x--) {
					d++;
					if (d == 18) {
						return;
					}
					TextView content = (TextView) getLayoutInflater().inflate(R.layout.text_layout, sl_hitory, false);
					content.setText(historys[x]);
					content.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							hl_editext_shopfans.setText(((TextView) v).getText().toString());
							hl_editext_shopfans.setSelection(hl_editext_shopfans.length());
							click = true;
							onSearch();
						}
					});
					sl_hitory.addView(content);
				}
			} else {
				TextView content = (TextView) getLayoutInflater().inflate(R.layout.text_layout, sl_hitory, false);
				content.setText(pre);
				content.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						hl_editext_shopfans.setText(((TextView) v).getText().toString());
						hl_editext_shopfans.setSelection(hl_editext_shopfans.length());
						click = true;
						onSearch();
					}
				});
				sl_hitory.addView(content);
			}
		}
	}
	
	/**
	 * 点击查询事件
	 */
	@OnClick({ R.id.sl_go_btn, R.id.search_shopfans, R.id.search_mobile, R.id.sl_clean })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sl_go_btn:
			if (isgo) {
				onSearch();
			} else
				onExit();
			break;
		case R.id.search_shopfans:
			sl_viewpage.setCurrentItem(0);
			break;
		case R.id.search_mobile:
			sl_viewpage.setCurrentItem(1);
			break;
		case R.id.sl_clean:
			sl_hitory.removeAllViews();
			prefs.putString("search_history", "");
			break;
		}
	}
	
	
	/**
	 * 页改变监听器
	 */
	OnPageChangeListener myChangeListen = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			// 获取每一个标签页所占宽度
			// currentIndex为上一次滑动所处标签页位置(zero,1,2)
			Animation animation = new TranslateAnimation(pWidth * currentIndex, pWidth * position, 0, 0);
			currentIndex = position;
			animation.setDuration(300l);
			animation.setFillAfter(true);// 动画结束后停留在当前所处位置
			sl_cursor.startAnimation(animation);
			// ((BaseMessageView) pageView.get(position)).mRequest();
			
		}
		
	};
	
	/**
	 * 查询
	 */
	public void onSearch(){
		String key = hl_editext_shopfans.getText().toString().trim();
		if (AbOtherUtil.textVerify(mContext, key)) {
			for (int i = 0; i < pageView.size(); i++) {
				((BaseSearchView) pageView.get(i)).mRequest(key);
			}
			hl_editext_shopfans.setText("");
			if (!click) {
				prefs.putString("search_history", !"".equals(prefs.getString("search_history", ""))
						? (prefs.getString("search_history", "") + "," + key) : key);
			}
			click = false;
			AbAppUtil.hideSoftInput(mContext, hl_editext_shopfans);
		}
	}
	
	//
	public void onEventMainThread(ResultGetListByShopIdBean msg) {
		initData();
		if (sl_index.getVisibility() == View.VISIBLE) {
			sl_index.setVisibility(View.GONE);
		}
		sl_content.setVisibility(View.VISIBLE);
		if (fansView != null)
			fansView.onEventMainThread(msg);
	}
	
	/**
	 * 返回上一个 
	 */
	public void shopFansBack(){
		il_shopfans_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
}
