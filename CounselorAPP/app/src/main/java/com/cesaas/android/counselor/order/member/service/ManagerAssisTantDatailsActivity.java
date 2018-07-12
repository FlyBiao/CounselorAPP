package com.cesaas.android.counselor.order.member.service;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.member.adapter.MemberQueryTagAdapter;
import com.cesaas.android.counselor.order.member.adapter.manager.ManagerAssisTantDetailsAdapter;
import com.cesaas.android.counselor.order.member.adapter.service.custom.MemberGradeAdapter;
import com.cesaas.android.counselor.order.member.bean.manager.AllVipListBean;
import com.cesaas.android.counselor.order.member.bean.manager.ResultAllVipListBean;
import com.cesaas.android.counselor.order.member.bean.service.check.ResultQueryVipBean;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultVipGradeBean;
import com.cesaas.android.counselor.order.member.bean.service.query.Grades;
import com.cesaas.android.counselor.order.member.net.GetUnBindVipNet;
import com.cesaas.android.counselor.order.member.net.service.AllVipListNet;
import com.cesaas.android.counselor.order.member.net.service.GradeListNet;
import com.cesaas.android.counselor.order.shopmange.ShopTagListActivity;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sing.datetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 店长管理店员-详情
 */
public class ManagerAssisTantDatailsActivity extends BasesActivity implements View.OnClickListener,BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,DatePickerDialog.OnDateSetListener{
    private LinearLayout ll_search_vip;
    private EditText et_search;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data;
    private LinearLayout llBaseBack;
    private TextView mTextViewTitle,mTextViewRightTitle;
    private TextView tv_sums;

    private DrawerLayout mDrawerLayout;
    private NavigationView navigation_right_view;
    private View headerView;
    private FlowTagLayout member_lable_flow_layout;
    private ImageView iv_add_tags;
    private LinearLayout ll_select_day,ll_select_vip;
    private TextView tv_sure_query,tv_cancel_query;
    private TextView tv_up,tv_bottom,tv_stored_value_arrow,tv_point_scope_arrow,tv_purchase_date_arrow,tv_birthday_scope_arrow,tv_registr_date_arrow;
    private EditText et_MoneyAreaMin,et_MoneyAreaMax,et_PointsAreaMin,et_PointsAreaMax,et_tag_count;
    private CheckBox cb_StoredValue,cb_buy_date,cb_birth_date,cb_points,cb_reg_date,cb_grade,cb_day,cb_member,cb_fans;;
    private TextView tv_grade,tv_day,tv_buy_start_date,tv_buy_end_date,tv_birthday_start_date,tv_birthday_end_date,tv_reg_start_date,tv_reg_end_date,tv_PointsAreaMin,tv_PointsAreaMax;

    private int selectDate=0;//2开始购买日期，3结束购买日期，4开始生日日期，5结束生日日期,6开始注册日期，7结束注册日期
    private int day=0;
    private int gradeId;
    private int tagsCounts=0;
    private int MemberType=10;
    private boolean isStoredValue=false;
    private boolean isBuyDate=false;
    private boolean isBirthDate=false;
    private boolean isPoints=false;
    private boolean isRegDate=false;
    private boolean isGrade=false;
    private boolean isDay=false;
    private boolean isMember=false;
    private boolean isFans=false;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;

    private String title;

    private List<GetTagListBean> tags=new ArrayList<>();
    private List<Grades> grades=new ArrayList<>();
    private List<ResultVipGradeBean.VipGradeBean> mVipData=new ArrayList<>();
    public MemberQueryTagAdapter<GetTagListBean> tagAdapter;
    private MemberGradeAdapter gradeAdapter;
    private GradeListNet gradeNet;

