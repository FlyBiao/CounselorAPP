package com.cesaas.android.counselor.order.webview.bean;

/**
 * Author FGB
 * Description 打开文件
 * Created at 2017/5/12 17:57
 * Version 1.0
 */

public class OpenFileBean extends BaseTypeBean{
    private FileBean param;

    public FileBean getParam() {
        return param;
    }

    public void setParam(FileBean param) {
        this.param = param;
    }

    public class FileBean{
        private String type;
        private String url;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
