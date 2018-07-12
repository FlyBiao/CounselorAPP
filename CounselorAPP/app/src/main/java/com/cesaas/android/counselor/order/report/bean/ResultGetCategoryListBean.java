package com.cesaas.android.counselor.order.report.bean;

import com.cesaas.android.counselor.order.bean.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author FGB
 * Description
 * Created 2017/3/25 15:06
 * Version 1.zero
 */
public class ResultGetCategoryListBean extends BaseBean{
    public ArrayList<GetCategoryListBean> TModel;

    public  class GetCategoryListBean implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        public int Id;//标签分类编号
        public String Title;//标签分类名称
        public int Type;//标签分类类型
        public int ParentId;//标签分类父级编号
        public int IsUsed;

    }
}
