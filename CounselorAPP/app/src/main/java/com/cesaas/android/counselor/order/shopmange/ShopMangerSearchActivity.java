package com.cesaas.android.counselor.order.shopmange;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.label.bean.ResultGetAllTagListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetAllTagListBean.GetAllTagListBean;
import com.cesaas.android.counselor.order.label.net.GetAllTagListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.cesaas.android.counselor.order.widget.flowlayout.Flow;
import com.cesaas.android.counselor.order.widget.flowlayout.FlowEntity;
import com.cesaas.android.counselor.order.widget.flowlayout.FlowLayout;

/**
 * 商品管理查询
 * @author FGB
 *
 */
public class ShopMangerSearchActivity extends BasesActivity{
	
	private LinearLayout ll_shop_mange_search_back;
	private TextView tv_search_type_shop;
	private FlowLayout mFlowLayout;
	private List<Flow> mList;
	private MClearEditText et_search_text;
	
	private GetAllTagListNet allTagListNet;
	private ArrayList<GetAllTagListBean> getAllTagList= new ArrayList<GetAllTagListBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_manger_search_layout);
		initView();
		intListener();
		mClick();
	}
	public void initView(){
		
		tv_search_type_shop=(TextView) findViewById(R.id.tv_search_type_shop);
		et_search_text=(MClearEditText) findViewById(R.id.et_search_text);
		mFlowLayout=(FlowLayout)findViewById(R.id.mFlowLayout);
		
		ll_shop_mange_search_back=(LinearLayout) findViewById(R.id.ll_shop_mange_search_back);
		
		allTagListNet=new GetAllTagListNet(mContext);
		allTagListNet.setData();
		
//		initData();
	}
	
	public void mClick(){
		ll_shop_mange_search_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
		
		tv_search_type_shop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(et_search_text.getText().toString())){
					Bundle bundle=new Bundle();
					bundle.putString("searchContent", et_search_text.getText().toString());
					Skip.mNextFroData(mActivity, ShopMangeActivity.class,bundle);
					
				}else{
					ToastFactory.getLongToast(mContext, "请输入搜索内容！");
				}
			}
		});
	}
	
	private void intListener() {
        mFlowLayout.setOnSelectListener(new FlowLayout.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                if(position==-1)return;
                et_search_text.setText(mList.get(position).getFlowName());
            }
        });
    }
	
	/**
	 * 接受EventBus发送消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGetAllTagListBean msg){
		if(msg.IsSuccess==true){
			getAllTagList.addAll(msg.TModel);
			
			List<Flow> list=new ArrayList<Flow>();
			 Flow mFlow;
			 for (int i = 0; i < getAllTagList.size(); i++) {
				 mFlow=new FlowEntity(getAllTagList.get(i).TagId+"",getAllTagList.get(i).TagName);
				 list.add(mFlow);
			}
			//设置店铺标签
			 mFlowLayout.setFlowData(list);
			 
		}else{
			ToastFactory.getLongToast(mContext, msg.Message);
		}
	}
	
	/**
	 * 初始化数据
	 */
	public void initData(){
		mList=getFlowList();
		mFlowLayout.setFlowData(mList);
	}
	
	/**
	 * 获取流布局列表
	 * @return
	 */
	private List<Flow> getFlowList() {
		 List<Flow> list=new ArrayList<Flow>();
	        Flow mFlow=new FlowEntity("1","毛呢大衣");
	        Flow mFlow2=new FlowEntity("2","本季主推");
	        Flow mFlow3=new FlowEntity("3","针织衫");
	        Flow mFlow4=new FlowEntity("4","秋季爆款");
	        Flow mFlow5=new FlowEntity("5","短袖");
	        list.add(mFlow);
	        list.add(mFlow2);
	        list.add(mFlow3);
	        list.add(mFlow4);
	        list.add(mFlow5);
	        
		return list;
	}
}
