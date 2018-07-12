package com.cesaas.android.counselor.order.webview.bean;

/**
 * Author FGB
 * Description 分享商品
 * Created at 2017/5/14 10:51
 * Version 1.0
 */

public class ShareProductBean extends BaseTypeBean{

    private ProductBean param;

    public ProductBean getParam() {
        return param;
    }

    public void setParam(ProductBean param) {
        this.param = param;
    }

    public class ProductBean {

        private String title;
        private String image_url;
        private String share_url;
        private String description;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
