package com.cesaas.android.counselor.order.manager.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.alioss.OSSKey;
import com.cesaas.android.counselor.order.boss.bean.ResultClerkBean;
import com.cesaas.android.counselor.order.compressimage.CompressHelper;
import com.cesaas.android.counselor.order.custom.pop.InitCSpinerPopWindow;
import com.cesaas.android.counselor.order.custom.pop.InitSpinerPopWindow;
import com.cesaas.android.counselor.order.custom.pop.SpinerListBean;
import com.cesaas.android.counselor.order.custom.pop.SpinerPopWindow;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.manager.adapter.AddTaskExecutorAdapter;
import com.cesaas.android.counselor.order.manager.bean.ClerkBean;
import com.cesaas.android.counselor.order.manager.bean.ContractsBean;
import com.cesaas.android.counselor.order.manager.bean.ResultCreateReleaseTaskBean;
import com.cesaas.android.counselor.order.manager.bean.ResultCreateTaskBean;
import com.cesaas.android.counselor.order.manager.bean.ResultTemplateListBean;
import com.cesaas.android.counselor.order.manager.net.CreateReleaseNet;
import com.cesaas.android.counselor.order.manager.net.CreateTaskNet;
import com.cesaas.android.counselor.order.manager.net.TemplateListNet;
import com.cesaas.android.counselor.order.shopmange.ChooseClerkActivity;
import com.cesaas.android.counselor.order.shoptask.bean.SelectCounselorListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.GetFileNameUtils;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.flybiao.materialdialog.MaterialDialog;
import com.lidroid.xutils.exception.HttpException;
import com.sing.datetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import io.rong.eventbus.EventBus;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 添加任务
 */
public class AddShopTaskActivity extends BasesActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener,EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate{

    private TextView tvTitle,tvLeftTitle;
    private TextView et_task_title;
    private TextView tv_select_task_type,tv_select_level_type;
    private TextView tv_task_start_date,tv_task_end_date;
    private CheckBox cb_load_exeutor,cb_load_related;
    private LinearLayout llBack,ll_submit_task,ll_create;
    private RecyclerView mRecyclerView;
    private RecyclerView mRelatedRecyclerViewRelated;
    /**
     * 拖拽排序九宫格控件
     */
    private BGASortableNinePhotoLayout mPhotosSnpl;

    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW = 2;

    private static String ossImageUrl;//
    private static OSS oss;
    private File oldFile;
    private File newFile;

    private String leftTitle;
    private int dateType=0;
    private int chooseClerkType;
    private int CategoryId;
    private int TemplateId;
    private int Status;
    private int TaskLeve;

    private MaterialDialog mMaterialDialog;

    private SpinerPopWindow<String> mSpinerPopWindow;
    private List<SpinerListBean> list;
    private SpinerPopWindow<String> mSpinerPopWindow2;
    private List<SpinerListBean> list2;

    private AddTaskExecutorAdapter addTaskExecutorAdapter;
    private List<ClerkBean> dataClerkBeanList;
    private AddTaskExecutorAdapter addTaskExecutorAdapter2;
    private List<ClerkBean> dataClerkBeanList2;

    private CreateTaskNet createTaskNet;
    private CreateReleaseNet createReleaseNet;
    private TemplateListNet templateListNet;

    private JSONArray imgArray=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop_task);
        mMaterialDialog=new MaterialDialog(mContext);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            leftTitle=bundle.getString("leftTitle");
        }

        initView();
        initData();
    }

    /**
     * 接收新建任务结果
     * @param msg
     */
//    public void onEventMainThread(ResultCreateReleaseTaskBean msg) {
//        if(msg.IsSuccess==true){
//            ToastFactory.getToast(mContext,"创建发布任务成功!");
//            Skip.mNext(mActivity,ManagerMainActivity.class);
//        }else{
//            ToastFactory.getToast(mContext,"创建发布任务失败："+msg.Message);
//        }
//    }

    /**
     * 接收新建任务结果
     * @param msg
     */
