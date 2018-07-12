package com.cesaas.android.counselor.order.store.bean;

import java.io.Serializable;

/**
 * 涂鸦图片Bean
 * Created by cebsaas on 2017/2/21.
 */

public class GraffitiImagePathBean implements Serializable{

    private String imagePath;//图片路劲

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
