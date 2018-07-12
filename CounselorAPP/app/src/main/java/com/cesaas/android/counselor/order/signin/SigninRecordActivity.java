package com.cesaas.android.counselor.order.signin;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.user.VipDataScreenActivity;
import com.cesaas.android.counselor.order.custom.calendar.GetDateSelect;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.signin.adapter.SigninRecordAdapter;
import com.cesaas.android.counselor.order.signin.bean.ResultSigninBean;
import com.cesaas.android.counselor.order.signin.bean.ResultSigninRecordBean;
import com.cesaas.android.counselor.order.signin.bean.SigninRecordBean;
import com.cesaas.android.counselor.order.signin.net.SigninRecordNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.StringToArrayUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.sing.datetimepicker.date.DatePickerDialog;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 签到记录
 */
public class SigninRecordActivity extends BasesActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private LinearLayout llBack;
    private TextView tvBaseTitle;
    private EditText etStartDate,etEndDate;

    private int selectDate=0;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private SigninRecordAdapter signinRecordAdapter;
    private List<SigninRecordBean> mStrings;
    private int size = 20;
    private int pageIndex=1;
    private String startTime;
    private String endTime;

    private SigninRecordNet signinRecordNet;

    private Calendar now;
    private DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_record);

        startTime=AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00");
        endTime=AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59");

        initData(pageIndex);

        initView();

        now= Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(SigninRecordActivity.this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 初始化访问数据
     * @param pageIndex 当前页
     */
    public void initData(int pageIndex){
        signinRecordNet=new SigninRecordNet(mContext);
        //默认查询当天数据
        signinRecordNet.setData(pageIndex,startTime ,endTime);
    }

    /**
     * 接收获取签到类型消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultSigninRecordBean msg) {
        if(msg.IsSuccess==true){
            mStrings = new ArrayList<>();
            mStrings.addAll(msg.TModel);
            setData();

        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败"+msg.Message);
        }
    }

    /**
     * 初始化数据
     */
    private void setData() {
        signinRecordAdapter = new SigninRecordAdapter(mStrings,mContext);
        signinRecordAdapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(signinRecordAdapter);
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSwipeMenuRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //下拉刷新重新加载
                    initData(pageIndex);

                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    /**
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {// 手指不能向上滑动了
                // TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。

                Toast.makeText(SigninRecordActivity.this, "滑到最底部了，加载更多数据！", Toast.LENGTH_SHORT).show();
                size += 20;
                for (int i = size - 20; i < size; i++) {
                    SigninRecordBean bean=new SigninRecordBean();
                    bean.setCREATE_TIME("222");
                    bean.setREMARK("33eee");
                    bean.setSIGN_TYPE(i);
                    mStrings.add(bean);
                }
                signinRecordAdapter.notifyDataSetChanged();
            }
        }
    };

    /**
     * 初始化视图控件
     */
    private void initView() {
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);

        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
        etStartDate= (EditText) findViewById(R.id.et_start_date);
        etEndDate= (EditText) findViewById(R.id.et_end_date);
        tvBaseTitle.setText("签到记录");

        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。

        // 添加滚动监听【加载更多】。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        llBack.setOnClickListener(this);
        etStartDate.setOnClickListener(this);
        etEndDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;
            case R.id.et_start_date://开始日期
                selectDate=0;
                GetDateSelect. getDateSelect(etStartDate,mActivity,dpd);
                break;
            case R.id.et_end_date://结束日期
                if(!TextUtils.isEmpty(etStartDate.getText().toString())){
                    selectDate=1;
                    GetDateSelect. getDateSelect(etEndDate,mActivity,dpd);
                }
                else{
                    ToastFactory.getLongToast(mContext,"请先选择开始时间！");
                }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        if(selectDate==1){//结束日期
            etEndDate.setText(date);
            endTime=date +" 23:59:59";
        }else{//开始日期
            startTime=date +" 00:00:00";
            etStartDate.setText(date);
        }
    }
}
