package com.cesaas.android.counselor.order.member.service;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.NewMainActivity;
import com.cesaas.android.counselor.order.base.BaseTemplateSinginActivity;
import com.cesaas.android.counselor.order.boss.bean.tag.TagDataUtils;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.tag.adapter.TagGoodsCategoryAdapter;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.member.adapter.service.multipleservice.CloseServiceAdapter;
import com.cesaas.android.counselor.order.member.adapter.service.multipleservice.MultipleServiceAdapter;
import com.cesaas.android.counselor.order.member.bean.service.ResultServiceResultBean;
import com.cesaas.android.counselor.order.member.bean.service.multipleservice.MultipleServiceBean;
import com.cesaas.android.counselor.order.member.bean.service.multipleservice.ResultGetVipTaskBean;
import com.cesaas.android.counselor.order.member.net.service.FinishAndCloseNet;
import com.cesaas.android.counselor.order.member.net.service.FinishNotCloseNet;
import com.cesaas.android.counselor.order.member.net.service.GetVipTaskNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.sing.datetimepicker.date.DatePickerDialog;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 会员服务
 */
public class MemberServiceResultActivity extends BaseTemplateSinginActivity implements DatePickerDialog.OnDateSetListener,CloseServiceAdapter.OnItemClickListener{

    private Switch switch_go_shop,switch_next_service;
    private TextView tvTitle,tv_sure,tv_sure_continue,tv_name,tv_phone,tv_sex,tv_cancel;
    private TextView tv_member_img,tv_member_mobile;
    private LinearLayout llBack,ll_query_member;
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
    private boolean isGoShop=false;
    private boolean isNextService=false;

    private int CreateShopTask=0;
    private int CreateServerTask=0;

    // 定义PopupWindow
    private PopupWindow popWindow;
    // 获取手机屏幕分辨率的类
    private DisplayMetrics dm;

    private FinishNotCloseNet net;
    private FinishAndCloseNet finishAndCloseNet;
    private GetVipTaskNet getVipTaskNet;

    private List<MultipleServiceBean> multipleServiceData=new ArrayList<>();
    public static TagGoodsCategoryAdapter<GetTagListBean> yearTagAdapter;
    private JSONArray array=new JSONArray();

