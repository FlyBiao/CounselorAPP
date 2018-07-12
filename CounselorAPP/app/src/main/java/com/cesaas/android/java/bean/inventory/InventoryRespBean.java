package com.cesaas.android.java.bean.inventory;

import com.cesaas.android.counselor.order.bean.BaseBean;

/**
 * Author FGB
 * Description
 * Created at 2018/5/25 9:50
 * Version 1.0
 */

public class InventoryRespBean extends BaseBean {
    public InventoryRecordsBean records;
    public Summary summary;

    public class Summary{

        private String _classname;
        private int diffCount;
        private int inentoryCount;
        private int inentoryTotal;

        public void set_classname(String _classname) {
            this._classname = _classname;
        }

        public void setDiffCount(int diffCount) {
            this.diffCount = diffCount;
        }

        public void setInentoryCount(int inentoryCount) {
            this.inentoryCount = inentoryCount;
        }

        public void setInentoryTotal(int inentoryTotal) {
            this.inentoryTotal = inentoryTotal;
        }

        public String get_classname() {
            return _classname;
        }

        public int getDiffCount() {
            return diffCount;
        }

        public int getInentoryCount() {
            return inentoryCount;
        }

        public int getInentoryTotal() {
            return inentoryTotal;
        }
    }
}