//    public void onEventMainThread(ResultCreateTaskBean msg) {
//        if(msg.IsSuccess==true){
//            ToastFactory.getToast(mContext,"加入草稿箱成功!");
//            Skip.mNext(mActivity,ManagerMainActivity.class);
//        }else{
//            ToastFactory.getToast(mContext,"加入草稿箱失败："+msg.Message);
//        }
//    }

    /**
     * 接收模板列表结果
     * @param msg
     */
    public void onEventMainThread(ResultTemplateListBean msg) {
        if(msg.IsSuccess==true) {
            if(msg.TModel!=null && msg.TModel.size()!=0){
                list=new ArrayList<>();
                for (int i=0;i<msg.TModel.size();i++){
                    SpinerListBean bean=new SpinerListBean();
                    bean.setName(msg.TModel.get(i).getTemplateName());
                    bean.setId(msg.TModel.get(i).getTemplateId());
                    bean.setCategoryId(msg.TModel.get(i).getCategoryId());
                    bean.setStatus(msg.TModel.get(i).getStatus());
                    list.add(bean);
                }
                InitSpinerPopWindow initSpinerPopWindow=new InitSpinerPopWindow(mContext,tv_select_task_type);
                mSpinerPopWindow = new SpinerPopWindow<>(this, list,itemClickListener);
                tv_select_task_type.setOnClickListener(initSpinerPopWindow.showPopupWindow(mSpinerPopWindow));
                mSpinerPopWindow.setOnDismissListener(initSpinerPopWindow.dismissListener);
            }
        }
    }

    private void initData(){
        templateListNet=new TemplateListNet(mContext);
        templateListNet.setData();
        imgArray=new JSONArray();

        list2=new ArrayList<>();
        SpinerListBean beans=new SpinerListBean();
        beans.setId(0);
        beans.setName("普通");
        list2.add(beans);

        SpinerListBean beans2=new SpinerListBean();
        beans2.setId(1);
        beans2.setName("重要不紧急");
        list2.add(beans2);

        SpinerListBean beans3=new SpinerListBean();
        beans3.setId(2);
        beans3.setName("紧急不重要");
        list2.add(beans3);

        SpinerListBean beans4=new SpinerListBean();
        beans4.setId(3);
        beans4.setName("重要而且紧急");
        list2.add(beans4);

        InitCSpinerPopWindow initSpinerPopWindow2=new InitCSpinerPopWindow(mContext,tv_select_level_type);
        mSpinerPopWindow2 = new SpinerPopWindow<>(this, list2,itemClickListener2);
        tv_select_level_type.setOnClickListener(initSpinerPopWindow2.showPopupWindow(mSpinerPopWindow2));
        mSpinerPopWindow2.setOnDismissListener(initSpinerPopWindow2.dismissListener);

        dataClerkBeanList=new ArrayList<>();
        ClerkBean clerkBeantest=new ClerkBean();
        clerkBeantest.setId(2008);
        clerkBeantest.setIcon("http://112.74.135.25/Dowlond/img/add_clerk.png");
        dataClerkBeanList.add(clerkBeantest);
        addTaskExecutorAdapter=new AddTaskExecutorAdapter(dataClerkBeanList);
        mRecyclerView.setAdapter(addTaskExecutorAdapter);

        dataClerkBeanList2=new ArrayList<>();
        ClerkBean clerkBeantest2=new ClerkBean();
        clerkBeantest2.setId(2008);
        clerkBeantest2.setIcon("http://112.74.135.25/Dowlond/img/add_clerk.png");
        dataClerkBeanList2.add(clerkBeantest2);
        addTaskExecutorAdapter2=new AddTaskExecutorAdapter(dataClerkBeanList2);
        mRelatedRecyclerViewRelated.setAdapter(addTaskExecutorAdapter2);

        setAdapterOnItemClick();
    }

    private void initView() {
        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tvLeftTitle.setText(leftTitle);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("新建任务");
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        et_task_title= (TextView) findViewById(R.id.et_task_title);

        cb_load_exeutor= (CheckBox) findViewById(R.id.cb_load_exeutor);
        cb_load_related= (CheckBox) findViewById(R.id.cb_load_related);
        et_task_title= (TextView) findViewById(R.id.et_task_title);
        tv_select_task_type= (TextView) findViewById(R.id.tv_select_task_type);
        tv_select_level_type= (TextView) findViewById(R.id.tv_select_level_type);
        tv_task_start_date= (TextView) findViewById(R.id.tv_task_start_date);
        tv_task_start_date.setOnClickListener(this);
        tv_task_end_date= (TextView) findViewById(R.id.tv_task_end_date);
        tv_task_end_date.setOnClickListener(this);
        ll_submit_task= (LinearLayout) findViewById(R.id.ll_submit_task);
        ll_submit_task.setOnClickListener(this);
        ll_create= (LinearLayout) findViewById(R.id.ll_create);
        ll_create.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_executor_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mRelatedRecyclerViewRelated = (RecyclerView) findViewById(R.id.rv_related_list);
        mRelatedRecyclerViewRelated.setLayoutManager(new GridLayoutManager(this,4));

        mPhotosSnpl = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_moment_add_photos);
        mPhotosSnpl.setMaxItemCount(9);
        mPhotosSnpl.setEditable(true);
        mPhotosSnpl.setPlusEnable(true);
        mPhotosSnpl.setSortable(true);
        // 设置拖拽排序控件的代理
        mPhotosSnpl.setDelegate(this);
    }

    public void setAdapterOnItemClick(){

        cb_load_exeutor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    if(mCache.getAsJSONArray("exector")!=null){
                        String strJson=JsonUtils.toJson(mCache.getAsJSONArray("exector"));
                        ResultClerkBean bean=JsonUtils.fromJson(strJson,ResultClerkBean.class);
                        for (int i=0;i<bean.values.size();i++){
                            if(bean.values.get(i).nameValuePairs.getId()!=2008){
                                ClerkBean clerkBean=new ClerkBean();
                                clerkBean.setId(bean.values.get(i).nameValuePairs.getId());
                                clerkBean.setName(bean.values.get(i).nameValuePairs.getName());
                                clerkBean.setShop(bean.values.get(i).nameValuePairs.getShop());
                                clerkBean.setIcon(bean.values.get(i).nameValuePairs.getIcon());
                                dataClerkBeanList.add(clerkBean);
                            }
                        }
                        addTaskExecutorAdapter=new AddTaskExecutorAdapter(dataClerkBeanList);
                        mRecyclerView.setAdapter(addTaskExecutorAdapter);
                    }else{
                        ToastFactory.getLongToast(mContext,"最近没有加载执行人额！");
                    }

                }else{
                    ToastFactory.getLongToast(mContext,"取消加载执行人");
                }
            }
        });
        cb_load_related.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    if(mCache.getAsJSONArray("related")!=null){
                        String strJson=JsonUtils.toJson(mCache.getAsJSONArray("related"));
                        ResultClerkBean bean=JsonUtils.fromJson(strJson,ResultClerkBean.class);
                        for (int i=0;i<bean.values.size();i++){
                            if(bean.values.get(i).nameValuePairs.getId()!=2008){
                                ClerkBean clerkBean=new ClerkBean();
                                clerkBean.setId(bean.values.get(i).nameValuePairs.getId());
                                clerkBean.setName(bean.values.get(i).nameValuePairs.getName());
                                clerkBean.setShop(bean.values.get(i).nameValuePairs.getShop());
                                clerkBean.setIcon(bean.values.get(i).nameValuePairs.getIcon());
                                dataClerkBeanList2.add(clerkBean);
                            }
                        }
                        addTaskExecutorAdapter2=new AddTaskExecutorAdapter(dataClerkBeanList2);
                        mRelatedRecyclerViewRelated.setAdapter(addTaskExecutorAdapter);
                    }else{
                        ToastFactory.getLongToast(mContext,"最近没有加载相关人员额！");
                    }

                }else{

                }
            }
        });

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(dataClerkBeanList.get(position).getId()==2008){
                    chooseClerkType=1;
                    Intent intent= new Intent(mContext, ChooseClerkActivity.class);
                    Skip.mSelActivityResult(mActivity, Constant.H5_CLERK_SELECT,intent);
                }
            }
        });
       mRelatedRecyclerViewRelated.addOnItemTouchListener(new OnItemClickListener() {
           @Override
           public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
               if(dataClerkBeanList2.get(position).getId()==2008){
                   chooseClerkType=2;
                   Intent intent= new Intent(mContext, ChooseClerkActivity.class);
                   Skip.mSelActivityResult(mActivity, Constant.H5_CLERK_SELECT,intent);
               }
           }
       });

    }


    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            CategoryId=list.get(position).getStatus();
            TemplateId=list.get(position).getId();
            Status=list.get(position).getStatus();
            tv_select_task_type.setText(list.get(position).getName());
        }
    };
    private AdapterView.OnItemClickListener itemClickListener2 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow2.dismiss();
            TaskLeve=list2.get(position).getId();