    private int id;
    private int VipId;
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
            Skip.mNext(mActivity,NewMainActivity.class,true);
        }else{
            ToastFactory.getLongToast(mContext,"添加服务结果失败："+msg.Message);
        }
    }

    public void onEventMainThread(ResultGetVipTaskBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                multipleServiceData=new ArrayList<>();
                multipleServiceData.addAll(msg.TModel);

            }
        }else{
            ToastFactory.getLongToast(mContext,"关闭任务失败："+ msg.Message);
        }
    }

    @Override
    public void initViews() {
        tv_cancel= (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        ll_query_member=findView(R.id.ll_query_member);
        switch_go_shop=findView(R.id.switch_go_shop);
        switch_next_service=findView(R.id.switch_next_service);
        tv_sure_continue=findView(R.id.tv_sure_continue);
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
                    switch_go_shop.setVisibility(View.VISIBLE);
                }else{
                    isGoShopDate=false;
                    switch_go_shop.setVisibility(View.GONE);
                }
            }
        });cb_next_service_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    isNextServiceDate=true;
                    switch_next_service.setVisibility(View.VISIBLE);
                }else{
                    isNextServiceDate=false;
                    switch_next_service.setVisibility(View.GONE);
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
        tv_sure_continue.setOnClickListener(this);
        tv_next_service_date.setOnClickListener(this);
        tv_go_shop_date.setOnClickListener(this);
        switch_go_shop.setOnClickListener(this);
        switch_next_service.setOnClickListener(this);
    }

    @Override
    public void initData() {
        tv_go_shop_date.setText(AbDateUtil.getCurrentDate("yyyy-MM-dd"));
        tv_next_service_date.setText(AbDateUtil.getCurrentDate("yyyy-MM-dd"));

        Bundle bundle=getIntent().getExtras();
        id=bundle.getInt("Id");
        VipId=bundle.getInt("VipId");
        Name=bundle.getString("Name");
        Phone=bundle.getString("Phone");
        Sex=bundle.getString("sex");
//        multipleServiceData=(List<MultipleServiceBean>)bundle.getSerializable("MultipleServiceList");
        tv_name.setText(Name);
        tv_phone.setText(Phone);

        getVipTaskNet=new GetVipTaskNet(mContext);
        getVipTaskNet.setData(VipId);

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
            case R.id.tv_cancel:
                Skip.mBack(mActivity);
                break;
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
            break;
            case R.id.switch_go_shop:
                if(isGoShop==false){
                    ToastFactory.getLongToast(mContext,"已开启提醒");
                    CreateShopTask=1;
                }else{
                    ToastFactory.getLongToast(mContext,"已关闭提醒");
                    CreateShopTask=0;
                }
                break;
            case R.id.switch_next_service:
                if(isNextService==false){
                    ToastFactory.getLongToast(mContext,"已开启提醒");
                    CreateServerTask=1;
                }else{
                    ToastFactory.getLongToast(mContext,"已关闭提醒");
                    CreateServerTask=0;
                }
                break;
            case R.id.tv_sure_continue:
                if(!TextUtils.isEmpty(et_desc.getText().toString()) && !"".equals(et_desc.getText().toString())){
                    taskCotiue();
                }else{
                    ToastFactory.getLongToast(mContext,"请输入沟通内容");
                }
                break;
            case R.id.tv_sure:
                if(!TextUtils.isEmpty(et_desc.getText().toString()) && !"".equals(et_desc.getText().toString())){
                    showPopupWindow(tv_sure);
                }else{
                    ToastFactory.getLongToast(mContext,"请输入沟通内容");
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
        DatePickerDialog dpd = DatePickerDialog.newInstance(MemberServiceResultActivity.this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
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


    private View view;
    private RecyclerView mServiceRecyclerView;
    private LinearLayout ll_sure_close;
    private ImageView iv_close_service;
    private CloseServiceAdapter serviceAdapter;

    private boolean isSelectAll = false;
    private boolean editorStatus = true;
    private int index = 0;

    private JSONArray taskIds;

    /**
     * 显示PopupWindow弹出菜单
     */
    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= layoutInflater.inflate(R.layout.popwindow_service_layout, null);
            dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            // 创建一个PopuWidow对象
            popWindow = new PopupWindow(view, dm.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_blue_bg));
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(false);
        // PopupWindow的显示及位置设置
//        if(Build.VERSION.SDK_INT >= 24){
            popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//        }else if(Build.VERSION.SDK_INT <= 20){
//            popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//        }else{
//            popWindow.setOutsideTouchable(false);
//            showAsDropDown(popWindow,parent,0,0);
//        }

        iv_close_service= (ImageView) view.findViewById(R.id.iv_close_service);
        mServiceRecyclerView= (RecyclerView) view.findViewById(R.id.rv_service_list);
        mServiceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ll_sure_close= (LinearLayout) view.findViewById(R.id.ll_sure_close);
        ll_sure_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                taskIds=new JSONArray();
                for (int i=0;i<serviceAdapter.getDataList().size();i++){
                    if(serviceAdapter.getDataList().get(i).isSelect()==true){
                        //获取已选服务数据
                        taskIds.put(serviceAdapter.getDataList().get(i).getTaskId());
                    }
                }

                closeTask(taskIds);
            }
        });
        iv_close_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });

        serviceAdapter=new CloseServiceAdapter(mContext);
        mServiceRecyclerView.setAdapter(serviceAdapter);
        serviceAdapter.notifyAdapter(multipleServiceData,false);
        serviceAdapter.setOnItemClickListener(this);
    }

    /**
     *
     * @param pw     popupWindow
     * @param anchor v
     * @param xoff   x轴偏移
     * @param yoff   y轴偏移
     */
    public static void showAsDropDown(final PopupWindow pw, final View anchor, final int xoff, final int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            pw.setHeight(height);
            pw.showAsDropDown(anchor, xoff, yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }

    @Override
    public void onItemClickListener(int pos, List<MultipleServiceBean> myLiveList) {
        if (editorStatus) {
            MultipleServiceBean myLive = myLiveList.get(pos);
            boolean isSelect = myLive.isSelect();
            if (!isSelect) {
                index++;
                myLive.setSelect(true);
                if (index == myLiveList.size()) {
                    isSelectAll = true;
                }
            } else {
                myLive.setSelect(false);
                index--;
                isSelectAll = false;
            }
            serviceAdapter.notifyDataSetChanged();
        }
    }

    public void closeTask(JSONArray taskIds){
        if(isGoShopDate==true && isNextServiceDate==true){
            if(VipId!=0){
                finishAndCloseNet=new FinishAndCloseNet(mContext);
                finishAndCloseNet.setData(id,VipId,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),0,CreateShopTask,CreateServerTask,taskIds);
            }else{
                finishAndCloseNet=new FinishAndCloseNet(mContext);
                finishAndCloseNet.setData(id,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),CreateShopTask,CreateServerTask,taskIds);
            }
        }else if(isNextServiceDate==true){
            if(VipId!=0){
                finishAndCloseNet=new FinishAndCloseNet(mContext);
                finishAndCloseNet.setData(id,VipId,serviceType,serviceResult,goShop,tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),0,0,CreateShopTask,CreateServerTask,taskIds);
            }else{
                finishAndCloseNet=new FinishAndCloseNet(mContext);
                finishAndCloseNet.setData(id,serviceType,serviceResult,goShop,tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),0,CreateShopTask,CreateServerTask,taskIds);
            }
        }else if(isGoShopDate==true){
            if(VipId!=0){
                finishAndCloseNet=new FinishAndCloseNet(mContext);
                finishAndCloseNet.setData(id,VipId,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),0,0,CreateShopTask,CreateServerTask,taskIds);
            }else{
                finishAndCloseNet=new FinishAndCloseNet(mContext);
                finishAndCloseNet.setData(id,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),CreateShopTask,CreateServerTask,taskIds);
            }
        }else{
            if(VipId!=0){
                finishAndCloseNet=new FinishAndCloseNet(mContext);
                finishAndCloseNet.setData(id,VipId,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),0,CreateShopTask,CreateServerTask,taskIds);
            }else{
                finishAndCloseNet=new FinishAndCloseNet(mContext);
                finishAndCloseNet.setData(id,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),CreateShopTask,CreateServerTask,taskIds);
            }
        }
    }


    public void taskCotiue(){
       if(isGoShopDate==true && isNextServiceDate==true){
        if(VipId!=0){
            net=new FinishNotCloseNet(mContext);
            net.setData(id,VipId,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),0,CreateShopTask,CreateServerTask);
        }else{
            net=new FinishNotCloseNet(mContext);
            net.setData(id,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),CreateShopTask,CreateServerTask);
        }
        }else if(isNextServiceDate==true){
            if(VipId!=0){
                net=new FinishNotCloseNet(mContext);
                net.setData(id,VipId,serviceType,serviceResult,goShop,tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),0,0,CreateShopTask,CreateServerTask);
            }else{
                net=new FinishNotCloseNet(mContext);
                net.setData(id,serviceType,serviceResult,goShop,tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),0,CreateShopTask,CreateServerTask);
            }
        }else if(isGoShopDate==true){
            if(VipId!=0){
                net=new FinishNotCloseNet(mContext);
                net.setData(id,VipId,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),0,0,CreateShopTask,CreateServerTask);
            }else{
                net=new FinishNotCloseNet(mContext);
                net.setData(id,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),CreateShopTask,CreateServerTask,"");
            }
        }else{
            if(VipId!=0){
                net=new FinishNotCloseNet(mContext);
                net.setData(id,VipId,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),0,CreateShopTask,CreateServerTask);
            }else{
                net=new FinishNotCloseNet(mContext);
                net.setData(id,serviceType,serviceResult,goShop,tv_go_shop_date.getText().toString(),tv_next_service_date.getText().toString(),array,et_remark.getText().toString(),et_desc.getText().toString(),CreateShopTask,CreateServerTask);
            }
        }
    }
}
