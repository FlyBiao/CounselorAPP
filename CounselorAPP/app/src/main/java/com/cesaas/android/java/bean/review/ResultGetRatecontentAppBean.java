package com.cesaas.android.java.bean.review;

import com.cesaas.android.java.bean.ErrorCode;
import com.cesaas.android.java.bean.JavaBaseValueBean;

import java.util.List;

/**
 * Author FGB
 * Description 查看导购评价
 * Created at 2018/6/21 9:54
 * Version 1.0
 */

public class ResultGetRatecontentAppBean extends ErrorCode {
    public ArgumentsBean arguments;

    public class ArgumentsBean {
        public RespBean resp;
    }
    public class RespBean extends JavaBaseValueBean {
        private int totalCount;
        private int totalScore;
        private int avgTotalValue;
        public RecordsBean records;
        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(int totalScore) {
            this.totalScore = totalScore;
        }

        public int getAvgTotalValue() {
            return avgTotalValue;
        }

        public void setAvgTotalValue(int avgTotalValue) {
            this.avgTotalValue = avgTotalValue;
        }

    }
    public class RecordsBean{
        public List<GetRatecontentAppBean> value;
    }
}
