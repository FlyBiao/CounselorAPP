package com.cesaas.android.counselor.order.rong.listener;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.ResourceManager;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.load.engine.Resource;
import com.cesaas.android.counselor.order.alioss.OSSKey;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.net.service.SendWxMsgNet;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.GetFileNameUtils;
import com.cesaas.android.counselor.order.utils.basefile.FileUitl;

import java.io.IOException;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Author FGB
 * Description
 * Created at 2018/3/26 18:09
 * Version 1.0
 */

public class OnSendMessageListener implements RongIM.OnSendMessageListener {

    private SendWxMsgNet wxMsgNet;
    private String vipId;
    private Context ct;
    private Activity activity;
    private int msgType=0;
    private int duration;
    private AbPrefsUtil pre;

    private static String ossImageUrl;//
    private static OSS oss;

    public OnSendMessageListener(Context ct,Activity activity,AbPrefsUtil pre){
        this.ct=ct;
        this.activity=activity;
        this.pre=pre;
    }

    /**
     * 消息发送前监听器处理接口（是否发送成功可以从 SentStatus 属性获取）。
     *
     * @param message 发送的消息实例。
     * @return 处理后的消息实例。
     */
    @Override
    public Message onSend(Message message) {
        //开发者根据自己需求自行处理逻辑
        vipId=message.getTargetId();

        try{
            MessageContent messageContent = message.getContent();
            if (messageContent instanceof TextMessage) {//文本消息
                TextMessage textMessage = (TextMessage) messageContent;
                msgType=1;
                wxMsgNet=new SendWxMsgNet(ct);
                wxMsgNet.setData(vipId,1,textMessage.getContent(),"");

            } else if (messageContent instanceof ImageMessage) {//图片消息
                ImageMessage imageMessage = (ImageMessage) messageContent;
                msgType=2;
                wxMsgNet=new SendWxMsgNet(ct);
                wxMsgNet.setData(vipId,2,"",imageMessage.getRemoteUri().toString());

            } else if (messageContent instanceof VoiceMessage) {//语音消息
                VoiceMessage voiceMessage = (VoiceMessage) messageContent;
                msgType=3;
                duration=voiceMessage.getDuration();
                if(voiceMessage.getUri().getPath()!=null){
                    OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSSKey.ACCESS_ID, OSSKey.ACCESS_KEY);
                    oss = new OSSClient(ct, OSSKey.OSS_ENDPOINT, credentialProvider);
                    setUploadAliOss(GetFileNameUtils.getVoiceFileName(voiceMessage.getUri().getPath(),pre),voiceMessage.getUri().getPath());
                }

            } else if (messageContent instanceof RichContentMessage) {//图文消息
                RichContentMessage richContentMessage = (RichContentMessage) messageContent;
                msgType=4;
//                Log.i("test", "onSent-RichContentMessage:" + message.getContent()+"==="+richContentMessage.getContent());
            } else {
                Log.i("test", "onSent-其他消息，自己来判断处理");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return message;
    }

    /**
     * 消息在 UI 展示后执行/自己的消息发出后执行,无论成功或失败。
     *
     * @param message              消息实例。
     * @param sentMessageErrorCode 发送消息失败的状态码，消息发送成功 SentMessageErrorCode 为 null。
     * @return true 表示走自己的处理方式，false 走融云默认处理方式。
     */
    @Override
    public boolean onSent(Message message,RongIM.SentMessageErrorCode sentMessageErrorCode) {

        if(message.getSentStatus()== Message.SentStatus.FAILED){
            if(sentMessageErrorCode== RongIM.SentMessageErrorCode.NOT_IN_CHATROOM){
                //不在聊天室
            }else if(sentMessageErrorCode== RongIM.SentMessageErrorCode.NOT_IN_DISCUSSION){
                //不在讨论组
            }else if(sentMessageErrorCode== RongIM.SentMessageErrorCode.NOT_IN_GROUP){
                //不在群组
            }else if(sentMessageErrorCode== RongIM.SentMessageErrorCode.REJECTED_BY_BLACKLIST){
                //你在他的黑名单中
            }
        }

        return false;
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
                //上传进度
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
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    Log.i("test","onFailure：本地异常如网络异常等:"+clientExcepion);
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.i("test","ErrorCode:"+serviceException.getErrorCode());
                    Log.i("test","RequestId:"+serviceException.getRequestId());
                    Log.i("test","HostId:"+serviceException.getHostId());
                    Log.i("test","RawMessage:"+serviceException.getRawMessage());
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
                ossImageUrl=OSSKey.VOICE_URL+request.getObjectKey();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wxMsgNet=new SendWxMsgNet(ct);
                        wxMsgNet.setData(vipId,3,ossImageUrl,duration);
                    }
                });
            }
        });
    }

}
