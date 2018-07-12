
package com.cesaas.android.counselor.order.webview.bean;

import java.util.List;

/**
 * Author FGB
 * Description 图片预览
 * Created at 2017/5/12 17:51
 * Version 1.0
 */

public class PreviewPictureBean extends BaseTypeBean {

    private PictureBean param;

    public PictureBean getParam() {
        return param;
    }

    public void setParam(PictureBean param) {
        this.param = param;
    }

    public class PictureBean{

        private List<String> image_url;//['图片地址1','图片地址2']
        private String show_index;//开始显示的图片

        public List<String> getImage_url() {
            return image_url;
        }

        public void setImage_url(List<String> image_url) {
            this.image_url = image_url;
        }

        public String getShow_index() {
            return show_index;
        }

        public void setShow_index(String show_index) {
            this.show_index = show_index;
        }
    }

}
