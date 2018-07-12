package com.cesaas.android.java.bean.move;

import com.cesaas.android.java.bean.ErrorCode;

/**
 * Author FGB
 * Description 调货发货详情
 * Created at 2018/5/26 15:25
 * Version 1.0
 */

public class ResultMoveDeliveryDetailBean extends ErrorCode {

    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean{
        public int errorCode;
        public int totalCount;
        public String errorMessage;
        public String _classname;
        public int countBoxNum;   // 总箱数
        public int countNum;      // 总件数
        public int shipmentsNum;      // 发货总数
        public int num;      // 总收货件总数
        public int differenceNum;      // 差异总数
        public int sendNum;      // 收货箱总发货数
        public int nums;      // 收货箱总收货数

        public MoveDeliveryListBeanBean model;
//        public DetailBean model;
    }
//    public class RecordsBean{
//        public MoveDeliveryListBeanBean model;
//    }
}
