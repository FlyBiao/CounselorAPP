package com.cesaas.android.counselor.order.member.salestalk;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.salestalk.bean.ResultCustomAddBean;
import com.cesaas.android.counselor.order.salestalk.bean.ResultCustomDeleteBean;
import com.cesaas.android.counselor.order.salestalk.bean.ResultCustomListBean;
import com.cesaas.android.counselor.order.salestalk.net.CustomAddNet;
import com.cesaas.android.counselor.order.salestalk.net.CustomDeleteNet;
import com.cesaas.android.counselor.order.salestalk.net.GetCustomListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 自定义销售话术
 * Created at 2018/6/11 14:30
 * Version 1.0
 */

public class SalesTalkCustomListActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle;
    private LinearLayout llBack;
    private TextView tv_add_custom_salestalk;
    private LoadMoreListView mLoadMoreListView;//加载更多
    private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新

    private boolean refresh=false;
    private static int pageIndex = 1;//当前页
    private int type;

    private CustomAddSelaStalkDialog addSelaStalkDialog;

    //自定义话术列表
    private GetCustomListNet customListNet;
    private List<ResultCustomListBean.CustomListBean> customListBeans=new ArrayList<ResultCustomListBean.CustomListBean>();

    //新增自定义话术
    private CustomAddNet customAddNet;
    //删除指定自定义话术
    private CustomDeleteNet customDeleteNet;

    private CommonAdapter<ResultCustomListBean.CustomListBean> adapter;
    private static SalesTalkCustomListActivity fragment;

    public static Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {

            pageIndex=1;
            fragment.loadData(pageIndex);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_sales_talk);
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("自定义话术");
        mLoadMoreListView=(LoadMoreListView) findViewById(R.id.load_more_custom_selastlk_list);
        mRefreshAndLoadMoreView=(RefreshAndLoadMoreView)findViewById(R.id.refresh_custom_selastlk_and_load_more);
        tv_add_custom_salestalk=(TextView)findViewById(R.id.tv_add_custom_salestalk);
        tv_add_custom_salestalk.setOnClickListener(this);

        initData();
    }
    /**
     * 设置自定义话术List数据适配器
     */
    private void setAdapter() {
        adapter=new CommonAdapter<ResultCustomListBean.CustomListBean>(mContext,R.layout.item_custom_selastlk,customListBeans) {

            @Override
            public void convert(ViewHolder holder, ResultCustomListBean.CustomListBean bean, final int postion) {
                holder.setText(R.id.tv_custom_selastlk_title, bean.Content);
                holder.getView(R.id.tv_delete_custom).setVisibility(View.VISIBLE);
                holder.setOnClickListener(R.id.tv_delete_custom, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MyAlertDialog(mContext).mInitShow("删除提示！", "是否确定删除该条话术信息！",
                                "确定", "点错了", new MyAlertDialog.ConfirmListener() {
                                    @Override
                                    public void onClick(Dialog dialog) {
                                        //删除自定义话术
                                        customDeleteNet=new CustomDeleteNet(mContext);
                                        customDeleteNet.setData(customListBeans.get(postion).Id);

                                        customListBeans.remove(postion);
                                        notifyDataSetChanged();

                                    }
                                }, null);
                    }
                });

            }
        };
        mLoadMoreListView.setAdapter(adapter);
        mRefreshAndLoadMoreView.setLoadMoreListView(mLoadMoreListView);
        mLoadMoreListView.setRefreshAndLoadMoreView(mRefreshAndLoadMoreView);
        // 设置下拉刷新监听
        mRefreshAndLoadMoreView
                .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refresh=true;
                        pageIndex = 1;
                        loadData(pageIndex);
                    }
                });
        // 设置加载监听
        mLoadMoreListView
                .setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        refresh=false;
                        loadData(pageIndex + 1);
                    }
                });
        mLoadMoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type==1){
                    setSendDialog(customListBeans.get(position).Content);
                }else{
                    Intent mIntent = new Intent();
                    mIntent.putExtra("result",customListBeans.get(position).Content);
                    setResult(10, mIntent);
                    finish();
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

        Intent intent=getIntent();
        String strType = intent.getStringExtra("Type");
        type=Integer.valueOf(strType);


        loadData(1);
    }

    /**
     * 加载数据
     * @param page 当前页
     */
    public void loadData(final int page) {

        if (page == 1) {
            customListBeans.clear();
        }
        customListNet=new GetCustomListNet(mContext);
        customListNet.setData(page);

        pageIndex = page;
    }

    /**
     * 接受EventBus发送消息【删除自定义话术】
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultCustomDeleteBean msg){
        if(msg.IsSuccess==true){
            ToastFactory.getLongToast(mContext, "删除成功!");

        }else{
            ToastFactory.getLongToast(mContext, "删除失败："+msg.Message);
        }
    }

    /**
     * 接受EventBus发送消息【新增自定义话术】
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultCustomAddBean msg){
        if(msg.IsSuccess==true){
            ToastFactory.getLongToast(mContext, "新增话术成功!");
            //关闭添加自定义话术对话框
            addSelaStalkDialog.dismiss();
            //刷新页面数据
            initData();
        }else{
            ToastFactory.getLongToast(mContext, "新增话术失败："+msg.Message);
        }
    }

    /**
     * 接受EventBus发送消息【自定义话术列表】
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultCustomListBean msg){
        if(msg.IsSuccess==true){
//			customListBeans.clear();
            if (msg.TModel.size() > 0&&msg.TModel.size()==20) {
                mLoadMoreListView.setHaveMoreData(true);
            } else {
                mLoadMoreListView.setHaveMoreData(false);
            }
            if(msg.TModel.size()!=0){
                customListBeans.addAll(msg.TModel);
                setAdapter();
            }
            mRefreshAndLoadMoreView.setRefreshing(false);
            mLoadMoreListView.onLoadComplete();
        }
        else{
            ToastFactory.getLongToast(mContext, msg.Message);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_custom_salestalk://添加自定义话术
                addSelaStalkDialog=new CustomAddSelaStalkDialog(mContext, R.style.dialog, R.layout.add_custom_selatalk_dialog);
                addSelaStalkDialog.setCancelable(true);
                addSelaStalkDialog.show();
                break;
        }
    }

    /**
     * 自定义添加话术Dialog
     * @author FGB
     *
     */
    public class CustomAddSelaStalkDialog extends Dialog implements View.OnClickListener {
        private Button btn_suer_add_sela_dialog,btn_cancel_add_sela_dialog;
        private MClearEditText mcet_add_sela_content;
        int layoutRes;//布局文件
        Context context;
        /**
         * 自定义店员列表Dialog构造函数
         * @param context 上下文
         * @param theme 主题
         * @param resLayout 布局
         */
        public CustomAddSelaStalkDialog(Context context, int theme,int resLayout){
            super(context, theme);
            this.context = context;
            this.layoutRes=resLayout;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(layoutRes);
            initView();
        }

        public void initView(){
            mcet_add_sela_content=(MClearEditText) findViewById(R.id.mcet_add_sela_content);
            btn_suer_add_sela_dialog=(Button) findViewById(R.id.btn_suer_add_sela_dialog);
            btn_cancel_add_sela_dialog=(Button) findViewById(R.id.btn_cancel_add_sela_dialog);
            btn_suer_add_sela_dialog.setOnClickListener(this);
            btn_cancel_add_sela_dialog.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_suer_add_sela_dialog://确定
                    if(!TextUtils.isEmpty(mcet_add_sela_content.getText().toString())){
                        customAddNet=new CustomAddNet(getContext());
                        customAddNet.setData(mcet_add_sela_content.getText().toString());

                    }else{
                        ToastFactory.getLongToast(getContext(), "请输入需要自定义的话术内容!");
                    }

                    break;

                case R.id.btn_cancel_add_sela_dialog://取消
                    addSelaStalkDialog.dismiss();
                    break;

                default:
                    break;
            }
        }
    }

    private Dialog sendDialog;
    private View dialogContentView;
    private EditText et_service_content;
    private TextView tv_copy;
    private LinearLayout ll_service_send;
    public void setSendDialog(String content){
        sendDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_sales_talk, null);
        sendDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);
        tv_copy= (TextView) dialogContentView.findViewById(R.id.tv_copy);
        et_service_content= (EditText) sendDialog.findViewById(R.id.et_service_content);
        et_service_content.setText(content);
        ll_service_send= (LinearLayout) sendDialog.findViewById(R.id.ll_service_send);
        ll_service_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.putExtra("result",et_service_content.getText().toString());
                setResult(10, mIntent);// 设置结果，并进行传送
                finish();
            }
        });
        tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(et_service_content.getText().toString())){
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(et_service_content.getText().toString());
                    ToastFactory.getLongToast(mContext,"复制成功");
                }else{
                    ToastFactory.getLongToast(mContext,"找不到可复制的内容");
                }
            }
        });

        sendDialog.getWindow().setGravity(Gravity.BOTTOM);
        sendDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        sendDialog.setCanceledOnTouchOutside(true);
        sendDialog.show();
    }
}
