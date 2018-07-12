package com.cesaas.android.counselor.order.workbench.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created 2017/4/15 11:02
 * Version 1.zero
 */
public class ResultReturnVisitDetailBean extends BaseBean {
    public ReturnVisitDetailBean TModel;


    public class ReturnVisitDetailBean implements Serializable{

        private String TalkMessage;
        private int RuleId;
        private int VipId;
        private String Image;
        private String NickName;
        private String Time;
        private String Grade;

        public String getGrade() {
            return Grade;
        }

        public void setGrade(String grade) {
            Grade = grade;
        }

        public String getTalkMessage() {
            return TalkMessage;
        }

        public void setTalkMessage(String talkMessage) {
            TalkMessage = talkMessage;
        }

        public int getRuleId() {
            return RuleId;
        }

        public void setRuleId(int ruleId) {
            RuleId = ruleId;
        }

        public int getVipId() {
            return VipId;
        }

        public void setVipId(int vipId) {
            VipId = vipId;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String image) {
            Image = image;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String nickName) {
            NickName = nickName;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }
    }
}
