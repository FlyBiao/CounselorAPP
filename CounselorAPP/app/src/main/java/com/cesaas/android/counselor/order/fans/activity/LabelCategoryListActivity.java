package com.cesaas.android.counselor.order.fans.activity;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.label.bean.ResultGetAllTagListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetAllTagListBean.GetAllTagListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetCategoryListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetCategoryListBean.GetCategoryListBean;
import com.cesaas.android.counselor.order.label.net.GetAllTagListNet;
import com.cesaas.android.counselor.order.label.net.GetCategoryListNet;
import com.cesaas.android.counselor.order.label.net.GetTagListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 粉丝标签
 * @author FGB
 *
 */
public class LabelCategoryListActivity extends BasesActivity implements OnClickListener{
	
	private LinearLayout ll_fans_labels_back,ll_add_fans_lable;
	private ListView listView;
	
	private GetAllTagListNet allTagListNet;
	private ArrayList<GetAllTagListBean> allTagList= new ArrayList<GetAllTagListBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fans_label_layout);
		
		allTagListNet=new GetAllTagListNet(mContext);
		allTagListNet.setData();
		
		initView();
		
	}

	private void initView() {
		
		listView=(ListView) findViewById(R.id.lv_label_category);
		
		ll_fans_labels_back=(LinearLayout) findViewById(R.id.ll_fans_labels_back);
		ll_add_fans_lable=(LinearLayout) findViewById(R.id.ll_add_fans_lable);
		ll_add_fans_lable.setOnClickListener(this);
		ll_fans_labels_back.setOnClickListener(this);
		
	}
	
	/**
	 * 接受EventBus发送消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGetAllTagListBean msg){
		if(msg.IsSuccess==true){
			allTagList.addAll(msg.TModel);
			
			setAdapter();
			
		}else{
			ToastFactory.getLongToast(mContext, msg.Message);
		}
	}
	
	private void setAdapter() {
		listView.setAdapter(new CommonAdapter<GetAllTagListBean>(mContext,R.layout.item_label_category_list,allTagList) {

			@Override
			public void convert(ViewHolder holder, GetAllTagListBean bean,int postion) {
				holder.setText(R.id.tv_label_category_name, bean.TagName);
				
			}

		});
		
		initItemClickListener();
	}

	private void initItemClickListener() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_fans_labels_back:
			Skip.mBack(mActivity);
			break;
			
		case R.id.ll_add_fans_lable://添加标签
			Skip.mNext(mActivity, AddCreateLabelActivity.class);
			break;
			
		default:
			break;
		}
	}
}
