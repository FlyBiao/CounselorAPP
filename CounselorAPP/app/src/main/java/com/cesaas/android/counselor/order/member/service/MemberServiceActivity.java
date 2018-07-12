package com.cesaas.android.counselor.order.member.service;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.base.BaseTemplateSinginActivity;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.member.adapter.service.MemberTagAdapter;
import com.cesaas.android.counselor.order.member.adapter.service.custom.MemberGradeAdapter;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultVipGradeBean;
import com.cesaas.android.counselor.order.member.bean.service.query.Grades;
import com.cesaas.android.counselor.order.member.bean.service.query.ResultQueryMemberBean;
import com.cesaas.android.counselor.order.member.net.service.GradeListNet;
import com.cesaas.android.counselor.order.shopmange.ShopTagListActivity;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.CalculateDateUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sing.datetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 会员服务
 */
public class MemberServiceActivity extends BaseTemplateSinginActivity implements DatePickerDialog.OnDateSetListener {

    private TextView tvTitle,tv_day,tv_sendQuestion,tv_end_day,tv_cancel;
    private TextView tv_stored_value_arrow,tv_purchase_date_arrow,tv_birthday_scope_arrow,tv_point_scope_arrow,tv_registr_date_arrow;
    private TextView tv_query_member,tv_grade,tv_PointsAreaMin,tv_PointsAreaMax;
    private LinearLayout llBack;
    private EditText et_service_title,et_service_name,et_MoneyAreaMin,et_MoneyAreaMax,et_PointsAreaMin,et_PointsAreaMax;
    private ImageView iv_add_tags;
    private FlowTagLayout member_lable_flow_layout;
    private CheckBox sendQuestion,cb_StoredValue,cb_buy_date,cb_birth_date,cb_points,cb_reg_date,cb_grade,cb_day;
    private LinearLayout ll_start_date,ll_end_date,ll_select_day,ll_select_vip;
    private TextView tv_start_date,et_end_date,tv_buy_start_date,tv_buy_end_date,tv_birthday_start_date,tv_birthday_end_date,tv_reg_start_date,tv_reg_end_date;

    private int selectDate=0;//0开始日期，1结束日期，2开始购买日期，3结束购买日期，4开始生日日期，5结束生日日期,6开始注册日期，7结束注册日期
    private int day=0;
    private int gradeId;
    private int send;
    private boolean isStoredValue=false;
    private boolean isBuyDate=false;
    private boolean isBirthDate=false;
    private boolean isPoints=false;
    private boolean isRegDate=false;
    private boolean isGrade=false;
    private boolean isDay=false;

    private String startDate;
    private String endDate;

