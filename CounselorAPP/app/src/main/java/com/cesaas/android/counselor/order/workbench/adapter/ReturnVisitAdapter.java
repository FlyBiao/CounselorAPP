package com.cesaas.android.counselor.order.workbench.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.MemberFragment;
import com.cesaas.android.counselor.order.member.SendMessageActivity;
import com.cesaas.android.counselor.order.member.net.SaveVisitRecordNet;
import com.cesaas.android.counselor.order.utils.CallUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.workbench.bean.ReturnVisitBean;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.rong.imkit.RongIM;

/**
 * Author FGB
 * Description 客户回访Adapter
 * Created 2017/4/11 15:02
 * Version 1.zero
 */
public class ReturnVisitAdapter extends SwipeMenuAdapter<ReturnVisitAdapter.DefaultViewHolder>{

    private List<ReturnVisitBean> mReturnVisitBeanList;
    private OnItemClickListener mOnItemClickListener;
    public static Context mContext;
    public static Activity mActivity;

    private String VipId;
    private String VipName;

    public ReturnVisitAdapter(List<ReturnVisitBean> mReturnVisitBeanList,Context mContext,Activity mActivity){
        this.mReturnVisitBeanList=mReturnVisitBeanList;
        this.mActivity=mActivity;
        this.mContext=mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_return_visit, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, final int position) {
        holder.setData(mReturnVisitBeanList.get(position).getNickName(),mReturnVisitBeanList.get(position).getGrade(),mReturnVisitBeanList.get(position).getTime(),mReturnVisitBeanList.get(position).getImage());
        holder.setOnItemClickListener(mOnItemClickListener);

        //Tel回访
        holder.iv_tel_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VipId=mReturnVisitBeanList.get(position).getVipId()+"";
                VipName=mReturnVisitBeanList.get(position).getNickName();

                CallUtils.call(mReturnVisitBeanList.get(position).getMobile(),mActivity);

                //调用通话监听
                TelephonyManager phoneManager = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
                phoneManager.listen(new PhoneListenUtils(mContext),
                        PhoneStateListener.LISTEN_CALL_STATE);
            }
        });

        //SMS回访
        holder.iv_sms_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("Tel",mReturnVisitBeanList.get(position).getMobile());
                Skip.mNextFroData(mActivity,SendMessageActivity.class,bundle);
            }
        });

        //weixin回访
        holder.iv_weixin_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动单聊会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(mContext, mReturnVisitBeanList.get(position).getVipId()+"",mReturnVisitBeanList.get(position).getNickName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReturnVisitBeanList == null ? 0 : mReturnVisitBeanList.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_return_title,tv_return_content,tv_return_data;
        MyImageViewWidget ivw_return_visit_icon;
        ImageView iv_top_arrow,iv_bottom_arrow,iv_tel_return,iv_sms_return,iv_weixin_return;
        RelativeLayout rl_bottom_arrow;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_return_title = (TextView) itemView.findViewById(R.id.tv_return_title);
            tv_return_content= (TextView) itemView.findViewById(R.id.tv_return_content);
            tv_return_data= (TextView) itemView.findViewById(R.id.tv_return_data);
            ivw_return_visit_icon= (MyImageViewWidget) itemView.findViewById(R.id.ivw_return_visit_icon);
            iv_top_arrow= (ImageView) itemView.findViewById(R.id.iv_top_arrow);
            iv_bottom_arrow= (ImageView) itemView.findViewById(R.id.iv_bottom_arrow);
            iv_tel_return= (ImageView) itemView.findViewById(R.id.iv_tel_return);
            iv_sms_return= (ImageView) itemView.findViewById(R.id.iv_sms_return);
            iv_weixin_return= (ImageView) itemView.findViewById(R.id.iv_weixin_return);
            rl_bottom_arrow= (RelativeLayout) itemView.findViewById(R.id.rl_bottom_arrow);


            //展开
            iv_bottom_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rl_bottom_arrow.setVisibility(View.VISIBLE);
                    iv_bottom_arrow.setVisibility(View.GONE);
                }
            });

            //收起
            iv_top_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rl_bottom_arrow.setVisibility(View.GONE);
                    iv_bottom_arrow.setVisibility(View.VISIBLE);
                }
            });

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,String grade,String date,String imgUrl) {
            if(grade!=null){
                this.tv_return_content.setText(grade);
            }else{
                this.tv_return_content.setText("暂无会员等级");
            }

            this.tv_return_title.setText(title);
            this.tv_return_data.setText(date);
            Glide.with(mContext).load(imgUrl).into(this.ivw_return_visit_icon);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    /**
     * Author FGB
     * Description 通话监听Utile
     * Created 2017/4/24 12:02
     * Version 1.zero
     */
    public class PhoneListenUtils extends PhoneStateListener {
        private final Context context;
        //获取本次通话的时间(单位:秒)
        int time = 0;
        //判断是否正在通话
        boolean isCalling;
        //控制循环是否结束
        boolean isFinish;
        private ExecutorService service;
        public PhoneListenUtils(Context context) {
            this.context = context;
            service = Executors.newSingleThreadExecutor();
        }
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    if (isCalling) {
                        isCalling = false;
                        isFinish = true;
                        service.shutdown();
//                        Toast.makeText(context, "本次通话" + time + "秒",
//                                Toast.LENGTH_LONG).show();
                        //调用并保存回访通话时长记录
                        SaveVisitRecordNet recordNet=new SaveVisitRecordNet(mContext);
                        recordNet.setData(Integer.parseInt(VipId),VipName,1,time+"");

                        time = 0;
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    isCalling = true;
                    service.execute(new Runnable() {
                        @Override
                        public void run() {
                            while (!isFinish) {
                                try {
                                    Thread.sleep(1000);
                                    time++;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    isFinish = false;
                    if(service.isShutdown()){
                        service = Executors.newSingleThreadExecutor();
                    }
                    break;
            }
        }
    }

}
