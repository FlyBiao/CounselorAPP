package com.cesaas.android.counselor.order.member.fragment.service;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.bean.ResultVipGetOneBean;
import com.cesaas.android.counselor.order.member.net.service.ChangeMemberBirthDayNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.sing.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 更改会员生日
 * Created at 2018/2/27 17:33
 * Version 1.record
 */

public class MemberBirthdayFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, DatePickerDialog.OnDateSetListener {

    private TextView tv_birthday,tv_member_birthday,et_birthday;
    private LinearLayout ll_sure,tv_new_birthday;
    private EditText et_remark;

    private ChangeMemberBirthDayNet net;
    private String VipBirthDay;
    private int VipId;

    /**
     * 单例
     */
    public static MemberBirthdayFragment newInstance() {
        MemberBirthdayFragment fragmentCommon = new MemberBirthdayFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_member_birthday;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        et_birthday=findView(R.id.et_birthday);
        tv_new_birthday=findView(R.id.tv_new_birthday);
        tv_member_birthday=findView(R.id.tv_member_birthday);
        et_remark=findView(R.id.et_remark);
        ll_sure=findView(R.id.ll_sure);
        tv_birthday=findView(R.id.tv_birthday);
        tv_birthday.setText(R.string.fa_calendars);
        tv_birthday.setTypeface(App.font);
        tv_new_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDateSelect(tv_new_birthday);
            }
        });
    }

    @Override
    public void initListener() {
        ll_sure.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    public void onEventMainThread(ResultVipGetOneBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && !"".equals(msg.TModel)){
                VipId=msg.TModel.getVipId();
                VipBirthDay=msg.TModel.getBirthDay();
                tv_member_birthday.setText(AbDateUtil.getDateYMDs(VipBirthDay));
            }
        }
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.ll_sure:
                new MyAlertDialog(getContext()).mInitShow("温馨提示！", "是否确定修改该会员生日？",
                        "我知道", "点错了", new MyAlertDialog.ConfirmListener() {
                            @Override
                            public void onClick(Dialog dialog) {
                                net=new ChangeMemberBirthDayNet(getContext());
                                net.setData(VipId,tv_birthday.getText().toString(),et_remark.getText().toString());
                            }
                        }, null);
                break;
        }
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onRefresh() {

    }

    /**
     * 日期选择选择
     * @param v
     */
    public void getDateSelect(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
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
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        et_birthday.setText("");
            tv_birthday.setText(date);
        }
}
