package com.cesaas.android.counselor.order.custom.chartview;

/**
 * Author FGB
 * Description
 * Created at 2017/5/7 20:33
 * Version 1.0
 */

public enum ChartStyle {
    /**
     * 扇形
     */
    FANSHAPE(0),
    /**
     * 环形
     */
    ANNULAR(1);
    private int style;
    private ChartStyle(int style){
        this.style=style;
    }
    public int getStyle() {
        return style;
    }
    public void setStyle(int style) {
        this.style = style;
    }

}
