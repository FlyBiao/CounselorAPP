package com.cesaas.android.counselor.order.power.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/1/30 17:51
 * Version 1.0
 */

public class MenuDataBean implements Serializable {
    private String MenuName;
    private String MenuNo;
    private Integer MenuImage;
    private int Status;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMenuNo() {
        return MenuNo;
    }

    public void setMenuNo(String menuNo) {
        MenuNo = menuNo;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public Integer getMenuImage() {
        return MenuImage;
    }

    public void setMenuImage(Integer menuImage) {
        MenuImage = menuImage;
    }
}