    private AllVipListNet allVipListNet;
    private ManagerAssisTantDetailsAdapter adapter;
    private List<AllVipListBean> mData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_assistant_details);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            title=bundle.getString("Title");
        }
        initView();
        initDrawerLayout();
        initData();
    }

    /**
     * 查询会员List
     * @param msg
     */
    public void onEventMainThread(ResultAllVipListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);
                tv_sums.setText(String.valueOf(msg.RecordCount));

                adapter=new ManagerAssisTantDetailsAdapter(mData,mActivity,mContext);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                    }
                });
            }else{
                adapter=new ManagerAssisTantDetailsAdapter(mData,mActivity,mContext);
                mRecyclerView.setAdapter(adapter);
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取会员列表失败："+msg.Message);
        }
    }

    public void initView(){
        tv_sums=(TextView)findViewById(R.id.tv_sums);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewTitle.setText(title);
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewRightTitle.setVisibility(View.VISIBLE);
        mTextViewRightTitle.setText(R.string.fa_glass);
        mTextViewRightTitle.setTypeface(App.font);
        mTextViewRightTitle.setTextSize(16);
        mTextViewRightTitle.setOnClickListener(this);
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        ll_search_vip= (LinearLayout) findViewById(R.id.ll_search_vip);
        et_search= (EditText) findViewById(R.id.et_search);
        et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    allVipListNet=new AllVipListNet(mContext);
                    allVipListNet.setData(et_search.getText().toString());
                }
                return false;
            }
        });
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation_right_view= (NavigationView) findViewById(R.id.navigation_right_view);
    }

    public void initData(){
        allVipListNet=new AllVipListNet(mContext);
        allVipListNet.setData();
    }

    public void onEventMainThread(ResultVipGradeBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                mVipData=new ArrayList<>();
                mVipData.addAll(msg.TModel);
            }else{
                ToastFactory.getLongToast(mContext,"暂无会员等级数据！");
            }
        }else{
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                if(mCache.getAsString("GetGradeList")!=null && !"".equals(mCache.getAsString("GetGradeList"))){
                    ResultVipGradeBean msg= JsonUtils.fromJson(mCache.getAsString("GetGradeList"),ResultVipGradeBean.class);
                    if(msg.IsSuccess!=false){
                        if(msg.TModel!=null && msg.TModel.size()!=0){
                            mVipData=new ArrayList<>();
                            mVipData.addAll(msg.TModel);
                        }else{
                            ToastFactory.getLongToast(mContext,"暂无会员等级数据！");
                        }
                    }else{
                        ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
                    }
                }else{
                    gradeNet=new GradeListNet(mContext,mCache);
                    gradeNet.setData();
                }
                break;
            case R.id.tv_buy_start_date:
                if(isBuyDate==true){
                    selectDate=2;
                    getDateSelect(tv_buy_start_date);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_buy_end_date:
                if(isBuyDate==true){
                    selectDate=3;
                    getDateSelect(tv_buy_start_date);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_birthday_start_date:
                if(isBirthDate==true){
                    selectDate=4;
                    getDateSelect(tv_birthday_start_date);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_birthday_end_date:
                if(isBirthDate==true){
                    selectDate=5;
                    getDateSelect(tv_birthday_start_date);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_reg_start_date:
                if(isRegDate==true){
                    selectDate=6;
                    getDateSelect(tv_reg_start_date);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_reg_end_date:
                if(isRegDate==true){
                    selectDate=7;
                    getDateSelect(tv_reg_end_date);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.ll_select_day:
                if(isDay==true){
                    setDayDialog();
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.ll_select_vip:
                if(isGrade==true){
                    setVipDialog(mVipData);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.iv_add_tags:
                Intent tagIntent = new Intent(mContext, ShopTagListActivity.class);
                startActivityForResult(tagIntent, Constant.H5_TAG_SELECT);
                break;
            case R.id.tv_cancel_query:
                resetQuery();
                break;
            case R.id.tv_sure_query:
                mDrawerLayout.closeDrawers();
                ResultQueryVipBean vipBean=new ResultQueryVipBean();
                if(isStoredValue==true){
                    if(!TextUtils.isEmpty(et_MoneyAreaMin.getText().toString()) && !TextUtils.isEmpty(et_MoneyAreaMax.getText().toString())){
                        vipBean.setMoneyAreaMin(Double.parseDouble(et_MoneyAreaMin.getText().toString()));
                        vipBean.setMoneyAreaMax(Double.parseDouble(et_MoneyAreaMax.getText().toString()));
                    }
                }

                if(isPoints==true){
                    if(!TextUtils.isEmpty(et_PointsAreaMin.getText().toString()) && !TextUtils.isEmpty(et_PointsAreaMax.getText().toString())){
                        vipBean.setPointsAreaMin(Integer.parseInt(et_PointsAreaMin.getText().toString()));
                        vipBean.setPointsAreaMax(Integer.parseInt(et_PointsAreaMax.getText().toString()));
                    }
                }

                if(isGrade==true){
                    grades=new ArrayList<>();
                    Grades g=new Grades();
                    g.setTitle(tv_grade.getText().toString());
                    g.setId(gradeId);
                    grades.add(g);
                    vipBean.setGrade(grades);
                }
                if(isBuyDate==true){
                    vipBean.setBuyDateAreaMin(tv_buy_start_date.getText().toString());
                    vipBean.setBuyDateAreaMax(tv_buy_end_date.getText().toString());
                }
                if(isBirthDate==true){
                    vipBean.setBirthdayAreaMin(tv_birthday_start_date.getText().toString());
                    vipBean.setBirthdayAreaMax(tv_birthday_end_date.getText().toString());
                }
                if (isRegDate==true){
                    vipBean.setCreateAreaMin(tv_reg_start_date.getText().toString());
                    vipBean.setCreateAreaMax(tv_reg_end_date.getText().toString());
                }
                if(!TextUtils.isEmpty(et_tag_count.getText().toString()) && !"".equals(et_tag_count.getText().toString())){
                    vipBean.setTagsCount(Integer.parseInt(et_tag_count.getText().toString()));
                }

                if (isDay=true){
                    vipBean.setNoBuyCount(day);
                }
                if(isFans==true){
                    vipBean.setIsVip(0);
                }
                if(isMember==true){
                    vipBean.setIsVip(1);
                }
                vipBean.setTag(tags);
                vipBean.setMemberType(MemberType);
                mData=new ArrayList<>();
                allVipListNet=new AllVipListNet(mContext);
                allVipListNet.setData(vipBean.getVipInfo());
                break;
            case R.id.tv_up:
                tagsCounts=tagsCounts+1;
                et_tag_count.setText(String.valueOf(tagsCounts));
                break;
            case R.id.tv_bottom:
                if(tagsCounts!=0){
                    tagsCounts=tagsCounts-1;
                }
                et_tag_count.setText(String.valueOf(tagsCounts));
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        adapter=new ManagerAssisTantDetailsAdapter(mData,mActivity,mContext);
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    @Override
    public void onLoadMoreRequested() {

    }

    private void initDrawerLayout() {
        headerView=navigation_right_view.getHeaderView(0);
        et_tag_count= (EditText) headerView.findViewById(R.id.et_tag_count);
        tv_up= (TextView) headerView.findViewById(R.id.tv_up);
        tv_up.setOnClickListener(this);
        tv_up.setText(R.string.fa_sort_up);
        tv_up.setTypeface(App.font);
        tv_bottom= (TextView) headerView.findViewById(R.id.tv_bottom);
        tv_bottom.setOnClickListener(this);
        tv_bottom.setText(R.string.fa_sort_desc);
        tv_bottom.setTypeface(App.font);
        tv_stored_value_arrow= (TextView) headerView.findViewById(R.id.tv_stored_value_arrow);
        tv_stored_value_arrow.setText(R.string.fa_long_arrow_right);
        tv_stored_value_arrow.setTypeface(App.font);
        tv_point_scope_arrow= (TextView) headerView.findViewById(R.id.tv_point_scope_arrow);
        tv_point_scope_arrow.setText(R.string.fa_long_arrow_right);
        tv_point_scope_arrow.setTypeface(App.font);
        tv_purchase_date_arrow= (TextView) headerView.findViewById(R.id.tv_purchase_date_arrow);
        tv_purchase_date_arrow.setText(R.string.fa_long_arrow_right);
        tv_purchase_date_arrow.setTypeface(App.font);
        tv_birthday_scope_arrow= (TextView) headerView.findViewById(R.id.tv_birthday_scope_arrow);
        tv_birthday_scope_arrow.setText(R.string.fa_long_arrow_right);
        tv_birthday_scope_arrow.setTypeface(App.font);
        tv_registr_date_arrow= (TextView) headerView.findViewById(R.id.tv_registr_date_arrow);
        tv_registr_date_arrow.setText(R.string.fa_long_arrow_right);
        tv_registr_date_arrow.setTypeface(App.font);
        member_lable_flow_layout= (FlowTagLayout) headerView.findViewById(R.id.member_lable_flow_layout);
        member_lable_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        iv_add_tags= (ImageView) headerView.findViewById(R.id.iv_add_tags);
        iv_add_tags.setOnClickListener(this);
        tv_grade= (TextView) headerView.findViewById(R.id.tv_grade);
        ll_select_vip= (LinearLayout) headerView.findViewById(R.id.ll_select_vip);
        ll_select_vip.setOnClickListener(this);
        ll_select_day= (LinearLayout) headerView.findViewById(R.id.ll_select_day);
        ll_select_day.setOnClickListener(this);
        tv_PointsAreaMin= (TextView) headerView.findViewById(R.id.tv_PointsAreaMin);
        tv_PointsAreaMax= (TextView) headerView.findViewById(R.id.tv_PointsAreaMax);
        tv_day= (TextView) headerView.findViewById(R.id.tv_day);
        tv_reg_start_date = (TextView)headerView. findViewById(R.id.tv_reg_start_date);
        tv_reg_start_date.setOnClickListener(this);
        tv_reg_end_date = (TextView)headerView. findViewById(R.id.tv_reg_end_date);
        tv_reg_end_date.setOnClickListener(this);
        tv_birthday_start_date= (TextView) headerView.findViewById(R.id.tv_birthday_start_date);
        tv_birthday_start_date.setOnClickListener(this);
        tv_birthday_end_date= (TextView) headerView.findViewById(R.id.tv_birthday_end_date);
        tv_birthday_end_date.setOnClickListener(this);
        tv_buy_end_date= (TextView) headerView.findViewById(R.id.tv_buy_end_date);
        tv_buy_end_date.setOnClickListener(this);
        tv_buy_start_date= (TextView) headerView.findViewById(R.id.tv_buy_start_date);
        tv_buy_start_date.setOnClickListener(this);
        tv_sure_query= (TextView) headerView.findViewById(R.id.tv_sure_query);
        tv_sure_query.setOnClickListener(this);
        tv_cancel_query= (TextView) headerView.findViewById(R.id.tv_cancel_query);
        tv_cancel_query.setOnClickListener(this);
        et_MoneyAreaMin= (EditText) headerView.findViewById(R.id.et_MoneyAreaMin);
        et_MoneyAreaMax= (EditText) headerView.findViewById(R.id.et_MoneyAreaMax);
        et_PointsAreaMin= (EditText) headerView.findViewById(R.id.et_PointsAreaMin);
        et_PointsAreaMax= (EditText) headerView.findViewById(R.id.et_PointsAreaMax);
        cb_StoredValue= (CheckBox) headerView.findViewById(R.id.cb_StoredValue);
        cb_StoredValue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isStoredValue=true;
                    et_MoneyAreaMin.setEnabled(true);
                    et_MoneyAreaMax.setEnabled(true);
                    et_MoneyAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.white));
                    et_MoneyAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.white));
                }else{
                    isStoredValue=false;
                    et_MoneyAreaMin.setEnabled(false);
                    et_MoneyAreaMax.setEnabled(false);
                    et_MoneyAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.white));
                    et_MoneyAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.white));
                }
            }
        });
        cb_buy_date= (CheckBox) headerView.findViewById(R.id.cb_buy_date);
        cb_buy_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isBuyDate=true;
                }else{
                    isBuyDate=false;
                }
            }
        });
        cb_birth_date= (CheckBox) headerView.findViewById(R.id.cb_birth_date);
        cb_birth_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isBirthDate=true;
                }else{
                    isBirthDate=false;
                }
            }
        });
        cb_points= (CheckBox) headerView.findViewById(R.id.cb_points);
        cb_points.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isPoints=true;
                    tv_PointsAreaMin.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_PointsAreaMax.setTextColor(mContext.getResources().getColor(R.color.white));
                    et_PointsAreaMin.setEnabled(true);
                    et_PointsAreaMax.setEnabled(true);
                    et_PointsAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.white));
                    et_PointsAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.white));
                }else{
                    isPoints=false;
                    tv_PointsAreaMin.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_PointsAreaMax.setTextColor(mContext.getResources().getColor(R.color.white));
                    et_PointsAreaMin.setEnabled(false);
                    et_PointsAreaMax.setEnabled(false);
                    et_PointsAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.white));
                    et_PointsAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.white));
                }
            }
        });
        cb_reg_date= (CheckBox) headerView.findViewById(R.id.cb_reg_date);
        cb_reg_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isRegDate=true;
                }else{
                    isRegDate=false;
                }
            }
        });
        cb_grade= (CheckBox) headerView.findViewById(R.id.cb_grade);
        cb_grade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isGrade=true;
                }else{
                    isGrade=false;
                }
            }
        });
        cb_day= (CheckBox) headerView.findViewById(R.id.cb_day);
        cb_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isDay=true;
                }else{
                    isDay=false;
                }
            }
        });
        cb_member= (CheckBox) headerView.findViewById(R.id.cb_member);
        cb_member.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    isMember=true;
                }else{
                    isMember=false;
                }
            }
        });
        cb_fans= (CheckBox) headerView.findViewById(R.id.cb_fans);
        cb_fans.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    isFans=true;
                    cb_member.setChecked(false);
                }else{
                    isFans=false;
                    cb_member.setChecked(true);
                }
            }
        });

    }

    /**
     * 重置会员筛选条件
     */
    public void resetQuery(){
        cb_StoredValue.setChecked(false);
        et_MoneyAreaMin.setText("");
        et_MoneyAreaMin.setHint("最小储值");
        et_MoneyAreaMax.setText("");
        et_MoneyAreaMax.setHint("最大储值");
        isStoredValue=false;
        et_MoneyAreaMin.setEnabled(false);
        et_MoneyAreaMax.setEnabled(false);
        et_MoneyAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
        et_MoneyAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));

        cb_buy_date.setChecked(false);
        isBuyDate=false;
        tv_buy_start_date.setText("");
        tv_buy_start_date.setText("开始日期");
        tv_buy_end_date.setText("");
        tv_buy_end_date.setText("结束日期");

        cb_birth_date.setChecked(false);
        isBirthDate=false;
        tv_birthday_start_date.setText("");
        tv_birthday_start_date.setText("开始范围");
        tv_birthday_end_date.setText("");
        tv_birthday_end_date.setText("结束范围");

        cb_day.setChecked(false);
        isDay=false;
        day=30;
        tv_day.setText("最近一个月");

        cb_grade.setChecked(false);
        isGrade=false;
        tv_grade.setText("");
        tv_grade.setText("选择会员等级");

        cb_points.setChecked(false);
        isPoints=false;
        tv_PointsAreaMin.setText("");
        tv_PointsAreaMin.setText("填写积分范围");
        tv_PointsAreaMax.setText("");
        tv_PointsAreaMax.setText("填写积分范围");
        tv_PointsAreaMin.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
        tv_PointsAreaMax.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
        et_PointsAreaMin.setEnabled(false);
        et_PointsAreaMax.setEnabled(false);
        et_PointsAreaMin.setText("最低积分");
        et_PointsAreaMax.setText("最高积分");
        et_PointsAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
        et_PointsAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));


        cb_reg_date.setChecked(false);
        isRegDate=false;
        tv_reg_start_date.setText("");
        tv_reg_start_date.setText("开始日期");
        tv_reg_end_date.setText("");
        tv_reg_end_date.setText("结束日期");

        tags=new ArrayList<>();
        tagAdapter = new MemberQueryTagAdapter<>(mContext);
        member_lable_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        member_lable_flow_layout.setAdapter(tagAdapter);
        tagAdapter.onlyAddAll(tags);
    }


    private Dialog dayDialog;
    private Dialog vipDialog;
    private Dialog baseDialog;
    private View dialogContentView;
    private EditText et_set_value;
    private TextView tv_three_day,tv_week,tv_half_month,tv_month,tv_service_content,tv_sure_set;
    private RecyclerView rv_member_list;
    public void setDayDialog(){
        dayDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_day, null);
        dayDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        tv_three_day= (TextView) dayDialog.findViewById(R.id.tv_three_day);
        tv_three_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=3;
                dayDialog.dismiss();
                tv_day.setText("最近三天");
            }
        });
        tv_week= (TextView) dayDialog.findViewById(R.id.tv_week);
        tv_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=7;
                dayDialog.dismiss();
                tv_day.setText("一个星期");
            }
        });
        tv_half_month= (TextView) dayDialog.findViewById(R.id.tv_half_month);
        tv_half_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=15;
                dayDialog.dismiss();
                tv_day.setText("半个月");

            }
        });
        tv_month= (TextView) dayDialog.findViewById(R.id.tv_month);
        tv_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=30;
                dayDialog.dismiss();
                tv_day.setText("一个月");
            }
        });

        dayDialog.getWindow().setGravity(Gravity.BOTTOM);
        dayDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        dayDialog.setCanceledOnTouchOutside(true);
        dayDialog.show();
    }

    public void setVipDialog(final List<ResultVipGradeBean.VipGradeBean> mData){
        vipDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_vip, null);
        vipDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        rv_member_list= (RecyclerView) vipDialog.findViewById(R.id.rv_member_list);
        rv_member_list.setLayoutManager(new LinearLayoutManager(this));
        rv_member_list.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                vipDialog.dismiss();
                gradeId=mData.get(position).getId();
                tv_grade.setText(mData.get(position).getTitle());
            }
        });

        gradeAdapter=new MemberGradeAdapter(mData,mActivity,mContext);
        rv_member_list.setAdapter(gradeAdapter);

        vipDialog.getWindow().setGravity(Gravity.BOTTOM);
        vipDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        vipDialog.setCanceledOnTouchOutside(true);
        vipDialog.show();
    }

    /**
     * 日期选择选择
     * @param v
     */
    public void getDateSelect(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(ManagerAssisTantDatailsActivity.this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.setThemeDark(false);// boolean,DarkTheme
        dpd.vibrate(true);// boolean,触摸震动
        dpd.dismissOnPause(false);// boolean,Pause时是否Dismiss
        dpd.showYearPickerFirst(false);// boolean,先选择年
        if (true) {// boolean,自定义颜色
            dpd.setAccentColor(getResources().getColor(R.color.new_base_bg));
        }
        if (true) {// boolean,设置标题
            dpd.setTitle("日期选择");
        }
        if (false) {// boolean,只能选择某些日期
            Calendar[] dates = new Calendar[13];
            for (int i = -6; i <= 6; i++) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.MONTH, i);
                dates[i + 6] = date;
            }
            dpd.setSelectableDays(dates);
        }
        if (true) {// boolean,部分高亮
            Calendar[] dates = new Calendar[13];
            for (int i = -6; i <= 6; i++) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.WEEK_OF_YEAR, i);
                dates[i + 6] = date;
            }
            dpd.setHighlightedDays(dates);
        }
        if (false) {// boolean,某些日期不可选
            Calendar[] dates = new Calendar[3];
            for (int i = -1; i <= 1; i++) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.DAY_OF_MONTH, i);
                dates[i + 1] = date;
            }
            dpd.setDisabledDays(dates);
        }
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        switch (selectDate){
            case 2://开始购买日期
                tv_buy_start_date.setText(date);
                break;
            case 3://结束购买日期
                tv_buy_end_date.setText(date);
                break;
            case 4://开始生日日期
                tv_birthday_start_date.setText(date);
                break;
            case 5://结束生日日期
                tv_birthday_end_date.setText(date);
                break;
            case 6://开始注册日期
                tv_reg_start_date.setText(date);
                break;
            case 7://结束注册日期
                tv_reg_end_date.setText(date);
                break;
        }
    }
}