    private GradeListNet net;
    private MemberGradeAdapter adapter;
    public MemberTagAdapter<GetTagListBean> tagAdapter;
    private List<ResultVipGradeBean.VipGradeBean> mData=new ArrayList<>();
    private ResultQueryMemberBean queryMemberBean=new ResultQueryMemberBean();
    private List<Grades> grades=new ArrayList<>();
    List<GetTagListBean> tags=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_service;
    }

    public void onEventMainThread(ResultVipGradeBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);
            }else{
                ToastFactory.getLongToast(mContext,"暂无会员等级数据！");
            }
        }else{
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }


    @Override
    public void initViews() {
        tv_cancel=findView(R.id.tv_cancel);
        et_PointsAreaMax=findView(R.id.et_PointsAreaMax);
        et_PointsAreaMin=findView(R.id.et_PointsAreaMin);
        et_MoneyAreaMin=findView(R.id.et_MoneyAreaMin);
        et_MoneyAreaMax=findView(R.id.et_MoneyAreaMax);
        tv_end_day=findView(R.id.tv_end_day);
        et_service_title=findView(R.id.et_service_title);
        member_lable_flow_layout=findView(R.id.member_lable_flow_layout);
        member_lable_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        iv_add_tags=findView(R.id.iv_add_tags);
        cb_day=findView(R.id.cb_day);
        cb_grade=findView(R.id.cb_grade);
        tv_sendQuestion=findView(R.id.tv_sendQuestion);
        cb_reg_date=findView(R.id.cb_reg_date);
        cb_points=findView(R.id.cb_points);
        cb_birth_date=findView(R.id.cb_birth_date);
        cb_buy_date=findView(R.id.cb_buy_date);
        cb_StoredValue=findView(R.id.cb_StoredValue);
        sendQuestion=findView(R.id.sendQuestion);
        tv_PointsAreaMin=findView(R.id.tv_PointsAreaMin);
        tv_PointsAreaMax=findView(R.id.tv_PointsAreaMax);
        et_service_name=findView(R.id.et_service_name);
        tv_day=findView(R.id.tv_day);
        tv_grade=findView(R.id.tv_grade);
        ll_select_vip=findView(R.id.ll_select_vip);
        ll_select_day=findView(R.id.ll_select_day);
        tv_reg_start_date=findView(R.id.tv_reg_start_date);
        tv_reg_end_date=findView(R.id.tv_reg_end_date);
        tv_birthday_start_date=findView(R.id.tv_birthday_start_date);
        tv_birthday_end_date=findView(R.id.tv_birthday_end_date);
        tv_buy_start_date=findView(R.id.tv_buy_start_date);
        tv_buy_end_date=findView(R.id.tv_buy_end_date);
        ll_start_date=findView(R.id.ll_start_date);
        ll_end_date=findView(R.id.ll_end_date);
        tv_start_date=findView(R.id.tv_start_date);
        et_end_date=findView(R.id.et_end_date);
        tvTitle=findView(R.id.tv_base_title);
        tvTitle.setText("会员服务");
        llBack=findView(R.id.ll_base_title_back);
        tv_stored_value_arrow= findView(R.id.tv_stored_value_arrow);
        tv_stored_value_arrow.setText(R.string.fa_long_arrow_right);
        tv_stored_value_arrow.setTypeface(App.font);
        tv_purchase_date_arrow=findView(R.id.tv_purchase_date_arrow);
        tv_purchase_date_arrow.setText(R.string.fa_long_arrow_right);
        tv_purchase_date_arrow.setTypeface(App.font);
        tv_birthday_scope_arrow=findView(R.id.tv_birthday_scope_arrow);
        tv_birthday_scope_arrow.setText(R.string.fa_long_arrow_right);
        tv_birthday_scope_arrow.setTypeface(App.font);
        tv_point_scope_arrow= findView(R.id.tv_point_scope_arrow);
        tv_point_scope_arrow.setText(R.string.fa_long_arrow_right);
        tv_point_scope_arrow.setTypeface(App.font);
        tv_registr_date_arrow=findView(R.id.tv_registr_date_arrow);
        tv_registr_date_arrow.setText(R.string.fa_long_arrow_right);
        tv_registr_date_arrow.setTypeface(App.font);
        tv_query_member=findView(R.id.tv_query_member);
    }

    @Override
    public void initListener() {
        iv_add_tags.setOnClickListener(this);
        llBack.setOnClickListener(this);
        tv_query_member.setOnClickListener(this);
        ll_start_date.setOnClickListener(this);
        ll_end_date.setOnClickListener(this);
        et_end_date.setOnClickListener(this);
        tv_buy_start_date.setOnClickListener(this);
        tv_buy_end_date.setOnClickListener(this);
        tv_birthday_start_date.setOnClickListener(this);
        tv_birthday_end_date.setOnClickListener(this);
        tv_reg_start_date.setOnClickListener(this);
        tv_reg_end_date.setOnClickListener(this);
        ll_select_day.setOnClickListener(this);
        ll_select_vip.setOnClickListener(this);
        tv_PointsAreaMin.setOnClickListener(this);
        tv_PointsAreaMax.setOnClickListener(this);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
        sendQuestion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    send=1;
                    tv_sendQuestion.setTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                }else{
                    send=0;
                    tv_sendQuestion.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                }
            }
        });
        cb_StoredValue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isStoredValue=true;
                    et_MoneyAreaMin.setEnabled(true);
                    et_MoneyAreaMax.setEnabled(true);
                    et_MoneyAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                    et_MoneyAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                }else{
                    isStoredValue=false;
                    et_MoneyAreaMin.setEnabled(false);
                    et_MoneyAreaMax.setEnabled(false);
                    et_MoneyAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                    et_MoneyAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                }
            }
        });
        cb_buy_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isBuyDate=true;
                    tv_buy_end_date.setTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                    tv_buy_start_date.setTextColor(mContext.getResources().getColor(R.color.new_base_bg));

                }else{
                    isBuyDate=false;
                    tv_buy_end_date.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                    tv_buy_start_date.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                }
            }
        });
        cb_birth_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isBirthDate=true;
                    tv_birthday_start_date.setTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                    tv_birthday_end_date.setTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                }else{
                    isBirthDate=false;
                    tv_birthday_start_date.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                    tv_birthday_end_date.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                }
            }
        });
        cb_points.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isPoints=true;
                    tv_PointsAreaMin.setTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                    tv_PointsAreaMax.setTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                    et_PointsAreaMin.setEnabled(true);
                    et_PointsAreaMax.setEnabled(true);
                    et_PointsAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                    et_PointsAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                }else{
                    isPoints=false;
                    tv_PointsAreaMin.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                    tv_PointsAreaMax.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                    et_PointsAreaMin.setEnabled(false);
                    et_PointsAreaMax.setEnabled(false);
                    et_PointsAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                    et_PointsAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                }
            }
        });
        cb_reg_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isRegDate=true;
                    tv_reg_start_date.setTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                    tv_reg_end_date.setTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                }else{
                    isRegDate=false;
                    tv_reg_start_date.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                    tv_reg_end_date.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                }
            }
        });
        cb_grade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isGrade=true;
                    tv_grade.setTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                }else{
                    isGrade=false;
                    tv_grade.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                }
            }
        });
        cb_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isDay=true;
                    tv_day.setTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                }else{
                    isDay=false;
                    tv_day.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
                }
            }
        });
    }

    @Override
    public void initData() {
        startDate=AbDateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss");
        tv_start_date.setText(AbDateUtil.getCurrentDate("yyyy-MM-dd"));

        if(mCache.getAsString("GetGradeList")!=null && !"".equals(mCache.getAsString("GetGradeList"))){
            ResultVipGradeBean msg= JsonUtils.fromJson(mCache.getAsString("GetGradeList"),ResultVipGradeBean.class);
            if(msg.IsSuccess!=false){
                if(msg.TModel!=null && msg.TModel.size()!=0){
                    mData=new ArrayList<>();
                    mData.addAll(msg.TModel);
                }else{
                    ToastFactory.getLongToast(mContext,"暂无会员等级数据！");
                }
            }else{
                ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
            }
        }else{
            net=new GradeListNet(mContext,mCache);
            net.setData();
        }
    }


    @Override
    public void processClick(View v) {

        switch (v.getId()){
            case R.id.iv_add_tags:
                Intent tagIntent = new Intent(mContext, MemberTagListActivity.class);
                tagIntent.putExtra("vipId","0");
                startActivityForResult(tagIntent, Constant.H5_TAG_SELECT);
                break;
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
            break;
            case R.id.tv_query_member:
                if(!TextUtils.isEmpty(et_service_title.getText().toString())){
                    if(!TextUtils.isEmpty(et_end_date.getText().toString())){
                        queryMemberBean=new ResultQueryMemberBean();
                        if(isStoredValue==true){
                            if(!TextUtils.isEmpty(et_MoneyAreaMin.getText().toString()) && !TextUtils.isEmpty(et_MoneyAreaMax.getText().toString())){
                                queryMemberBean.setMoneyAreaMin(Double.parseDouble(et_MoneyAreaMin.getText().toString()));
                                queryMemberBean.setMoneyAreaMax(Double.parseDouble(et_MoneyAreaMax.getText().toString()));
                            }
                        }

                        if(isPoints==true){
                            if(!TextUtils.isEmpty(et_PointsAreaMin.getText().toString()) && !TextUtils.isEmpty(et_PointsAreaMax.getText().toString())){
                                queryMemberBean.setPointsAreaMin(Integer.parseInt(et_PointsAreaMin.getText().toString()));
                                queryMemberBean.setPointsAreaMax(Integer.parseInt(et_PointsAreaMax.getText().toString()));
                            }
                        }

                        if(isGrade==true){
                            grades=new ArrayList<>();
                            Grades g=new Grades();
                            g.setTitle(tv_grade.getText().toString());
                            g.setId(gradeId);
                            grades.add(g);
                            queryMemberBean.setGrades(grades);
                        }

                        if(isBuyDate==true){
                            queryMemberBean.setBuyDateAreaMin(tv_buy_start_date.getText().toString());
                            queryMemberBean.setBuyDateAreaMax(tv_buy_end_date.getText().toString());
                        }
                        if(isBirthDate==true){
                            queryMemberBean.setBirthdayAreaMin(tv_birthday_start_date.getText().toString());
                            queryMemberBean.setBirthdayAreaMax(tv_birthday_end_date.getText().toString());
                        }
                        if (isRegDate==true){
                            queryMemberBean.setCreateAreaMin(tv_reg_start_date.getText().toString());
                            queryMemberBean.setCreateAreaMax(tv_reg_end_date.getText().toString());
                        }
                        if (isDay=true){
                            queryMemberBean.setNoBuyCount(day);
                        }

                        queryMemberBean.setTitle(et_service_title.getText().toString());
                        queryMemberBean.setBeginDate(tv_start_date.getText().toString());
                        queryMemberBean.setEndDate(et_end_date.getText().toString());
                        queryMemberBean.setRemark(et_service_name.getText().toString());
                        queryMemberBean.setSendQuestion(send);
                        queryMemberBean.setTags(tags);

                        bundle.putSerializable("queryMemberBean",queryMemberBean);
                        Skip.mNextFroData(mActivity,QueryMemberActivity.class,bundle);
                    }else{
                        ToastFactory.getLongToast(mContext,"请选择截止时间！");
                    }
                }else{
                    ToastFactory.getLongToast(mContext,"请填写服务名称！");
                }
                break;
            case R.id.ll_start_date:
                selectDate=0;
                getDateSelect(ll_start_date);
                break;
            case R.id.ll_end_date:
                selectDate=1;
                getDateSelect(ll_end_date);
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
                    setVipDialog(mData);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_service_title:
                setBaseDialog(1);
                break;
            case R.id.tv_MoneyAreaMin:
                if(isStoredValue==true){
                    setBaseDialog(2);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_MoneyAreaMax:
                if(isStoredValue==true){
                    setBaseDialog(3);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_PointsAreaMin:
                if(isPoints==true){
                    setBaseDialog(5);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_PointsAreaMax:
                if(isPoints==true){
                    setBaseDialog(6);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            default:
                break;
        }
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

        adapter=new MemberGradeAdapter(mData,mActivity,mContext);
        rv_member_list.setAdapter(adapter);

        vipDialog.getWindow().setGravity(Gravity.BOTTOM);
        vipDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        vipDialog.setCanceledOnTouchOutside(true);
        vipDialog.show();
    }

    public void setBaseDialog(final int type){
        baseDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_base, null);
        baseDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        tv_sure_set= (TextView) baseDialog.findViewById(R.id.tv_sure_set);
        et_set_value= (EditText) baseDialog.findViewById(R.id.et_set_value);
        tv_service_content= (TextView) baseDialog.findViewById(R.id.tv_service_content);
        switch (type){
            case 1:
                tv_service_content.setText("填写服务名称");
                break;
            case 2:
                tv_service_content.setText("最低储值余额");
                break;
            case 3:
                tv_service_content.setText("最高储值余额");
                break;
            case 4:
                tv_service_content.setText("填写服务说明");
                break;
            case 5:
                tv_service_content.setText("最低积分范围");
                break;
            case 6:
                tv_service_content.setText("最高积分范围");
                break;
        }
        tv_sure_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        tv_PointsAreaMin.setText(et_set_value.getText().toString());
                        break;
                    case 6:
                        tv_PointsAreaMax.setText(et_set_value.getText().toString());
                        break;
                }
                baseDialog.dismiss();
            }
        });

        baseDialog.getWindow().setGravity(Gravity.BOTTOM);
        baseDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        baseDialog.setCanceledOnTouchOutside(true);
        baseDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==901){//标签筛选
            if(data!=null){
                tags=new ArrayList<>();
                tags=(ArrayList<GetTagListBean>)data.getSerializableExtra("selectList");

                tagAdapter = new MemberTagAdapter<>(mContext);
                member_lable_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
                member_lable_flow_layout.setAdapter(tagAdapter);
                tagAdapter.onlyAddAll(tags);
            }
        }
    }

    /**
     * 日期选择选择
     * @param v
     */
    public void getDateSelect(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(MemberServiceActivity.this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
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
            case 0://开始日期
                startDate=date+" 00:00:00";
                tv_start_date.setText(date);
                break;
            case 1://结束日期
                endDate=date+" 23:59:59";
                et_end_date.setText(date);
                tv_end_day.setText(AbDateUtil.formats(endDate)+"天后到期");
                tv_end_day.setVisibility(View.GONE);
                break;
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
