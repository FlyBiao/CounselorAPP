package com.cesaas.android.counselor.order.fans.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.Fans;
import com.cesaas.android.counselor.order.bean.ResultFans;
import com.cesaas.android.counselor.order.bean.ResultGroupFans;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.net.FansNet;
import com.cesaas.android.counselor.order.net.GroupFansNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.index.decoration.DividerItemDecoration;
import com.cesaas.android.counselor.order.widget.index.decoration.TitleItemDecoration;
import com.cesaas.android.counselor.order.widget.indexbar.CityBean;
import com.cesaas.android.counselor.order.widget.indexbar.IndexBar;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;

/**
 * 群发
 * @author FGB
 *
 */
public class GroupSendAactivity extends BasesActivity implements OnClickListener{

	private LinearLayout ll_group_send_back;
	private RelativeLayout rl_sure_group_send;
	
	private TextView tv_fans_lists,tv_labelss,tv_selectall,tv_show,tv_bt_cancleselectall;
	private ArrayList<TextView> tvs=new ArrayList<TextView>();
	
	private RecyclerView mRv;
    private CityAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas;
    
    private GroupFansNet fansNet;
    private ArrayList<Fans> fansList;
    private int checkNum; // 记录选中的条目数量 
    
    public static JSONArray fansArray;
	public JSONArray fansJsonArray;

    private TitleItemDecoration mDecoration;
    
    private boolean flag=true;

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
		setContentView(R.layout.activity_group_send_message_layout);
		
		fansNet=new GroupFansNet(mContext);
		fansNet.setData();
		