//            TemplateId=list2.get(position).getId();
//            Status=list2.get(position).getStatus();
            tv_select_level_type.setText(list2.get(position).getName());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_task_start_date:
                dateType=0;
                getDateSelect(tv_task_start_date);
                break;
            case R.id.tv_task_end_date:
                dateType=1;
                getDateSelect(tv_task_end_date);
                break;
            case R.id.ll_create:
                mCache.put("exector",JsonUtils.toJson(dataClerkBeanList));
                mCache.put("related",JsonUtils.toJson(dataClerkBeanList2));

                JSONArray contractsJsonArray=new JSONArray();
                for (int i=0;i<dataClerkBeanList.size();i++){
                    if(dataClerkBeanList.get(i).getId()!=2008){
                        ContractsBean contracts=new ContractsBean();
                        contracts.setCounselorId(dataClerkBeanList.get(i).getId());
                        contracts.setCounselorName(dataClerkBeanList.get(i).getName());
                        contracts.setDescription("执行人员");
                        contracts.setImageUrl(dataClerkBeanList.get(i).getIcon());
                        contracts.setShopId(dataClerkBeanList.get(i).getShopId());
                        contractsJsonArray.put(contracts.getInfo());
                    }
                }
                JSONArray contractBakJsonArray=new JSONArray();
                for (int i=0;i<dataClerkBeanList.size();i++){
                    if(dataClerkBeanList.get(i).getId()!=2008){
                        ContractsBean contractBak=new ContractsBean();
                        contractBak.setCounselorId(dataClerkBeanList.get(i).getId());
                        contractBak.setCounselorName(dataClerkBeanList.get(i).getName());
                        contractBak.setDescription("相关人员");
                        contractBak.setImageUrl(dataClerkBeanList.get(i).getIcon());
                        contractBak.setShopId(dataClerkBeanList.get(i).getShopId());
                        contractBakJsonArray.put(contractBak.getInfo());
                    }
                }

                createTaskNet=new CreateTaskNet(mContext);
                createTaskNet.setData(et_task_title.getText().toString(),tv_task_start_date.getText().toString(),
                        tv_task_end_date.getText().toString(),imgArray,CategoryId,TemplateId,TaskLeve,prefs.getString("nickName"),contractsJsonArray,contractBakJsonArray);

                break;
            case R.id.ll_submit_task:
                if (mMaterialDialog != null) {
                    mMaterialDialog.setTitle("温馨提示！")
                            .setMessage("确定发布改任务吗？")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    //执行确定操作
                                    mMaterialDialog.dismiss();
                                    mCache.put("exector",JsonUtils.toJson(dataClerkBeanList));
                                    mCache.put("related",JsonUtils.toJson(dataClerkBeanList2));

                                    JSONArray contractsJsonArray1=new JSONArray();
                                    for (int i=0;i<dataClerkBeanList.size();i++){
                                        if(dataClerkBeanList.get(i).getId()!=2008){
                                            ContractsBean contracts=new ContractsBean();
                                            contracts.setCounselorId(dataClerkBeanList.get(i).getId());
                                            contracts.setCounselorName(dataClerkBeanList.get(i).getName());
                                            contracts.setDescription("执行人员");
                                            contracts.setImageUrl(dataClerkBeanList.get(i).getIcon());
                                            contracts.setShopId(dataClerkBeanList.get(i).getShopId());
                                            contractsJsonArray1.put(contracts.getInfo());
                                        }
                                    }
                                    JSONArray contractBakJsonArray1=new JSONArray();
                                    for (int i=0;i<dataClerkBeanList.size();i++){
                                        if(dataClerkBeanList.get(i).getId()!=2008){
                                            ContractsBean contractBak=new ContractsBean();
                                            contractBak.setCounselorId(dataClerkBeanList.get(i).getId());
                                            contractBak.setCounselorName(dataClerkBeanList.get(i).getName());
                                            contractBak.setDescription("相关人员");
                                            contractBak.setImageUrl(dataClerkBeanList.get(i).getIcon());
                                            contractBak.setShopId(dataClerkBeanList.get(i).getShopId());
                                            contractBakJsonArray1.put(contractBak.getInfo());
                                        }
                                    }

                                    createReleaseNet=new CreateReleaseNet(mContext);
                                    createReleaseNet.setData(et_task_title.getText().toString(),tv_task_start_date.getText().toString(),tv_task_end_date.getText().toString(),imgArray,CategoryId,TemplateId,TaskLeve,prefs.getString("nickName"),contractsJsonArray1,contractBakJsonArray1);

                                }
                            }).setNegativeButton("返回", new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            mMaterialDialog.dismiss();
//
                        }}).setCanceledOnTouchOutside(true).show();
                }

                break;
        }
    }

    /**
     * 日期选择选择
     * @param v
     */
    public void getDateSelect(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(AddShopTaskActivity.this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.setThemeDark(false);// boolean,DarkTheme
        dpd.vibrate(true);// boolean,触摸震动
        dpd.dismissOnPause(false);// boolean,Pause时是否Dismiss
        dpd.showYearPickerFirst(false);// boolean,先选择年
        if (true) {// boolean,自定义颜色
            dpd.setAccentColor(getResources().getColor(R.color.darkcyan));
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
        if(dateType==0){
            tv_task_start_date.setText(date+" 00:00:00");
        }else{
            tv_task_end_date.setText(date+" 23:59:59");
        }
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(this, mPhotosSnpl.getMaxItemCount(), models, models, position, false), REQUEST_CODE_PHOTO_PREVIEW);
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

            startActivityForResult(BGAPhotoPickerActivity.newIntent(this, true ? takePhotoDir : null, mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount(), null, false), REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
            if (false) {//是否单选
                mPhotosSnpl.setData(BGAPhotoPickerActivity.getSelectedImages(data));
            } else {
                mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                //获取照片集合
                for (int i=0;i<BGAPhotoPickerActivity.getSelectedImages(data).size();i++){
                    // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
                    OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSSKey.ACCESS_ID, OSSKey.ACCESS_KEY);
                    oss = new OSSClient(mContext, OSSKey.OSS_ENDPOINT, credentialProvider);
                    //压缩图片
                    oldFile=new File(BGAPhotoPickerActivity.getSelectedImages(data).get(i));
                    newFile = CompressHelper.getDefault(mContext).compressToFile(oldFile);
                    //调用AliOss上传图片
                    setUploadAliOss(GetFileNameUtils.getFileName(newFile.getAbsolutePath(),prefs),newFile.getAbsolutePath());
                }
            }
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
            //获取照片集合
//            for (int i=0;i<BGAPhotoPickerPreviewActivity.getSelectedImages(data).size();i++){
//                Log.i("test","拍照:"+BGAPhotoPickerPreviewActivity.getSelectedImages(data).get(i));
//            }
        }
        if(resultCode==701 && data!=null){//店员选择
            List<SelectCounselorListBean> selectCounselorListBeen=(ArrayList<SelectCounselorListBean>)data.getSerializableExtra("selectList");
            if(chooseClerkType==1){
                for (int i=0;i<selectCounselorListBeen.size();i++){
                    ClerkBean clerkBean=new ClerkBean();
                    clerkBean.setId(selectCounselorListBeen.get(i).getCOUNSELOR_ID());
                    clerkBean.setShopId(selectCounselorListBeen.get(i).getSHOP_ID());
                    clerkBean.setName(selectCounselorListBeen.get(i).getCOUNSELOR_NICKNAME());
                    clerkBean.setShop(selectCounselorListBeen.get(i).getSHOP_NAME());
                    clerkBean.setIcon(selectCounselorListBeen.get(i).getCOUNSELOR_ICON());
                    dataClerkBeanList.add(clerkBean);
                }
                addTaskExecutorAdapter=new AddTaskExecutorAdapter(dataClerkBeanList);
                mRecyclerView.setAdapter(addTaskExecutorAdapter);

            }else{
                for (int i=0;i<selectCounselorListBeen.size();i++){
                    ClerkBean clerkBean=new ClerkBean();
                    clerkBean.setShopId(selectCounselorListBeen.get(i).getSHOP_ID());
                    clerkBean.setId(selectCounselorListBeen.get(i).getCOUNSELOR_ID());
                    clerkBean.setName(selectCounselorListBeen.get(i).getCOUNSELOR_NICKNAME());
                    clerkBean.setShop(selectCounselorListBeen.get(i).getSHOP_NAME());
                    clerkBean.setIcon(selectCounselorListBeen.get(i).getCOUNSELOR_ICON());
                    dataClerkBeanList2.add(clerkBean);
                }
                addTaskExecutorAdapter2=new AddTaskExecutorAdapter(dataClerkBeanList2);
                mRelatedRecyclerViewRelated.setAdapter(addTaskExecutorAdapter2);
            }
        }
    }

    public void setUploadAliOss(String imageName,String imageUrl){
        /**
         * 构造上传请求
         * bucketName - 上传到Bucket的名字
         * objectKey - 上传到OSS后的ObjectKey
         * uploadFilePath - 上传文件的本地路径
         */
        PutObjectRequest put = new PutObjectRequest(OSSKey.BUCKET_NAME, imageName, imageUrl);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//				Log.i(Constant.TAG, "Uploading..."+"=totalSize=="+totalSize+"==currentSize:"+currentSize);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        dialog.show();
                    }
                });

            }
        });
        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            /**
             * 上传失败
             * @param arg0
             * @param arg0
             *
             */
            @Override
            public void onFailure(PutObjectRequest arg0,
                                  com.alibaba.sdk.android.oss.ClientException clientExcepion,
                                  com.alibaba.sdk.android.oss.ServiceException serviceException) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        dialog.dismiss();
                    }
                });
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    Log.i(Constant.TAG,"onFailure：本地异常如网络异常等:"+clientExcepion);
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.i(Constant.TAG,"ErrorCode:"+serviceException.getErrorCode());
                    Log.i(Constant.TAG,"RequestId:"+serviceException.getRequestId());
                    Log.i(Constant.TAG,"HostId:"+serviceException.getHostId());
                    Log.i(Constant.TAG,"RawMessage:"+serviceException.getRawMessage());
                }
            }

            /**
             * 上传成功
             * @param request
             * @param result
             */
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                //获取上传阿里云Image
                ossImageUrl=OSSKey.IMAGE_URL+request.getObjectKey();
                imgArray.put(ossImageUrl);
            }
        });
    }


    /**
     * Author FGB
     * Description 创建发布
     * Created at 2017/9/15 13:35
     * Version 1.0
     */

    public class CreateReleaseNet extends BaseNet {
        public CreateReleaseNet(Context context) {
            super(context, true);
            this.uri="ShopTask/Sw/ShopTask/CreateRelease";
        }

        public void setData(String TaskTitle, String StartDate, String EndDate, JSONArray ImageUrl, int CategoryId,int TemplateId, int TaskLeve, String CreateName, JSONArray Contracts, JSONArray ContractBak) {
            try {
                data.put("TaskTitle",TaskTitle);
                data.put("StartDate",StartDate);
                data.put("EndDate",EndDate);
                data.put("Images",ImageUrl);
//            data.put("Status",Status);
                data.put("CategoryId",CategoryId);
                data.put("TemplateId",TemplateId);
                data.put("TaskLeve",TaskLeve);
                data.put("CreateName",CreateName);
                data.put("Contracts",Contracts);
                data.put("ContractBak",ContractBak);
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mPostNet(); // 开始请求网络
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
//            Log.i(Constant.TAG, "创建发布"+rJson);
            ResultCreateReleaseTaskBean bean=new ResultCreateReleaseTaskBean();
            if(bean.IsSuccess==true){
                ToastFactory.getToast(mContext,"创建发布任务成功!");
                Skip.mNext(mActivity,ManagerMainActivity.class);
            }else{
                ToastFactory.getToast(mContext,"创建发布任务失败："+bean.Message);
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
            Log.i(Constant.TAG, "创建发布"+err);
        }
    }
    /**
     * Author FGB
     * Description 新建任务草稿
     * Created at 2017/9/14 11:22
     * Version 1.0
     */

    public class CreateTaskNet extends BaseNet{
        public CreateTaskNet(Context context) {
            super(context, true);
            this.uri="ShopTask/Sw/ShopTask/Create";
        }

        public void setData(String TaskTitle, String StartDate, String EndDate,JSONArray ImageUrl, int CategoryId,int TemplateId, int TaskLeve, String CreateName, JSONArray Contracts,JSONArray ContractBak) {
            try {
                data.put("TaskTitle",TaskTitle);
                data.put("StartDate",StartDate);
                data.put("EndDate",EndDate);
                data.put("Images",ImageUrl);
//            data.put("Status",Status);
                data.put("CategoryId",CategoryId);
                data.put("TemplateId",TemplateId);
                data.put("TaskLeve",TaskLeve);
                data.put("CreateName",CreateName);
                data.put("Contracts",Contracts);
                data.put("ContractBak",ContractBak);
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mPostNet(); // 开始请求网络
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            Log.i(Constant.TAG, "新建任务草稿"+rJson);
            ResultCreateTaskBean bean=new ResultCreateTaskBean();
            if(bean.IsSuccess==true){
                ToastFactory.getToast(mContext,"加入草稿箱成功!");
                Skip.mNext(mActivity,ManagerMainActivity.class);
            }else{
                ToastFactory.getToast(mContext,"加入草稿箱失败："+bean.Message);
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
            Log.i(Constant.TAG, "新建任务草稿"+err);
        }
    }
}
