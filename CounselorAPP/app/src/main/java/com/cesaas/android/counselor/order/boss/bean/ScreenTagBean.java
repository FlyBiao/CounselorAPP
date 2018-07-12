package com.cesaas.android.counselor.order.boss.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 筛选标签Bean
 * Created at 2018/1/22 14:46
 * Version 1.0
 */

public class ScreenTagBean implements Serializable {
    private int id;
    private int type;//标签类型 1年份，2季节，3大类小类，4波段,5商品品类
    private String Name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
