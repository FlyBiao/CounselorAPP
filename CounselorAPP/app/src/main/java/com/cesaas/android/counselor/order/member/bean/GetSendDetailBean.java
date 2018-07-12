package com.cesaas.android.counselor.order.member.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created 2017/4/27 11:30
 * Version 1.0
 */
public class GetSendDetailBean {

    private String Title;
    private String Image;
    public List<Fans> Fans;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public class Fans{
        private int Status;
        private String VipIcon;
        private String VipId;
        private String VipName;

        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
        }

        public String getVipIcon() {
            return VipIcon;
        }

        public void setVipIcon(String vipIcon) {
            VipIcon = vipIcon;
        }

        public String getVipId() {
            return VipId;
        }

        public void setVipId(String vipId) {
            VipId = vipId;
        }

        public String getVipName() {
            return VipName;
        }

        public void setVipName(String vipName) {
            VipName = vipName;
        }
    }
}
