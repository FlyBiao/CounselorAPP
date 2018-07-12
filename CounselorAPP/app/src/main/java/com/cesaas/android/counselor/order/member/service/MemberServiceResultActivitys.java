package com.cesaas.android.counselor.order.member.service;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.base.BaseTemplateSinginActivity;
import com.cesaas.android.counselor.order.boss.bean.tag.TagDataUtils;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.tag.adapter.TagGoodsCategoryAdapter;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultServiceResultBean;
import com.cesaas.android.counselor.order.member.net.service.AddServiceResultNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.sing.datetimepicker.date.DatePickerDialog;

import org.json.JSONArray;

import java.util.Calendar;
import java.util.List;

/**
 * 会员服务
 */
public class MemberServiceResultActivitys extends BaseTemplateSinginActivity implements DatePickerDialog.OnDateSetListener{

    private TextView tvTitle,tv_sure,tv_name,tv_phone,tv_sex;
    private TextView tv_member_img,tv_member_mobile;
    private LinearLayout llBack;
    private FlowTagLayout member_lable_flow_layout;
    private CheckBox cbx_service_type1,cbx_service_type2;
    private CheckBox cbx_service_result1,cbx_service_result2,cbx_service_result3;
    private CheckBox cbx_go_shop1,cbx_go_shop2,cbx_go_shop3;
    private CheckBox cb_next_service_date,cb_goshop_date;
    private TextView tv_go_shop_date,tv_next_service_date;
    private EditText et_desc,et_remark;

    private int selectDate=0;//0到店日期，1下次服务日期
    private int goShop=1;//1.愿意来店2.不愿意来店3.不确定
    private int serviceResult=1;//1.接听或有回复2.没反馈3.拒绝沟通
    private int serviceType=1;//1.电话2.其它
    private boolean isGoShopDate=false;
    private boolean isNextServiceDate=false;

    private AddServiceResultNet net;

    public static TagGoodsCategoryAdapter<GetTagListBean> yearTagAdapter;
    private JSONArray array=new JSONArray();

