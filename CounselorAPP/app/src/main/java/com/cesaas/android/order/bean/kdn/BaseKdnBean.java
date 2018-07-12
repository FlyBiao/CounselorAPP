package com.cesaas.android.order.bean.kdn;

/**
 * Author FGB
 * Description
 * Created at 2018/4/20 13:53
 * Version 1.0
 */

public class BaseKdnBean {
    private String EBusinessID;//商家Id
    private String Reason;//失败原因
    private String ResultCode;//结果码
    private boolean Success;//是否成功
    private String UniquerRequestNumber;

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getUniquerRequestNumber() {
        return UniquerRequestNumber;
    }

    public void setUniquerRequestNumber(String uniquerRequestNumber) {
        UniquerRequestNumber = uniquerRequestNumber;
    }
}
