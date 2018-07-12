package com.cesaas.android.counselor.order.store.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 门店陈列详情结果Bean
 * Created by FGB on 2017/3/1.
 */

public class ResultStoreDisplayDetailBean extends BaseBean{

    public StoreDisplayDetailBean TModel;

    public class StoreDisplayDetailBean{
        public String Content;

        public List<String> Shows;

        public List<Images>  Images;

        public String DoneDescribe;

        public String Comment;

        public String FlowId;

        public String TaskId;
    }


}
