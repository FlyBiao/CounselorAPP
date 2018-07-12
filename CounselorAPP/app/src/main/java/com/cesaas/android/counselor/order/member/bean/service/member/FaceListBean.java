package com.cesaas.android.counselor.order.member.bean.service.member;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/3/23 15:43
 * Version 1.0
 */

public class FaceListBean implements Serializable {
    private String ImageTime;
    private String ImagePath;
    private String ImageUrl;
    private String EquipmentName;

    public String getImageTime() {
        return ImageTime;
    }

    public void setImageTime(String imageTime) {
        ImageTime = imageTime;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        EquipmentName = equipmentName;
    }
}
