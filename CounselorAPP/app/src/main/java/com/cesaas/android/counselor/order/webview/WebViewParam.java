package com.cesaas.android.counselor.order.webview;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/5/11 9:02
 * Version 1.0
 */

public class WebViewParam {

    private List<Integer> RolesId;

    private int Mutil;

    public void setRolesId(List<Integer> RolesId){
        this.RolesId = RolesId;
    }
    public List<Integer> getRolesId(){
        return this.RolesId;
    }
    public void setMutil(int Mutil){
        this.Mutil = Mutil;
    }
    public int getMutil(){
        return this.Mutil;
    }
}