		initView();
		mBack();
		mSend();
		setSelectAll();
	}

	/**
	 * 初始化视图控件
	 */
	private void initView() {
		
		tv_show=(TextView) findViewById(R.id.tv_show);
		tv_bt_cancleselectall=(TextView) findViewById(R.id.tv_bt_cancleselectall);
		tv_selectall=(TextView) findViewById(R.id.tv_selectall);
		tv_fans_lists=(TextView) findViewById(R.id.tv_fans_lists);
		tv_labelss=(TextView) findViewById(R.id.tv_labelss);
		
		tv_fans_lists.setOnClickListener(this);
		tv_labelss.setOnClickListener(this);
		
		tvs.add(tv_fans_lists);
		tvs.add(tv_labelss);
		
		mRv = (RecyclerView) findViewById(R.id.rv);
		//使用indexBar
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar
		
		rl_sure_group_send=(RelativeLayout) findViewById(R.id.rl_sure_group_send);
		ll_group_send_back=(LinearLayout) findViewById(R.id.ll_group_send_back);
	}
	
	/**
	 * 设置全选
	 */
	public void setSelectAll(){
		//全选
		tv_selectall.setOnClickListener(new OnClickListener() { 
            @Override 
            public void onClick(View v) { 
            	tv_bt_cancleselectall.setVisibility(View.VISIBLE);
            	tv_selectall.setVisibility(View.GONE);
            	fansJsonArray=new JSONArray();
            	fansArray=new JSONArray();
            	// 遍历list的长度，将MyAdapter中的map值全部设为true 
                for (int i = 0; i < mDatas.size(); i++) {
                    if (mAdapter.getIsSelected().get(i)==false) {
                    	mAdapter.getIsSelected().put(i, true);
                        if (flag) {
                           //设置已选粉丝mDatas.get(i).FANS_NICKNAME;
                           CityBean bean=new CityBean();
                           bean.FANS_ID=mDatas.get(i).FANS_ID;
                           bean.FANS_ICON=mDatas.get(i).FANS_ICON;
                           bean.FANS_OPENID=mDatas.get(i).FANS_OPENID;
                           bean.FANS_NICKNAME=mDatas.get(i).FANS_NICKNAME;
                           fansJsonArray.put(bean.getFansItem());
                        }
                    }
                } 
                flag=false;
                // 数量设为list的长度 
                checkNum = mDatas.size(); 
                // 刷新listview和TextView的显示 
                dataChanged(); 
                fansArray=fansJsonArray;
                
//                反选按钮的回调接口 
//                // 遍历list的长度，将已选的设为未选，未选的设为已选 
//                for (int i = zero; i < mDatas.size(); i++) {
//                    if (CityAdapter.getIsSelected().get(i)) { 
//                    	CityAdapter.getIsSelected().put(i, false); 
//                        checkNum--; 
//                        tv_selectall.setText("全选");
//                    } else { 
//                    	CityAdapter.getIsSelected().put(i, true); 
//                        checkNum++; 
//                        tv_selectall.setText("全不选");
//                        
//                        //设置已选粉丝mDatas.get(i).FANS_NICKNAME;
//                        CityBean bean=new CityBean();
//                        bean.FANS_ID=mDatas.get(i).FANS_ID;
//                        bean.FANS_ICON=mDatas.get(i).FANS_ICON;
//                        bean.FANS_OPENID=mDatas.get(i).FANS_OPENID;
//                        bean.FANS_NICKNAME=mDatas.get(i).FANS_NICKNAME;
//                        fansJsonArray.put(bean.getFansItem());
//                    } 
//                } 
//                flag=true;
//                // 刷新listview和TextView的显示 
//                dataChanged(); 
//                fansArray=fansJsonArray;
            } 
		});
		
		//取消全选
		tv_bt_cancleselectall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv_selectall.setVisibility(View.VISIBLE);
				tv_bt_cancleselectall.setVisibility(View.GONE);
				
				fansJsonArray=new JSONArray();
            	fansArray=new JSONArray();
				 // 遍历list的长度，将已选的按钮设为未选 
                for (int i = 0; i < mDatas.size(); i++) { 
                    if (mAdapter.getIsSelected().get(i)) { 
                    	mAdapter.getIsSelected().put(i, false); 
                        checkNum--;// 数量减1 
                        
                        //设置已选粉丝mDatas.get(i).FANS_NICKNAME;
                        CityBean bean=new CityBean();
                        bean.FANS_ID=mDatas.get(i).FANS_ID;
                        bean.FANS_ICON=mDatas.get(i).FANS_ICON;
                        bean.FANS_OPENID=mDatas.get(i).FANS_OPENID;
                        bean.FANS_NICKNAME=mDatas.get(i).FANS_NICKNAME;
                        fansJsonArray.put(bean.getFansItem());
                    } 
                } 
                flag=true;
                // 刷新listview和TextView的显示 
                dataChanged(); 	
                fansArray=fansJsonArray;
                
			}
		});
		
	}
	
	 // 刷新listview和TextView的显示 
    private void dataChanged() { 
        // 通知listView刷新 
        mAdapter.notifyDataSetChanged(); 
        // TextView显示最新的选中数目 
        tv_show.setText("已选中" + checkNum + "人"); 
    }; 
	
	/**
	 * 返回上一个页面
	 */
	public void mBack(){
		ll_group_send_back.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
	
	/**
	 * 发送群给发消息
	 */
	public void mSend(){
		rl_sure_group_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(checkNum!=0){
					Bundle bundle=new Bundle();
					bundle.putInt("checkNum",checkNum);
					Skip.mNextFroData(mActivity, GroupSendMessageAactivity.class,bundle);
					
				}else{
					ToastFactory.getLongToast(mContext, "请先选择粉丝再发送！");
				}
				
			}
		});
	}
	
	 /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas() {
       mRv.setAdapter(mAdapter = new CityAdapter(this, mDatas));
       mRv.addItemDecoration(mDecoration = new TitleItemDecoration(this, mDatas));
       mRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

       mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
               .setNeedRealIndex(true)//设置需要真实的索引
               .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
               .setmSourceDatas(mDatas);//设置数据源
    }

    /**
     * 菜单点击事件
     */
	@Override
	public void onClick(View v) {
		int index=-1;
		
		switch (v.getId()) {
		case R.id.tv_fans_lists:
			index=0;
			break;
			
		case R.id.tv_labelss://
			index=1;
			break;

		default:
			break;
		}
		setColor(index);		
	}
	
	/**
	 * 设置选中Text的color
	 * @param index
	 */
	private void setColor(int index) {
		for(int i=0;i<tvs.size();i++){
			tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.color_title_bar));
			tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.day_sign_content_text_white_30));
		}
		tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.white));
		tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_shop_mange_bg));
	}
	
	/**
	 * 接受EventBus发送消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGroupFans msg) {
		
		if (msg.IsSuccess==true) {
			fansList= new ArrayList<Fans>();
			fansList.addAll(msg.TModel);
			
			mDatas = new ArrayList<CityBean>();
			mRv.setLayoutManager(mManager = new LinearLayoutManager(this));
			
			 for (int i = 0; i < fansList.size(); i++) {
		            CityBean bean = new CityBean();
		            bean.setCity(fansList.get(i).FANS_NICKNAME);
		            bean.FANS_NICKNAME=fansList.get(i).FANS_NICKNAME;
		            bean.FANS_NAME=fansList.get(i).FANS_NAME;
		            bean.FANS_ICON=fansList.get(i).FANS_ICON;
		            bean.FANS_ID=fansList.get(i).FANS_ID;
		            bean.FANS_OPENID=fansList.get(i).FANS_OPENID;
		            mDatas.add(bean);
		        }
			 
			 //调用初始化粉丝数据方法
			 initDatas();
			 
		}else{
			ToastFactory.getLongToast(mContext, "获取粉丝数据失败!"+msg.Message);
		}
	}
	
	/**
	 * Created by FGB .
	 * Date: 16/08/28
	 */

	public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
	    private Context mContext;
	    private List<CityBean> mDatas;
	    private LayoutInflater mInflater;
	    
	  //用来控制checkBox的选中状态
	    private HashMap<Integer, Boolean> isSelected;
	    //对isSelected进行封装
	    public HashMap<Integer, Boolean> getIsSelected() {
	        return isSelected;
	    }
	    //设置是否选择
	    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
	    	this.isSelected = isSelected;
	    }

	    public CityAdapter(Context mContext, List<CityBean> mDatas) {
	        this.mContext = mContext;
	        this.mDatas = mDatas;
	        mInflater = LayoutInflater.from(mContext);
	        isSelected=new HashMap<Integer,Boolean>();
	        //往集合中添加位置
	        initdata();
	    }
	    
	    private void initdata() {
	        for (int i = 0; i < mDatas.size(); i++) {
	            isSelected.put(i, false);//默认为false状态
	        }
	    }

	    @Override
	    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
	        return new ViewHolder(mInflater.inflate(R.layout.item_city, parent, false));
	    }

	    @Override
	    public void onBindViewHolder(final ViewHolder holder, final int position) {
	        final CityBean cityBean = mDatas.get(position);
	        holder.tvCity.setText(cityBean.FANS_NICKNAME);
	        bitmapUtils.display(holder.iv_group_fans_img, cityBean.FANS_ICON, App.mInstance().bitmapConfig);
	        
	     // 根据isSelected来设置checkbox的选中状况 
	        final CityBean bean=mDatas.get(position);
	        holder.item_cb.setChecked(getIsSelected().get(position));
	        holder.itemView.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	
	            	// 改变CheckBox的状态 
	                holder.item_cb.toggle(); 
	            	CityAdapter.this.getIsSelected().put(position, holder.item_cb.isChecked()); 
	                 // 调整选定条目 
	                 if (holder.item_cb.isChecked() == true) { 
	                     checkNum++; 
	                  // 刷新listview和TextView的显示 
	                     dataChanged();
//	                     Toast.makeText(mContext, "dd="+bean.FANS_NICKNAME, zero).show();
	                 } else { 
	                     checkNum--; 
	                  // 刷新listview和TextView的显示 
	                     dataChanged();
	                 } 
	            }
	        });
	        
	    }

	    @Override
	    public int getItemCount() {
	        return mDatas != null ? mDatas.size() : 0;
	    }

	    public class ViewHolder extends RecyclerView.ViewHolder {
	        TextView tvCity;
	        MyImageViewWidget iv_group_fans_img;
	        CheckBox item_cb;

	        public ViewHolder(View itemView) {
	            super(itemView);
	            tvCity = (TextView) itemView.findViewById(R.id.tvCity);
	            item_cb=(CheckBox) itemView.findViewById(R.id.cbCheckBox);
	            iv_group_fans_img=(MyImageViewWidget) itemView.findViewById(R.id.iv_group_fans_img);
	        }
	    }
	}
}