    private int id;
    private String Name;
    private String Phone;
    private String Sex;

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_service_result;
    }

    public void onEventMainThread(ResultServiceResultBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"添加服务结果成功!");
            finish();
        }else{
            ToastFactory.getLongToast(mContext,"添加服务结果失败："+msg.Message);
        }
    }

    @Override
    public void initViews() {
        cb_goshop_date=findView(R.id.cb_goshop_date);
        cb_next_service_date=findView(R.id.cb_next_service_date);
        tv_sex=findView(R.id.tv_sex);
        tv_phone=findView(R.id.tv_phone);
        tv_name=findView(R.id.tv_name);
        tvTitle=findView(R.id.tv_base_title);
        tvTitle.setText("会员服务-处理结果");
        llBack=findView(R.id.ll_base_title_back);
        tv_member_img=findView(R.id.tv_member_img);
        tv_member_img.setText(R.string.users_o_s);
        tv_member_img.setTypeface(App.font);
        tv_member_mobile=findView(R.id.tv_member_mobile);
        tv_member_mobile.setText(R.string.fa_phone);
        tv_member_mobile.setTypeface(App.font);
        member_lable_flow_layout=findView(R.id.member_lable_flow_layout);
        member_lable_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        cbx_service_type1=findView(R.id.cbx_service_type1);
        cbx_service_type2=findView(R.id.cbx_service_type2);
        cbx_service_result1=findView(R.id.cbx_service_result1);
        cbx_service_result2=findView(R.id.cbx_service_result2);
        cbx_service_result3=findView(R.id.cbx_service_result3);
        cbx_go_shop1=findView(R.id.cbx_go_shop1);
        cbx_go_shop2=findView(R.id.cbx_go_shop2);
        cbx_go_shop3=findView(R.id.cbx_go_shop3);
        tv_go_shop_date=findView(R.id.tv_go_shop_date);
        tv_next_service_date=findView(R.id.tv_next_service_date);
        et_desc=findView(R.id.et_desc);
        et_remark=findView(R.id.et_remark);
        tv_sure=findView(R.id.tv_sure);
        setCheck();
    }

    private void setCheck() {
        cb_goshop_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isGoShopDate=true;
                }else{
                    isGoShopDate=false;
                }
            }
        });cb_next_service_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    isNextServiceDate=true;
                }else{
                    isNextServiceDate=false;
                }
            }
        });
        cbx_service_type1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    serviceType=1;
                    cbx_service_type2.setChecked(false);
                }
            }
        });
        cbx_service_type2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    serviceType=2;
                    cbx_service_type1.setChecked(false);
                }
            }
        });cbx_service_result1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    serviceResult=1;
                    cbx_service_result2.setChecked(false);
                    cbx_service_result3.setChecked(false);
                }
            }
        });cbx_service_result2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    serviceResult=2;
                    cbx_service_result1.setChecked(false);
                    cbx_service_result3.setChecked(false);
                }
            }
        });cbx_service_result3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    serviceResult=3;
                    cbx_service_result1.setChecked(false);
                    cbx_service_result2.setChecked(false);
                }
            }
        });cbx_go_shop1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    goShop=1;
                    cbx_go_shop2.setChecked(false);
                    cbx_go_shop3.setChecked(false);
                }
            }
        });cbx_go_shop2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    goShop=2;
                    cbx_go_shop1.setChecked(false);
                    cbx_go_shop3.setChecked(false);
                }
            }
        });cbx_go_shop3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    goShop=3;
                    cbx_go_shop1.setChecked(false);
                    cbx_go_shop2.setChecked(false);
                }
            }
        });
    }

    @Override
    public void initListener() {
        llBack.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        tv_next_service_date.setOnClickListener(this);
        tv_go_shop_date.setOnClickListener(this);
    }

    @Override
    public void initData() {
        tv_go_shop_date.setText(AbDateUtil.getCurrentDate("yyyy-MM-dd"));
        tv_next_service_date.setText(AbDateUtil.getCurrentDate("yyyy-MM-dd"));

        Bundle bundle=getIntent().getExtras();
        id=bundle.getInt("Id");
        Name=bundle.getString("Name");
        Phone=bundle.getString("Phone");
        Sex=bundle.getString("sex");
        tv_name.setText(Name);
        tv_phone.setText(Phone);

        if(Sex.equals("1")){
            tv_sex.setText("(男士)");
        }else{
            tv_sex.setText("(女士)");
        }

        yearTagAdapter = new TagGoodsCategoryAdapter(mContext);
        member_lable_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        member_lable_flow_layout.setAdapter(yearTagAdapter);
        yearTagAdapter.onlyAddAll(TagDataUtils.getMoodList());
        member_lable_flow_layout.setOnTagSelectListener(new com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    array=new JSONArray();
                    for (int i : selectedList) {
                        array.put(TagDataUtils.getMoodList().get(i).getTagName());
                    }
                } else {
                    Toast.makeText(mContext,"没有选择客人心情！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
            break;
            case R.id.tv_sure:
                if(isGoShopDate==true && isNextServiceDate==true){
                    net=new AddServiceResultNet(mContext);
                    net.setData(id,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),1);
                }else if(isNextServiceDate==true){
                    net=new AddServiceResultNet(mContext);
                    net.setData(id,serviceType,serviceResult,goShop,tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),0,0);
                }else if(isGoShopDate==true){
                    net=new AddServiceResultNet(mContext);
                    net.setData(id,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),"d");
                }else{
                    net=new AddServiceResultNet(mContext);
                    net.setData(id,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),1);
                }
                break;
            case R.id.tv_go_shop_date:
                selectDate=0;
                getDateSelect(tv_go_shop_date);
                break;
            case R.id.tv_next_service_date:
                selectDate=1;
                getDateSelect(tv_next_service_date);
                break;
            default:
                break;
        }
    }

    /**
     * 日期选择选择
     * @param v
     */
    public void getDateSelect(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(MemberServiceResultActivitys.this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
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
                tv_go_shop_date.setText(date);
                break;
            case 1://结束日期
                tv_next_service_date.setText(date);
                break;
        }
    }

}
