package com.cesaas.android.counselor.order.label;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.flowlayout.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.flowlayout.OnTagSelectListener;
import com.cesaas.android.counselor.order.custom.flowlayout.TagAdapter;
import com.cesaas.android.counselor.order.label.adapter.AddCreateLabelAdapter;
import com.cesaas.android.counselor.order.label.bean.ResultGetCategoryListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetCategoryListBean.GetCategoryListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetTagListBean;
import com.cesaas.android.counselor.order.label.bean.ResultGetTagListBean.GetTagListBean;
import com.cesaas.android.counselor.order.label.net.GetCategoryListNet;
import com.cesaas.android.counselor.order.label.net.GetTagListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 编辑标签
 * @author FGB
 *
 */
public class EditTagActivity extends BasesActivity implements OnClickListener{
	
	private LinearLayout ll_edit_tag_back;
	private ListView lv_edit_tag_category_list;
	private TextView tv_tag_title,tv_add_tag;
	
	private List<String> dataSource;
    private FlowTagLayout mSizeFlowTagLayout;
    private FlowTagLayout mMobileFlowTagLayout;
    private TagAdapter<String> mSizeTagAdapter;
    private TagAdapter<String> mMobileTagAdapter;;
    
    private AddCreateLabelAdapter adapter;
	private Dialog dialog;
	private View inflate;
	
	public String categoryName;//标签分类名称
	public int categoryId;//标签分类编号
	
	//获取标签列表
	private GetTagListNet getTagListNet;
	private ArrayList<GetTagListBean> getTagListBeans= new ArrayList<GetTagListBean>();
	
	//获取类型列表
	private GetCategoryListNet categoryListNet;
	private ArrayList<GetCategoryListBean> categoryListBeans= new ArrayList<GetCategoryListBean>();
	
	private ArrayList<GetCategoryListBean> arrayTemp = new ArrayList<ResultGetCategoryListBean.GetCategoryListBean>();
	private ArrayList<GetCategoryListBean> arrayBean = new ArrayList<ResultGetCategoryListBean.GetCategoryListBean>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_tag);
		
		categoryListNet=new GetCategoryListNet(mContext);
		categoryListNet.setData();
		
		initView();
	}
	
	/**
	 * 接受EventBus发送消息[分类下所属的标签列表]
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGetTagListBean msg){
		if(msg.IsSuccess==true){
			
			getTagListBeans= new ArrayList<GetTagListBean>();
			getTagListBeans.addAll(msg.TModel);
			//设置标签
			dataSource= new ArrayList<String>();
			
			//单选
			mSizeTagAdapter=new TagAdapter<String>(this);
			mSizeFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
			mSizeFlowTagLayout.setAdapter(mSizeTagAdapter);
			
			for (int i = 0; i < getTagListBeans.size(); i++) {
				dataSource.add(getTagListBeans.get(i).TagName);
			}
			mSizeTagAdapter.onlyAddAll(dataSource);
			
			//多选
//			mMobileTagAdapter=new TagAdapter<String>(this);
//			mMobileFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
//			mMobileFlowTagLayout.setAdapter(mMobileTagAdapter);
//			for (int i = zero; i < getTagListBeans.size(); i++) {
//				dataSource.add(getTagListBeans.get(i).TagName);
//			}
//			mMobileTagAdapter.onlyAddAll(dataSource);
			
			
		}else{
			ToastFactory.getLongToast(mContext, msg.Message);
		}
	}
	
	/**
	 * 接受EventBus发送消息【标签分类】
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGetCategoryListBean msg){
		if(msg.IsSuccess==true){
			arrayTemp = msg.TModel;
			arrayBean = arrayWithSuperId(0);
			categoryListBeans.addAll(arrayBean);
			whileArray(arrayBean);
			setAdapter();
		}else{
			ToastFactory.getLongToast(mContext, msg.Message);
		}
	}
	
	public ArrayList<GetCategoryListBean> arrayWithSuperId(int superId){
		ArrayList<GetCategoryListBean> arrayR = new ArrayList<ResultGetCategoryListBean.GetCategoryListBean>();
		ArrayList<GetCategoryListBean> arrayT = new ArrayList<ResultGetCategoryListBean.GetCategoryListBean>();
		
		for (int i = 0; i < arrayTemp.size(); i++) {
			GetCategoryListBean bean = arrayTemp.get(i);
			
			if (bean.ParentId == superId) {
				arrayR.add(bean);
				
			}else{
				
				arrayT.add(bean);
			}
		}
		arrayTemp = arrayT;
		return arrayR;
	}
	
	public void whileArray(ArrayList<GetCategoryListBean> array){
		for (int i = 0; i < array.size(); i++) {
			GetCategoryListBean bean = array.get(i);
			int superId = bean.CategoryId;
			bean.arraySub = arrayWithSuperId(superId);
			if (bean.arraySub.size() > 0) {
				whileArray(bean.arraySub);
			}
		}
	}
	

	/**
	 * 设置数据适配器【标签分类列表】Adapter
	 */
	private void setAdapter() {
		adapter=new AddCreateLabelAdapter(categoryListBeans, mContext);
		lv_edit_tag_category_list.setAdapter(adapter);
		
		initItemClickListener();		
	}

	/**
	 * ListView Item Click
	 */
	private void initItemClickListener() {
		lv_edit_tag_category_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				categoryName=categoryListBeans.get(position).CategoryName;
				categoryId=categoryListBeans.get(position).CategoryId;
				
				getTagListNet=new GetTagListNet(mContext);
				getTagListNet.setData(categoryId);
				
				// 切换选中的内容
				adapter.setSelectedItem(position);
				// 更新数据
				adapter.notifyDataSetChanged();
				show();
				
			}
		});
	}
	
	/**
	 * 初始化视图控件
	 */
	private void initView() {
		
		lv_edit_tag_category_list=(ListView) findViewById(R.id.lv_edit_tag_category_list);
		ll_edit_tag_back=(LinearLayout) findViewById(R.id.ll_edit_tag_back);
		ll_edit_tag_back.setOnClickListener(this);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_edit_tag_back://返回
			Skip.mBack(mActivity);
			break;

		default:
			break;
		}
	}
	
	public void show(){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.item_tag_category_dialog, null);
        mSizeFlowTagLayout = (FlowTagLayout)inflate. findViewById(R.id.size_flow_layout);
        mMobileFlowTagLayout=(FlowTagLayout)inflate. findViewById(R.id.mobile_flow_layout);
        tv_tag_title=(TextView) inflate.findViewById(R.id.tv_tag_title);
        tv_tag_title.setText(categoryName);
        tv_add_tag=(TextView) inflate.findViewById(R.id.tv_add_tag);
        
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
//        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
        
        //点击单选
        mSizeFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i : selectedList) {
                        sb.append(parent.getAdapter().getItem(i));
                        sb.append(":");
                    }
                    ToastFactory.getLongToast(mContext, "点击单选"+sb.toString());
                }else{
                	ToastFactory.getLongToast(mContext, "没有选择标签");
                }
            }
        });
        
        //多选
        mMobileFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    StringBuilder sb = new StringBuilder();

                    for (int i : selectedList) {
                        sb.append(parent.getAdapter().getItem(i));
                        sb.append(":");
                    }
                    ToastFactory.getLongToast(mContext, "多选:"+sb.toString());
                    
                }else{
                	
                	ToastFactory.getLongToast(mContext, "没有选择标签");
                }
            }
        });
    }
	
}
