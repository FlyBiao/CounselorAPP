package com.cesaas.android.counselor.order.dialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.GetShopFansDetailActivity;
import com.cesaas.android.counselor.order.bean.ResultGetListByShopIdBean.FansListByShopIdBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 会员查询Dialog
 * @author FGB
 *
 */
public class SearchDialog extends BasesActivity {

	ImageView iv_search_back;
	ImageView iv_search_search;
	AutoCompleteTextView actv_search;
	LinearLayout ll_search_history;
	ListView lv_search_history;
	TextView tv_search_history;

	SharedPreferences preferences;

	public static ArrayList<FansListByShopIdBean> fansList = new ArrayList<FansListByShopIdBean>();
	private ArrayList<FansListByShopIdBean> temp = new ArrayList<FansListByShopIdBean>();
	private ArrayList<String> numberList = new ArrayList<String>();
	private ArrayList<String> nameList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_search);

		initView();

		initListener();

		initAutoComplete("history", actv_search);

		temp.clear();
		numberList.clear();
		nameList.clear();
		temp = fansList;
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i).FANS_MOBILE != null) {
				numberList.add(fansList.get(i).FANS_MOBILE);
				nameList.add(fansList.get(i).FANS_NICKNAME);
			}
		}
		String[] arr = new String[numberList.size()];
		for (int j = 0; j < numberList.size(); j++) {
			arr[j] = numberList.get(j) + "   " + nameList.get(j);
		}

		ArrayAdapter adapter = new ArrayAdapter<String>(mContext,
				R.layout.simple_dropdown_item_line, arr);
		actv_search.setAdapter(adapter);

	}

	/**
	 * 初始化监听
	 */
	private void initListener() {
		iv_search_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
		
		tv_search_history.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences sp = getSharedPreferences("history", 0);
				sp.edit().clear().commit();
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext, R.layout.simple_dropdown_item_line);
				lv_search_history.setAdapter(adapter);
				ll_search_history.setVisibility(View.GONE);
			}
		});

		actv_search.addTextChangedListener(new TextWatcher() {
			String content = actv_search.getText().toString().trim();
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
				if (start == 0) {
					ll_search_history.setVisibility(View.VISIBLE);
				} else {
					ll_search_history.setVisibility(View.GONE);
				}
				
				if (content ==null) {
					ll_search_history.setVisibility(View.VISIBLE);
				} else {
					ll_search_history.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		//查询点击事件监听
		iv_search_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String id = "";
				String number = actv_search.getText().toString().trim();
				String name = "";
				Set<String> set = new HashSet<String>();
				for (int i = 0; i < fansList.size(); i++) {
					if (number.equals(fansList.get(i).FANS_MOBILE)) {
						id = fansList.get(i).FANS_ID;
						name = fansList.get(i).FANS_NICKNAME;
						set.add(number);
						set.add(name);
						break;
					}
				}

				Bundle bundle = new Bundle();
				bundle.putString("fansId", id);
				if (id == null || id.equals("")) {
					ToastFactory.show(mContext, "改号码不存在", ToastFactory.CENTER);
				} else {
					saveHistory("history", actv_search);
					SearchDialog.this.finish();
					Skip.mNextFroData(mActivity,
							GetShopFansDetailActivity.class, bundle);
				}
			}
		});

		actv_search.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String str = actv_search.getAdapter().getItem(position)
						.toString();
				actv_search.setText(str.substring(0, 11));
				actv_search.dismissDropDown();
				actv_search.setSelection(11);
				ll_search_history.setVisibility(View.GONE);
			}
		});

		lv_search_history.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String numberString = lv_search_history.getAdapter()
						.getItem(position).toString();
				actv_search.setText(numberString.substring(0, 11));
				ll_search_history.setVisibility(View.GONE);
				actv_search.dismissDropDown();
				actv_search.setSelection(11);
			}
		});
	}

	/**
	 * 初始化自动搜索
	 * @param field
	 * @param auto
	 */
	private void initAutoComplete(String field, AutoCompleteTextView auto) {
		SharedPreferences sp = getSharedPreferences("history", 0);
		String longhistory = sp.getString("history", "");
		String[] hisArrays = longhistory.split(",");
		if (!hisArrays[0].equals("")) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					R.layout.simple_dropdown_item_line, hisArrays);
			if (hisArrays.length > 5) {
				String[] newArrays = new String[5];
				System.arraycopy(hisArrays, 0, newArrays, 0, 5);
				adapter = new ArrayAdapter<String>(this,
						R.layout.simple_dropdown_item_line, newArrays);
			}
			lv_search_history.setAdapter(adapter);
		} else {
			ll_search_history.setVisibility(View.GONE);
		}

		auto.setThreshold(1);
		auto.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				AutoCompleteTextView view = (AutoCompleteTextView) v;
				if (hasFocus) {
					view.showDropDown();
				}
			}
		});
	}

	/**
	 * 保存历史搜索记录
	 * @param field
	 * @param auto
	 */
	private void saveHistory(String field, AutoCompleteTextView auto) {
		String text = auto.getText().toString();

		SharedPreferences sp = getSharedPreferences("history", 0);
		String longhistory = sp.getString(field, "@");
		if (!longhistory.contains(text)) {
			for (int i = 0; i < numberList.size(); i++) {
				if (text.equals(numberList.get(i))) {
					StringBuilder sb = new StringBuilder(longhistory);
					sb.insert(0, text+"   "+nameList.get(i) + ",");
					if (longhistory.contains("@")) {
						sb.delete(sb.length() - 1, sb.length());
					}
					sp.edit().putString("history", sb.toString()).commit();
				}
			}

		}
	}

	/**
	 * 初始化dialog弹出位置
	 */
	private void initView() {
		Window dialogWindow = getWindow();

		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		
		p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.65
		p.y = 30;
		dialogWindow.setGravity(Gravity.TOP);
		dialogWindow.setAttributes(p);

		actv_search = (AutoCompleteTextView) findViewById(R.id.actv_search);
		iv_search_back = (ImageView) findViewById(R.id.iv_search_back);
		iv_search_search = (ImageView) findViewById(R.id.iv_search_search);
		ll_search_history = (LinearLayout) findViewById(R.id.ll_search_history);
		lv_search_history = (ListView) findViewById(R.id.lv_search_history);
		tv_search_history = (TextView) findViewById(R.id.tv_search_history);

		actv_search.setFocusable(true);
		actv_search.requestFocus();
	}
}
