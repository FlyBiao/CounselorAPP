package com.cesaas.android.counselor.order.fans.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.HomeActivity;
import com.cesaas.android.counselor.order.label.adapter.AddCreateLabelAdapter;
import com.cesaas.android.counselor.order.label.bean.ResultAddTagBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetCategoryListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetCategoryListBean.GetCategoryListBean;
import com.cesaas.android.counselor.order.label.net.AddTagNet;
import com.cesaas.android.counselor.order.label.net.GetCategoryListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;

/**
 * 新建标签
 * @author FGB
 *
 */
public class AddCreateLabelActivity extends BasesActivity implements OnClickListener{
	
	private LinearLayout ll_create_label_back;
	private TextView tv_sure_create_label;
	private MClearEditText et_tag_value;
	private ListView lv_label_category_list;
	
	private int categoryId;
	private String tagValue;
	
	private GetCategoryListNet categoryListNet;
	private ArrayList<GetCategoryListBean> categoryListBeans= new ArrayList<GetCategoryListBean>();
	
	private AddCreateLabelAdapter adapter;
	private AddTagNet addTagNet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_lable);
		
		categoryListNet=new GetCategoryListNet(mContext);
		categoryListNet.setData();
		
		initView();
	}

	/**
	 * 初始化视图控件
	 */
	private void initView() {
		et_tag_value=(MClearEditText) findViewById(R.id.et_tag_value);
		lv_label_category_list=(ListView) findViewById(R.id.lv_label_category_list);
		ll_create_label_back=(LinearLayout) findViewById(R.id.ll_create_label_back);
		tv_sure_create_label=(TextView) findViewById(R.id.tv_sure_create_label);
		ll_create_label_back.setOnClickListener(this);
		tv_sure_create_label.setOnClickListener(this);
	}
	
	/**
	 * 接受EventBus发送消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultAddTagBean msg){
		if(msg.IsSuccess==true){
			ToastFactory.getLongToast(mContext, "新建标签成功!");
			Skip.mNext(mActivity, HomeActivity.class);
			
		}else{
			ToastFactory.getLongToast(mContext, msg.Message);
		}
	}
	
	
	/**
	 * 接受EventBus发送消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGetCategoryListBean msg){
		if(msg.IsSuccess==true){
			categoryListBeans.addAll(msg.TModel);
			
			setAdapter();
			
		}else{
			ToastFactory.getLongToast(mContext, msg.Message);
		}
	}

	private void setAdapter() {
		
		adapter=new AddCreateLabelAdapter(categoryListBeans, mContext);
		lv_label_category_list.setAdapter(adapter);
		
		initItemClickListener();
	}
	
	/**
	 * 初始化ListView点击Item
	 */
	private void initItemClickListener() {
		lv_label_category_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				categoryId=categoryListBeans.get(position).CategoryId;
				tagValue=et_tag_value.getText().toString();
				
				// 切换选中的内容
				adapter.setSelectedItem(position);
				// 更新数据
				adapter.notifyDataSetChanged();
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_sure_create_label://确定新建标签
			if(!TextUtils.isEmpty(tagValue)){
				addTagNet=new AddTagNet(mContext);
				addTagNet.setData(categoryId, tagValue);
			}
			else{
				ToastFactory.getLongToast(mContext, "请输入标签名称！");
			}
			
			break;
			
		case R.id.ll_create_label_back://返回
			Skip.mBack(mActivity);
			break;

		default:
			break;
		}
		
	}
}
